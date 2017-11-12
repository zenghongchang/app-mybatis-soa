package com.hnust.dao.it.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hnust.orm.user.UserSubordinate;

@Repository
public interface IUserSubordinateDao {
    
    /**
     * 查找用户下级
     *
     * @param map
     * @return
     */
    public List<UserSubordinate> findAllSubOrSups(Map<String, Object> map);
    
    /**
     * 查找用户上级
     * 
     * @param subirdinateId
     * @return
     */
    public List<UserSubordinate> findSuperior(Integer subirdinateId);
    
    /**
     * 增加用户上下级
     * 
     * @param userSubordinate
     */
    public int addUserSubordinate(UserSubordinate userSubordinate);
    
    /**
     * 删除下级
     * 
     * @param userId
     * @return
     */
    public int deleteUserSubordinate(Map<String, Object> map);
    
    /**
     * 批量增加上下级
     * 
     * @param list
     * @return
     */
    public Integer addSubordinateBatch(List<UserSubordinate> list); 
}