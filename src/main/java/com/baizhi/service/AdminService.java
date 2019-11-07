package com.baizhi.service;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    void login(String adminName, String adminPassword, String enCode, HttpSession session);
}
