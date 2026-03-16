package nhom13.vn.controller;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nhom13.vn.service.IUserService;
import nhom13.vn.service.impl.UserServiceImpl;
@WebServlet(urlPatterns = {"/signup"})
public class SignupController extends HttpServlet {

    IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        int result = userService.register(username, password, email);

        if (result == 0) {

            resp.sendRedirect(req.getContextPath() + "/login?msg=success");

        } else {

            req.setAttribute("errorMsg", "Username hoặc Email đã tồn tại");

            req.getRequestDispatcher("/view/signup.jsp").forward(req, resp);

        }
    }
}