package main.java.ua.nure.koroliuk.db;

import main.java.ua.nure.chub.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;


public class HsqldbUserDAO implements UserDAO {
    private final String SELECT_ALL_QUERY = "SELECT id,firstname, lastname, dateofbirth FROM users";
    private final String SELECT_USER_BY_ID = "SELECT id,firstname, lastname, dateofbirth FROM users WHERE id = ?";
    private final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private final String UPDATE_USER = "UPDATE users SET id = ?, firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
    private final String SELECT_BY_NAMES = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE firstname=? and lastname=?";

    private ConnectionFactory connectionFactory;


    public HsqldbUserDAO() {

    }

    public HsqldbUserDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Collection find(String firstName, String lastName) throws DatabaseException {
        Collection result = new LinkedList();

        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection
                     .prepareStatement(SELECT_BY_NAMES)) {


            statement.setString(1, firstName);
            statement.setString(2, lastName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(new Long(resultSet.getLong(1)));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
                result.add(user);
            }
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return result;
    }


    @Override
    public Long create(User user) throws DatabaseException {
        try (Connection connection = connectionFactory.createConnection();
             CallableStatement callableStatement = connection.prepareCall("CALL IDENTITY()");
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
             ResultSet keys = callableStatement.executeQuery()) {
            connection.setAutoCommit(false);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));

            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of the inserted rows: " + n);
            } else {
                connection.commit();
            }
            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user.getId();
    }

    @Override
    public void update(User user) throws DatabaseException {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER);) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setDate(4, (Date) user.getDateOfBirth());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) throws DatabaseException {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(Long id) throws DatabaseException {
        User user = new User();
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    user.setId(rs.getLong(1));
                    user.setFirstName(rs.getString(2));
                    user.setLastName(rs.getString(3));
                    user.setDateOfBirth(rs.getDate(4));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        Collection<User> result = new LinkedList<>();
        try (Connection connection = connectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY)) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong(1));
                    user.setFirstName(rs.getString(2));
                    user.setLastName(rs.getString(3));
                    user.setDateOfBirth(rs.getDate(4));
                    result.add(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
