package com.hnust.dao.it.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hnust.orm.user.UserGroup;

@Repository
public interface IUserGroupDao {
	/**
	 * 查找用户组列表
	 *
	 * @param map
	 * @return
	 */
	public List<UserGroup> findUserGroups(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public Long getTotalUserGroup(Map<String, Object> map);

	/**
	 * 实体修改
	 *
	 * @param UserGroup
	 * @return
	 */
	public int updateUserGroup(UserGroup userGroup);

	/**
	 * 添加用户组
	 *
	 * @param UserGroup
	 * @return
	 */
	public int addUserGroup(UserGroup userGroup);
	
	/**
	 * 批量添加用户组
	 *
	 * @param UserGroup
	 * @return
	 */
	public int addUserGroupBatch(List<UserGroup> userGroups);

	/**
	 * 删除用户组
	 *
	 * @param id
	 * @return
	 */
	public int deleteUserGroup(Integer id);
	
	/**
	 * 根据组名称或组代码查找组用户id
	 * @param queryMap
	 * @return
	 */
	public List<Integer> fetchByGroupNameOrCode(Map<String,Object>queryMap);

	/**
	 * 删除用户对应的组
	 * @param id
	 * @return
	 */
	public int deleteUserGroupByUserId(Integer id);
	
	public List<?> findMaxLevelGroup();

	public Integer deleteGroupByIds(Map<String, Object> map);

	/**
	 * 删除组对应的用户
	 * @param id
	 * @return
	 */
	public Integer deleteUserByIds(Map<String, Object> map);

	/**
	 * 删除用户对应的组By GroupId
	 * @param id
	 * @return
	 */
	public int deleteUserGroupByGroupId(Integer id);
}
