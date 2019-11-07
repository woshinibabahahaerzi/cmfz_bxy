package com.baizhi.controller;

import com.baizhi.api.BaseApiService;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
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
@RequestMapping("album")
public class AlbumController extends BaseApiService {

    @Autowired
    private AlbumService albumService;


    @RequestMapping("selectAllAlbums")
    public Map<String,Object> selectAllBanners(Integer page,Integer rows){
        Map<String, Object> map = albumService.selectAllAlbums(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Album album){
        Map<String, Object> map = null;
        if ("add".equals(oper)){
            map = add(album);
        }if ("edit".equals(oper)){
            map = edit(album);
        }if ("del".equals(oper)){
            map = del(album);
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException {

    }


    public Map<String,Object> add(Album album){
        String id = null;
        try {
            id = albumService.add(album);
            return setResultSuccessData(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            return setResultError(e.getMessage());
        }
    }

    public Map<String,Object> edit(Album album){
        try {
            albumService.update(album);
            return setResultSuccess();
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }

    public Map<String,Object> del(Album album){
        try {
            albumService.delete(album);
            System.out.println();
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }



}
