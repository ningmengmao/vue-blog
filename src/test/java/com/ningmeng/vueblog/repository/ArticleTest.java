package com.ningmeng.vueblog.repository;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void test(){
        Optional<Article> byId = articleRepository.findById(1);
        Article article = byId.get();
        System.out.println(article.getCreateTime());
    }

    @Rollback(false)
    @Transactional
    @Test
    public void testAdd(){
        Article article = new Article("tagTEST", "abstract", "content", 1, new Date().getTime(), false);
        Tag tag = tagRepository.findById(5).get();
        Tag tag1 = tagRepository.findById(6).get();
        article.getTags().add(tag);
        article.getTags().add(tag1);
//        tagRepository.save(tag);
//        tagRepository.save(tag1);
        articleRepository.saveAndFlush(article);
    }

    @Test
    public void testAdds() throws InterruptedException {
        for (int i=0; i < 20; i++){
            String title = "title" + (i *10);
            Article article = new Article(title, "abstract", "content", 1, new Date().getTime(),false);
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));
            articleRepository.save(article);
        }
    }

    @Test
    public void testGet(){
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i <= 40 ; i++) {
            integers.add(i);
        }
        for (Article article : articleRepository.findAllById(integers)) {
            System.out.println(article.getTags().size());
        }
    }

    @Test
    public void dos(){
        List<Article> all = articleRepository.findAll();
        for (Article a: all) {
            a.setUpdateTime(a.getCreateTime());
        }
        articleRepository.saveAll(all);
        articleRepository.flush();
    }
}
