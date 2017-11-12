package edu.hnust.application.dao.it.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import edu.hnust.application.orm.user.User;

@Repository
public interface IUserDao {
    
    /**
     * 登录
     *
     * @param user
     * @return
     */
    public User login(User user);
    
    /**
     * 查找用户列表
     *
     * @param map
     * @return
     */
    public List<User> findUsers(Map<String, Object> map);
    
    /**
     * @param map
     * @return
     */
    public Long getTotalUser(Map<String, Object> map);
    
    /**
     * 实体修改
     *
     * @param user
     * @return
     */
    public int updateUser(User user);
    
    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public Integer addUser(User user);
    
    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    public int deleteUser(Integer id);
    
    /**
     * 查找用户
     * 
     * @param id
     * @return
     */
    public User findById(Integer id);
    
    /**
     * 查询用户上下级别
     * 
     * @param map
     * @return
     */
    public List<User> findUserSubOrSup(Map<String, Object> map);
    
    /**
     * 查询用户
     * 
     * @param groupId
     * @return
     */
    public List<User> findUsersByGroupId(int groupId);
    
    /**
     * 查询组不包含的用户
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
    public List<User> findUsersByFactoryId(int factoryId);
    
    /**
     * 查询供应所权限用户
     * 
     * @param providerId
     * @return
     */
    public List<User> findUsersByProviderId(int providerId);
}