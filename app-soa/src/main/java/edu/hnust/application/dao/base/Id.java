package edu.hnust.application.dao.base;

public class Id {
    private String id;
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id [id=" + id + "]";
    }
}
