package com.team.case6.blog.mapper;

import com.team.case6.blog.model.DTO.BlogDTO;
import com.team.case6.blog.model.entity.Blog;
import com.team.case6.tag.mapper.ITagMapper;
import com.team.case6.tag.service.ITagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BlogMapperImpl implements IBlogMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ITagMapper tagMapper;

    private ITagService tagService;

    @Override
    public Blog toEntity(BlogDTO dto) {
        return modelMapper.map(dto, Blog.class);
    }

    @Override
    public BlogDTO toDto(Blog entity) {
        BlogDTO blogDTO = modelMapper.map(entity, BlogDTO.class);
        blogDTO.setCategoryId(entity.getCategory().getId());
        blogDTO.setCategoryName(entity.getCategory().getName());
        blogDTO.setStatus(entity.getBlogStatus().getStatus());
        blogDTO.setAvatar(entity.getUserInfo().getAvatar());
        blogDTO.setUsername(entity.getUserInfo().getName());
//        blogDTO.setTag(tagMapper.toDto(tagService.findAllByBlog(entity)));
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
        modelMapper.map(dto,entity);
        entity.setCountLike(0L);
        entity.setCountComment(0L);
    }
}
