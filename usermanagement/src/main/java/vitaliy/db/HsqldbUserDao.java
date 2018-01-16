package vitaliy.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;



import java.sql.Date;

import vitaliy.User;

class HsqldbUserDao implements UserDao {

	private final String SELECT_ALL_QUERY = "SELECT id,firstname, lastname, dateofbirth FROM users";
	private final String SELECT_USER_BY_ID = "SELECT id,firstname, lastname, dateofbirth FROM users WHERE id = ?";
	private final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private final String DELETE_USER = "DELETE FROM users WHERE id = ?";
	private final String UPDATE_USER = "UPDATE users SET id = ?, firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
	private final String SELECT_BY_NAMES = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE firstname=? and lastname=?";
	
	public ConnectionFactory connectionFactory;
	
	public HsqldbUserDao() {
		
	}
	
	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}



	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}



	public User create(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection
					.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
			int n = statement.executeUpdate();
			if (n != 1) {
				throw new DatabaseException("Number of the inserted rows: " + n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next()) {
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return user;
		} catch (DatabaseException e){
			throw e;
		}catch (SQLException e) {
		
			throw new DatabaseException(e);
		}
	}

	@Override
	public void update(User user) throws DatabaseException {
		

	}

	@Override
	public void delete(User user) throws DatabaseException {
		

	}

	@Override
	public User find(Long id) throws DatabaseException {
		User user = new User();
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    user.setId(rs.getLong(1));
                    user.setFirstName(rs.getString(2));
                    user.setLastName(rs.getString(3));
                    user.setDateOfBirthd(rs.getDate(4));
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
		
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()) {
				User user = new User();
				 user.setId(resultSet.getLong(1));
                 user.setFirstName(resultSet.getString(2));
                 user.setLastName(resultSet.getString(3));
                 user.setDateOfBirthd(resultSet.getDate(4));
                 result.add(user);
			}
		} catch (DatabaseException e) {
			throw e;
		}catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return result;
	}

	
	public Collection<User> find(String firstName, String lastName) throws DatabaseException {
		Collection<User> result = new LinkedList<>();
		
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAMES);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				 user.setId(resultSet.getLong(1));
                 user.setFirstName(resultSet.getString(2));
                 user.setLastName(resultSet.getString(3));
                 user.setDateOfBirthd(resultSet.getDate(4));
                 result.add(user);
			}
		} catch (DatabaseException e) {
			throw e;
		}catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return result;
	}

}
