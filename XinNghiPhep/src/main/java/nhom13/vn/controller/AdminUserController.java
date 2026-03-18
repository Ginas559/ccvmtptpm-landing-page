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

@WebServlet({
        "/admin/users",
        "/admin/users/add",
        "/admin/users/insert",
        "/admin/users/edit",
        "/admin/users/update",
        "/admin/users/delete"
})
public class AdminUserController extends HttpServlet {

    IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        // ADD
        if (uri.contains("/add")) {
            req.getRequestDispatcher("/view/admin/user-form.jsp")
                    .forward(req, resp);
        }

        // EDIT
        else if (uri.contains("/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            User user = userService.findById(id);

            req.setAttribute("user", user);
            req.getRequestDispatcher("/view/admin/user-form.jsp")
                    .forward(req, resp);
        }

        // DELETE
        else if (uri.contains("/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));

            User user = userService.findById(id);
            user.setStatus(0);
            userService.update(user);

            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }

        // LIST
        else {
            List<String> roles = Arrays.asList("EMPLOYEE", "MANAGER");
            List<User> users = userService.findByRoles(roles);

            req.setAttribute("list", users);
            req.getRequestDispatcher("/view/admin/users.jsp")
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String role = req.getParameter("role"); // 🔥 admin có role

        // INSERT
        if (uri.contains("/insert")) {

            String username = req.getParameter("username");
            String password = req.getParameter("password");

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setRole(role); // 🔥 khác manager
            user.setStatus(1);

            userService.insert(user);

            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }

        // UPDATE
        else if (uri.contains("/update")) {

            int id = Integer.parseInt(req.getParameter("id"));
            User user = userService.findById(id);

            user.setFullName(fullName);
            user.setEmail(email);
            user.setRole(role); // 🔥 admin sửa role được

            userService.update(user);

            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }
}