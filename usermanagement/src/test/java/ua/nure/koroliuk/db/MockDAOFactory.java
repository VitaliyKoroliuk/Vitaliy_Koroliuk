package test.java.ua.nure.koroliuk.db;

import com.mockobjects.dynamic.Mock;
import main.java.ua.nure.chub.db.DAOFactory;
import main.java.ua.nure.chub.db.UserDAO;


public class MockDAOFactory extends DAOFactory {
    private Mock mockUserDAO;

    public MockDAOFactory() {
        mockUserDAO = new Mock(UserDAO.class);
    }

    public Mock getMockUserDAO() {
        return mockUserDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        return (UserDAO) mockUserDAO.proxy();
    }
}
