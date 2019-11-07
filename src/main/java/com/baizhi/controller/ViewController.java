package com.baizhi.controller;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.dao.BannerDAO;
import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述信息 ()
 *
 * @author bxy
 * @Date 2019/11/4$ 11:14$
 * @version: V_1.0.0
 */
@RestController
public class ViewController {

    @Autowired
    private BannerDAO bannerDAO;
    @Autowired
    private AlbumDAO albumDAO;
    @Autowired
    private ArticleDAO articleDAO;

    @RequestMapping("first_page")
    public Map<String,Object> firstPage(String uid,String type,String sub_type){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        if ("all".equals(type)){
            try {
                //示意代码   查询五张用于展示的轮播图
                List<Banner> banners = bannerDAO.selectAll();
                //示意代码   查询六张专辑
                List<Album> albums = albumDAO.selectAll();
                //示意代码   查询两篇当前用户所属上师最新发表的文章
                List<Article> articles = articleDAO.selectAll();
                map.put("code",200);
                map.put("header",banners);
                map1.put("albums",albums);
                map1.put("articles",articles);
                map.put("body",map1);
                return map;
            } catch (Exception e) {
                map.put("code",500);
                map.put("msg","参数错误");
                e.printStackTrace();
            }
        }if ("wen".equals(type)){
            try {
                //示意代码   查询所有专辑
                List<Album> albums = albumDAO.selectAll();
                map.put("code",200);
                map1.put("albums",albums);
                map.put("body",map1);
                return map;
            } catch (Exception e) {
                map.put("code",500);
                map.put("msg","参数错误");
                e.printStackTrace();
            }
        }if ("si".equals(type)){
            if ("ssyj".equals(sub_type)){
                try {
                    //示意代码   查询两篇当前用户所属上师最新发表的文章
                    Article article = new Article();
                    article.setAuthor("自己");
                    List<Article> articles = articleDAO.select(article);
                    map.put("code",200);
                    map1.put("articles",articles);
                    map.put("body",map1);
                    return map;
                } catch (Exception e) {
                    map.put("code",500);
                    map.put("msg","参数错误");
                    e.printStackTrace();
                }
            }if ("xmfy".equals(sub_type)){
                try {
                    //示意代码   查询两篇当前用户所属上师最新发表的文章
                    Article article = new Article();
                    article.setAuthor("其他");
                    List<Article> articles = articleDAO.select(article);
                    map.put("code",200);
                    map1.put("articles",articles);
                    map.put("body",map1);
                    return map;
                } catch (Exception e) {
                    map.put("code",500);
                    map.put("msg","参数错误");
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public String add(@RequestBody User user){
        System.out.println(user);
        return "123456789";
    }
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public String delete(MultipartFile cover){
        System.out.println(cover.getOriginalFilename());
        return "1111111111";
    }

    }
