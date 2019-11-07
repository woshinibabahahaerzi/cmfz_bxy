package com.baizhi.service;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName ArticleServiceImpl
 * @Description TODO ()
 * @Author buxy
 * @Date 2019/10/29 22:50
 * @Version 1.0
 **/
@Service
@Transactional
public class ArticleServiceImpl extends BaseApiService implements ArticleService {
    @Autowired
    private ArticleDAO articleMapper;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> selectAllArticles(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Article> articles = articleMapper.selectByRowBounds(article, rowBounds);
        int count = articleMapper.selectCount(article);
        return setResultSuccessDataByPage(articles,page,count%rows==0?count/rows:count/rows+1,count);
    }

    @Override
    public String add(Article article) {
        String id = UUID.randomUUID().toString().replace("-", "");
        article.setId(id);
        article.setCreateDate(new Date());
        try {
            articleMapper.insertSelective(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void update(Article article) {
        System.out.println("article=========**********========="+article);
        articleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public void delete(Article article) {
        articleMapper.deleteByPrimaryKey(article.getId());
    }
}
