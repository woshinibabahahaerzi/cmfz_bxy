package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

/**
 * @ClassName AlbumService
 * @Description TODO ()
 * @Author buxy
 * @Date 2019/10/28 22:46
 * @Version 1.0
 **/
public interface ArticleService {
    Map<String,Object> selectAllArticles(Integer page, Integer rows);

    String add(Article article);

    void update(Article article);

    void delete(Article article);


}
