package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserServiceRest {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        if (!userRepository.findByUsername(user.getUsername()).isPresent()) {
            userRepository.save(user);
        }
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userRepository.findByUsername(user.getUsername())
                .filter(u -> u.getPassword().equals(user.getPassword()))
                .orElse(null);
    }

    @GetMapping("/exists/{username}")
    public boolean exists(@PathVariable String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
