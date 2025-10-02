//package sk.tuke.gamestudio.ServiceRestTest;
//
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import sk.tuke.gamestudio.server.webservice.UserServiceRest;
//
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//import static org.testng.AssertJUnit.assertTrue;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserServiceRestTest {
//
//    @PersistenceContext
//    private UserServiceRest userServiceRest;
//
//    @Test
//    public void testRegisterUser() {
//        userServiceRest.register("RestUser");
//
//        List<String> users = userServiceRest.getAllUsers();
//        assertTrue(users.contains("RestUser"));
//    }
//}
//
