package com.hnust.dao.it.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hnust.orm.user.Group;

@Repository
public interface IGroupDao {
    /**
     * 查找组列表
     *
     * @param map
     * @return
     */
    public List<Group> findGroups(Map<String, Object> map);
    
    /**
     * 查找组
     * 
     * @param id
     * @return
     */
    public Group findById(Integer id);
    
    /**
     * @param map
     * @return
     */
    public Long getTotalGroup(Map<String, Object> map);
    
    /**
     * 实体修改
     * 
     * @param Group
     * @return
     */
    public int updateGroup(Group group);
    
    /**
     * 添加组
     *
     * @param Group
     * @return
     */
    public int addGroup(Group group);
    
    /**
     * 删除组
     *
     * @param id
     * @return
     */
    public int deleteGroup(Integer id);
    
    /**
     * 根据用户id查找用户组
     * 
     * @param id
     * @return
     */
    public List<Group> findGroupsByUserId(Integer id);
    
}
