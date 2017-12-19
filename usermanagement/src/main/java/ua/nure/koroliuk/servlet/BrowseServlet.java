package main.java.ua.nure.koroliuk.servlet;

import main.java.ua.nure.chub.db.DatabaseException;
import main.java.ua.nure.chub.db.HsqldbUserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;



public class BrowseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        browse(req, resp);
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection users;
        HsqldbUserDAO userDAO = new HsqldbUserDAO();
        try {
            users = userDAO.findAll();
            req.getSession().setAttribute("users", users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
