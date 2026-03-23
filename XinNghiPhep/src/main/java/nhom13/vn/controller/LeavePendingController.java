package nhom13.vn.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nhom13.vn.entity.LeaveRequest;
import nhom13.vn.entity.User;
import nhom13.vn.service.ILeaveRequestService;
import nhom13.vn.service.impl.LeaveRequestServiceImpl;

@WebServlet("/leave/pending")
public class LeavePendingController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ILeaveRequestService service = LeaveRequestServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("account");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<LeaveRequest> list = null;
        String role = user.getRole();

        if ("EMPLOYEE".equals(role)) {
            list = service.getPendingByUser(user.getId());
        } else if ("MANAGER".equals(role)) {
            list = service.getPendingEmployees();
        } else if ("SUPER_ADMIN".equals(role)) {
            list = service.getPendingAll();
        }

        req.setAttribute("leaveList", list);
        req.setAttribute("pageTitle", "Pending Leave Requests");
        req.getRequestDispatcher("/view/leave/list.jsp").forward(req, resp);
    }
}
