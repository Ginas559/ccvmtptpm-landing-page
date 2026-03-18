package nhom13.vn.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom13.vn.entity.LeaveRequest;
import nhom13.vn.entity.User;
import nhom13.vn.factory.LeaveRequestFactory;
import nhom13.vn.service.ILeaveRequestService;
import nhom13.vn.service.impl.LeaveRequestServiceImpl;

import java.io.IOException;
import java.sql.Date;
@WebServlet({
    "/leave/create",
    "/leave/insert"
})
public class LeaveRequestController extends HttpServlet {

    ILeaveRequestService service = new LeaveRequestServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/view/leave/create.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String start = req.getParameter("startDate");
        String end = req.getParameter("endDate");
        String reason = req.getParameter("reason");

        // validation
        if (start == null || end == null || reason == null ||
            start.isEmpty() || end.isEmpty() || reason.isEmpty()) {

            req.setAttribute("alert", "Vui lòng nhập đầy đủ!");
            req.getRequestDispatcher("/view/leave/create.jsp").forward(req, resp);
            return;
        }

        User user = (User) req.getSession().getAttribute("account");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            // 🔥 DÙNG FACTORY
            LeaveRequest lr = LeaveRequestFactory.create(user, start, end, reason);

            service.create(lr);

            HttpSession session = req.getSession();

            session.setAttribute("message", "Tạo đơn nghỉ thành công!");

            String role = user.getRole();

            if ("MANAGER".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/manager/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/employee/dashboard");
            }

        } catch (Exception e) {
            req.setAttribute("alert", "Lỗi dữ liệu!");
            req.getRequestDispatcher("/view/leave/create.jsp").forward(req, resp);
        }
    }
}