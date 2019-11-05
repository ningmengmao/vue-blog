package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.entity.Tag;
import com.ningmeng.vueblog.service.CommentService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@ToString
@Setter
@Getter
public class ArticleVO {

    private Integer id;
    private String title;
    private String articleAbstract;
    private String content;
    private Integer views;
    private Long createTime;
    private Long updateTime;
    private Boolean isTop;
    private List<CommentVO> comments = new ArrayList<>();
    private Set<String> tags = new HashSet<>();
    private Boolean hasUpdate;


    public ArticleVO(){}

    public ArticleVO(Article article, CommentService commentService) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.articleAbstract = article.getArticleAbstract();
        this.content = article.getContent();
        this.views = article.getViews();
        this.createTime = article.getCreateTime();
        this.updateTime = article.getUpdateTime();
        this.isTop = article.getIsTop();
        this.hasUpdate = updateTime > createTime ;

        for (Tag tag : article.getTagSet())
            this.tags.add(tag.getTagName());
        ArrayList<Comment> coms = new ArrayList<>(article.getCommentSet());
        coms.sort((o1, o2) -> {
            if (o2.getCreateTime() > o1.getCreateTime())
                return 1;
            else if (o2.getCreateTime().equals(o1.getCreateTime()))
                return 0;
            else return -1;
        });

        if (commentService != null)
            for (Comment c : coms) {
                this.comments.add(CommentVO.newInstance(c, commentService.getCommentsByOriginalCommentId(c.getId())));
            }
    }

}
