package com.hnust.dao.base;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.hnust.common.util.Order;
import com.hnust.dto.PageBean;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class MongoDao<T> {
    
    private MongoOperations mongoTemplate;    
    private String orderAscField;    
    private String orderDescField;
    
    public MongoOperations getMongoTemplate() {
        return mongoTemplate;
    }
    
    public void setMongoTemplate(MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    public String getOrderAscField() {
        return this.orderAscField;
    }
    
    public void setOrderAscField(String orderAscField) {
        this.orderAscField = orderAscField;
    }
    
    public String getOrderDescField() {
        return this.orderDescField;
    }
    
    public void setOrderDescField(String orderDescField) {
        this.orderDescField = orderDescField;
    }    
    
    private String _getCollectionName(Class<?> clazz) {
        String className = clazz.getName();
        Integer lastIndex = Integer.valueOf(className.lastIndexOf("."));
        className = className.substring(lastIndex.intValue() + 1);
        return StringUtils.uncapitalize(className);
    }
    
    public long count(Class<T> clazz) {
        return this.mongoTemplate.count(new Query(), _getCollectionName(clazz));
    }
    
    public long count(Criteria criteria, Class<T> clazz) {
        return this.mongoTemplate.count(new Query(criteria), _getCollectionName(clazz));
    }
    
    public List<T> find(Criteria criteria, Class<T> clazz) {
        Query query = new Query(criteria);
        _sort(query);
        return this.mongoTemplate.find(query, clazz, _getCollectionName(clazz));
    }
    
    public Object group(Criteria criteria, GroupBy groupBy, Class<?> clazz) {
        if (null == criteria) {
            return this.mongoTemplate.group(_getCollectionName(clazz), groupBy, clazz);
        }
        return this.mongoTemplate.group(criteria, _getCollectionName(clazz), groupBy, clazz);
    }
    
    public List<T> find(Criteria criteria, Class<T> clazz, Integer pageSize) {
        Query query = new Query(criteria).limit(pageSize.intValue());
        _sort(query);
        return this.mongoTemplate.find(query, clazz, _getCollectionName(clazz));
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PageBean find(Criteria criteria, Integer pageSize, Integer pageNumber, Class<?> clazz) {
        Query query = new Query(criteria).skip((pageNumber.intValue() - 1) * pageSize.intValue()).limit(pageSize.intValue());
        _sort(query);
        List datas = this.mongoTemplate.find(query, clazz, _getCollectionName(clazz));
        Query querycount = new Query(criteria);
        long count = this.mongoTemplate.count(querycount, _getCollectionName(clazz));
        PageBean<T> pageModel = new PageBean<T>(datas, count, pageSize);
        return pageModel;
    }
    
    public PageBean<T> find(Criteria criteria, Order order, Integer pageSize, Integer pageNumber, Class<T> clazz) {
        Query query = new Query(criteria).skip((pageNumber.intValue() - 1) * pageSize.intValue()).limit(pageSize.intValue());
        _sort(query, order);
        List<T> datas = this.mongoTemplate.find(query, clazz, _getCollectionName(clazz));
        Query querycount = new Query(criteria);
        long count = this.mongoTemplate.count(querycount, _getCollectionName(clazz));
        PageBean<T> pageModel = new PageBean<T>(datas, count, pageSize);
        return pageModel;
    }
    
    /**
     * add by 1234 2016/12/24 分页查询（不包含count）
     * 
     * @param criteria
     * @param pageSize
     * @param pageNumber
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List findList(Criteria criteria, Integer pageSize, Integer pageNumber, Class<?> clazz) {
        Query query = new Query(criteria).skip((pageNumber.intValue() - 1) * pageSize.intValue()).limit(pageSize.intValue());
        _sort(query);
        List<?> datas = this.mongoTemplate.find(query, clazz, _getCollectionName(clazz));
        return datas;
    }
    
    public Object findAndModify(Criteria criteria, Update update, Class<?> clazz) {
        return this.mongoTemplate.findAndModify(new Query(criteria), update, clazz, _getCollectionName(clazz));
    }
    
    public Object findAndRemove(Criteria criteria, Class<?> clazz) {
        return this.mongoTemplate.findAndRemove(new Query(criteria), clazz, _getCollectionName(clazz));
    }
    
    @SuppressWarnings("rawtypes")
    public List findAll(Class<?> clazz) {
        return this.mongoTemplate.findAll(clazz, _getCollectionName(clazz));
    }
    
    public Object findById(Object id, Class<?> clazz) {
        return this.mongoTemplate.findById(id, clazz, _getCollectionName(clazz));
    }
    
    @SuppressWarnings("rawtypes")
    public List findIds(Criteria criteria, Class<?> clazz) {
        return this.mongoTemplate.find(new Query(criteria), Id.class, _getCollectionName(clazz));
    }
    
    public Object findOne(Criteria criteria, Class<?> clazz) {
        Query query = new Query(criteria);
        _sort(query);        
        return this.mongoTemplate.findOne(query, clazz, _getCollectionName(clazz));
    }
    
    public Object findOne(Criteria criteria, Integer skip, Class<?> clazz) {
        Query query = new Query(criteria).skip(skip.intValue()).limit(1);
        _sort(query);        
        return this.mongoTemplate.findOne(query, clazz, _getCollectionName(clazz));
    }
    
    public Object findOne(Integer skip, Class<?> clazz) {
        Query query = new Query().skip(skip.intValue()).limit(1);
        _sort(query);        
        return this.mongoTemplate.findOne(query, clazz, _getCollectionName(clazz));
    }
    
    public Boolean remove(Object object) {
        this.mongoTemplate.remove(object);
        return true;
    }
    
    public Boolean remove(Criteria criteria, Class<?> clazz) {
        this.mongoTemplate.remove(new Query(criteria), _getCollectionName(clazz));
        return true;
    }
    
    public MongoOperations getMongoOperation() {
        return this.mongoTemplate;
    }
    
    @SuppressWarnings("hiding")
    public <T> void dropTable(Class<T> paramClass) {
        this.mongoTemplate.dropCollection(paramClass);
    }
    
    public String getNextId(Class<?> clazz) {
        return getNextId(_getCollectionName(clazz));
    }
    
    public String getNextId(String seq_name) {
        String sequence_collection = "seq";
        String sequence_field = "seq";        
        DBCollection seq = this.mongoTemplate.getCollection(sequence_collection);        
        DBObject query = new BasicDBObject();
        query.put("_id", seq_name);        
        DBObject change = new BasicDBObject(sequence_field, Integer.valueOf(1));
        DBObject update = new BasicDBObject("$inc", change);        
        DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
        return res.get(sequence_field).toString();
    }
    
    /**
     * add by 1234 自定义Order排序
     * 
     * @param query
     * @param order
     */
    private void _sort(Query query, Order order) {
        if (order != null && StringUtils.isNotEmpty(order.getPropertyName())) {
            if (order.isAscending()) {
                if ("id".equals(order.getPropertyName())) {
                    this.orderAscField = "_id";
                } else {
                    this.orderAscField = order.getPropertyName();
                }                
                query.with(new Sort(Sort.Direction.ASC, new String[] {this.orderAscField}));
            } else {
                if ("id".equals(order.getPropertyName())) {
                    this.orderDescField = "_id";
                } else {
                    this.orderDescField = order.getPropertyName();
                }                
                query.with(new Sort(Sort.Direction.DESC, new String[] {this.orderDescField}));
            }
        }
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private Criteria _parseRequestRestrictionOr(Map<String, Object> query) {
        Criteria allOrCriteria = new Criteria();
        List<Criteria> criterias = new ArrayList<Criteria>();
        if (null != query) {
            for (String key : query.keySet()) {
                Object value = query.get(key);
                if (StringUtils.startsWith(key, "$and"))
                    criterias.add(getRequestRestriction((Map)value));
                else {
                    criterias.addAll(_parseCriteria(key, value));
                }
            }
        }
        if (!criterias.isEmpty()) {
            allOrCriteria.orOperator((Criteria[])criterias.toArray(new Criteria[criterias.size()]));
        }        
        return allOrCriteria;
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private List<Criteria> _parseCriteria(String key, Object value) {
        if ("id".equals(key)) {
            key = "_id";
        }
        List<Criteria> criterias = new ArrayList<Criteria>();
        Map<String, Object> compareValue = null;
        if ((value instanceof Map)) {
            compareValue = (Map)value;
            for (String compare : compareValue.keySet()) {
                Object _compareValue = compareValue.get(compare);
                if ("$ge".equals(compare))
                    criterias.add(Criteria.where(key).gte(_compareValue));
                else if ("$le".equals(compare))
                    criterias.add(Criteria.where(key).lte(_compareValue));
                else if ("$gt".equals(compare))
                    criterias.add(Criteria.where(key).gt(_compareValue));
                else if ("$lt".equals(compare))
                    criterias.add(Criteria.where(key).lt(_compareValue));
                else if ("$in".equals(compare))
                    criterias.add(Criteria.where(key).in((Collection)_compareValue));
                else if ("$like".equals(compare))
                    criterias.add(Criteria.where(key).regex(Pattern.compile(Pattern.quote((String)_compareValue), 2)));
                else if ("$left_like".equals(compare))
                    criterias.add(Criteria.where(key).regex(Pattern.compile(Pattern.quote((String)_compareValue + "$"), 2)));
                else if ("$right_like".equals(compare))
                    criterias.add(Criteria.where(key).regex(Pattern.compile(Pattern.quote("^" + (String)_compareValue), 2)));
                else if ("$not_like".equals(compare))
                    criterias.add(Criteria.where(key).not().regex((String)_compareValue));
                else if ("$left_like".equals(compare))
                    criterias.add(Criteria.where(key).not().regex(Pattern.compile(Pattern.quote((String)_compareValue + "$"), 2)));
                else if ("$not_right_like".equals(compare))
                    criterias.add(Criteria.where(key).not().regex(Pattern.compile(Pattern.quote("^" + (String)_compareValue), 2)));
                else if ("$ne".equals(compare))
                    criterias.add(Criteria.where(key).ne(_compareValue));
                else if ("$null".equals(compare))
                    criterias.add(Criteria.where(key).is(null));
                else if ("$not_null".equals(compare))
                    criterias.add(Criteria.where(key).not().is(null));
                else if ("$not_in".equals(compare))
                    criterias.add(Criteria.where(key).not().in((Collection)_compareValue));
                else if ("$where".equals(compare))
                    criterias.add(Criteria.where("$where").is(_compareValue));
            }
        } else {
            criterias.add(Criteria.where(key).is(value));
        }
        
        return criterias;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Criteria getRequestRestriction(Map<String, Object> query) {
        Criteria allCriteria = new Criteria();
        List<Criteria> criterias = new ArrayList<Criteria>();
        if (null != query) {
            for (String key : query.keySet()) {
                if ("$or".equals(key)) {
                    Map orValues = (Map)query.get(key);
                    criterias.add(_parseRequestRestrictionOr(orValues));
                } else {
                    Object value = query.get(key);
                    criterias.addAll(_parseCriteria(key, value));
                }
            }
        }
        if (!criterias.isEmpty()) {
            allCriteria.andOperator((Criteria[])criterias.toArray(new Criteria[criterias.size()]));
        }        
        return allCriteria;
    }
    
    @SuppressWarnings("unchecked")
    public PageBean<T> fetchCollectionPage(Map<String, Object> requestArg, Class<?> clazz) {
        Map<String, Object> request = new HashMap<String, Object>();
        if (null == requestArg.get("query")) {
            request.put("query", requestArg);
        } else {
            request.putAll(requestArg);
        }
        Criteria criteria = getRequestRestriction((HashMap<String, Object>)request.get("query"));
        String sortField = CommonDaoHelper.getRequestSortField(request);
        String sortDirection = CommonDaoHelper.getRequestSortDirection(request);
        Integer pageSize = CommonDaoHelper.getRequestPageSize(request);
        Integer pageNumber = CommonDaoHelper.getRequestPageNumber(request);
        
        if ("-1".equals(sortDirection)) {
            setOrderDescField(sortField);
            setOrderAscField(null);
        } else {
            setOrderAscField(sortField);
            setOrderDescField(null);
        }        
        return find(criteria, pageSize, pageNumber, clazz);
    }
    
    @SuppressWarnings("unchecked")
    public List<T> fetchCollection(Map<String, Object> requestArg, Class<?> clazz) {
        Map<String, Object> request = new HashMap<String, Object>();
        if (null == requestArg.get("query")) {
            request.put("query", requestArg);
        } else {
            request.putAll(requestArg);
        }
        Criteria criteria = getRequestRestriction((HashMap<String, Object>)request.get("query"));
        String sortField = CommonDaoHelper.getRequestSortField(request);
        String sortDirection = CommonDaoHelper.getRequestSortDirection(request);
        Integer pageSize = CommonDaoHelper.getRequestPageSize(request);
        Integer pageNumber = CommonDaoHelper.getRequestPageNumber(request);
        
        if ("-1".equals(sortDirection)) {
            setOrderDescField(sortField);
            setOrderAscField(null);
        } else {
            setOrderAscField(sortField);
            setOrderDescField(null);
        }        
        return findList(criteria, pageSize, pageNumber, clazz);
    }
    
    /**
     * add by 1234 自定义order的分页查询（包含count）
     * 
     * @param query
     * @param order
     * @param pageSize
     * @param pageNumber
     * @param clazz
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public PageBean<T> fetchCollection(Map<String, Object> query, Order order, Integer pageSize, Integer pageNumber, Class clazz) {
        Criteria criteria = getRequestRestriction(query);        
        return find(criteria, order, pageSize, pageNumber, clazz);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Long fetchCollectionCount(Map<String, Object> requestArg, Class clazz) {
        Map<String, Object> request = new HashMap<String, Object>();
        if (null == requestArg.get("query")) {
            request.put("query", requestArg);
        } else {
            request.putAll(requestArg);
        }
        Criteria criteria = getRequestRestriction((HashMap)request.get("query"));        
        return Long.valueOf(count(criteria, clazz));
    }
    
    private void _sort(Query query) {
        if (null != this.orderAscField) {
            if ("id".equals(this.orderAscField)) {
                this.orderAscField = "_id";
            }            
            query.with(new Sort(Sort.Direction.ASC, new String[] {this.orderAscField}));
        } else if (null != this.orderDescField) {
            if ("id".equals(this.orderDescField)) {
                this.orderDescField = "_id";
            }            
            query.with(new Sort(Sort.Direction.DESC, new String[] {this.orderDescField}));
        }
    }
    
    @SuppressWarnings("rawtypes")
    public WriteResult updateMulti(Criteria criteria, Update update, Class clazz) {
        return this.mongoTemplate.updateMulti(new Query(criteria), update, _getCollectionName(clazz));
    }
    
    public void saveOrUpdate(Object object) {
        this.mongoTemplate.save(object, _getCollectionName(object.getClass()));
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void insert(Collection batchToSave, Class clazz) {
        this.mongoTemplate.insert(batchToSave, _getCollectionName(clazz));
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Boolean batchUpdate(Map<String, Object> requestArgs, Class clazz) {
        try {
            Object ids = requestArgs.get("ids");
            if (null != ids) {
                Update update = new Update();
                Map<String, Object> updates = (Map<String, Object>)requestArgs.get("updates");
                if (null == updates) {
                    updates = new HashMap<String, Object>();
                    updates.putAll(requestArgs);
                }
                updates.remove("id");
                updates.remove("ids");
                updates.remove("class");
                for (String key : updates.keySet()) {
                    update.set(key, updates.get(key));
                }
                updateMulti(Criteria.where("_id").in((List)ids), update, clazz);
            } else {
                List<Map<String, Object>> allUpdates = (List)requestArgs.get("updates");
                for (Map<String, Object> perUpdates : allUpdates) {
                    Object id = perUpdates.get("id");
                    if (null != id) {
                        Update update = new Update();
                        perUpdates.remove("id");
                        perUpdates.remove("class");
                        for (String key : perUpdates.keySet()) {
                            update.set(key, perUpdates.get(key));
                        }
                        findAndModify(Criteria.where("id").is(id), update, clazz);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }
    
    @SuppressWarnings("unchecked")
    public Boolean update(Map<String, Object> requestArgs, Class<?> clazz) {
        Object id = requestArgs.get("id");
        if (null == id)
            return Boolean.valueOf(false);
        try {
            Update update = new Update();
            Map<String, Object> updates = (Map<String, Object>)requestArgs.get("updates");
            if (null == updates) {
                updates = new HashMap<String, Object>();
                updates.putAll(requestArgs);
            }
            updates.remove("id");
            updates.remove("class");
            for (String key : updates.keySet()) {
                update.set(key, updates.get(key));
            }
            findAndModify(Criteria.where("id").is(id), update, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }
    
    public Boolean save(Map<String, Object> requestArgs, Class<?> clazz) {
        try {
            Object object = clazz.newInstance();
            if (null == requestArgs.get("id")) {
                requestArgs.put("id", getNextId(clazz));
            }
            BeanUtils.populate(object, requestArgs);
            saveOrUpdate(object);
        } catch (Exception e) {
            e.printStackTrace();            
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }
    
    public void delete(Object object) {
        remove(object);
    }
    
    public Boolean deleteById(Object id, Class<?> clazz) {
        Object object = findById(id, clazz);
        if (null == object) {
            return Boolean.valueOf(false);
        }
        return remove(object);
    }
    
    @SuppressWarnings("unchecked")
    public Class<?> getTClass() {
        try {
            // 反射得到T的真实类型
            ParameterizedType ptype = (ParameterizedType)this.getClass().getGenericSuperclass();// 获取当前new的对象的泛型的父类的类型
            Class<?> clazz = (Class<T>)ptype.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型model = clazz.newInstance();实例化需要的时候添加
            return clazz;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}