package com.ningmeng.vueblog.bo;

import com.ningmeng.vueblog.entity.Article;

import java.io.Serializable;
import java.util.Set;

public class TagBO implements Serializable {

    private Integer id;
    private String tagName;

    private Set<Article> articleSet;
}
