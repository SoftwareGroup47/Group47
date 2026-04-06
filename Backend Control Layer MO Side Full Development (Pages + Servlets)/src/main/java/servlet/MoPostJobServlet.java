import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.example.model.Job;
import org.example.util.FileDBHelper;

@WebServlet(name = "MoPostJobServlet", urlPatterns = "/MoPostJobServlet")
public class MoPostJobServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // 获取表单数据
        String jobName = request.getParameter("jobName");
        String requirements = request.getParameter("requirements");
        String publisher = request.getParameter("publisher");
        
        // 验证数据
        if (jobName == null || jobName.trim().isEmpty() ||
            requirements == null || requirements.trim().isEmpty() ||
            publisher == null || publisher.trim().isEmpty()) {
            request.setAttribute("error", "请填写所有必填字段");
            request.getRequestDispatcher("/mo/post_job.jsp").forward(request, response);
            return;
        }
        
        try {
            // 创建新岗位
            Job newJob = new Job(jobName, requirements, publisher);
            
            // 保存数据
            FileDBHelper.addJob(newJob);
            
            // 显示成功消息
            request.setAttribute("message", "岗位发布成功！");
            request.getRequestDispatcher("/mo/post_job.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "发布失败：" + e.getMessage());
            request.getRequestDispatcher("/mo/post_job.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 重定向到发布岗位页面
        response.sendRedirect("/mo/post_job.jsp");
    }
}