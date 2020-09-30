package pl.coderslab.users;

import pl.coderslab.User;
import pl.coderslab.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/show")
public class UserShow extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean back = Boolean.parseBoolean(request.getParameter("back"));
        if (back) {
            response.sendRedirect(request.getContextPath() + "/user/list");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        request.setAttribute("user",userDao.read(Integer.parseInt(request.getParameter("id"))));
        getServletContext().getRequestDispatcher("/user/showUser.jsp")
                .forward(request, response);
    }
}
