package com.team.case6.blog.model.DTO;

import com.team.case6.blog.model.entity.BlogStatus;
import com.team.case6.category.model.Category;
import com.team.case6.core.model.entity.Status;
import com.team.case6.core.model.entity.UserInfo;
import lombok.Data;

import java.util.Set;


@Data
public class BlogDTO {
    private Long id;
    private String username;
    private Long category;
    private String title;
    private String describes;
    private String content;
    private String picture;
    private String createAt;
    private Status status;
    private Long countLike;
    private String updateAt;
    private Set<Long> tag;
}
