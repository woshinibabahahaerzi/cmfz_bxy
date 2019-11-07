package com.baizhi.controller;

import com.baizhi.api.BaseApiService;
import com.baizhi.service.AdminService;
import com.baizhi.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController extends BaseApiService {

    @Autowired
    private AdminService adminService;

    @RequestMapping("imageCode")
    /**
      * @Author: bxy
      * @Description: 
      * @Date: 2019/10/25
      * @Param session: 
     * @Param response: 
      void 
      **/
    public void imageCode(HttpSession session, HttpServletResponse response) {
        CreateValidateCode instance = CreateValidateCode.Instance();
        String code = instance.getString();
        //将验证码存入session
        session.setAttribute("sessionCode", code);
        System.out.println("验证码：      " + code);
        //设置响应类型
        response.setContentType("image/png");
        //生成验证码图片
        BufferedImage image = instance.getImage();
        //将图片以流的形式响应给页面
        try {
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("系统错误，验证码生成失败");
        }
    }

    @RequestMapping("login")
    /**
      * @Author: bxy
      * @Description: 这是后台管理员的登录方法
      * @Date: 2019/10/25
      * @Param adminName: 前台接收的管理员账号
     * @Param adminPassword:  密码
     * @Param enCode:   验证码
     * @Param session:   
      java.util.Map<java.lang.String,java.lang.Object> 
      **/
   /**
    * @Author: bxy
    * @Description: 
    * @Date: 2019/10/25
    * @Param adminName:
 * @Param adminPassword:
 * @Param enCode:
 * @Param session:
    java.util.Map<java.lang.String,java.lang.Object> 
   **/
    public Map<String, Object> login (String adminName, String adminPassword, String enCode, HttpSession session) {
        try {
            adminService.login(adminName, adminPassword, enCode, session);
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return setResultError(e.getMessage());
        }
    }
}