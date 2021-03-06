package com.ningmeng.vueblog.controller;

import com.ningmeng.vueblog.annocation.AdminAccessAnnotation;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Tag;
import com.ningmeng.vueblog.service.ArticleService;
import com.ningmeng.vueblog.service.TagService;
import com.ningmeng.vueblog.util.MyJson;
import com.ningmeng.vueblog.vo.ArticleVO;
import com.ningmeng.vueblog.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/tags")
@RestController
public class TagController {

    private TagService tagService;
    private ArticleService articleService;

    @GetMapping("")
    public Map<String,Object> getAllTag(){
        List<Tag> allTag = tagService.getAllTag();
        ArrayList<TagVO> tagVOS = new ArrayList<>();
        for (Tag t : allTag)
            tagVOS.add(TagVO.newInstance(t));
        return MyJson.toJson(MyJson.SUCCESS, "success", tagVOS);
    }

    @AdminAccessAnnotation
    @PostMapping("")
    public Map<String, Object> addTag(@RequestBody Map<String, String> tagName, HttpServletRequest request, HttpServletResponse response) {

        Tag tag = new Tag();
        tag.setTagName(tagName.get("tagName"));
        tagService.insert(tag);
        return MyJson.toJson(MyJson.SUCCESS, "success", new ArrayList<>());
    }

    @AdminAccessAnnotation
    @DeleteMapping("")
    public Map<String, Object> delTag(@RequestBody Map<String, String> tagNameMap, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(tagNameMap);
        String tagName = tagNameMap.get("tagName");
        Tag byName = tagService.getByName(tagName);
        if (byName != null){
            tagService.delete(byName.getId());
            return MyJson.toJson(MyJson.SUCCESS, "success", new ArrayList<>());
        }
        return MyJson.toJson(MyJson.BAD_REQUEST, "错误的id", new ArrayList<>());
    }

    @GetMapping("/{name}/{pageNum}")
    public Map<String,Object> getByTagName(@PathVariable("name") String name, @PathVariable("pageNum") int pageNum){
        Tag byName = tagService.getByName(name);
        if (byName == null)
            return MyJson.toJson(MyJson.BAD_REQUEST, "tag不存在", new ArrayList<>());
        List<Article> byTagId = articleService.findByTagId(byName.getId(), pageNum);
        int size = articleService.findByTagIdTotal(byName.getId());
        ArrayList<ArticleVO> articleVOS = new ArrayList<>();
        for (Article a : byTagId)
            articleVOS.add(new ArticleVO(a, null));
        HashMap<String, Object> map = new HashMap<>();
        map.put("pages", Math.ceil(size / 8.0));
        map.put("articles", articleVOS);
        return MyJson.toJson(MyJson.SUCCESS, "success", map);
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }
}
