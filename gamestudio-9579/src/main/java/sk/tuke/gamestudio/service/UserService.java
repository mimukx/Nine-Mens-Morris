package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.User;

import java.util.Optional;


public interface UserService {
    boolean exists(String username) throws UserException;
    void register(String email, String username, String password) throws UserException;
    boolean login(String username, String password) throws UserException;
    boolean addUser(User user);
    boolean activateUser(String code);

    Optional<User> findByActivationCode(String code);

    void sendActivationLink(String email);

    void save(User user);
}
