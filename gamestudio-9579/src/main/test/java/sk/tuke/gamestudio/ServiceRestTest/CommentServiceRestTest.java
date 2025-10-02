package sk.tuke.gamestudio.ServiceRestTest;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.server.webservice.CommentServiceRest;

import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentServiceRestTest {

    @PersistenceContext
    private CommentServiceRest commentServiceRest;

    @Test
    public void testAddAndGetComment() {
        Comment comment = new Comment("nine_mens_morris", "RestPlayer", "Nice game!", new Date());
        commentServiceRest.addComment(comment);

        List<Comment> comments = commentServiceRest.getComments("nine_mens_morris");
        assertFalse(comments.isEmpty());
        assertEquals("RestPlayer", comments.get(0).getUsername());
    }
}

