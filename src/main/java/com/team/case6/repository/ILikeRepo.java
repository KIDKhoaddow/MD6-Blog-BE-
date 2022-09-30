package com.team.case6.repository;


import com.team.case6.model.dto.LikeCount;
import com.team.case6.model.dto.LikesBlog;
import com.team.case6.model.entity.blog.Blog;
import com.team.case6.model.entity.extra.Like;
import com.team.case6.model.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILikeRepo extends JpaRepository<Like,Long> {

    Optional<Like> findAllByBlogAndAndUserInfo(Blog blog, UserInfo userInfo);

    List<Like> findAllByBlog_Id(Long blogId);

    @Query(value = "SELECT count(user_info_id)as countLikes,blog_id,user_info_id" +
            " FROM case4.likies where blog_id=?1 " +
            "group by user_info_id ;", nativeQuery = true)
    List<LikesBlog> findCountLikeByBlogId(Long blogId);

    void deleteLikeByBlogId(Long blogId);
    @Query(value = "SELECT blogs.id as blogId ,count(likies.blog_id) as countL FROM case4.blogs" +
            " inner join case4.likies on likies.blog_id = blogs.id " +
            "group by likies.blog_id  limit 10;", nativeQuery = true)
    List<LikeCount> findCount();


}
