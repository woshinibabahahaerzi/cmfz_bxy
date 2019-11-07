package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.Map;

public interface BannerService {

    /**
     * @Author: bxy
     * @Description:  展示所有轮播图
     * @Date: 2019/10/28
     * @Param page:
     * @Param rows:
     java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> selectAllBanners(Integer page,Integer rows);

    String add(Banner banner);

    void update(Banner banner) ;

    void delete(Banner banner);


}
