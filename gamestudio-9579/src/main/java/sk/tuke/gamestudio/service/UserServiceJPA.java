package sk.tuke.gamestudio.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.repository.UserRepository;

import java.util.Optional;


public class UserServiceJPA implements UserService {

    @Autowired
    private UserRepository userRepository;

    private User loggedInUser;

    @Override
    @Transactional
    public void register(String username, String email, String password) throws UserException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserException("User already exists");
        }
        User user = new User(username, email, password);
        loggedInUser = userRepository.save(user);
    }



    @Override
    public boolean exists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean login(String username, String password) throws UserException {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> {
                    loggedInUser = u;
                    return true;
                }).orElse(false);
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
