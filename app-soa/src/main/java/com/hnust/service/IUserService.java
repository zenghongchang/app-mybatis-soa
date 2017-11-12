package com.hnust.service;

import java.util.List;
import java.util.Map;

import com.hnust.exception.ServiceException;
import com.hnust.orm.user.User;

public interface IUserService {
    /**
     * @param user
     * @return
     */
    public User login(User user)
        throws ServiceException;
    
    /**
     * @param map
     * @return
     */
    public List<User> findUser(Map<String, Object> map)
        throws ServiceException;
    
    /**
     * @param map
     * @return
     */
    public Long getTotalUser(Map<String, Object> map)
        throws ServiceException;
    
    /**
     * @param user
     * @return
     */
    public Boolean updateUser(User user)
        throws ServiceException;
    
    /**
     * @param id
     * @return
     */
    public int deleteUser(Integer id)
        throws ServiceException;
    
    /**
     * @param id
     * @return
     */
    public User findById(Integer id);
    
    /**
     * 查找该用户对应的上级或者下级 map.put("userId",userId);查找下级 map.put("subordinateId",subordinateId)查找上级
     * 
     * @param id
     * @return
     */
    public List<User> findUserSubOrSup(Map<String, Object> map);
    
    /**
     * 查找某用户组所有User
     * 
     * @param groupId
     * @return
     */
    public List<User> findUsersByGroupId(int groupId);
    
    /**
     * 
     * @param groupId
     * @return
     */
    public List<User> findUsersByGroupIdExcept(int groupId);
    
    /**
     * 查询工厂权限的用户
     * 
     * @param factoryId
     * @return
     */
    public List<User> findUsersByFactoryId(Integer factoryId);
    
    /**
     * 查询供应商权限
     * 
     * @param factoryId
     * @return
     */
    public List<User> findUsersByProviderId(int parseInt);
    
    /**
     * 新增用户 对应用户组 用户上下级
     * 
     * @param user
     * @param groups
     * @param insertSubs
     * @param insertSups
     * @return
     */
    public Boolean addUser(User user);
}