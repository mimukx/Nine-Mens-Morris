package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.repository.CommentRepository;


import java.util.List;

@Service
@Transactional
public class CommentServiceJPA implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        return commentRepository.findByGameOrderByCommentedOnDesc(game)
                .stream().limit(10).toList();
    }

    @Override
    public void reset() {
        commentRepository.deleteAll();
    }
}
