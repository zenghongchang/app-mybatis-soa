package edu.hnust.application.dto;

import java.io.Serializable;
import java.util.List;

import edu.hnust.application.orm.user.Acl;
import edu.hnust.application.orm.user.Group;
import edu.hnust.application.orm.user.Menu;

public class GroupInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Group group;    
    private List<Menu> menus;    
    private List<Acl> acls;
    
    public Group getGroup() {
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
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
