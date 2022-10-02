package com.team.case6.core.controller;

import com.team.case6.blog.model.entity.Blog;
import com.team.case6.category.model.Category;
import com.team.case6.comment.model.Comment;
import com.team.case6.like.model.Like;
import com.team.case6.blog.service.blog.IBlogService;
import com.team.case6.category.service.ICategoryService;
import com.team.case6.comment.service.ICommentService;
import com.team.case6.like.service.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/userView")
public class UserViewController {
    @Autowired
    private ICategoryService categorySV;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ILikeService likeService;

    @Autowired
    private ICommentService commentService;


    @GetMapping("/listBlogByCategoryName/{categoryName}")
    public ResponseEntity<List<Blog>> getListBlogByCategoryName(@PathVariable Optional<String> categoryName) {
        if (!categoryName.isPresent()) {
            if (!categorySV.findByName(categoryName.get()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(blogService.findAllByCategory_Name(categoryName.get()), HttpStatus.OK);
    }

    @GetMapping("/category/{idCategory}")
    public ResponseEntity<Category> getCategory(@PathVariable Long idCategory) {
        Optional<Category> category = categorySV.findById(idCategory);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }


    @GetMapping("/countLike/{idBlog}")
    public ResponseEntity<?> countLike(@PathVariable Long idBlog) {
        List<Like> likeList = likeService.findAllByBlogId(idBlog);
        return new ResponseEntity<>(likeList, HttpStatus.OK);
    }


    @GetMapping("/listComment/{idBlog}")
    public ResponseEntity<List<Comment>> getCommentByBlogId(@PathVariable Long idBlog) {
        return new ResponseEntity<>(commentService.findAllByBlog_Id(idBlog), HttpStatus.OK);
    }

    @GetMapping("/listCountComment/{idBlog}")
    public ResponseEntity<?> countListCommentByBlogId(@PathVariable Long idBlog) {
        return new ResponseEntity<>(commentService.findAllByBlog_Id(idBlog).size(), HttpStatus.OK);
    }
}

