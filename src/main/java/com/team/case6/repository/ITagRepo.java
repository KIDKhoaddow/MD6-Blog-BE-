package com.team.case6.repository;

import com.team.case6.model.entity.classify.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITagRepo extends JpaRepository<Tag,Long> {
}
