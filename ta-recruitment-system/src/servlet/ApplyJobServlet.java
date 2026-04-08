package servlet;

import model.Job;
import model.User;
import util.FileDBHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ta/applyJob")
public class ApplyJobServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 登录校验
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("/login.jsp");
            return;
        }

        // 2. 获取要申请的岗位ID
        String jobId = request.getParameter("jobId");

        // 3. 读取岗位，标记已申请
        List<Job> jobList = FileDBHelper.readJobs();
        boolean applySuccess = false;
        for (Job job : jobList) {
            if (job.getId().equals(jobId)) {
                job.setApplicant(loginUser.getStudentId()); // 记录申请人学号
                applySuccess = true;
                break;
            }
        }

        if (applySuccess) {
            FileDBHelper.saveJobs(jobList);
            response.getWriter().write("岗位申请成功");
        } else {
            response.getWriter().write("岗位不存在");
        }
    }
}