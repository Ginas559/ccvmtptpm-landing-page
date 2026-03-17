package nhom13.vn.controller;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nhom13.vn.entity.User;
import nhom13.vn.service.IUserService;
import nhom13.vn.service.impl.UserServiceImpl;

@WebServlet("/admin/users")
public class AdminUserController extends HttpServlet {

    IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<String> roles = Arrays.asList("EMPLOYEE", "MANAGER");

        List<User> users = userService.findByRoles(roles);

        req.setAttribute("list", users);
        req.getRequestDispatcher("/view/admin/users.jsp")
                .forward(req, resp);
    }
}