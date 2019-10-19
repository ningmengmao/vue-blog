package com.ningmeng.vueblog.service.impl;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Tag;
import com.ningmeng.vueblog.service.ArticleService;
import com.ningmeng.vueblog.service.CommentService;
import com.ningmeng.vueblog.service.TagService;
import com.ningmeng.vueblog.util.MyJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping("api/side")
@RestController
public class SideBarController {

    private ArticleService articleService;
    private TagService tagService;
    private CommentService commentService;

    @GetMapping("")
    public Map<String,Object> getInfo(){
        List<Article> mostComment = articleService.getByMostComment();
        List<Article> mostView = articleService.getByMostView();
        mostComment.sort(
                (o1, o2) -> {
                    if (o1.getCommentSet().size() > o2.getCommentSet().size())
                        return -1;
                    else if (o1.getCommentSet().size() == (o2.getCommentSet().size()))
                        return 0;
                    else
                        return 1;
                }
        );
        mostView.sort((o1, o2) ->{
            if (o1.getViews() > o2.getViews())
                return -1;
            else if (o1.getViews().equals(o2.getViews()))
                return 0;
            else
                return 1;
        });

        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Object> commentList = new ArrayList<>();
        ArrayList<Object> viewList = new ArrayList<>();
        for (Article a : mostComment){
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("id", a.getId());
            temp.put("title", a.getTitle());
            commentList.add(temp);
        }
        for (Article a : mostView){
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("id", a.getId());
            temp.put("title", a.getTitle());
            viewList.add(temp);
        }
        map.put("mostView", viewList);
        map.put("mostComment", commentList);

        List<Tag> allTag = tagService.getAllTag();
        ArrayList<Object> tagList = new ArrayList<>();
        for (Tag t : allTag){
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("name", t.getTagName());
            temp.put("total", t.getArticleSet().size());
            tagList.add(temp);
        }
        map.put("tags", tagList);

        map.put("articleTotal", articleService.getArticleTotal());
        map.put("commentTotal", commentService.getCommentTotal());
        map.put("viewsTotal", articleService.getViewsTotal());
        map.put("visitTotal", 20);


        return MyJson.toJson(MyJson.SUCCESS, "success", map);
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
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
