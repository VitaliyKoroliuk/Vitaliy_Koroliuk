package test.java.ua.nure.koroliuk.db;


import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.db.ConnectionFactory;
import main.java.ua.nure.chub.db.ConnectionFactoryImpl;
import main.java.ua.nure.chub.db.DatabaseException;
import main.java.ua.nure.chub.db.HsqldbUserDAO;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;


public class HsqldbUserDAOTest extends DatabaseTestCase {
    private HsqldbUserDAO dao;
    private ConnectionFactory connectionFactory;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl(
                "org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:db/usermanagement",
                "sa",
                "");
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        InputStream is = new FileInputStream(new File("src\\test\\resources\\usersDataSet.xml"));
        IDataSet iDataSet = new XmlDataSet(is);
        return iDataSet;
    }

    @Before
    public void setUp() throws Exception {
        connectionFactory = new ConnectionFactoryImpl(
                "org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:db/usermanagement",
                "sa",
                "");
        dao = new HsqldbUserDAO(connectionFactory);

    }

    @Test
    public void testCreate() {
        try {
            User user = new User();
            user.setFirstName("Sergey");
            user.setLastName("Ivanov");
            user.setDateOfBirth(new Date());
            Assert.assertNull(user.getId());
            Long id = dao.create(user);
            Assert.assertNotNull(user);
            Assert.assertNotNull(id);
        } catch (DatabaseException e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testFindAll() {
        try {
            Collection collection = dao.findAll();
            Assert.assertNotNull("Collection is null", collection);
            Assert.assertEquals("Collection size", 2, collection.size());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void testFindById() {
        try {
            User user = dao.findById(0L);
            Assert.assertNotNull(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
