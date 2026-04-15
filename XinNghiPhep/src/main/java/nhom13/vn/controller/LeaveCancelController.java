package nhom13.vn.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nhom13.vn.entity.User;
import nhom13.vn.service.ILeaveRequestService;
import nhom13.vn.service.impl.LeaveRequestServiceImpl;

@WebServlet("/leave/cancel")
public class LeaveCancelController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ILeaveRequestService service = LeaveRequestServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (!canCancelOwnLeave(user)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Only employees, managers, and super admins can cancel their own leave requests");
            return;
        }

        String idRaw = req.getParameter("id");
        int leaveId;
        try {
            leaveId = Integer.parseInt(idRaw);
            if (leaveId <= 0) {
                throw new NumberFormatException("id must be positive");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid leave request id");
            return;
        }

        boolean cancelled = service.cancelForViewer(leaveId, user);
        String statusPath = resolveStatusPath(user);
        if (cancelled) {
            resp.sendRedirect(req.getContextPath() + statusPath + "?msg=cancelled");
            return;
        }

        resp.sendRedirect(req.getContextPath() + statusPath + "?msg=notfound");
    }

    private static boolean canCancelOwnLeave(User user) {
        if (user == null || user.getRole() == null) {
            return false;
        }

        String role = user.getRole().trim().toUpperCase();
        return "EMPLOYEE".equals(role) || "MANAGER".equals(role) || "SUPER_ADMIN".equals(role);
    }

    private static String resolveStatusPath(User user) {
        if (user == null || user.getRole() == null) {
            return "/employee/status";
        }

        String role = user.getRole().trim().toUpperCase();
        if ("MANAGER".equals(role)) {
            return "/manager/status";
        }
        if ("SUPER_ADMIN".equals(role)) {
            return "/admin/status";
        }
        return "/employee/status";
    }
}
