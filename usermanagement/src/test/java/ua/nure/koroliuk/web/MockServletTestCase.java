package test.java.ua.nure.koroliuk.web;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;
import main.java.ua.nure.chub.db.DAOFactory;
import test.java.ua.nure.chub.db.MockDAOFactory;

import java.util.Properties;


public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {
    private Mock mockUserDAO;

    @Override
    protected void tearDown() throws Exception {
        getMockUserDAO().verify();
        super.tearDown();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDAOFactory.class.getName());
        DAOFactory.init(properties);
        setMockUserDAO(((MockDAOFactory) DAOFactory.getInstance()).getMockUserDAO());

    }

    public Mock getMockUserDAO() {
        return mockUserDAO;
    }

    public void setMockUserDAO(Mock mockUserDAO) {
        this.mockUserDAO = mockUserDAO;
    }
}
