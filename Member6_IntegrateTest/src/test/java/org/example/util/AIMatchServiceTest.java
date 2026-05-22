package org.example.util;

import org.example.model.Job;
import org.example.model.TAProfile;
import org.junit.jupiter.api.*;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for the AI-powered skill matching service.
 * Tests all three matching dimensions: exact, fuzzy/synonym, and partial matching.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AIMatchServiceTest {

    @BeforeAll
    static void setup() {
        System.out.println("=== AI Match Service Tests ===");
    }

    // ── Exact Matching Tests ──

    @Test @Order(1)
    @DisplayName("Exact match: 100% when all skills identically match")
    void testExactMatchAllIdentical() {
        Job job = new Job("Java TA", "java, python, sql", "mo1");
        TAProfile profile = new TAProfile("ta1", "java, python, sql", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertEquals(100.0, r.score, 0.01, "Full exact match should score 100%");
        assertEquals(3, r.exactMatches);
        assertEquals(0, r.fuzzyMatches);
        assertEquals(0, r.partialMatches);
        assertEquals(0, r.missingCount);
    }

    @Test @Order(2)
    @DisplayName("Exact match: 33% when 1 of 3 skills match")
    void testExactMatchPartial() {
        Job job = new Job("ML TA", "machine learning, python, sql", "mo1");
        TAProfile profile = new TAProfile("ta1", "python", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertTrue(r.score < 50.0, "Only 1 of 3 matched: score should be below 50%");
        assertTrue(r.exactMatches == 1);
        assertEquals(2, r.missingCount);
    }

    @Test @Order(3)
    @DisplayName("Exact match: 0% when no skills match")
    void testExactMatchNone() {
        Job job = new Job("Java TA", "java, spring", "mo1");
        TAProfile profile = new TAProfile("ta1", "painting, cooking", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertEquals(0.0, r.score, 0.01);
        assertEquals(0, r.matchedCount);
        assertEquals(2, r.missingCount);
    }

    // ── Fuzzy / Synonym Matching Tests ──

    @Test @Order(4)
    @DisplayName("Fuzzy match: 'machine learning' matches 'ML' via synonym map")
    void testFuzzyMatchSynonym() {
        Job job = new Job("ML TA", "machine learning", "mo1");
        TAProfile profile = new TAProfile("ta1", "ML", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertTrue(r.fuzzyMatches >= 1, "ML should fuzzy-match machine learning");
        assertTrue(r.score > 50.0, "Synonym match should give reasonable score");
    }

    @Test @Order(5)
    @DisplayName("Fuzzy match: 'JavaScript' matches 'JS' and 'Node.js'")
    void testFuzzyMatchProgrammingLang() {
        Job job = new Job("Web TA", "JavaScript, HTML", "mo1");
        TAProfile profile = new TAProfile("ta1", "JS, HTML", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertTrue(r.matchedCount >= 2, "JS should match JavaScript, HTML should exact match");
    }

    @Test @Order(6)
    @DisplayName("Fuzzy match: 'teaching' matches 'tutoring' and 'mentoring'")
    void testFuzzyMatchSoftSkills() {
        Job job = new Job("Tutor", "teaching, communication", "mo1");
        TAProfile profile = new TAProfile("ta1", "tutoring, English", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertTrue(r.fuzzyMatches >= 1, "tutoring should fuzzy-match teaching");
    }

    @Test @Order(7)
    @DisplayName("Fuzzy match: 'database' synonym group matches 'MySQL'")
    void testFuzzyMatchDatabase() {
        Job job = new Job("DB TA", "database, SQL", "mo1");
        TAProfile profile = new TAProfile("ta1", "MySQL, PostgreSQL", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertTrue(r.matchedCount >= 1, "MySQL should match database or SQL via synonyms");
    }

    // ── Partial / Containment Matching Tests ──

    @Test @Order(8)
    @DisplayName("Partial match: longer skill contains shorter required skill")
    void testPartialMatchContainment() {
        Job job = new Job("Dev TA", "coding", "mo1");
        TAProfile profile = new TAProfile("ta1", "Python coding expert", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertTrue(r.partialMatches >= 1 || r.matchedCount >= 1,
                "Containment matching should detect 'coding' within 'Python coding expert'");
    }

    // ── Edge Cases ──

    @Test @Order(9)
    @DisplayName("Edge case: empty requirements → 100% score")
    void testEmptyRequirements() {
        Job job = new Job("Helper", "", "mo1");
        TAProfile profile = new TAProfile("ta1", "anything", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertEquals(100.0, r.score, 0.01, "Empty requirements → no mismatch possible");
    }

    @Test @Order(10)
    @DisplayName("Edge case: case insensitivity")
    void testCaseInsensitive() {
        Job job = new Job("Java TA", "JAVA, PYTHeaN", "mo1");
        TAProfile profile = new TAProfile("ta1", "java, Python", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertEquals(2, r.exactMatches, "Case should not affect matching");
        assertTrue(r.score > 90.0);
    }

    @Test @Order(11)
    @DisplayName("Edge case: null or empty skills")
    void testNullSkills() {
        Job job = new Job("Java TA", "java", "mo1");
        TAProfile profile = new TAProfile("ta1", null, "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertEquals(0.0, r.score, 0.01);
        assertEquals(1, r.missingCount);
    }

    // ── Workload Tests ──

    @Test @Order(12)
    @DisplayName("Workload advice: normal workload")
    void testWorkloadAdviceNormal() {
        String advice = AIMatchService.getWorkloadAdvice(1, 0);
        assertTrue(advice.contains("normal"), "Low workload should produce normal advice");
    }

    @Test @Order(13)
    @DisplayName("Workload advice: high workload warning")
    void testWorkloadAdviceHigh() {
        String advice = AIMatchService.getWorkloadAdvice(5, 2);
        assertTrue(advice.toLowerCase().contains("multiple accepted") ||
                   advice.toLowerCase().contains("high"),
                   "High workload should trigger a warning");
    }

    @Test @Order(14)
    @DisplayName("Match suggestion: high score (>80%)")
    void testMatchSuggestionHigh() {
        String suggestion = AIMatchService.getMatchSuggestion(85.0);
        assertTrue(suggestion.toLowerCase().contains("recommended"),
                   "High score should include 'recommended'");
    }

    @Test @Order(15)
    @DisplayName("Match suggestion: low score (<40%)")
    void testMatchSuggestionLow() {
        String suggestion = AIMatchService.getMatchSuggestion(25.0);
        assertTrue(suggestion.toLowerCase().contains("low") ||
                   suggestion.toLowerCase().contains("other"),
                   "Low score should suggest other options");
    }

    // ── Comprehensive Multi-Dimensional Test ──

    @Test @Order(16)
    @DisplayName("Comprehensive: exact + fuzzy + partial all contribute to score")
    void testMixedMatchingDimensions() {
        Job job = new Job("Full-Stack TA", 
                "Java, machine learning, git, database, communication", "mo1");
        TAProfile profile = new TAProfile("ta1", 
                "Java, ML, github, SQL, public speaking", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        
        System.out.println("Score: " + r.score + "%");
        System.out.println("Exact: " + r.exactMatches + 
                         " Fuzzy: " + r.fuzzyMatches + 
                         " Partial: " + r.partialMatches);
        System.out.println("Missing: " + String.join(", ", r.missingSkills));

        // Java should exact-match Java
        // ML should fuzzy-match machine learning
        // github should fuzzy-match git
        // SQL should fuzzy-match database
        // public speaking should fuzzy-match communication
        assertTrue(r.score >= 60.0, "Score should be at least 60% with combined matching");
        assertTrue(r.matchedCount >= 3, "At least 3 skills should match via combined methods");
    }

    @Test @Order(17)
    @DisplayName("Star rating: score-to-star conversion")
    void testScoreStars() {
        Job job = new Job("Test", "skill", "mo");
        TAProfile profile = new TAProfile("ta1", "skill", "A", "");
        AIMatchService.MatchResult r = AIMatchService.calculateMatchScore(job, profile);
        assertEquals("5", r.getScoreStars(), "100% match should give 5 stars");
    }
}
