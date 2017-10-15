package koroliukkn155;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;


public class UserTest extends TestCase {

	private User user;
	private Date dateOfBirth;
	
	protected void setUp() throws Exception {
		super.setUp();
		user = new User();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1984, Calendar.MAY, 26);
		dateOfBirth = calendar.getTime(); 
	}

	
	public void testGetFullName() {
		user.setFirstName("John");
		user.setLastName("Week");
		assertEquals("Week, John", user.getFullName());
	}
	
	public void testGetAge() {
		user.setDateOfBirth(dateOfBirth);
		assertEquals(2017 - 1984, user.getAge());
	}
}
