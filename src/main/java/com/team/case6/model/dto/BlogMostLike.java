package com.team.case6.model.dto;

public interface BlogMostLike {
    Long getBlogId();
    String getUsername();
    String getCategory();
    String getTitle();
    String getCreateAt();
    int getCountLike();
}
