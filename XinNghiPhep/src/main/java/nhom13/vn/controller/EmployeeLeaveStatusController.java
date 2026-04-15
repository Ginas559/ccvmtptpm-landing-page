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

@WebServlet({"/employee/status", "/manager/status", "/admin/status"})
public class EmployeeLeaveStatusController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ILeaveRequestService leaveRequestService = LeaveRequestServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("account");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String role = user.getRole();
        if (!"EMPLOYEE".equals(role) && !"MANAGER".equals(role) && !"SUPER_ADMIN".equals(role)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Only employees, managers, and super admins can check leave status");
            return;
        }

        String selectedStatus = normalizeStatus(req.getParameter("status"));
        List<LeaveRequest> leaveRequests = leaveRequestService.getByUserWithReview(user.getId(), selectedStatus);

        String statusPath = resolveStatusPath(role);
        String dashboardPath = resolveDashboardPath(role);

        req.setAttribute("leaveList", leaveRequests);
        req.setAttribute("selectedStatus", selectedStatus == null ? "ALL" : selectedStatus);
        req.setAttribute("statusPath", statusPath);
        req.setAttribute("dashboardPath", dashboardPath);

        req.getRequestDispatcher("/view/employee/status.jsp").forward(req, resp);
    }

    private String normalizeStatus(String status) {
        if (status == null) {
            return null;
        }

        String normalized = status.trim().toUpperCase();
        if (normalized.isEmpty() || "ALL".equals(normalized)) {
            return null;
        }

        if (!"PENDING".equals(normalized)
                && !"APPROVED".equals(normalized)
                && !"REJECTED".equals(normalized)
                && !"CANCELLED".equals(normalized)) {
            return null;
        }

        return normalized;
    }

    private static String resolveStatusPath(String role) {
        if ("MANAGER".equals(role)) {
            return "/manager/status";
        }
        if ("SUPER_ADMIN".equals(role)) {
            return "/admin/status";
        }
        return "/employee/status";
    }

    private static String resolveDashboardPath(String role) {
        if ("MANAGER".equals(role)) {
            return "/manager/dashboard";
        }
        if ("SUPER_ADMIN".equals(role)) {
            return "/admin/dashboard";
        }
        return "/employee/dashboard";
    }
}

