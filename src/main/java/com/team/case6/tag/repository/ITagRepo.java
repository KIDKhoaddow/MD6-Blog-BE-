package com.team.case6.tag.repository;

import com.team.case6.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITagRepo extends JpaRepository<Tag,Long> {
}
