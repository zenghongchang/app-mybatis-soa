package edu.hnust.application.mongo.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hnust.application.dao.mongo.user.IUserDao;
import edu.hnust.application.mongo.service.it.user.IUserService;
import edu.hnust.application.orm.user.User;

@Service("mongoUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao mongoUserDao;
    
    @Override
    public int addUser(User user) {
        return mongoUserDao.addUser(user);
    }
}