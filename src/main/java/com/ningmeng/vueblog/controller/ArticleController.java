package com.ningmeng.vueblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ningmeng.vueblog.annocation.AdminAccessAnnotation;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.entity.Tag;
import com.ningmeng.vueblog.service.ArticleService;
import com.ningmeng.vueblog.service.CommentService;
import com.ningmeng.vueblog.service.TagService;
import com.ningmeng.vueblog.util.MyJson;
import com.ningmeng.vueblog.vo.ArticleVO;
import com.ningmeng.vueblog.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.*;

@RequestMapping("api/articles")
@RestController
public class ArticleController {

    private ArticleService articleService;
    private TagService tagService;
    private CommentService commentService;

    @GetMapping("/{pageNum}")
    public Map<String, Object> getArticleByPageNum(@PathVariable("pageNum") Integer number){
        if (number < 1)
            return MyJson.toJson(MyJson.BAD_REQUEST, "页码数必须大于0", new ArrayList<ArticleVO>());
        IPage<Article> articles = articleService.getArticlesByPageNumber(number);
        long pages = articles.getPages();
        if (number > pages)
            return MyJson.toJson(MyJson.BAD_REQUEST, "页码数超出", new ArrayList<ArticleVO>());
        ArrayList<ArticleVO> articleVOS = new ArrayList<>();
        for (Article a : articles.getRecords()) {
            ArticleVO vo = new ArticleVO(a, null);
            vo.setContent("");

            ArrayList<CommentVO> commentVOS = new ArrayList<>();
            for (Comment comment : a.getCommentSet()) {
                commentVOS.add(CommentVO.newInstance(comment, new ArrayList<>()));
            }
            commentVOS.sort((o1, o2) -> {
                if (o1.getCreateTime() > o2.getCreateTime())
                    return -1;
                else if (o1.getCreateTime() == o2.getCreateTime())
                    return 0;
                else
                    return 1;
            });
            vo.setComments(commentVOS);
            articleVOS.add(vo);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("articles", articleVOS);
        map.put("pages", pages);

        return MyJson.toJson(MyJson.SUCCESS, "success", map);
    }

    /**
     * 获取分页总数
     * @return
     */
    @GetMapping("/pages")
    public Map<String, Object> getPages(){
        long pages = articleService.getArticlesByPageNumber(1).getPages();
        return MyJson.toJson(MyJson.SUCCESS, "success", pages);
    }

    @GetMapping("/article/{id}")
    public Map<String,Object> getOneArticle(@PathVariable("id") int id){
        Article byId = articleService.findById(id);
        if (byId == null)
            return MyJson.toJson(MyJson.BAD_REQUEST, "文章不存在", new ArrayList<>());
        // 更新views
        byId.setViews(byId.getViews() + 1);
        articleService.update(byId);
        byId.setCommentSet(articleService.getParentCommentById(byId.getId()));
        HashMap<String, Object> map = new HashMap<>();
        ArticleVO articleVO = new ArticleVO(byId, commentService);

        map.put("current", articleVO);
        List<Integer> ids = articleService.articleIds();
        ids.sort(Integer::compareTo);
        // before
        if (ids.indexOf(id) > 0) {
            Article before = articleService.findById(ids.get(ids.indexOf(id) -1));
            if (before != null) {
                HashMap<String, String> temp = new HashMap<>();
                temp.put("id", before.getId().toString());
                temp.put("title", before.getTitle());
                map.put("before", temp);
            }
        }
        // after
        if (ids.indexOf(id) + 1 < ids.size()){
            Article next = articleService.findById(ids.get(ids.indexOf(id) + 1));
            if (next != null) {
                HashMap<String, String> temp = new HashMap<>();
                temp.put("id", next.getId().toString());
                temp.put("title", next.getTitle());
                map.put("next", temp);
            }
        }
        int count = 0;
        while(true){
            count += 1;
            Article random = articleService.findById(ids.indexOf(new Random().nextInt(ids.size())));
            if (random != null){
                HashMap<String, String> temp = new HashMap<>();
                temp.put("id", random.getId().toString());
                temp.put("title", random.getTitle());
                map.put("random", temp);
                break;
            }
            if (count > 10) {
                map.put("random", byId);
                break;
            }
        }
        return MyJson.toJson(MyJson.SUCCESS, "success", map);
    }

    @AdminAccessAnnotation
    @PutMapping("/article/{id}")
    public Map<String, Object> updateArticle(@PathVariable("id") int id, @RequestBody Map<String, Object> body, HttpServletRequest request, HttpServletResponse response) {
        if (id != (Integer) body.get("id")){
            return MyJson.toJson(MyJson.BAD_REQUEST, "文章id错误", new ArrayList<>());
        }
        Article byId = articleService.findById(id);

        byId.setTitle((String) body.get("title"));
        byId.setUpdateTime(Instant.now().toEpochMilli());
        byId.setArticleAbstract((String) body.get("abstract"));
        byId.setContent((String) body.get("content"));
        HashSet<Tag> tags = new HashSet<>();
        for (String t : (List<String>)body.get("tags")){
            Tag byName = tagService.getByName(t);
            if (byName != null)
                tags.add(byName);
        }

        byId.setTagSet(tags);
        articleService.update(byId);

        return null;
    }

    @AdminAccessAnnotation
    @DeleteMapping("/article/{id}")
    public Map<String, Object> deleteArticle(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {
        if (id < 1){
            return MyJson.toJson(MyJson.BAD_REQUEST, "文章id错误", new ArrayList<>());
        }
        articleService.delete(id);
        return MyJson.toJson(MyJson.SUCCESS, "success", new ArrayList<>());
    }

    @AdminAccessAnnotation
    @PostMapping("/article")
    public Map<String, Object> addArticle(@RequestBody Map<String, Object> body, HttpServletRequest request, HttpServletResponse response) {
        String title = (String) body.get("title");
        String content = (String) body.get("content");
        String articleAbstract = (String) body.get("abstract");
        List<String> tags = (List<String>) body.get("tags");


        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setArticleAbstract(articleAbstract);
        HashSet<Tag> set = new HashSet<Tag>();
        for (String s : tags){
            Tag name = tagService.getByName(s);
            if (name != null)
                set.add(name);
        }
        article.getTagSet().addAll(set);
        articleService.insert(article);

        return MyJson.toJson(MyJson.SUCCESS, "success", new ArrayList<>());
    }
    /**
     * 四条记录
     * @return
     */
    @GetMapping("/mostComment")
    public Map<String,Object> getByMostComment(){
        List<Article> byMostComment = articleService.getByMostComment();
        ArrayList<ArticleVO> articleVOS = new ArrayList<>();
        for (Article a : byMostComment)
            articleVOS.add(new ArticleVO(a, null));
        return MyJson.toJson(MyJson.SUCCESS, "success", articleVOS);
    }

    @GetMapping("/mostView")
    public Map<String, Object> getByMostView(){
        List<Article> byMostView = articleService.getByMostView();
        ArrayList<ArticleVO> articleVOS = new ArrayList<>();
        for (Article a : byMostView)
            articleVOS.add(new ArticleVO(a, null));
        return MyJson.toJson(MyJson.SUCCESS, "success", articleVOS);
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
