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
import java.util.List;
@WebServlet("/leave/list")
public class LeaveRequestListController extends HttpServlet {

    private ILeaveRequestService service = LeaveRequestServiceImpl.getInstance();

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
            list = service.getByUser(user.getId());

        } else if ("MANAGER".equals(role)) {
            list = service.getAllEmployees();

        } else if ("SUPER_ADMIN".equals(role)) {
            list = service.getAll();
        }

        req.setAttribute("leaveList", list);

        req.getRequestDispatcher("/view/leave/list.jsp")
           .forward(req, resp);
    }
}