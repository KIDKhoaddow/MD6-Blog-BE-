package com.team.case6.service.comment;


import com.team.case6.model.entity.extra.Comment;
import com.team.case6.service.IGeneralService;

import java.util.List;

public interface ICommentService extends IGeneralService<Comment> {

    List<Comment> findAllByBlog_Id(Long blogId);
}
