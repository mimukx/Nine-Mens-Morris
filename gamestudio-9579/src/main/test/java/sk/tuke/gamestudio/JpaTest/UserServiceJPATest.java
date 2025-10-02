package sk.tuke.gamestudio.JpaTest;

import org.springframework.test.context.TestPropertySource;
import sk.tuke.gamestudio.service.UserService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@TestPropertySource(properties = {"spring.profiles.active=test"})
@Transactional
public class UserServiceJPATest {

    @PersistenceContext
    private UserService userService;

    @Test
    public void testRegisterAndExists() {
        String username = "Tester";
        String email = "tester@example.com";
        String password = "secure123";

        userService.register(username, email, password);

        assertTrue(userService.exists(username));
    }

    @Test
    public void testNotExists() {
        assertFalse(userService.exists("NonExistentUser"));
    }
}
