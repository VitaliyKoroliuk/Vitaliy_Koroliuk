package test.java.ua.nure.koroliuk.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.db.DAOFactory;
import main.java.ua.nure.chub.gui.MainFrame;
import org.junit.Assert;
import org.junit.Test;
import test.java.ua.nure.chub.db.MockDAOFactory;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;


public class MainFrameTest extends JFCTestCase {
    private static final String firstName = "Lera";
    private static final String lastName = "Chub";
    private static final Date now = new Date();
    private MainFrame mainFrame;
    private Mock mockUserDAO;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDAOFactory.class.getName());
        DAOFactory.getInstance().init(properties);
        mockUserDAO = ((MockDAOFactory) DAOFactory.getInstance()).getMockUserDAO();
        mockUserDAO.expectAndReturn("findAll", new ArrayList<>());
        setHelper(new JFCTestHelper());
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            mockUserDAO.verify();
            mainFrame.setVisible(false);
            getHelper().cleanUp(this);

            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder;
        finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        Assert.assertNotNull("Could't find component ' " + name + "'", component);
        return component;
    }

    @Test
    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");
        JTable table = (JTable) find(JTable.class, "userTable");
        Assert.assertEquals(3, table.getColumnCount());
        Assert.assertEquals("ID", table.getColumnName(0));
        Assert.assertEquals("First Name", table.getColumnName(1));
        Assert.assertEquals("Last Name", table.getColumnName(2));

        find(JButton.class, "addButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "editButton");
        find(JButton.class, "detailsButton");
    }

    public void testAddUser() {
        User user = new User(0L, firstName, lastName, now);
        User expectedUser = new User(0L, firstName, lastName, now);
        mockUserDAO.expectAndReturn("create", user, expectedUser);

        ArrayList<User> users = new ArrayList<>();
        users.add(expectedUser);
        mockUserDAO.expectAndReturn("findAll", users);

        JTable table = (JTable) find(JTable.class, "userTable");
        Assert.assertEquals(0, table.getRowCount());

        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
        find(JPanel.class, "addPanel");

        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
        JButton okButton = (JButton) find(JButton.class, "okButton");
        find(JButton.class, "cancelButton");

        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date = format.format(now);
        getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");
        JTable table2 = (JTable) find(JTable.class, "userTable");

        Assert.assertEquals(1, table2.getRowCount());
    }

    public void testDeleteUser() {
        try {
            User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
            mockUserDAO.expect("delete", expectedUser);

            List users = new List();
            mockUserDAO.expectAndReturn("findAll", users);

            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(0, table.getRowCount());
            mockUserDAO.verify();

        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
