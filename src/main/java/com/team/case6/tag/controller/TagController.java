package com.team.case6.tag.controller;

import com.team.case6.blog.model.entity.Blog;
import com.team.case6.blog.service.blog.IBlogService;
import com.team.case6.blog.service.blogStautus.IBlogStatusService;
import com.team.case6.category.service.ICategoryService;
import com.team.case6.comment.service.ICommentService;
import com.team.case6.core.service.userInfo.IUserInfoService;
import com.team.case6.like.service.ILikeService;
import com.team.case6.tag.mapper.TagMapperImpl;
import com.team.case6.tag.model.TagDTO;
import com.team.case6.tag.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/tag")
@CrossOrigin("*")
public class TagController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private IBlogStatusService blogStatusService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ICategoryService categorySV;
    @Autowired
    private ILikeService likeService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ITagService tagService;

    @Autowired
    private TagMapperImpl tagMapper;

    @GetMapping("/{idBlog}")
    public ResponseEntity<Set<TagDTO>> getListTagByBlog(@PathVariable Long idBlog) {
        Blog blog = blogService.findById(idBlog).get();
        return new ResponseEntity<>(tagMapper.toDto(tagService.findAllByBlog(blog)), HttpStatus.OK);
    }
}
