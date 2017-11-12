package com.hnust.mongo.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnust.dao.mongo.user.IUserDao;
import com.hnust.mongo.service.it.user.IUserService;
import com.hnust.orm.user.User;

@Service("mongoUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao mongoUserDao;
    
    @Override
    public int addUser(User user) {
        return mongoUserDao.addUser(user);
    }
}