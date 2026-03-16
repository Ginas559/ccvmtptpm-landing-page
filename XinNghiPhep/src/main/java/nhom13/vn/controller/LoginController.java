package nhom13.vn.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import nhom13.vn.service.IUserService;
import nhom13.vn.service.impl.UserServiceImpl;
import nhom13.vn.entity.User;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);

        if (user != null) {

            HttpSession session = req.getSession();
            session.setAttribute("account", user);

            resp.sendRedirect(req.getContextPath() + "/view/home.jsp");

        } else {

            req.setAttribute("alert", "Sai username hoặc password");

            req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
        }
    }
}