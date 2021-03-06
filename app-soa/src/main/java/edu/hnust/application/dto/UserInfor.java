package edu.hnust.application.dto;

import java.io.Serializable;
import java.util.List;

import edu.hnust.application.orm.user.Acl;
import edu.hnust.application.orm.user.Menu;
import edu.hnust.application.orm.user.User;

public class UserInfor implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private User user;    
    private List<Menu> menus;    
    private List<Acl> acls;    
    private List<GroupInfo> groups;
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public List<GroupInfo> getGroups() {
        return groups;
    }
    
    public void setGroups(List<GroupInfo> groups) {
        this.groups = groups;
    }
    
    public List<Menu> getMenus() {
        return menus;
    }
    
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
    
    public List<Acl> getAcls() {
        return acls;
    }
    
    public void setAcls(List<Acl> acls) {
        this.acls = acls;
    }    
}