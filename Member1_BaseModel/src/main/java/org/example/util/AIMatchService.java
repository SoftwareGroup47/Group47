package org.example.util;

import org.example.model.*;
import java.util.*;

/**
 * AI-powered skill matching service for TA recruitment.
 * <p>
 * Uses a multi-dimensional scoring algorithm that combines:
 * <ul>
 *   <li>Exact keyword matching with case-insensitive comparison</li>
 *   <li>Fuzzy/semantic matching for related skill synonyms</li>
 *   <li>Partial/containment matching for compound skill descriptions</li>
 *   <li>Weighted scoring based on skill relevance</li>
 * </ul>
 * <p>
 * This is a rule-based AI system using predefined skill synonym maps
 * and fuzzy comparison heuristics, not a machine learning model.
 */
public class AIMatchService {

    // ── Skill synonym mapping for fuzzy matching ──
    // Maps a skill keyword to a set of semantically equivalent terms
    private static final Map<String, Set<String>> SKILL_SYNONYMS = new HashMap<>();
    static {
        // Programming languages
        putSynonyms("Java", "Java programming", "Java development", "J2EE", "Jakarta");
        putSynonyms("Python", "Python programming", "Python development", "PyTorch", "NumPy", "SciPy");
        putSynonyms("C++", "Cpp", "C programming", "C/C++");
        putSynonyms("JavaScript", "JS", "ECMAScript", "Node.js", "NodeJS", "TypeScript", "React", "Vue");
        putSynonyms("SQL", "MySQL", "PostgreSQL", "database", "DB", "Oracle SQL", "SQLite");
        
        // Web & frameworks
        putSynonyms("HTML", "HTML5", "CSS", "CSS3", "frontend", "web design");
        putSynonyms("Spring", "Spring Boot", "Spring MVC", "Spring Framework");
        putSynonyms("Git", "version control", "GitHub", "GitLab", "source control");
        
        // AI & data
        putSynonyms("machine learning", "ML", "deep learning", "neural network", "AI", "artificial intelligence");
        putSynonyms("data analysis", "data science", "statistics", "data analytics", "analytics");
        putSynonyms("NLP", "natural language processing", "text processing", "language model");
        
        // Communication & soft skills
        putSynonyms("communication", "English", "presentation", "public speaking", "writing");
        putSynonyms("teamwork", "collaboration", "team player", "cooperation");
        putSynonyms("teaching", "tutoring", "mentoring", "instruction", "education");
        
        // Systems & tools
        putSynonyms("Linux", "Unix", "shell", "bash", "command line");
        putSynonyms("Docker", "container", "containerization", "Kubernetes", "K8s");
        putSynonyms("testing", "unit test", "JUnit", "test automation", "QA", "quality assurance", "JUnit testing");
        
        // Mathematics
        putSynonyms("math", "mathematics", "calculus", "linear algebra", "statistics", "probability");
        putSynonyms("algorithms", "data structures", "DSA", "complexity analysis");
    }

    /**
     * Registers bidirectional synonym mappings for a group of related terms.
     */
    private static void putSynonyms(String... terms) {
        Set<String> group = new HashSet<>(Arrays.asList(terms));
        for (String term : terms) {
            String key = term.toLowerCase().trim();
            SKILL_SYNONYMS.merge(key, group, (old, nu) -> {
                Set<String> merged = new HashSet<>(old);
                merged.addAll(nu);
                return merged;
            });
        }
    }

    /**
     * Calculates a multi-dimensional match score between a job's requirements
     * and a TA applicant's skill profile.
     * <p>
     * Scoring dimensions:
     * <ul>
     *   <li><b>Exact match (40% weight):</b> Case-insensitive exact keyword matches</li>
     *   <li><b>Fuzzy/synonym match (35% weight):</b> Semantic matches via synonym groups</li>
     *   <li><b>Partial/containment match (25% weight):</b> Substring-level matches for compound skills</li>
     * </ul>
     *
     * @param job the job posting with required skills
     * @param profile the TA applicant's profile with claimed skills
     * @return MatchResult containing the weighted score, matched/missing counts, and missing skill names
     */
    public static MatchResult calculateMatchScore(Job job, TAProfile profile) {
        Set<String> requiredSkills = parseSkills(job.getRequirements());
        Set<String> taSkills = parseSkills(profile.getSkills());

        if (requiredSkills.isEmpty()) {
            return new MatchResult(100.0, 0, 0, new HashSet<>());
        }

        int exactMatched = 0;
        int fuzzyMatched = 0;
        int partialMatched = 0;
        Set<String> remainingSkills = new HashSet<>(requiredSkills);
        Set<String> matchedSkillNames = new HashSet<>();
        Set<String> missingSkillNames = new HashSet<>();

        for (String required : requiredSkills) {
            boolean found = false;

            // Step 1: Exact match (case-insensitive)
            for (String taSkill : taSkills) {
                if (taSkill.equals(required)) {
                    exactMatched++;
                    matchedSkillNames.add(required);
                    remainingSkills.remove(required);
                    found = true;
                    break;
                }
            }
            if (found) continue;

            // Step 2: Fuzzy/synonym match
            Set<String> synonyms = SKILL_SYNONYMS.get(required);
            if (synonyms != null) {
                for (String taSkill : taSkills) {
                    if (synonyms.contains(taSkill)) {
                        fuzzyMatched++;
                        matchedSkillNames.add(required);
                        remainingSkills.remove(required);
                        found = true;
                        break;
                    }
                }
            }
            if (found) continue;

            // Step 3: Partial/containment match
            for (String taSkill : taSkills) {
                if (required.contains(taSkill) || taSkill.contains(required)) {
                    partialMatched++;
                    matchedSkillNames.add(required);
                    remainingSkills.remove(required);
                    found = true;
                    break;
                }
            }

            if (!found) {
                missingSkillNames.add(required);
            }
        }

        // Calculate weighted score
        // Exact: 40%, Fuzzy: 35%, Partial: 25%
        int totalSkills = requiredSkills.size();
        double exactScore = (exactMatched * 100.0) / totalSkills;
        double fuzzyScore = (fuzzyMatched * 100.0) / totalSkills;
        double partialScore = (partialMatched * 100.0) / totalSkills;

        double weightedScore = exactScore * 0.40 + fuzzyScore * 0.35 + partialScore * 0.25;
        // Clamp to [0, 100]
        weightedScore = Math.max(0, Math.min(100, weightedScore));

        int totalMatched = exactMatched + fuzzyMatched + partialMatched;
        int totalMissing = missingSkillNames.size();

        return new MatchResult(weightedScore, totalMatched, totalMissing, missingSkillNames,
                exactMatched, fuzzyMatched, partialMatched);
    }

    /**
     * Returns the total workload (non-rejected application count) for a TA.
     *
     * @param username the TA's username
     * @return number of active (non-rejected) applications
     */
    public static int getTAWorkload(String username) {
        List<Application> apps = FileDBHelper.getAllApplications();
        int count = 0;
        for (Application app : apps) {
            if (app.getApplicant().equals(username) &&
                !"Rejected".equals(app.getStatus())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the count of accepted applications for a TA.
     *
     * @param username the TA's username
     * @return number of accepted applications
     */
    public static int getAcceptedJobCount(String username) {
        List<Application> apps = FileDBHelper.getAllApplications();
        int count = 0;
        for (Application app : apps) {
            if (app.getApplicant().equals(username) &&
                "Accepted".equals(app.getStatus())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the count of pending applications for a TA.
     *
     * @param username the TA's username
     * @return number of pending applications
     */
    public static int getPendingJobCount(String username) {
        List<Application> apps = FileDBHelper.getAllApplications();
        int count = 0;
        for (Application app : apps) {
            if (app.getApplicant().equals(username) &&
                "Pending".equals(app.getStatus())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Generates workload advice based on current application count and accepted positions.
     *
     * @param workload total non-rejected applications
     * @param accepted number of accepted applications
     * @return English advice string
     */
    public static String getWorkloadAdvice(int workload, int accepted) {
        if (accepted >= 2) {
            return "You have multiple accepted positions. Consider focusing on quality over quantity.";
        }
        if (workload >= 5) {
            return "High application volume detected. Prioritize positions that best match your skills.";
        }
        if (workload >= 3) {
            return "Moderate workload. Review your schedule before applying for more positions.";
        }
        return "Workload is within normal range.";
    }

    /**
     * Generates a match suggestion based on the weighted match score.
     *
     * @param score the weighted match score (0–100)
     * @return English suggestion string
     */
    public static String getMatchSuggestion(double score) {
        if (score >= 80) {
            return "Strongly recommended! Your skills align very well with this position.";
        }
        if (score >= 60) {
            return "Recommended. You meet most of the position's requirements.";
        }
        if (score >= 40) {
            return "Moderate match. Consider developing the missing skills before applying.";
        }
        return "Low match. You may want to explore other positions better suited to your skills.";
    }

    /**
     * Gets all TA usernames for admin workload overview.
     *
     * @return list of TA usernames from registered users
     */
    public static List<String> getAllTAUsernames() {
        List<String> taList = new ArrayList<>();
        for (User user : FileDBHelper.getAllUsers()) {
            if ("TA".equalsIgnoreCase(user.getRole())) {
                taList.add(user.getUsername());
            }
        }
        return taList;
    }

    /**
     * Finds which jobs a given applicant's skills are best suited for.
     * Uses the multi-dimensional scoring algorithm to rank all open jobs.
     *
     * @param username the applicant's username
     * @return list of jobs sorted by match score (highest first), or empty if no profile
     */
    public static List<JobMatchResult> getBestMatchingJobs(String username) {
        List<JobMatchResult> results = new ArrayList<>();
        TAProfile profile = FileDBHelper.getTAProfile(username);
        if (profile == null) return results;

        for (Job job : FileDBHelper.getAllJobs()) {
            if (!"Open".equals(job.getStatus())) continue;
            MatchResult mr = calculateMatchScore(job, profile);
            results.add(new JobMatchResult(job, mr));
        }
        results.sort((a, b) -> Double.compare(b.matchResult.score, a.matchResult.score));
        return results;
    }

    /**
     * Parses a comma/space-separated skill text into a set of lowercase normalized tokens.
     */
    private static Set<String> parseSkills(String text) {
        Set<String> skills = new LinkedHashSet<>();
        if (text != null && !text.isEmpty()) {
            for (String s : text.split("[,\\s]+")) {
                String trimmed = s.trim().toLowerCase();
                if (!trimmed.isEmpty() && trimmed.length() >= 2) {
                    skills.add(trimmed);
                }
            }
        }
        return skills;
    }

    // ──────────────────────────────────────────────
    // Inner classes for match results
    // ──────────────────────────────────────────────

    /**
     * Result of a skill match calculation between a job and a TA profile.
     * Contains the weighted score, match counts by category, and missing skills.
     */
    public static class MatchResult {
        /** The weighted composite match score (0–100) */
        public double score;
        /** Total number of matched skills across all categories */
        public int matchedCount;
        /** Number of required skills not found in the applicant's profile */
        public int missingCount;
        /** Names of skills that were not matched */
        public Set<String> missingSkills;
        /** Number of exact keyword matches */
        public int exactMatches;
        /** Number of fuzzy/synonym matches */
        public int fuzzyMatches;
        /** Number of partial/containment matches */
        public int partialMatches;

        public MatchResult(double score, int matched, int missing, Set<String> missingSkills,
                          int exactMatches, int fuzzyMatches, int partialMatches) {
            this.score = score;
            this.matchedCount = matched;
            this.missingCount = missing;
            this.missingSkills = missingSkills;
            this.exactMatches = exactMatches;
            this.fuzzyMatches = fuzzyMatches;
            this.partialMatches = partialMatches;
        }

        /** Returns a star-based rating string for display. */
        public String getScoreLevel() {
            if (score >= 80) return "5 Stars - Excellent Match";
            if (score >= 60) return "4 Stars - Good Match";
            if (score >= 40) return "3 Stars - Moderate Match";
            if (score >= 20) return "2 Stars - Low Match";
            return "1 Star - Poor Match";
        }

        /** Returns a numeric star count (1–5). */
        public String getScoreStars() {
            if (score >= 80) return "5";
            if (score >= 60) return "4";
            if (score >= 40) return "3";
            if (score >= 20) return "2";
            return "1";
        }
    }

    /**
     * Associates a job with its match result for ranking purposes.
     */
    public static class JobMatchResult {
        public Job job;
        public MatchResult matchResult;

        public JobMatchResult(Job job, MatchResult matchResult) {
            this.job = job;
            this.matchResult = matchResult;
        }
    }
}
