package com.baizhi.controller;

import com.baizhi.api.BaseApiService;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * 类描述信息 ()
 *
 * @author bxy
 * @Date 2019/10/28$ 14:22$
 * @version: V_1.0.0
 */
@RestController
@RequestMapping("chapter")
public class ChapterController extends BaseApiService {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("selectAllChaptersByAlbumId")
    public Map<String,Object> selectAllChaptersByAlbumId(String albumId,Integer page,Integer rows){
        Map<String, Object> map = chapterService.selectChaptersByAlbumId(page, rows, albumId);
        return map;
    }

    @RequestMapping("edit")
    public Map<String,Object> edit(String oper,Chapter chapter,String albumId) {
        Map<String, Object> map = null;
        if ("add".equals(oper)){
            map = add(chapter,albumId);
        }if ("edit".equals(oper)){
            map = update(chapter);
        }if ("del".equals(oper)){
            map = delete(chapter,albumId);
        }
        return map;
    }

    public Map<String, Object> add(Chapter chapter,String albumId){
        try {
            String id = chapterService.add(chapter, albumId);
            return setResultSuccessData(id);
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }

    public Map<String, Object> update(Chapter chapter){

        try {
            chapterService.update(chapter);
            System.out.println();
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }

    public Map<String, Object> delete(Chapter chapter,String albumId){
        try {
            chapterService.delete(chapter,albumId);
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }

    @RequestMapping("upload")
    public void upload(MultipartFile url, String id, HttpSession session){
        try {
            //文件上传
            File file = new File(session.getServletContext().getRealPath("statics/chapter/music"), url.getOriginalFilename());
            url.transferTo(file);
            //修改数据库信息
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setUrl(url.getOriginalFilename());

            long urlSize = url.getSize();//...B
            System.out.println("urlSize:        "+urlSize);
            BigDecimal bigDecimal1 = new BigDecimal(urlSize);
            BigDecimal bigDecimal2 = new BigDecimal(1024);
            BigDecimal decimal = bigDecimal1.divide(bigDecimal2).divide(bigDecimal2).setScale(2, RoundingMode.HALF_UP);
            System.out.println("decimal:         "+decimal);
            //文件大小
            chapter.setSize(decimal+"MB");
            Encoder encoder = new Encoder();
            long duration = encoder.getInfo(file).getDuration();
            System.out.println("duration:       "+duration);
            String time = duration / 1000 / 60 + ":" + duration / 1000 % 60;
            System.out.println("time :             "+time);
            //文件时长
            chapter.setDuration(time);
            chapterService.update(chapter);
            System.out.println("章节添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("章节添加失败");

        }


    }




}
