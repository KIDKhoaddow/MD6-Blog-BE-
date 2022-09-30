package com.team.case6.service.comment;


import com.team.case6.model.entity.extra.Comment;
import com.team.case6.repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CommentServiceImpl implements ICommentService{
    @Autowired
    private ICommentRepo commentRepo;

    @Override
    public List<Comment> findAll() {
        return commentRepo.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    public void removeById(Long id) {
        commentRepo.deleteById(id);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepo.findById(id);
    }

    @Override
    public List<Comment> findAllByBlog_Id(Long blogId) {
        return commentRepo.findAllByBlog_Id(blogId );
    }
}
