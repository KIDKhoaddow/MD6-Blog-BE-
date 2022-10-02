package com.team.case6.core.controller;

import com.team.case6.blog.model.entity.BlogStatus;
import com.team.case6.core.model.entity.Status;
import com.team.case6.blog.service.blog.IBlogService;
import com.team.case6.blog.service.blogStautus.IBlogStatusService;
import com.team.case6.core.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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



    @GetMapping("/statues")
    public ResponseEntity<Status[]> getListStatus(){
        return  new ResponseEntity<>(Status.values(),HttpStatus.OK);
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
