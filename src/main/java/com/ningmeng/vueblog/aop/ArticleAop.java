package com.ningmeng.vueblog.aop;

import com.ningmeng.vueblog.annocation.ArticleAnnotation;
import com.ningmeng.vueblog.entity.Article;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;



@Component
@Aspect
public class ArticleAop {

    @Pointcut("@annotation(com.ningmeng.vueblog.annocation.ArticleAnnotation)")
    public void pointCut(){}

    @Before(value = "pointCut()")
    public void injectUpdateTime(JoinPoint joinPoint) {
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        ArticleAnnotation annotation = signature.getMethod().getAnnotation(ArticleAnnotation.class);
        boolean isIterable = annotation.isIterable();
        Object[] args = joinPoint.getArgs();

        if (!isIterable)
            for (Object o : args){
                if (o.getClass().getSimpleName().equals("Article")) {
                    Article article = (Article) o;
                    Long updateTime = article.getUpdateTime();
                    if (updateTime == null){
                        article.setUpdateTime(article.getCreateTime());
                    }
                }
            }
        else {
            for (Object o : args){
                if (Iterable.class.isAssignableFrom(o.getClass())){

                    boolean flag = false;
                    @SuppressWarnings("unchecked")
                    Iterable<Object> iterable = (Iterable<Object>) o;
                    for (Object oo : iterable){
                        if (!oo.getClass().getSimpleName().equals("Article"))
                            break;
                        flag = true;
                    }
                    if (flag){
                        Iterable<Article> articles = (Iterable<Article>) o;
                        for (Article next : articles) {
                            if (next.getUpdateTime() == null) {
                                next.setUpdateTime(next.getCreateTime());
                            }
                        }
                    }
                }
            }

        }
    }

}
