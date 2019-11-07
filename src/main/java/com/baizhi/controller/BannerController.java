package com.baizhi.controller;

import com.baizhi.api.BaseApiService;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 类描述信息 ()
 *
 * @author bxy
 * @Date 2019/10/28$ 10:00$
 * @version: V_1.0.0
 */
@RestController
@RequestMapping("banner")
public class BannerController extends BaseApiService {

    @Autowired
    private BannerService bannerService;


    @RequestMapping("selectAllBanners")
    public Map<String,Object> selectAllBanners(Integer page,Integer rows){
        Map<String, Object> map = bannerService.selectAllBanners(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Banner banner){
        Map<String, Object> map = null;
        if ("add".equals(oper)){
            map = add(banner);
        }if ("edit".equals(oper)){
            map = edit(banner);
        }if ("del".equals(oper)){
            map = del(banner);
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException {
        //文件上传
        cover.transferTo(new File(request.getSession().getServletContext().getRealPath("statics/banner/image"),cover.getOriginalFilename()));
        //修改数据库中的图片路径
        Banner banner = new Banner();
        banner.setId(id);
        banner.setCover(cover.getOriginalFilename());
        bannerService.update(banner);

    }


    public Map<String,Object> add(Banner banner){
        String id = null;
        try {
            id = bannerService.add(banner);
            return setResultSuccessData(id);
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }

    public Map<String,Object> edit(Banner banner){
        try {
            bannerService.update(banner);
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }

    public Map<String,Object> del(Banner banner){
        try {
            bannerService.delete(banner);
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }



}
