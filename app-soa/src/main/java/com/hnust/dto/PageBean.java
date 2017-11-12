
package com.hnust.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = -561199328829520324L;
    
    private Integer currentPage = 1;
    
    private Long totalPages = 0L;
    
    private Long totalRecords = 0L;
    
    private Long totalRows = 0L;
    
    private Integer pageLimit = 10;
    
    private List<T> pageList;
    
    private List<Integer> selects;
    
    public PageBean() {
        super();
        this.setSelects(new ArrayList<Integer>());
        this.getSelects().add(10);
        this.getSelects().add(30);
        this.getSelects().add(50);
        this.getSelects().add(100);
        this.getSelects().add(200);
    }
    
    public PageBean(List<T> pageList, Long totalRecords, Integer pageLimit) {
        super();
        this.totalRecords = totalRecords;
        this.pageLimit = pageLimit;
        this.pageList = pageList;
    }
    
    public PageBean(int currentPage, int pageLimit) {
        this.currentPage = currentPage;
        this.pageLimit = pageLimit;
    }
    
    public Integer getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    
    public Long getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
    
    public Long getTotalRecords() {
        return totalRecords;
    }
    
    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
    
    public Long getTotalRows() {
        return totalRows;
    }
    
    public void setTotalRows(Long totalRows) {
        this.totalRows = totalRows;
    }
    
    public Integer getPageLimit() {
        return pageLimit;
    }
    
    public void setPageLimit(Integer pageLimit) {
        this.pageLimit = pageLimit;
    }
    
    public List<T> getPageList() {
        return pageList;
    }
    
    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
    
    public List<Integer> getSelects() {
        return selects;
    }
    
    public void setSelects(List<Integer> selects) {
        this.selects = selects;
    }
}