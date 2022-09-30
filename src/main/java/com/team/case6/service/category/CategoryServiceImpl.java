package com.team.case6.service.category;


import com.team.case6.model.dto.ShowCategory;
import com.team.case6.model.entity.classify.Category;
import com.team.case6.repository.ICategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepo categoryRepo;

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void removeById(Long id) {
        categoryRepo.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepo.findById(id);
    }


    @Override
    public Iterable<ShowCategory> getAllCategoryByUserId(Long user_id) {
        return categoryRepo.getAllCategoryByUserId(user_id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepo.findCategoryByName(name);
    }
}
