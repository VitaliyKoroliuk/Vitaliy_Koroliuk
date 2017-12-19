package test.java.ua.nure.koroliuk.db;

import junit.framework.TestCase;
import main.java.ua.nure.chub.db.DAOFactory;
import main.java.ua.nure.chub.db.UserDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class DAOFactoryTest extends TestCase {

    private DAOFactory daoFactory;

    @Before
    public void setUp() throws Exception {
        daoFactory = DAOFactory.getInstance();
    }

    @Test
    public void testGetDAOFactory() {
        Assert.assertNotNull("DAOFactory instance is null", daoFactory);
    }

    @Test
    public void testGetUserDAO() {
        UserDAO userDAO = daoFactory.getUserDAO();
        Assert.assertNotNull("UserDAO instance is null", userDAO);
    }
}
