package edu.hnust.application.orm.user;

import java.io.Serializable;

/**
 * 组按钮权限表
 * @author tomtop327
 *
 */
public class GroupAclPermissions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Integer id;
	public Integer aclId;// 按钮ID
	public Integer groupId;//组id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAclId() {
		return aclId;
	}
	public void setAclId(Integer aclId) {
		this.aclId = aclId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}
