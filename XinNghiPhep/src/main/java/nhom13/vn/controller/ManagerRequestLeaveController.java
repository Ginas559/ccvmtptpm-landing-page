package nhom13.vn.controller;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom13.vn.entity.LeaveRequest;
import nhom13.vn.entity.User;
import nhom13.vn.service.ILeaveRequestService;
import nhom13.vn.service.impl.LeaveRequestServiceImpl;
@WebServlet("/manager/request")
public class ManagerRequestLeaveController extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/view/manager/request.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("account");

        String reason = req.getParameter("reason");

        // gọi service tạo đơn nghỉ (giống employee)
        // leaveService.createLeave(user.getId(), reason);

        resp.sendRedirect(req.getContextPath() + "/manager/dashboard");
    }
}