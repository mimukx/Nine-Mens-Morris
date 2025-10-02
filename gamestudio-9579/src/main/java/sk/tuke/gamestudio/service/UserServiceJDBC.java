package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserServiceJDBC implements UserService {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    private User loggedInUser;

    @Autowired
    private UserRepository userRepository;

    private static final String INSERT = "INSERT INTO users (username) VALUES (?)";

    private static final String SELECT = "SELECT COUNT(*) FROM users WHERE username = ?";

    @Override
    public void register(String username, String email, String password) throws UserException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)")) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new UserException("Registration failed", e);
        }
    }


    public boolean exists(String username) throws UserException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(SELECT)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("Check failed", e);
        }
    }



    @Override
    public boolean login(String username, String password) throws UserException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    loggedInUser = new User();
                    loggedInUser.setIdent(rs.getInt("ident"));
                    loggedInUser.setUsername(rs.getString("username"));
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new UserException("Login failed", e);
        }

        return false;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    public boolean activateUser(String code) {
        Optional<User> user = userRepository.findByActivationCode(code);
        if (user.isEmpty())
            return false;

        user.get().setActivationCode(null);
        userRepository.save(user.get());
        return true;
    }

    @Override
    public Optional<User> findByActivationCode(String code) {
        return Optional.empty();
    }

    @Override
    public void sendActivationLink(String email) {

    }

    @Override
    public void save(User user) {

    }


}
