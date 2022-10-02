package com.team.case6.blog.repository;


import com.team.case6.blog.model.entity.BlogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogStatusRepo extends JpaRepository<BlogStatus,Long> {
}
