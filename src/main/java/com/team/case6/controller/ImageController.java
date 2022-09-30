package com.team.case6.controller;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/Image")
public class ImageController {
    @Autowired
    ServletContext context;


//xem anh bang link localhost8080:/Image/ten anh
    @GetMapping(value = "/{nameImage}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String nameImage) throws IOException {

        var imgFile = new ClassPathResource("/Image/"+nameImage);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }

}
