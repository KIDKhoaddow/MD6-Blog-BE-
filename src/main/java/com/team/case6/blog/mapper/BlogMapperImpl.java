package com.team.case6.blog.mapper;

import com.team.case6.blog.model.DTO.BlogDTO;
import com.team.case6.blog.model.entity.Blog;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BlogMapperImpl implements IBlogMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Blog toEntity(BlogDTO dto) {



        return modelMapper.map(dto, Blog.class);
    }

    @Override
    public BlogDTO toDto(Blog entity) {
        BlogDTO blogDTO = modelMapper.map(entity, BlogDTO.class);
        blogDTO.setCategoryName(entity.getCategory().getId());
        blogDTO.setStatus(entity.getBlogStatus().getStatus());
        blogDTO.setUsername(entity.getUserInfo().getUser().getUsername());
        return blogDTO;
    }

    @Override
    public List<Blog> toEntity(List<BlogDTO> dtoList) {
        List<Blog> blogs=new ArrayList<>();
        for (BlogDTO element:dtoList) {
            blogs.add(toEntity(element));
        }
        return blogs;
    }

    @Override
    public List<BlogDTO> toDto(List<Blog> entityList) {
        List<BlogDTO> blogs=new ArrayList<>();
        for (Blog element:entityList) {
            blogs.add(toDto(element));
        }
        return blogs;

    }

    @Override
    public void updateFromDTO(BlogDTO dto, Blog entity) {

    }
}
