package com.ningmeng.vueblog.repository;

import com.ningmeng.vueblog.entity.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

//    //按最新更新时间获取一页文章
//    Page<Article> findAllByUpdateTimeOrderByCreateTimeDesc(Pageable pageable);

    List<Article> findAllByUpdateTimeBetween(long start, long end);


//    List<Article> findTagIdByArticleId(@Param("id") int id);

    @Query(value = "select id, title, abstract, content, views, create_time, update_time, is_top " +
            "from b_article where id in (" +
            "select article_id from b_article_tag where tag_id = :id " +
            ")"
    )
    List<Article> findArticleIdByTagId(@Param("id") int id);


}
