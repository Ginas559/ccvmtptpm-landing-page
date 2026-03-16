package nhom13.vn.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Cấu hình các URL cần được bảo vệ [4, 5]
@WebFilter(urlPatterns = {"/admin/*", "/home", "/manager/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter (nếu cần) [3, 6]
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Bước 1: Ép kiểu để làm việc với giao thức HTTP [7, 8]
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        // Bước 2: Kiểm tra đối tượng account trong Session [8]
        HttpSession session = req.getSession(false); 
        Object userObj = (session != null) ? session.getAttribute("account") : null;

        // Bước 3: Xử lý logic chặn hoặc cho đi tiếp [8]
        if (userObj == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang login [8]
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            // Nếu đã đăng nhập, cho phép yêu cầu đi tiếp tới Servlet mục tiêu [8, 9]
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Giải phóng tài nguyên khi filter kết thúc [3, 10]
    }
}