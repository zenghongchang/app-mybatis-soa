package edu.hnust.application.dao.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class CommonDaoHelper {
    public static final String DESC = "-1";    
    public static final String ASC = "1"; 
    public static final Integer PAGE_SIZE = Integer.valueOf(20);    
    public static final Integer PAGE_NUMBER = Integer.valueOf(1);    
    public static final Integer MAX_PAGE_SIZE = Integer.valueOf(5000);    
    public static final String WHERE = "$where";    
    public static final String OR = "$or";    
    public static final String AND = "$and";    
    public static final String GE = "$ge";
    public static final String LE = "$le";    
    public static final String GT = "$gt";    
    public static final String LT = "$lt";    
    public static final String IN = "$in";    
    public static final String NOT_IN = "$not_in";    
    public static final String NE = "$ne";    
    public static final String LIKE = "$like";    
    public static final String LEFT_LIKE = "$left_like";    
    public static final String RIGHT_LIKE = "$right_like";    
    public static final String NOT_LIKE = "$not_like";    
    public static final String NOT_LEFT_LIKE = "$not_left_like";    
    public static final String NOT_RIGHT_LIKE = "$not_right_like";    
    public static final String NULL = "$null";    
    public static final String NOT_NULL = "$not_null";
    
    /**
     * 
     * 排序域
     * 
     * @param requestArgs
     * @return
     * @author Henry(fba02)
     * @version [版本号, 2017年11月11日]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static String getRequestSortField(Map<String, Object> requestArgs) {
        String sortField = "id";
        Map<String, Object> sort = (HashMap<String, Object>)requestArgs.get("sort");
        if (null != sort) {
            Iterator<String> localIterator = sort.keySet().iterator();
            if (localIterator.hasNext()) {
                String key = (String)localIterator.next();
                sortField = key;
            }
        }
        return sortField;
    }
    
    /**
     * 排序域
     * 
     * @param requestArgs
     * @return
     * @author Henry(fba02)
     * @version [版本号, 2017年11月11日]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static String getRequestSortDirection(Map<String, Object> requestArgs) {
        String sortDirection = "-1";
        Map<String, Object> sort = (HashMap<String, Object>)requestArgs.get("sort");
        if (null != sort) {
            Iterator<String> localIterator = sort.keySet().iterator();
            if (localIterator.hasNext()) {
                String key = (String)localIterator.next();
                sortDirection = sort.get(key).toString();
            }
        }
        return sortDirection;
    }
    
    /**
     * 分页域
     * @param requestArgs
     * @return
     * @author  Henry(fba02)
     * @version  [版本号, 2017年11月11日]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static Integer getRequestPageSize(Map<String, Object> requestArgs) {
        Integer pageSize = MAX_PAGE_SIZE;
        Map<String, Object> pagination = (HashMap<String, Object>)requestArgs.get("pagination");
        if ((null != pagination) && (null != pagination.get("pageSize")) && (StringUtils.isNumeric(pagination.get("pageSize").toString()))) {
            pageSize = new Integer(pagination.get("pageSize").toString());
        }
        if (pageSize.intValue() > MAX_PAGE_SIZE.intValue()) {
            pageSize = MAX_PAGE_SIZE;
        }
        return pageSize;
    }
    
    @SuppressWarnings("unchecked")
    public static Integer getRequestPageNumber(Map<String, Object> requestArgs) {
        Integer pageNumber = PAGE_NUMBER;
        Map<String, Object> pagination = (HashMap<String, Object>)requestArgs.get("pagination");
        if ((null != pagination) && (null != pagination.get("pageNumber")) && (StringUtils.isNumeric(pagination.get("pageNumber").toString()))) {
            pageNumber = new Integer(pagination.get("pageNumber").toString());
        }        
        return pageNumber;
    }
    
    @SuppressWarnings("unchecked")
    public static List<String> getRequestFields(Map<String, Object> requestArgs) {
        List<String> fields = new ArrayList<String>();
        if (null != requestArgs.get("fields")) {
            fields = (List<String>)requestArgs.get("fields");
        }        
        return fields;
    }
}