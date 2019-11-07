package com.ningmeng.vueblog.controller;

import com.ningmeng.vueblog.annocation.AdminAccessAnnotation;
import com.ningmeng.vueblog.entity.Link;
import com.ningmeng.vueblog.service.LinkService;
import com.ningmeng.vueblog.util.MyJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("api/links")
@RestController
public class LinkController {

    private LinkService linkService;

    @GetMapping("")
    public Map<String, Object> getLinks(){
        List<Link> allLinks = linkService.getAllLinks();
        return MyJson.toJson(MyJson.SUCCESS, "success", allLinks);
    }

    @AdminAccessAnnotation
    @PostMapping("")
    public Map<String, Object> addLink(@RequestBody Map<String, Object> linkMap, HttpServletRequest request, HttpServletResponse response) {
        String description = (String) linkMap.get("description");
        String title = (String) linkMap.get("title");
        String url = (String) linkMap.get("url");
        Link link = new Link();
        link.setUrl(url);
        link.setDescription(description);
        link.setTitle(title);
        linkService.insert(link);
        return MyJson.toJson(MyJson.SUCCESS, "success", new ArrayList<>());
    }

    @AdminAccessAnnotation
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {
        if (id < 1)
            return MyJson.toJson(MyJson.BAD_REQUEST, "id 必须大于0", new ArrayList<>());
        linkService.delete(id);
        return MyJson.toJson(MyJson.SUCCESS, "success", new ArrayList<>());
    }
    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }
}
