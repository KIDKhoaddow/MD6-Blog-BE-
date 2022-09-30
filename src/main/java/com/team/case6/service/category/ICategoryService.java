package com.team.case6.service.category;


import com.team.case6.model.dto.ShowCategory;
import com.team.case6.model.entity.classify.Category;
import com.team.case6.service.IGeneralService;

import java.util.Optional;

public interface ICategoryService extends IGeneralService<Category> {

    Iterable<ShowCategory> getAllCategoryByUserId(Long user_id);
    Optional<Category> findByName(String name);
}
