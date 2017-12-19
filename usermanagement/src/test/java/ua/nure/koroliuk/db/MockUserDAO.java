package test.java.ua.nure.koroliuk.db;

import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.db.ConnectionFactory;
import main.java.ua.nure.chub.db.DatabaseException;
import main.java.ua.nure.chub.db.UserDAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class MockUserDAO implements UserDAO {
    private long id = 0;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public Long create(User user) throws DatabaseException {
        Long currentId = ++id;
        user.setId(currentId);
        users.put(currentId, user);
        return user.getId();
    }

    @Override
    public void update(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    @Override
    public void delete(User user) throws DatabaseException {

    }

    @Override
    public User findById(Long id) throws DatabaseException {
        return (User) users.get(id);
    }

    @Override
    public Collection findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }

    @Override
    public Collection find(String firstName, String lastName) throws DatabaseException {
        Collection<User> foundUsers = new LinkedList<>();
        for (Map.Entry<Long, User> user : users.entrySet()) {
            if (user.getValue().getFirstName().equals(firstName) && user.getValue().getLastName().equals(lastName)) {
                foundUsers.add(user.getValue());
            }
        }
        return foundUsers;
    }
}
