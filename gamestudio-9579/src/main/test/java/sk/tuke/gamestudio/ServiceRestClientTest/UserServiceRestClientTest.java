//package sk.tuke.gamestudio.ServiceRestClientTest;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import sk.tuke.gamestudio.service.UserServiceRestClient;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class UserServiceRestClientTest {
//
//    private RestTemplate restTemplate;
//    private UserServiceRestClient userService;
//
//    private final String baseUrl = "http://localhost:8080/api/user";
//
//    @BeforeEach
//    void setup() {
//        restTemplate = mock(RestTemplate.class);
//        userService = new UserServiceRestClient();
//        userService.setRestTemplate(restTemplate);
//    }
//
//    @Test
//    void testRegister() {
//        String username = "TestUser";
//        doNothing().when(restTemplate).postForObject(baseUrl + "/register", username, Void.class);
//
//        assertDoesNotThrow(() -> userService.register(username));
//        verify(restTemplate).postForObject(baseUrl + "/register", username, Void.class);
//    }
//
//    @Test
//    void testExistsTrue() {
//        String username = "TestUser";
//        when(restTemplate.getForObject(baseUrl + "/exists/" + username, Boolean.class))
//                .thenReturn(true);
//
//        assertTrue(userService.exists(username));
//    }
//
//    @Test
//    void testExistsFalse() {
//        String username = "UnknownUser";
//        when(restTemplate.getForObject(baseUrl + "/exists/" + username, Boolean.class))
//                .thenReturn(false);
//
//        assertFalse(userService.exists(username));
//    }
//
//    @Test
//    void testGetAllUsernames() {
//        List<String> mockList = Arrays.asList("Alice", "Bob", "Charlie");
//        when(restTemplate.getForEntity(baseUrl + "/all", List.class))
//                .thenReturn(new ResponseEntity<>(mockList, HttpStatus.OK));
//
//        List<String> result = userService.getAllUsernames();
//        assertEquals(3, result.size());
//        assertTrue(result.contains("Alice"));
//    }
//}
//
