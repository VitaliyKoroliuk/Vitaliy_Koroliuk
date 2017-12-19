package test.java.ua.nure.koroliuk;

import main.java.ua.nure.chub.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.fail;



public class UserTest {
    private User user;
    private Date dateOfBirth;
    private static final int YEAR = 1994;
    private static final int DAY = 27;

    @Before
    public void setUp() throws Exception {
        user = new User();
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, Calendar.OCTOBER, DAY);
        dateOfBirth = calendar.getTime();
    }

    @Test
    public void getFullName() {
        user.setFirstName("Valeriia");
        user.setLastName("Chub");
        Assert.assertEquals("Chub, Valeriia", user.getFullName());

    }

    @Test
    public void getFullNameWithoutFirstName() {
        user.setLastName("Chub");
        try {
            user.getFullName();
            fail("IllegalStateException expecting");
        } catch (IllegalStateException e) {

        }
    }

    @Test
    public void getAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        user.setDateOfBirth(dateOfBirth);
        Assert.assertEquals(currentYear - YEAR, user.getAge());
    }
}
