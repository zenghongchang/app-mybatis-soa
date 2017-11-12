package edu.hnust.application.orm.user;

public class UserSubordinate {
    private Integer id;    
    private Integer userId;    
    private Integer subordinateId;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getSubordinateId() {
        return subordinateId;
    }
    
    public void setSubordinateId(Integer subordinateId) {
        this.subordinateId = subordinateId;
    }
    
    @Override
    public String toString() {
        return "UserSubordinate [id=" + id + ", userId=" + userId + ", subordinateId=" + subordinateId + "]";
    }    
}