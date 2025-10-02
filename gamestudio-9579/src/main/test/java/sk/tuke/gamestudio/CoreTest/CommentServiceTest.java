package sk.tuke.gamestudio.CoreTest;

import org.junit.Test;
import org.testng.annotations.BeforeMethod;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.CommentServiceJPA;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class CommentServiceTest {

    private CommentService commentService;

    @BeforeMethod
    public void setUp() {
        commentService = new CommentServiceJPA();
        commentService.reset();
    }

    @Test
    public void testAddAndGetComment() {
        Comment comment = new Comment("Nine Men's Morris", "TestUser", "Great game!", new Date());
        commentService.addComment(comment);

        List<Comment> comments = commentService.getComments("Nine Men's Morris");

        assertFalse(comments.isEmpty());
        Comment retrieved = comments.get(0);
        assertEquals(retrieved.getUsername(), "TestUser");
        assertEquals(retrieved.getComment(), "Great game!");
    }

    @Test
    public void testResetComments() {
        commentService.addComment(new Comment("Nine Men's Morris", "User1", "Nice!", new Date()));
        commentService.reset();
        assertTrue(commentService.getComments("Nine Men's Morris").isEmpty());
    }
}
