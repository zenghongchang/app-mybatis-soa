package edu.hnust.application.orm.user;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    // id
    private Integer id;    
    // 按鈕名
    private String menuName;    
    // 国际化key
    private String i18nKey;    
    // 連接
    private String url;    
    // 父级菜单
    private Integer parent;    
    // 创建时间
    private String createDate;    
    // 更新时间
    private String updateDate;    
    // 创建
    private Integer creater;    
    // 系统备注
    private String sysRemark;    
    private List<Acl> acls;
    
    public List<Acl> getAcls() {
        return acls;
    }
    
    public void setAcls(List<Acl> acls) {
        this.acls = acls;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getMenuName() {
        return menuName;
    }
    
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
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
    
    public Integer getCreater() {
        return creater;
    }
    
    public void setCreater(Integer creater) {
        this.creater = creater;
    }
    
    public String getSysRemark() {
        return sysRemark;
    }
    
    public void setSysRemark(String sysRemark) {
        this.sysRemark = sysRemark;
    }
    
    public Integer getParent() {
        return parent;
    }
    
    public void setParent(Integer parent) {
        this.parent = parent;
    }
    
    public String getI18nKey() {
        return i18nKey;
    }
    
    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }
    
    @Override
    public String toString() {
        return "Menu [id=" + id + ", menuName=" + menuName + ", i18nKey=" + i18nKey + ", url=" + url + ", parent=" + parent + ", createDate=" + createDate + ", updateDate=" + updateDate + ", creater=" + creater + ", sysRemark=" + sysRemark + ", acls=" + acls + "]";
    }
}