package com.team.case6.controller;

import com.team.case6.model.entity.blog.Blog;
import com.team.case6.model.entity.blog.BlogStatus;
import com.team.case6.model.entity.extra.Status;
import com.team.case6.model.entity.user.UserInfo;
import com.team.case6.model.entity.user.UserStatus;
import com.team.case6.service.blog.IBlogService;
import com.team.case6.service.blogStautus.IBlogStatusService;
import com.team.case6.service.userInfo.IUserInfoService;
import com.team.case6.service.userStatus.IUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminViewController {
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IBlogService iBlogService;
    @Autowired
    private IBlogStatusService blogStatusService;

    @Autowired
    private IUserStatusService userStatusService;

    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getListUserInfo(){
        return new ResponseEntity<>(userInfoService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/statues")
    public ResponseEntity<Status[]> getListStatus(){
        return  new ResponseEntity<>(Status.values(),HttpStatus.OK);
    }
    @GetMapping("/blogs")
    public  ResponseEntity<List<Blog>> getListBlogs(){
       return new ResponseEntity<>( iBlogService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/banUser/{id}")
    public ResponseEntity<UserStatus> banUser(@PathVariable Long id){
        UserStatus userStatus=userStatusService.findById(id).get();
        userStatus.setVerify(false);
        userStatusService.save(userStatus);
        return new ResponseEntity<>(userStatus, HttpStatus.OK);
    }

    @GetMapping("/activeUser/{id}")
    public ResponseEntity<UserStatus> activeUser(@PathVariable Long id){
        UserStatus userStatus=userStatusService.findById(id).get();
        userStatus.setVerify(true);
        userStatusService.save(userStatus);
        return new ResponseEntity<>(userStatus, HttpStatus.OK);
    }

    @GetMapping("/banBlog/{id}")
    public ResponseEntity<BlogStatus> banBlog(@PathVariable Long id){
        BlogStatus blogStatus=blogStatusService.findById(id).get();
        //lưu lại thời gian update
        blogStatus.setUpdateAt(getUpdateAt());

        blogStatus.setVerify(false);
        blogStatusService.save(blogStatus);
        return new ResponseEntity<>(blogStatus, HttpStatus.OK);
    }

    @GetMapping("/activeBlog/{id}")
    public ResponseEntity<BlogStatus> activeBlog(@PathVariable Long id){
        BlogStatus blogStatus=blogStatusService.findById(id).get();
        blogStatus.setVerify(true);
        blogStatus.setUpdateAt(getUpdateAt());
        blogStatusService.save(blogStatus);
        return new ResponseEntity<>(blogStatus, HttpStatus.OK);
    }

    @GetMapping("/publicBlog/{id}")
    public ResponseEntity<BlogStatus> publicBlog(@PathVariable Long id){
        BlogStatus blogStatus=blogStatusService.findById(id).get();
        blogStatus.setStatus(Status.PUBLIC);
        blogStatus.setUpdateAt(getUpdateAt());
        blogStatusService.save(blogStatus);
        return new ResponseEntity<>(blogStatus, HttpStatus.OK);
    }

    private String getUpdateAt(){
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = localDate.format(fmt1);
        return formatDateTime;
    }

}
