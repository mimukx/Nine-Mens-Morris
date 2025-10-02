package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.repository.UserRepository;
import java.util.Optional;

@Service
public class UserServiceRestClient implements UserService {
    private final String url = "http://localhost:8080/api/user";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private User loggedInUser;

    @Override
    public boolean exists(String username) {
        Boolean exists = restTemplate.getForObject(url + "/exists/" + username, Boolean.class);
        return exists != null && exists;
    }

    @Override
    public boolean login(String username, String password) throws UserException {
        try {
            User user = new User(username, password);
            Boolean result = restTemplate.postForObject(url + "/login", user, Boolean.class);
            if (Boolean.TRUE.equals(result)) {
                loggedInUser = user;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new UserException("Login failed: " + e.getMessage(), e);
        }
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


    public void register(String email, String username, String password) throws UserException {
        User user = new User(email, username, password);
        restTemplate.postForObject(url + "/register", user, Void.class);
    }

}

