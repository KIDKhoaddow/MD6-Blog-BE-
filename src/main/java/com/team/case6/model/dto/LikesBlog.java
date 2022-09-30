package com.team.case6.model.dto;

import com.team.case6.model.entity.blog.Blog;
import com.team.case6.model.entity.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikesBlog {
    private Long likes;
    private Blog idBlog;
    private UserInfo idUser;

}
