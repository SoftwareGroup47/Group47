package org.example.servlet;

import org.example.model.*;
import org.example.util.AIMatchService;
import org.example.util.FileDBHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Admin dashboard for viewing all TA workload information.
 * <p>
 * Provides a consolidated view of every TA's workload stats:
 * total applications, accepted positions, pending applications,
 * workload advice, and skill match summaries.
 */
public class AdminWorkloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        // Role check: MOs can also view the admin-like workload overview
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (role == null || (!"MO".equals(role) && !"TA".equals(role))) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Unauthorized access'); window.location.href='login.jsp';</script>");
            return;
        }

        // Collect workload data for all TAs
        List<TAWorkloadInfo> workloadList = new ArrayList<>();
        List<String> taUsernames = AIMatchService.getAllTAUsernames();

        for (String taUser : taUsernames) {
            int workload = AIMatchService.getTAWorkload(taUser);
            int accepted = AIMatchService.getAcceptedJobCount(taUser);
            int pending = AIMatchService.getPendingJobCount(taUser);
            String advice = AIMatchService.getWorkloadAdvice(workload, accepted);
            TAProfile profile = FileDBHelper.getTAProfile(taUser);

            TAWorkloadInfo info = new TAWorkloadInfo();
            info.username = taUser;
            info.workload = workload;
            info.accepted = accepted;
            info.pending = pending;
            info.advice = advice;
            info.hasProfile = profile != null;
            info.skills = profile != null ? profile.getSkills() : "N/A";
            workloadList.add(info);
        }

        request.setAttribute("workloadList", workloadList);
        request.getRequestDispatcher("admin_workload.jsp").forward(request, response);
    }

    /**
     * Data holder for TA workload information used in the admin dashboard.
     */
    public static class TAWorkloadInfo {
        public String username;
        public int workload;
        public int accepted;
        public int pending;
        public String advice;
        public boolean hasProfile;
        public String skills;
    }
}
