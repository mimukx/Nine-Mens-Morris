package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.interfaces.MailSender;
import sk.tuke.gamestudio.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    public boolean addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            return false;

        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        mailSender.send(
                user.getEmail(),
                "Activation code",
                "Please activate your account: http://localhost:8080/register_step1/activate/" + user.getActivationCode()
        );

        return true;
    }

    @Override
    public boolean exists(String username) {
        return userRepository.findByUsername(username).isPresent();
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
        return userRepository.findByActivationCode(code);
    }

    @Override
    public void sendActivationLink(String email) {
        User user = new User();
        user.setEmail(email);
        user.setActivationCode(UUID.randomUUID().toString());
//        userRepository.save(user);

        mailSender.send(
                email,
                "Activate your account",
                "Please activate your account: http://localhost:8080/register_step1/activate/" + user.getActivationCode()
        );
    }


    @Override
    public void register(String email, String username, String password) throws UserException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserException("Username already taken");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserException("Email already in use");
        }

        User user = new User(username, email, password);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        mailSender.send(
                email,
                "Activate your account",
                "Please activate your account by clicking the following link:\n\n"
                        + "http://localhost:8080/register/activate/" + user.getActivationCode()
        );
    }


    @Override
    public boolean login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }


    public void save(User user) {
        userRepository.save(user);
    }

}
