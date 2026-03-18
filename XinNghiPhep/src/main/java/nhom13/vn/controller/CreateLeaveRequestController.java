package nhom13.vn.controller;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom13.vn.entity.LeaveRequest;
import nhom13.vn.entity.User;
import nhom13.vn.service.ILeaveRequestService;
import nhom13.vn.service.impl.LeaveRequestServiceImpl;

import java.sql.Date;
@WebServlet({
    "/employee/leave/create",
    "/employee/leave/insert"
})
public class CreateLeaveRequestController extends HttpServlet {

    ILeaveRequestService service = new LeaveRequestServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/view/employee/leave/create.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 🔥 FIX LỖI TIẾNG VIỆT
        req.setCharacterEncoding("UTF-8");

        String start = req.getParameter("startDate");
        String end = req.getParameter("endDate");
        String reason = req.getParameter("reason");

        // 🔥 VALIDATION RỖNG
        if (start == null || end == null || reason == null ||
            start.isEmpty() || end.isEmpty() || reason.isEmpty()) {

            req.setAttribute("alert", "Vui lòng nhập đầy đủ thông tin!");
            req.getRequestDispatcher("/view/employee/leave/create.jsp")
                    .forward(req, resp);
            return;
        }

        // 🔥 LẤY USER TỪ SESSION
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            // 🔥 PARSE DATE
            Date startDate = Date.valueOf(start);
            Date endDate = Date.valueOf(end);

            // 🔥 VALIDATION NGÀY (QUAN TRỌNG)
            if (startDate.after(endDate)) {
                req.setAttribute("alert", "Ngày bắt đầu phải trước ngày kết thúc!");
                req.getRequestDispatcher("/view/employee/leave/create.jsp")
                        .forward(req, resp);
                return;
            }

            // 🔥 TẠO OBJECT
            LeaveRequest lr = new LeaveRequest();
            lr.setUser(user);
            lr.setStartDate(startDate);
            lr.setEndDate(endDate);
            lr.setReason(reason);
            lr.setStatus("PENDING");

            // 🔥 GỌI SERVICE
            service.create(lr);

            // 🔥 REDIRECT (TRÁNH DUPLICATE)
            resp.sendRedirect(req.getContextPath() + "/employee/dashboard");

        } catch (Exception e) {
            // 🔥 BẮT LỖI DATE FORMAT (rất hay xảy ra)
            req.setAttribute("alert", "Ngày không hợp lệ!");
            req.getRequestDispatcher("/view/employee/leave/create.jsp")
                    .forward(req, resp);
        }
    }
}