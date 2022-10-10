package com.team.case6.tag.service;

import com.team.case6.tag.model.Tag;
import com.team.case6.tag.repository.ITagRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TagServiceImpl implements ITagService {
    @Autowired
    ITagRepo tagRepo;

    @Override
    public List<Tag> findAll() {
        return tagRepo.findAll();
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepo.save(tag);
    }

    @Override
    public void removeById(Long id) {
            tagRepo.deleteById(id);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepo.findById(id);
    }
}
