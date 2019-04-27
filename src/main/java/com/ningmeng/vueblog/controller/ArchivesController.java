package com.ningmeng.vueblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.service.ArticleService;
import com.ningmeng.vueblog.util.MyJson;
import com.ningmeng.vueblog.vo.ArticleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("api/archives")
@RestController
public class ArchivesController {

    private ArticleService articleService;

    @GetMapping("/{year}/{month}/{pageNum}")
    public Map<String, Object> getArticleByYearMonth(@PathVariable("year") Integer year, @PathVariable("month") Integer month, @PathVariable("pageNum") Integer pageNum){
        if ( year<2019 || month <1 || month > 12 )
            return MyJson.toJson(MyJson.BAD_REQUEST, "日期错误", new ArrayList<>());
        if (pageNum < 1)
            return MyJson.toJson(MyJson.BAD_REQUEST, "页码数必须大于０", new ArrayList<>());
        IPage<Article> articleIPage = articleService.findByYearAndMonth(year, month, pageNum);
        long pages = articleIPage.getPages();
        if (pageNum > pages)
            return MyJson.toJson(MyJson.BAD_REQUEST, "超出页码数", new ArrayList<>());
        ArrayList<ArticleVO> articleVOS = new ArrayList<>();
        for (Article a : articleIPage.getRecords())
            articleVOS.add(new ArticleVO(a));
        return MyJson.toJson(MyJson.SUCCESS, "success", articleVOS);
    }

    @GetMapping("/{year}/{month}/pages")
    public Map<String, Object> getPages(@PathVariable("year") Integer year, @PathVariable("month") Integer month ){
        if ( year<2019 || month <1 || month > 12 )
            return MyJson.toJson(MyJson.BAD_REQUEST, "日期错误", new ArrayList<>());
        long pages = articleService.findByYearAndMonth(year, month, 1).getPages();
        return MyJson.toJson(MyJson.SUCCESS, "success", pages);
    }

    @GetMapping("/totals")
    public Map<String, Object> getArticleTotals(){
        int articleTotal = articleService.getArticleTotal();
        int year = LocalDate.now().getYear();
        HashMap<String, Integer> total = new HashMap<>();
        for (int y = 2019; y <= year; y ++)
            for (int month = 1; month <=12 ; month ++){
                int byYearAndMonth = articleService.getArticleTotalByYearAndMonth(year, month);
                if (byYearAndMonth != 0 )
                    total.put(year + "-" + month, byYearAndMonth);
            }

        HashMap<String, Object> map = new HashMap<>();
        map.put("totals", articleTotal);
        map.put("total", total);
        return MyJson.toJson(MyJson.SUCCESS, "success", map);
    }


    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }
}
