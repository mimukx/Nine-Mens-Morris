package sk.tuke.gamestudio.ServiceRestClientTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentServiceRestClient;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceRestClientTest {

    private RestTemplate restTemplate;
    private CommentServiceRestClient commentService;

    private final String baseUrl = "http://localhost:8080/api/comment";

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        commentService = new CommentServiceRestClient();
        commentService.setRestTemplate(restTemplate);
    }

    @Test
    void testAddComment() {
        Comment comment = new Comment("nine_mens_morris", "Tester", "Great game!", new Date());
        doNothing().when(restTemplate).postForEntity(baseUrl, comment, Comment.class);

        assertDoesNotThrow(() -> commentService.addComment(comment));
        verify(restTemplate).postForEntity(baseUrl, comment, Comment.class);
    }

    @Test
    void testGetComments() {
        Comment[] mockComments = {
                new Comment("nine_mens_morris", "Tester1", "Cool!", new Date()),
                new Comment("nine_mens_morris", "Tester2", "Nice game!", new Date())
        };

        when(restTemplate.getForEntity(baseUrl + "/nine_mens_morris", Comment[].class))
                .thenReturn(new org.springframework.http.ResponseEntity<>(mockComments, org.springframework.http.HttpStatus.OK));

        List<Comment> result = commentService.getComments("nine_mens_morris");

        assertEquals(2, result.size());
        assertEquals("Tester1", result.get(0).getUsername());
        assertEquals("Nice game!", result.get(1).getComment());
    }

    @Test
    void testResetNotSupported() {
        assertThrows(UnsupportedOperationException.class, () -> commentService.reset());
    }
}

