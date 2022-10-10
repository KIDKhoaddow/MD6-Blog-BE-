package com.team.case6.comment.service;


import com.team.case6.comment.model.Comment;
import com.team.case6.comment.repository.ICommentRepo;
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

    @Override
    public Long getCountCommentByBlogId(Long blogId) {
        return commentRepo.countCommentByBlog_Id(blogId);
    }
}
