package com.ningmeng.vueblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.service.CommentService;
import com.ningmeng.vueblog.util.MyJson;
import com.ningmeng.vueblog.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/comments")
@RestController
public class CommentController {

    private CommentService commentService;

    @GetMapping("")
    public Map<String, Object> getComments(){
        ArrayList<CommentVO> commentVOS = new ArrayList<>();
        for (Comment c : commentService.getNewComment())
            commentVOS.add(new CommentVO(c));
        return MyJson.toJson(MyJson.SUCCESS, "success", commentVOS);
    }

    @GetMapping("/{pageNum}")
    public Map<String,Object> getOnePageComments(@PathVariable("pageNum") Integer pageNum){
        if (pageNum < 1)
            return MyJson.toJson(MyJson.BAD_REQUEST, "页码数必须大于0", new ArrayList<>());
        IPage<Comment> commentsIPage = commentService.getCommentsByPageNum(pageNum);
        long pages = commentsIPage.getPages();
        if (pageNum > pages)
            return MyJson.toJson(MyJson.BAD_REQUEST, "页码数超出", new ArrayList<>());
        List<Comment> records = commentsIPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("pages", pages);
        map.put("commentList", records);
        return MyJson.toJson(MyJson.SUCCESS, "success", map);
    }

    @DeleteMapping("/{id}")
    public Map<String,Object> delete(@PathVariable("id") Integer id){
        if (id < 1)
            return MyJson.toJson(MyJson.BAD_REQUEST, "id必须大于0", new ArrayList<>());
        commentService.delete(id);
        return MyJson.toJson(MyJson.SUCCESS, "success", new ArrayList<>());
    }

    /**
     * 要做登录验证
     * @param id
     * @param content
     * @return
     */
    @PostMapping("/{id}")
    public Map<String,Object> addComment(@PathVariable("id") int id, @RequestBody Map<String,String> content){
        // todo 登录验证
        Comment comment = new Comment();
        comment.setContent(content.get("content"));
        comment.setCreateTime(Instant.now().toEpochMilli());
        System.out.println( id + "-------" + content.get("content"));
        return MyJson.toJson(MyJson.BAD_REQUEST, "bad request", new ArrayList<>());
//        return MyJson.toJson(MyJson.SUCCESS,"success", new ArrayList<>());
    }


    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
