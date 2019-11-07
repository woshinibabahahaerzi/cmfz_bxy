package com.baizhi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.UserDAO;
import com.baizhi.entity.Album;
import com.baizhi.entity.Student;
import com.baizhi.entity.Teacher;
import com.baizhi.entity.User;
import io.goeasy.GoEasy;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = CmfzBxyApplication.class)
@RunWith(SpringRunner.class)
public class CmfzBxyApplicationTests {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AlbumDAO albumDAO;

    @Test
    public void contextLoads() {
        /*List<User> users = userDAO.selectAll();
        for (User user : users) {
            System.out.println(user);
        }*/
        User user = new User();
        /*user.setUsername("zhangsan");
        user.setPassword("545");
        List<User> list = userDAO.select(user);
        for (User user1 : list) {
            System.out.println(user1);
        }*/
        /*User user1 = userDAO.selectByPrimaryKey("1");
        System.out.println(user1);*/
        /*user.setUsername("zhangsan");
        int i = userDAO.selectCount(user);
        System.out.println("============"+i);*/
        //第一个参数  下标    第二个参数   每页条数   page 2  rows  2
        /*RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userDAO.selectByRowBounds(user, rowBounds);
        for (User user1 : users) {
            System.out.println("==========="+user1);
        }*/

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //criteria.andLike("username","__h%");
        criteria.andBetween("age",10,40).
                 andGreaterThan("age",1)
                .andIsNull("password");
        List<User> users = userDAO.selectByExample(example);
        for (User user1 : users) {
            System.out.println("=========="+user1);
        }
    }

    @Test
    public void testMapper() {
        User user = new User();
        user.setId("1");
        user.setUsername("xxxxx");
        /*user.setUsername("zhangsan");
        int i = userDAO.delete(user);
        System.out.println("============="+i);*/
        //int i = userDAO.insert(user);
        //int i = userDAO.insertSelective(user);
        //System.out.println("========="+i);
//        int i = userDAO.updateByPrimaryKeySelective(user);
//        System.out.println("=========="+i);
        /*List<User> users = userDAO.selectAll();
        for (User user1 : users) {
            System.out.println(user1);
        }*/
        /*User userById = userDAO.selectUserById("1");
        System.out.println(userById);
        */

        List<User> users = userDAO.selectSome();
        for (User user1 : users) {
            System.out.println(user1);
        }

    }


    @Test
    public void testPOI() {
        List<Album> albums = albumDAO.selectAll();
        //创建工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建样式
        CellStyle cellStyle1 = workbook.createCellStyle();
        //创建字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setFontName("楷体");
        font.setItalic(true);
        font.setUnderline(Font.U_DOUBLE_ACCOUNTING);
        cellStyle1.setFont(font);
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        //创建样式
        CellStyle cellStyle = workbook.createCellStyle();
        //创建日期格式
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd日 HH时mm分ss秒");
        cellStyle.setDataFormat(format);
        //创建工作表 sheet
        Sheet sheet = workbook.createSheet("album");
        //设置列宽  第一个参数下标 给那一列设置宽度   第二个参数  宽度是多少  *256
        sheet.setColumnWidth(3,50*256);
        //创建第一行  参数：第几行   下标
        Row row = sheet.createRow(0);
        String[] strings ={"编号","标题","集数","创建日期"};
        for (int i = 0; i < strings.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle1);
            //给单元格赋值
            cell.setCellValue(strings[i]);
        }
        for (int i = 0; i < albums.size(); i++) {
            Row row1 = sheet.createRow(i+1);
            //创建单元格
            row1.createCell(0).setCellValue(albums.get(i).getId());
            row1.createCell(1).setCellValue(albums.get(i).getTitle());
            row1.createCell(2).setCellValue(albums.get(i).getChapterCount());
            Cell cell = row1.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(albums.get(i).getCreateDate());
        }
        //写出到磁盘
        try {
            workbook.write(new FileOutputStream("D:/album.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testPOIImport() {
        try {
            Workbook workbook = new HSSFWorkbook(new FileInputStream("D:/album.xls"));
            Sheet sheet = workbook.getSheet("album");
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i < lastRowNum; i++) {
                Row row = sheet.getRow(i+1);
                String id    = row.getCell(0).getStringCellValue();
                String title = row.getCell(1).getStringCellValue();
                double count = row.getCell(2).getNumericCellValue();
                Date date    = row.getCell(3).getDateCellValue();
                Album album = new Album();
                album.setId(id);
                album.setTitle(title);
                album.setChapterCount((int)count);
                album.setCreateDate(date);
                System.out.println(album);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testEasyPOI() {

        List<Student> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Student student = new Student(i+"", "小花花"+i, 10+i, new Date(),"F:\\source\\cmfz_bxy\\src\\main\\webapp\\statics\\image\\shouye.png");
            list.add(student);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("java168班学生","学生"),
                Student.class, list);
        try {
            workbook.write(new FileOutputStream("D:/student.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEasyPOI1() {

        List<Student> list1 = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Student student = new Student(i+"", "小花花"+i, 10+i, new Date(),"F:\\source\\cmfz_bxy\\src\\main\\webapp\\statics\\image\\shouye.png");
            list1.add(student);
        }

        List<Student> list2 = new ArrayList<>();
        for (int i = 11; i <= 20; i++) {
            Student student = new Student(i+"", "小飞飞"+i, 10+i, new Date(),"F:\\source\\cmfz_bxy\\src\\main\\webapp\\statics\\image\\shz.png");
            list2.add(student);
        }

        List<Teacher> teachers = new ArrayList<>();
        Teacher suns = new Teacher("111", "suns", "男", list1);
        Teacher liucy = new Teacher("222", "liucy", "男", list2);
        teachers.add(suns);
        teachers.add(liucy);


        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("百知学生信息管理", "一六八班", "168"),
                Teacher.class, teachers);
        try {
            workbook.write(new FileOutputStream("D:/student.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGoEasy(){
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-e5d4279b789f4f21903f02cf71db6e00");
        goEasy.publish("qwe", "Hello, GoEasy!");
    }



    @Test
    public void testMsg(){


        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FpdDyyvtbkErxXYPam5", "AT6wXqPUOeHEmKSj6Q4AC4BPnzjs2O");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15171361114,15397730552,17671115652");
        request.putQueryParameter("SignName", "天圆地方");
        request.putQueryParameter("TemplateCode", "SMS_164100021");//SMS_164100021
        request.putQueryParameter("TemplateParam", "{\"code\":\"666666\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }



}
