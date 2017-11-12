package edu.hnust.application.orm.user;

import java.io.Serializable;

public class UserLoginLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Integer userId;
    private String loginName;
    private String ipAddress;
    private String loginAddress;
    private String userAgent;
    private String loginTime;
    private String logoutTime;
    private Long   duration;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getLoginName() {
        return loginName;
    }
    
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getLoginAddress() {
        return loginAddress;
    }
    
    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public String getLoginTime() {
        return loginTime;
    }
    
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
    
    public String getLogoutTime() {
        return logoutTime;
    }
    
    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }
    
    public Long getDuration() {
        return duration;
    }
    
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "UserLoginLog [id=" + id + ", userId=" + userId + ", loginName=" + loginName + ", ipAddress=" + ipAddress + ", loginAddress=" + loginAddress + ", userAgent=" + userAgent + ", loginTime=" + loginTime + ", logoutTime=" + logoutTime + ", duration=" + duration + "]";
    }
}