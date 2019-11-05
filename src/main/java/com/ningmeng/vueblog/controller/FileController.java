package com.ningmeng.vueblog.controller;

import com.ningmeng.vueblog.util.MyJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("api/file")
@RestController
public class FileController {

    @Value("${upload-image-path}")
    private String path;

    @PostMapping("/img")
    public Map<String,Object> uploadImage(MultipartFile image,  HttpServletRequest request){
        String imageName = image.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            image.transferTo(new File(path + uuid + "_" +imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = "static/images/" + uuid + "_" + imageName;
        HashMap<String, String> map = new HashMap<>();
        map.put("url", url);
        map.put("fileName", uuid+"_"+imageName);
        return MyJson.toJson(MyJson.SUCCESS, "success", map);
    }

    @GetMapping("/img/{fileName}")
    public Map<String,Object> getImage(@PathVariable("fileName") String fileName){

        return null;
    }

    @DeleteMapping("/img/{fileName}")
    public Map<String,Object> delImage(@PathVariable("fileName") String fileName){

        return null;
    }
}
