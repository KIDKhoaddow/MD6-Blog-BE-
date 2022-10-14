package com.team.case6.blog.controller;

import com.team.case6.blog.mapper.BlogMapperImpl;
import com.team.case6.blog.mapper.IBlogMapper;
import com.team.case6.blog.model.DTO.BlogDTO;
import com.team.case6.blog.model.DTO.BlogDTORecentlyPerCategory;
import com.team.case6.blog.model.DTO.BlogMostLike;
import com.team.case6.blog.model.DTO.BlogsOfUser;
import com.team.case6.category.model.CategoryDTO;
import com.team.case6.comment.service.ICommentService;
import com.team.case6.core.model.dto.PictureForm;
import com.team.case6.blog.model.entity.Blog;
import com.team.case6.blog.model.entity.BlogStatus;
import com.team.case6.category.model.Category;
import com.team.case6.core.model.entity.Status;
import com.team.case6.core.model.entity.UserInfo;
import com.team.case6.blog.service.blog.IBlogService;
import com.team.case6.blog.service.blogStautus.IBlogStatusService;
import com.team.case6.category.service.ICategoryService;
import com.team.case6.like.service.ILikeService;
import com.team.case6.core.service.userInfo.IUserInfoService;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog")
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
    @Autowired
    private ICommentService commentService;

    @Value("${file-upload-system}")
    private String uploadPathSystem;

    @Value("${file-upload-blog}")
    private String uploadPathBlog;

    @Autowired
    private IBlogMapper blogMapper;

    @GetMapping("")
    public ResponseEntity<List<Blog>> getListBlogs() {
        for (Blog element : blogService.findAll()) {
            Long numberLike = likeService.getCountLikeByBlogId(element.getId());
            Long numberComment = commentService.getCountCommentByBlogId(element.getId());
            element.setCountLike(numberLike);
            element.setCountComment(numberComment);
            blogService.save(element);
        }


        return new ResponseEntity<>(blogService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idBlog}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long idBlog) {
        Optional<Blog> blog = blogService.findById(idBlog);
        if(!blog.isPresent()){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogMapper.toDto(blog.get()),HttpStatus.OK);
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<BlogDTO>> getListBlogByUserId(@PathVariable Long idUser) {
        UserInfo userInfo = userInfoService.findByUserId(idUser);
        List<Blog> blogList = blogService.findAllByUserInfo(userInfo);
        return new ResponseEntity<>(blogMapper.toDto(blogList), HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Blog>> getListBlogPublic() {
        return new ResponseEntity<>(blogService.findBlogPublic(), HttpStatus.OK);
    }

    @GetMapping("/public/category/{idCategory}")
    public ResponseEntity<List<BlogDTO>> getListBlogPublic(@PathVariable Long idCategory) {
        Category category = categorySV.findById(idCategory).get();
        return new ResponseEntity<>(blogMapper.toDto(blogService.findBlogPublicByCategory(category)), HttpStatus.OK);
    }

    @GetMapping("/public/category/top-ten-most-like/{idCategory}")
    public ResponseEntity<List<Blog>> getTopTenBlogMostLikeByCategory(@PathVariable Long idCategory) {
        Category category = categorySV.findById(idCategory).get();
        return new ResponseEntity<>(blogService.findBlogPublicByCategory(category).stream()
                .sorted(Comparator.comparing(Blog::getCountLike).reversed())
                .limit(10)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/public/top-ten-most-like")
    public ResponseEntity<List<Blog>> getTopTenBlogMostLike() {
        return new ResponseEntity<>(blogService.findAll().stream()
                .sorted(Comparator.comparing(Blog::getCountLike).reversed())
                .filter(blog -> blog.getBlogStatus().getStatus() == Status.PUBLIC)
                .limit(10)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/public/most-like")
    public ResponseEntity<BlogDTO> getTopBlogMostLike() {
        return new ResponseEntity<>(blogMapper
                .toDto(blogService.findAll().stream()
                        .sorted(Comparator.comparing(Blog::getCountLike).reversed())
                        .filter(blog -> blog.getBlogStatus().getStatus() == Status.PUBLIC)
                        .limit(1)
                        .collect(Collectors.toList()).get(0)), HttpStatus.OK);
    }

    @GetMapping("/public/most-like-per-category")
    public ResponseEntity<List<Blog>> getTopBlogMostLikePerCategory() {
        List<Blog> blogs = new ArrayList<>();
        for (Category category : categorySV.findAll()) {
            List<Blog> blogsCategory = blogService.findAllByCategory(category);
            LocalDate date = LocalDate.parse(blogsCategory.get(0).getCreateAt());
            System.out.println(date);
            if (blogsCategory.size() != 0)
                blogs.add(blogsCategory.stream()
                        .sorted(Comparator.comparing(Blog::getCountLike).reversed())
                        .filter(blog -> blog.getBlogStatus().getStatus() == Status.PUBLIC).limit(1)
                        .collect(Collectors.toList()).get(0));
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/public/new-blog-per-category")
    public ResponseEntity<List<Blog>> getNewBlogPerCategory() {
        List<Blog> blogs = new ArrayList<>();
        for (Category category : categorySV.findAll()) {
            List<Blog> blogsCategory = blogService.findAllByCategory(category);
            if (blogsCategory.size() != 0) {
                blogs.add(blogsCategory.stream()
                        .filter(blog -> blog.getBlogStatus().getStatus() == Status.PUBLIC)
                        .sorted(comparator)
                        .limit(1).collect(Collectors.toList()).get(0));
            }
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/public/three-new-blog-per-category")
    public ResponseEntity<List<BlogDTORecentlyPerCategory>> getThreeNewBlogsPerCategoryVer1() {
        List<BlogDTORecentlyPerCategory> blogs =new ArrayList<>();
        for (Category category : categorySV.findAll()) {
            List<Blog> blogsCategory = blogService.findAllByCategory(category);
            blogs.add( new BlogDTORecentlyPerCategory( category.getName(), new ArrayList<>()));
            if (blogsCategory.size() != 0) {
                blogs.get(blogs.size()-1).setBlogs(blogMapper.toDto(blogsCategory.stream()
                        .filter(blog -> blog.getBlogStatus().getStatus() == Status.PUBLIC)
                        .sorted(comparator)
                        .limit(3).collect(Collectors.toList())));
            }
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }



    private Comparator<Blog> comparator = (c1, c2) -> {
        return Integer.valueOf(LocalDate.parse(c2.getCreateAt()).getDayOfYear()).compareTo(LocalDate.parse(c1.getCreateAt()).getDayOfYear());
    };

    @GetMapping("/private")
    public ResponseEntity<List<Blog>> getListBlogPrivate() {
        return new ResponseEntity<>(blogService.findBlogPrivate(), HttpStatus.OK);
    }

    @GetMapping("/listBlogsOfUser")
    public ResponseEntity<List<BlogsOfUser>> getBlogsOfUser() {
        return new ResponseEntity<>(blogService.findBlogsOfUser(), HttpStatus.OK);
    }

    @GetMapping("/listBlogsMostLike")
    public ResponseEntity<List<BlogMostLike>> getBlogsMostLike() {
        return new ResponseEntity<>(blogService.findBlogsMostLike(), HttpStatus.OK);
    }

    @GetMapping("/recently")
    public ResponseEntity<List<BlogDTO>> getBlogRecently() {
        return new ResponseEntity<>(blogMapper.toDto(blogService.findBlogPublic()
                .stream()
                .sorted(comparator).limit(6)
                .collect(Collectors.toList())), HttpStatus.OK);
    }

    @PostMapping("/{idUserInfo}")
    public ResponseEntity<BlogDTO> createBlog(@PathVariable Long idUserInfo, @RequestBody BlogDTO blogDTO ){
        Optional<UserInfo> userInfo = userInfoService.findById(idUserInfo);
        if (!userInfo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Blog blog = new Blog();
        blogMapper.updateFromDTO(blogDTO, blog);
        blog.setCategory(categorySV.findById(blogDTO.getCategoryId()).get());


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

        return new ResponseEntity<>(blogDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{idUserInfo}")
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
                FileCopyUtils.copy(multipartFile.getBytes(),
                        new File(uploadPathBlog + image));
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

    @PatchMapping("/confirm/{id}")
    public ResponseEntity<BlogStatus> confirmBlog(@PathVariable Long id) {
        BlogStatus blogStatus = blogStatusService.findById(id).get();
        //lưu lại thời gian update
        blogStatus.setUpdateAt(getUpdateAt());

        blogStatus.setStatus(Status.ADMITTED);
        blogStatusService.save(blogStatus);
        return new ResponseEntity<>(blogStatus, HttpStatus.OK);
    }

    @PatchMapping("/ban/{id}")
    public ResponseEntity<BlogStatus> banBlog(@PathVariable Long id) {
        BlogStatus blogStatus = blogStatusService.findById(id).get();
        //lưu lại thời gian update
        blogStatus.setUpdateAt(getUpdateAt());

        blogStatus.setVerify(false);
        blogStatusService.save(blogStatus);
        return new ResponseEntity<>(blogStatus, HttpStatus.OK);
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<BlogStatus> activeBlog(@PathVariable Long id) {
        BlogStatus blogStatus = blogStatusService.findById(id).get();
        blogStatus.setVerify(true);
        blogStatus.setUpdateAt(getUpdateAt());
        blogStatusService.save(blogStatus);
        return new ResponseEntity<>(blogStatus, HttpStatus.OK);
    }

    @PatchMapping("/publicBlog/{id}")
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

    @PatchMapping("/privateBlog/{id}")
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

    @DeleteMapping("/delete/{idBlog}")
    public ResponseEntity<UserInfo> deleteByUserInfo(@PathVariable Long idBlog) {
        BlogStatus blogStatus = blogService.findById(idBlog).get().getBlogStatus();
        blogStatusService.removeById(blogStatus.getId());
        likeService.deleteLikeByBlogId(idBlog);
        blogService.removeById(idBlog);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getUpdateAt() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDate.format(fmt1);
    }
}
