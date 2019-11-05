package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Tag;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.*;

@ToString
@Setter
@Getter
public class TagVO {

    private Integer id;
    private String tagName;
    private Integer total;
    private List<Article> articles;

    public static TagVO newInstance(Tag tag) {
        TagVO tagVO = new TagVO();
        BeanUtils.copyProperties(tag, tagVO);
        tagVO.total = tag.getArticleSet().size();
        tagVO.articles = Collections.list(Collections.enumeration(tag.getArticleSet()));
        return tagVO;
    }
}
