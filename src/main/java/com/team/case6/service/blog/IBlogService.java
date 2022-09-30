package com.team.case6.service.blog;


import com.team.case6.model.dto.BlogMostLike;
import com.team.case6.model.dto.BlogsOfUser;
import com.team.case6.model.entity.blog.Blog;
import com.team.case6.model.entity.user.UserInfo;
import com.team.case6.service.IGeneralService;

import java.util.List;

public interface IBlogService extends IGeneralService<Blog> {
    List<Blog> findAllByCategory_Name(String categoryName);
    List<Blog> findAllByUserInfo(UserInfo userInfo);
    List<BlogsOfUser> findBlogsOfUser();
    List<BlogMostLike> findBlogsMostLike();
}
