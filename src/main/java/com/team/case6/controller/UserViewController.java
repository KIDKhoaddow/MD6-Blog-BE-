package com.team.case6.controller;

import com.team.case6.model.dto.BlogMostLike;
import com.team.case6.model.dto.BlogsOfUser;
import com.team.case6.model.dto.LikeCount;
import com.team.case6.model.entity.blog.Blog;
import com.team.case6.model.entity.classify.Category;
import com.team.case6.model.entity.extra.Comment;
import com.team.case6.model.entity.extra.Like;
import com.team.case6.service.blog.IBlogService;
import com.team.case6.service.category.ICategoryService;
import com.team.case6.service.comment.ICommentService;
import com.team.case6.service.like.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @GetMapping("/listCategory")
    public ResponseEntity<List<Category>> getListCategory() {
        return new ResponseEntity<>(categorySV.findAll(), HttpStatus.OK);
    }

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
    public ResponseEntity<Category> getCategory(@PathVariable Long idCategory){
        Optional<Category> category=categorySV.findById(idCategory);
        if(!category.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category.get(),HttpStatus.OK);
    }
    @GetMapping("/blog/{idBlog}")
    public ResponseEntity<Blog> getBlog(@PathVariable Long idBlog) {
        Optional<Blog> blog = blogService.findById(idBlog);
        if (!blog.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blog.get(), HttpStatus.OK);
    }
    @GetMapping("/countLike/{idBlog}")
    public  ResponseEntity<?> countLike(@PathVariable Long idBlog){
        List<Like> likeList=likeService.findAllByBlogId(idBlog);
        return new ResponseEntity<>(likeList,HttpStatus.OK);
    }
    public List<Blog> findAllBlogById(List<LikeCount> likeCountList){
        List<Blog> blogs = new ArrayList<>();
        for (LikeCount likeCount : likeCountList) {
            blogs.add(blogService.findById(likeCount.getBlogId()).get());
        }
        return blogs;
    }

    @PostMapping("/top-10-like")
    public ResponseEntity<List<Blog>> demo() {
        List<LikeCount> likeCounts = likeService.findCount();
        return new ResponseEntity<>(findAllBlogById(likeCounts), HttpStatus.OK);
    }


    @GetMapping("/listComment/{idBlog}")
    public  ResponseEntity<List<Comment>> getCommentByBlogId(@PathVariable Long idBlog){
        return new ResponseEntity<>(commentService.findAllByBlog_Id(idBlog),HttpStatus.OK);
    }
    @GetMapping("/listCountComment/{idBlog}")
    public  ResponseEntity<?> countListCommentByBlogId(@PathVariable Long idBlog){
        return new ResponseEntity<>(commentService.findAllByBlog_Id(idBlog).size(),HttpStatus.OK);
    }
@GetMapping("/listBlogsOfUser")
    public  ResponseEntity<List<BlogsOfUser>> getBlogsOfUser(){
        return new ResponseEntity<>(blogService.findBlogsOfUser(),HttpStatus.OK);
}
    @GetMapping("/listBlogsMostLike")
    public  ResponseEntity<List<BlogMostLike>> getBlogsMostLike(){
        return new ResponseEntity<>(blogService.findBlogsMostLike(),HttpStatus.OK);
    }

}
