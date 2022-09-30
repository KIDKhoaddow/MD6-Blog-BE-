package com.team.case6.repository;


import com.team.case6.model.entity.blog.BlogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogStatusRepo extends JpaRepository<BlogStatus,Long> {
}
