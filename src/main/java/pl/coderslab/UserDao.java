package pl.coderslab;


import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.User;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ? ,? )";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE users.id = ? ";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET username = ? , email = ? , password = ? WHERE users.id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE users.id = ?";
    private static final String FIND_ALL_USER_QUERY = "SELECT * FROM users";


    public User[] findAll () {
        try (Connection conn = DbUtil.getConnection()) {
            User[] users = new User[0];
            PreparedStatement preStmt = conn.prepareStatement(FIND_ALL_USER_QUERY);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user,users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User[] addToArray (User u , User[] users) {
         users = Arrays.copyOf(users, users.length + 1);
         users[users.length -1] = u;
         return users;
    }

    public void delete (int usersId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(DELETE_USER_QUERY);
            preStmt.setInt(1,usersId);
            preStmt.executeUpdate();
            System.out.println("Usunięto");
        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    public void update (User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(UPDATE_USER_QUERY);
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2,user.getEmail());
            preStmt.setString(3,this.hashPassword(user.getPassword()));
            preStmt.setInt(4,user.getId());
            preStmt.executeUpdate();
        } catch (SQLException e ) {
            e.printStackTrace();
        }

    }

    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt =conn.prepareStatement(READ_USER_QUERY);
            preStmt.setInt(1,userId);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(hashPassword(resultSet.getString("password")));
                return user;
            }
            System.out.println("Podane ID nie widnieje w bazie");
        } catch (SQLException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public User createUser(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, hashPassword(user.getPassword()));
            preStmt.executeUpdate();
            ResultSet resultSet = preStmt.getGeneratedKeys();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                System.out.println("Id: " + id);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
