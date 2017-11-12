package com.hnust.dao.mongo.user.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hnust.dao.base.MongoDao;
import com.hnust.dao.mongo.user.IUserDao;
import com.hnust.orm.user.User;

@Repository("mongoUserDao")
public class UserDaoImpl implements IUserDao {    
    @Autowired
    private MongoDao<User> mongoDao;
    
    @Override
    public User login(User user) {
        return null;
    }
    
    @Override
    public List<User> findUsers(Map<String, Object> map) {
        return null;
    }
    
    @Override
    public Long getTotalUser(Map<String, Object> map) {
        return null;
    }
    
    @Override
    public int updateUser(User user) {
        return 0;
    }
    
    @Override
    public int addUser(User user) {
        Integer id = Integer.valueOf(mongoDao.getNextId(user.getClass()));
        user.setId(id);
        mongoDao.saveOrUpdate(user);
        return id;
    }
    
    @Override
    public int deleteUser(Integer id) {

        return 0;
    }
}