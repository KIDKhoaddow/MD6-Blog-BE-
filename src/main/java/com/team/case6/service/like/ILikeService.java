package com.team.case6.service.like;

import com.team.case6.model.dto.LikeCount;
import com.team.case6.model.dto.LikesBlog;
import com.team.case6.model.entity.blog.Blog;
import com.team.case6.model.entity.extra.Like;
import com.team.case6.model.entity.user.UserInfo;
import com.team.case6.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ILikeService extends IGeneralService<Like> {
    Optional<Like> findAllByBlogAndAndUserInfo(Blog blog, UserInfo userInfo);

    List<Like> findAllByBlogId(Long blogId);
    List<LikesBlog> findCountLikeByBlogId(Long blogId);
    void deleteLikeByBlogId(Long blogId);

    List<LikeCount> findCount();

}
