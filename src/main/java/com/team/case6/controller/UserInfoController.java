package com.team.case6.controller;

import com.team.case6.model.dto.PictureForm;
import com.team.case6.model.entity.user.User;
import com.team.case6.model.entity.user.UserInfo;
import com.team.case6.service.user.UserService;
import com.team.case6.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/userInfo")
@CrossOrigin("*")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Value("${file-upload}")
    private String uploadPath;

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<UserInfo> findByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(userInfoService.findByUserId(userId), HttpStatus.OK);
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<UserInfo> findById(@PathVariable Long id) {
        Optional<UserInfo> userInfo=userInfoService.findById(id);
        if (!userInfo.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userInfoService.findById(id).get(), HttpStatus.OK);
    }
    @PutMapping("/avatar/{userId}")
    public ResponseEntity<UserInfo> editAvatar(@PathVariable Long userId, @ModelAttribute PictureForm pictureForm) {
        UserInfo userInfo = userInfoService.findByUserId(userId);
        MultipartFile multipartFile = pictureForm.getPicture();
        String image = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        userInfo.setAvatar(image);
        userInfoService.save(userInfo);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserInfo> updateProfile(@PathVariable Long userId, @ModelAttribute UserInfo userInfo) {
        UserInfo userInfo1 = userInfoService.findByUserId(userId);
        User user = userService.findById(userId).get();
        userInfo.setId(userInfo1.getId());
        userInfo.setAvatar(userInfo1.getAvatar());
        userInfo.setUser(user);
        userInfoService.save(userInfo);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<UserInfo> deleteUserInfo(@PathVariable Long userId) {
        UserInfo userInfo1 = userInfoService.findByUserId(userId);
        userInfoService.removeById(userInfo1.getId());
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
