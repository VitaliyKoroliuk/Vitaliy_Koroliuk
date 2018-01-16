package vitaliy.db;

import vitaliy.User;
import vitaliy.db.ConnectionFactory;
import vitaliy.db.ConnectionFactoryImpl;
import vitaliy.db.DatabaseException;
import vitaliy.db.HsqldbUserDao;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;





public class HsqldbUserDaoTest extends DatabaseTestCase{

	private HsqldbUserDao dao;
	private	ConnectionFactory connectionFactory;
	
	
	

	protected void setUp() throws Exception {
		
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}



	public void testCreate() {
		try {
			User user = new User();
			user.setFirstName("John");
			user.setLastName("Doe");
			user.setDateOfBirthd(new Date());
			assertNull(user.getId());
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		} catch (DatabaseException e) {
		
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testFindAll() {
		try {
			Collection<User> collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size", 2, collection.size());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver"
				, "jdbc:hsqldb:file:db/usermanagement",
				"sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}



	
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader()
				.getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}
