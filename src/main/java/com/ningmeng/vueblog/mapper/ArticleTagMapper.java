package com.ningmeng.vueblog.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface ArticleTagMapper  {

    @Delete("delete from b_article_tag where article_id = #{article_id}")
    int deleteByArticleId(@Param("article_id") int article_id);

    @Delete("delete from b_article_tag where tag_id = #{tag_id}")
    int deleteByTagId(@Param("tag_id") int tag_id);

    @Delete("delete from b_article_tag where article_id = #{article_id} and tag_id = #{tag_id}")
    int deleteByArticleIdAndTagId(@Param("article_id") int article_id, @Param("tag_id") int tag_id);

    @Insert("insert into b_article_tag(article_id, tag_id) values(#{article_id}, #{tag_id})")
    int insert(@Param("article_id") int article_id, @Param("tag_id") int tag_id);
}
