package edu.hnust.application.exception;

@SuppressWarnings("serial")
public class ServiceException extends BaseException {
    
    private String code;    
    private String desc;    
    private Throwable throwable;
    
    public ServiceException() {
        super();
    }
    
    public ServiceException(String code, String desc, Throwable throwable) {
        super();
        this.code = code;
        this.desc = desc;
        this.throwable = throwable;
    }
    
    public ServiceException(String code, String desc) {
        super();
        this.code = code;
        this.desc = desc;
    }
    
    public ServiceException(String desc) {
        super();
        this.desc = desc;
    }
    
    public ServiceException(Throwable throwable) {
        super();
        this.throwable = throwable;
    }
    
    public ServiceException(String desc, Throwable throwable) {
        super();
        this.desc = desc;
        this.throwable = throwable;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public Throwable getThrowable() {
        return throwable;
    }
    
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
