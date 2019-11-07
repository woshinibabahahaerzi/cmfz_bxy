package com.baizhi.service;

import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;


@Service
@Transactional

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;



    @Override
    @Transactional(readOnly = true ,propagation = Propagation.SUPPORTS)
    public List<User> selectAll() {
        List<User> users = userDAO.selectAll();

        return users;
    }
}




