package main.java.ua.nure.koroliuk.servlet;

import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.db.DAOFactory;
import main.java.ua.nure.chub.db.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class AddServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showPage(req, resp);
    }

    protected void processUser(User user) throws DatabaseException {
        DAOFactory.getInstance().getUserDAO().create(user);
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }
}
