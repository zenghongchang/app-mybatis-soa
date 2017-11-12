package edu.hnust.application.orm.user;

import java.io.Serializable;

public class Acl implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	//国际化key
    private String i18nKey;
    private String describe;
    private String aclBtn;
    private String aclUrl;
    private String createDate;
    private String updateDate;
    private Integer createUser;
    private String sysRemark;
    private Integer menuId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getAclBtn() {
		return aclBtn;
	}
	public void setAclBtn(String aclBtn) {
		this.aclBtn = aclBtn;
	}
	public String getAclUrl() {
		return aclUrl;
	}
	public void setAclUrl(String aclUrl) {
		this.aclUrl = aclUrl;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getSysRemark() {
		return sysRemark;
	}
	public void setSysRemark(String sysRemark) {
		this.sysRemark = sysRemark;
	}
	public String getI18nKey() {
		return i18nKey;
	}
	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}
	
}
