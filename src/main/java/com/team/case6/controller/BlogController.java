package com.team.case6.controller;

import com.team.case6.model.dto.PictureForm;
import com.team.case6.model.entity.blog.Blog;
import com.team.case6.model.entity.blog.BlogStatus;
import com.team.case6.model.entity.classify.Category;
import com.team.case6.model.entity.extra.Status;
import com.team.case6.model.entity.user.UserInfo;
import com.team.case6.service.blog.IBlogService;
import com.team.case6.service.blogStautus.IBlogStatusService;
import com.team.case6.service.category.ICategoryService;
import com.team.case6.service.like.ILikeService;
import com.team.case6.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/blog")
@CrossOrigin("*")
public class BlogController {
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

    @Value("${file-upload}")
    private String uploadPath;


    @PostMapping("/create/{idUserInfo}")
    public ResponseEntity<Blog> createBlog(@PathVariable Long idUserInfo, @RequestPart Blog blog
            , @RequestPart("fileImage") MultipartFile multipartFile) {
        Optional<UserInfo> userInfo = userInfoService.findById(idUserInfo);
        if (!userInfo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String uploadDir = "/Image";
            Path uploadPath = Paths.get(uploadDir);
            blog.setPicture(fileName);
            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                InputStream inputStream = multipartFile.getInputStream();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(filePath.toFile().getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//lấy thông số ngày tháng năm khởi tạo
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        localDate.format(fmt1);
        String userRegisDate = String.valueOf(localDate);
        blog.setCreateAt(userRegisDate);
//Lưu vào database

        BlogStatus blogStatus = new BlogStatus();
        blogStatusService.save(blogStatus);
        blog.setBlogStatus(blogStatus);
        blog.setUserInfo(userInfo.get());
        blogService.save(blog);

        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }


    @PutMapping("/update/{idUserInfo}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long idUserInfo, @RequestBody Blog blog
            , @ModelAttribute PictureForm pictureForm) {
        Optional<UserInfo> userInfo = userInfoService.findById(idUserInfo);
        Optional<Blog> blogOptional = blogService.findById(blog.getId());
        if (!userInfo.isPresent() || !blogOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BlogStatus blogStatus = blogOptional.get().getBlogStatus();
        Category category = categorySV.findById(blog.getCategory().getId()).get();

        //lưu ảnh truyền về
        String image = "";
        try {
            if (pictureForm.getPicture() != null) {
                MultipartFile multipartFile = pictureForm.getPicture();
                image = multipartFile.getOriginalFilename();
                FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath + image));
            }
        } catch (IOException e) {
            image = blog.getPicture();
            e.printStackTrace();
        }
        if (!image.equals("")) {
            blogOptional.get().setPicture(image);
        }
        blogOptional.get().setTitle(blog.getTitle());
        blogOptional.get().setCategory(category);
        blogOptional.get().setContent(blog.getContent());
        blogOptional.get().setDescribes(blog.getDescribes());
        blogStatus.setUpdateAt(getUpdateAt());
        blogStatus.setStatus(blog.getBlogStatus().getStatus());

        blogStatusService.save(blogStatus);
        blogService.save(blogOptional.get());
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idBlog}")
    public ResponseEntity<UserInfo> deleteByUserInfo(@PathVariable Long idBlog) {
        BlogStatus blogStatus = blogService.findById(idBlog).get().getBlogStatus();
        blogStatusService.removeById(blogStatus.getId());
        likeService.deleteLikeByBlogId(idBlog);
        blogService.removeById(idBlog);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/publicBlog/{id}")
    public ResponseEntity<BlogStatus> publicBlog(@PathVariable Long id) {
        BlogStatus blogStatus = blogStatusService.findById(id).get();
        if (blogStatus.getStatus().equals(Status.PENDING)) {
            return new ResponseEntity<>(blogStatus, HttpStatus.NOT_ACCEPTABLE);
        }
        blogStatus.setStatus(Status.PUBLIC);

        blogStatus.setUpdateAt(getUpdateAt());
        blogStatusService.save(blogStatus);
        return new ResponseEntity<>(blogStatus, HttpStatus.OK);
    }

    @GetMapping("/privateBlog/{id}")
    public ResponseEntity<BlogStatus> privateBlog(@PathVariable Long id) {
        BlogStatus blogStatus = blogStatusService.findById(id).get();
        if (blogStatus.getStatus().equals(Status.PENDING)) {
            return new ResponseEntity<>(blogStatus, HttpStatus.NOT_ACCEPTABLE);
        }
        blogStatus.setStatus(Status.PRIVATE);

        blogStatus.setUpdateAt(getUpdateAt());
        blogStatusService.save(blogStatus);
        return new ResponseEntity<>(blogStatus, HttpStatus.OK);
    }


    @GetMapping("/listBlog/{idUser}")
    public ResponseEntity<List<Blog>> getListBlogByUserId(@PathVariable Long idUser) {
        UserInfo userInfo = userInfoService.findByUserId(idUser);
        List<Blog> blogList = blogService.findAllByUserInfo(userInfo);
        return new ResponseEntity<>(blogList, HttpStatus.OK);
    }

    private String getUpdateAt() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDate.format(fmt1);
    }


}
