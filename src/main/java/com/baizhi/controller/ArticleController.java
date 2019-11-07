package com.baizhi.controller;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @ClassName ArticleController
 * @Description TODO ()
 * @Author buxy
 * @Date 2019/10/29 22:52
 * @Version 1.0
 **/
@RestController
@RequestMapping("article")
public class ArticleController extends BaseApiService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleDAO articleDAO;

    @RequestMapping("selectAllArticles")
    public Map<String,Object> selectAllArticles(Integer page,Integer rows){
        Map<String, Object> map = articleService.selectAllArticles(page, rows);
        return map;
    }


    @RequestMapping("browser")
    public Map<String,Object> browser(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        //http://localhost:8989/cmfz/statics/image
        String url = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/statics/image/";

        File file = new File(request.getSession().getServletContext().getRealPath("statics/image"));
        File[] files = file.listFiles();
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            Map<String, Object> photo = new HashMap<>();
            photo.put("is_dir",false);
            photo.put("has_file",false);
            photo.put("filesize",f.length());
            photo.put("is_photo",true);
            String extension = FilenameUtils.getExtension(f.getName());
            photo.put("filetype", extension);
            photo.put("filename",f.getName());
            photo.put("datetime",new Date());
            list.add(photo);
        }
        map.put("current_url",url);
        map.put("total_count",files.length);
        map.put("file_list",list);
        return map;
    }

    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile imgFile, HttpServletRequest request){
        //{"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}
        Map<String, Object> map = new HashMap<>();
        //文件上传
        try {
            System.out.println("imgFile:      "+imgFile.getOriginalFilename());
            imgFile.transferTo(new File(request.getSession().getServletContext().getRealPath("/statics/image"),imgFile.getOriginalFilename()));
            //给与响应
            String url = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/statics/image/"+imgFile.getOriginalFilename();
            map.put("error",0);
            map.put("url",url);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            return map;
        }
    }

    //导出文章
    @RequestMapping("exportArticle")
    public void exportArticle(HttpServletResponse response){
        List<Article> articles = articleDAO.selectAll();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("article");
        String[] strings = {"编号","标题","作者","创建日期"};
        Row row = sheet.createRow(0);
        for (int i = 0; i < strings.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(strings[i]);
        }
        for (int i = 0; i < articles.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(articles.get(i).getId());
            row1.createCell(1).setCellValue(articles.get(i).getTitle());
            row1.createCell(2).setCellValue(articles.get(i).getAuthor());
            row1.createCell(3).setCellValue(articles.get(i).getCreateDate());
        }
        try {
            String fileName = URLEncoder.encode("article.xls", "UTF-8");
            response.setHeader("content-disposition","attachment;filename="+fileName);
            response.setContentType("application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //导出文章
    @RequestMapping("importArticle")
    public void importArticle(MultipartFile article){
        try {
            Workbook workbook = new HSSFWorkbook(article.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, Article article) {
        Map<String, Object> map = new HashMap<>(16);
        String add="add";
        String update="edit";
        String del="del";
        if (add.equals(oper)){
            map = add(article);
        }if (update.equals(oper)){
            map = update(article);
        }if (del.equals(oper)){
            map = delete(article);
        }
        return map;
    }


    public Map<String, Object> add(Article article) {

        try {
            String id = articleService.add(article);
            return setResultSuccessData(id);
        } catch (Exception e) {
            e.printStackTrace();
            return setResultParamterError(e.getMessage());
        }
    }

    public Map<String, Object> update(Article article) {
        /*if ("".equals(article.getContent())){
            article.setContent(null);
        }*/
        try {
            articleService.update(article);
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return setResultParamterError(e.getMessage());
        }
    }

    public Map<String, Object> delete(Article article) {
        try {
            articleService.delete(article);
            return setResultSuccess();
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }



}
