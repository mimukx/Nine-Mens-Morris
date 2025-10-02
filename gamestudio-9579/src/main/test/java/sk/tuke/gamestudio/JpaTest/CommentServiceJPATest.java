package sk.tuke.gamestudio.JpaTest;

import org.springframework.test.annotation.DirtiesContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = sk.tuke.gamestudio.server.GameStudioServer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentServiceJPATest {

    @PersistenceContext
    private CommentService commentService;

    @Test
    public void testAddAndFindComments() {
        Comment comment = new Comment("Nine Men's Morris", "Tester", "Nice game!", new Date());
        commentService.addComment(comment);

        List<Comment> comments = commentService.getComments("Nine Men's Morris");

        assertFalse(comments.isEmpty());
        assertEquals("Tester", comments.get(0).getUsername());
        assertEquals("Nice game!", comments.get(0).getComment());
    }

    @Test
    public void testResetComments() {
        commentService.addComment(new Comment("Nine Men's Morris", "Tester", "First comment", new Date()));
        commentService.reset();
        List<Comment> comments = commentService.getComments("Nine Men's Morris");

        assertTrue(comments.isEmpty());
    }
}

