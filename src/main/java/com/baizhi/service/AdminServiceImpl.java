package com.baizhi.service;

import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDAO adminDAO;

    @Override
    public void login(String adminName, String adminPassword, String enCode, HttpSession session) {
        if (!"".equals(enCode) || enCode==null){
            if (!"".equals(adminName) || adminName==null){
                if (!"".equals(adminPassword) || adminPassword==null){
                    String sessionCode = (String) session.getAttribute("sessionCode");
                    if (!enCode.equals(sessionCode)){
                        Admin admin = new Admin();
                        admin.setAdminName(adminName);
                        Admin dbAdmin = adminDAO.selectOne(admin);
                        if (dbAdmin.getAdminPassword().equals(adminPassword)){
                            session.setAttribute("admin",dbAdmin.getAdminNickname());
                            System.out.println("昵称：    "+dbAdmin.getAdminNickname());
                        }else {
                            throw  new RuntimeException("密码错误！");
                        }
                    }else {
                        throw  new RuntimeException("验证码错误！");
                    }
                }else {
                    throw  new RuntimeException("密码不能为空！");
                }
            }else {
                throw  new RuntimeException("用户名不能为空！");
            }
        }else {
            throw  new RuntimeException("验证码不能为空！");
        }
    }
}
