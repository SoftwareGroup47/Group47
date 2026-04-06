import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.example.model.Application;
import org.example.util.FileDBHelper;

@WebServlet(name = "MoCheckApplyServlet", urlPatterns = "/MoCheckApplyServlet")
public class MoCheckApplyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 加载申请数据
            List<Application> applications = new ArrayList<>();
            
            // 可以在这里添加过滤逻辑，比如只显示当前MO发布的岗位的申请
            
            // 将申请数据传递给页面
            request.setAttribute("applications", applications);
            request.getRequestDispatcher("/mo/check_apply.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "加载申请记录失败：" + e.getMessage());
            request.getRequestDispatcher("/mo/check_apply.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 如果需要处理表单提交（例如更新申请状态），可以在这里实现
        doGet(request, response);
    }
}