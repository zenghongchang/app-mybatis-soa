package edu.hnust.application.service.impl.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import edu.hnust.application.dao.it.user.IUserDao;
import edu.hnust.application.dao.it.user.IUserGroupDao;
import edu.hnust.application.dao.it.user.IUserSubordinateDao;
import edu.hnust.application.exception.ServiceException;
import edu.hnust.application.orm.user.User;
import edu.hnust.application.orm.user.UserGroup;
import edu.hnust.application.orm.user.UserSubordinate;
import edu.hnust.application.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
    public static final Logger log = Logger.getLogger(UserServiceImpl.class);// 日志文件
    
    @Resource
    private IUserDao userDao;    
    @Resource
    private IUserGroupDao groupDao;    
    @Resource
    private IUserSubordinateDao userSubordinateDao;
    
    @Override
    public User login(User user) {
        log.info("login user:" + user.getUserName() + " - " + user.getPassword());
        return userDao.login(user);
    }
    
    @Override
    public List<User> findUser(Map<String, Object> map) {
        return userDao.findUsers(map);
    }
    
    @Override
    public Boolean updateUser(User user) {
        List<Integer> existGroupIds = new ArrayList<Integer>();
        List<Integer> existSubs = new ArrayList<Integer>();
        List<Integer> existSups = new ArrayList<Integer>();        
        if (null != user.getId()) {
            Map<String, Object> gMap = new HashMap<String, Object>();
            gMap.put("userId", user.getId());
            List<UserGroup> ups = groupDao.findUserGroups(gMap);
            List<Integer> deleteGroupIds = new ArrayList<Integer>();
            for (UserGroup up : ups) {
                if (null != user.getGroupIds() && user.getGroupIds().contains(up.getId())) {// 存在的不变动
                    existGroupIds.add(up.getGroupId());
                } else {
                    deleteGroupIds.add(up.getGroupId());// 不存在则删除
                }
            }            
            // 下级
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", user.getId());
            List<UserSubordinate> subs = userSubordinateDao.findAllSubOrSups(map);
            List<Integer> deleteSubIds = new ArrayList<Integer>();
            for (UserSubordinate sub : subs) {
                if (null != user.getSubIds() && user.getSubIds().contains(sub.getSubordinateId())) {
                    existSubs.add(sub.getSubordinateId());
                } else {
                    deleteSubIds.add(sub.getSubordinateId());
                }
            }            
            // 上级
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("subordinateId", user.getId());
            List<UserSubordinate> sups = userSubordinateDao.findAllSubOrSups(map2);
            List<Integer> deleteSupIds = new ArrayList<Integer>();
            for (UserSubordinate sup : sups) {
                if (null != user.getSupIds() && user.getSupIds().contains(sup.getUserId())) {
                    existSups.add(sup.getUserId());
                } else {
                    deleteSupIds.add(sup.getUserId());
                }
            }            
            // delete
            if (!CollectionUtils.isEmpty(deleteGroupIds)) {
                Map<String, Object> groupMap = new HashMap<String, Object>();
                groupMap.put("userId", user.getId());
                groupMap.put("groupIds", deleteGroupIds);
                if (groupDao.deleteGroupByIds(groupMap) < 1) {
                    throw new ServiceException();
                }
            }
            
            if (!CollectionUtils.isEmpty(deleteSubIds)) {
                Map<String, Object> subMap = new HashMap<String, Object>();
                subMap.put("userId", user.getId());
                subMap.put("subIds", deleteSubIds);
                if (userSubordinateDao.deleteUserSubordinate(subMap) < 1) {
                    throw new ServiceException();
                }
            }            
            if (!CollectionUtils.isEmpty(deleteSupIds)) {
                Map<String, Object> supMap = new HashMap<String, Object>();
                supMap.put("subordinateId", user.getId());
                supMap.put("userIds", deleteSupIds);
                if (userSubordinateDao.deleteUserSubordinate(supMap) < 1) {
                    throw new ServiceException();
                }
            }
        }        
        List<UserGroup> groups = new ArrayList<UserGroup>();
        if (null != user.getGroupIds()) {
            for (Integer groupId : user.getGroupIds()) {
                if (existGroupIds.contains(groupId)) {
                    continue;
                }
                UserGroup userGroup = new UserGroup();
                userGroup.setUserId(user.getId());
                userGroup.setGroupId(groupId);
                groups.add(userGroup);
            }
        }
        List<UserSubordinate> insertSubs = new ArrayList<UserSubordinate>();
        if (null != user.getSubIds()) {
            for (Integer subId : user.getSubIds()) {
                if (existSubs.contains(subId)) {
                    continue;
                }
                UserSubordinate userSub = new UserSubordinate();
                userSub.setUserId(user.getId());
                userSub.setSubordinateId(subId);
                insertSubs.add(userSub);
            }
        }
        List<UserSubordinate> insertSups = new ArrayList<UserSubordinate>();
        if (null != user.getSupIds()) {
            for (Integer supId : user.getSupIds()) {
                if (existSups.contains(supId)) {
                    continue;
                }
                UserSubordinate userSup = new UserSubordinate();
                userSup.setUserId(supId);
                userSup.setSubordinateId(user.getId());
                insertSups.add(userSup);
            }
        }
        
        if (!CollectionUtils.isEmpty(groups)) {
            if (groupDao.addUserGroupBatch(groups) < groups.size()) {
                throw new ServiceException();
            }
        }
        if (!CollectionUtils.isEmpty(insertSubs)) {
            if (userSubordinateDao.addSubordinateBatch(insertSubs) < insertSubs.size()) {
                throw new ServiceException();
            }
        }
        if (!CollectionUtils.isEmpty(insertSups)) {
            if (userSubordinateDao.addSubordinateBatch(insertSups) < insertSups.size()) {
                throw new ServiceException();
            }
        }
        if (userDao.updateUser(user) < 0) {
            throw new ServiceException();
        }
        return true;
    }
    
    @Override
    public Long getTotalUser(Map<String, Object> map) {
        return userDao.getTotalUser(map);
    }
    
    @Override
    public Boolean addUser(User user) {
        Integer userId = userDao.addUser(user);
        if (userId <= 0) {
            throw new ServiceException();
        }
        user.setId(user.getId());
        List<UserGroup> groups = new ArrayList<UserGroup>();
        if (null != user.getGroupIds()) {
            for (Integer groupId : user.getGroupIds()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setUserId(user.getId());
                userGroup.setGroupId(groupId);
                groups.add(userGroup);
            }
        }
        List<UserSubordinate> insertSubs = new ArrayList<UserSubordinate>();
        if (null != user.getSubIds()) {
            for (Integer subId : user.getSubIds()) {
                UserSubordinate userSub = new UserSubordinate();
                userSub.setUserId(user.getId());
                userSub.setSubordinateId(subId);
                insertSubs.add(userSub);
            }
        }
        List<UserSubordinate> insertSups = new ArrayList<UserSubordinate>();
        if (null != user.getSupIds()) {
            for (Integer supId : user.getSupIds()) {
                UserSubordinate userSup = new UserSubordinate();
                userSup.setUserId(supId);
                userSup.setSubordinateId(user.getId());
                insertSups.add(userSup);
            }
        }
        if (!CollectionUtils.isEmpty(groups)) {
            if (groupDao.addUserGroupBatch(groups) < groups.size()) {
                throw new ServiceException();
            }
        }
        if (!CollectionUtils.isEmpty(insertSubs)) {
            if (userSubordinateDao.addSubordinateBatch(insertSubs) < insertSubs.size()) {
                throw new ServiceException();
            }
        }
        if (!CollectionUtils.isEmpty(insertSups)) {
            if (userSubordinateDao.addSubordinateBatch(insertSups) < insertSups.size()) {
                throw new ServiceException();
            }
        }
        
        return true;
    }
    
    @Override
    public int deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }
    
    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }
    
    @Override
    public List<User> findUserSubOrSup(Map<String, Object> map) {
        return userDao.findUserSubOrSup(map);
    }
    
    @Override
    public List<User> findUsersByGroupId(int groupId) {
        return userDao.findUsersByGroupId(groupId);
    }
    
    @Override
    public List<User> findUsersByGroupIdExcept(int groupId) {
        return userDao.findUsersByGroupIdExcept(groupId);
    }
    
    @Override
    public List<User> findUsersByFactoryId(Integer factoryId) {
        return userDao.findUsersByFactoryId(factoryId);
    }
    
    @Override
    public List<User> findUsersByProviderId(int providerId) {
        return userDao.findUsersByProviderId(providerId);
    }
}