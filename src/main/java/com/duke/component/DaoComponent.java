package com.duke.component;

import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义SQL 组件
 */
@Transactional
@Repository
public class DaoComponent {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * * 查询数据集合
     * @param sql 查询sql sql中的参数用:name格式
     * @param params 查询参数map格式，key对应参数中的:name
     * @param clazz 实体类型为空则直接转换为map格式
     * @return
     */
    @SuppressWarnings("unchecked")
    public<T> List<T> queryListEntity(String sql, Map<String, Object> params, Class<T> clazz){
        List<Map<String, Object>> result = DBQuery(sql, params);
        if (clazz != null) {
            List<T>  entityList = convert(clazz, result);
            return entityList;
        }
        return new ArrayList<>();
    }

    /**
     * 查询第一条
     * @param sql
     * @param params
     * @param clazz
     * @param <T>
     * @return
     */
    public<T> T queryEntity(String sql, Map<String, Object> params, Class<T> clazz){
        List<Map<String, Object>> result = DBQuery(sql, params);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        if (clazz != null) {
            T  entityList = convertObj(clazz, result.get(0));
            return entityList;
        }
        return null;
    }

    private List<Map<String, Object>> DBQuery(String sql, Map<String, Object> params) {
        Session session = entityManager.unwrap(Session.class);
        //SQLQuery query = session.createSQLQuery(sql);
        NativeQuery query = session.createNativeQuery(sql);
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        //query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (List<Map<String, Object>>) query.list();
    }

    private<T> T convertObj(Class<T> clazz, Map<String, Object> map) {
        T result;
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        result = null;
        try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            result = clazz.newInstance();
            for (String key:map.keySet()) {
                String attrName = key.toLowerCase();
                for (PropertyDescriptor prop : props) {
                    attrName = removeUnderLine(attrName);
                    if (!attrName.equals(prop.getName())) {
                        continue;
                    }
                    Method method = prop.getWriteMethod();
                    Object value = map.get(key);
                    if (value != null) {
                        value = ConvertUtils.convert(value,prop.getPropertyType());
                        method.invoke(result,value);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("数据转换错误");
        }
        return result;
    }

    private<T> List<T> convert(Class<T> clazz, List<Map<String, Object>> list) {
        List<T> result;
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        result = new ArrayList<>();
        try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (Map<String, Object> map : list) {
                T obj = clazz.newInstance();
                for (String key:map.keySet()) {
                    String attrName = key.toLowerCase();
                    for (PropertyDescriptor prop : props) {
                        attrName = removeUnderLine(attrName);
                        if (!attrName.equals(prop.getName())) {
                            continue;
                        }
                        Method method = prop.getWriteMethod();
                        Object value = map.get(key);
                        if (value != null) {
                            value = ConvertUtils.convert(value,prop.getPropertyType());
                            method.invoke(obj,value);
                        }

                    }
                }
                result.add(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException("数据转换错误");
        }
        return result;
    }

    private String removeUnderLine(String attrName) {
        //去掉数据库字段的下划线
        if(attrName.contains("_")) {
            String[] names = attrName.split("_");
            String firstPart = names[0];
            String otherPart = "";
            for (int i = 1; i < names.length; i++) {
                String word = names[i].replaceFirst(names[i].substring(0, 1), names[i].substring(0, 1).toUpperCase());
                otherPart += word;
            }
            attrName = firstPart + otherPart;
        }
        return attrName;
    }

    /**
     * 获取记录条数
     * @param sql
     * @param params
     * @return
     */
    public Integer getCountBy(String sql,Map<String, Object> params){
        Query query =  entityManager.createNativeQuery(sql);
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        BigInteger bigInteger  = (BigInteger) query.getSingleResult();
        return bigInteger==null?0:bigInteger.intValue();
    }

    /**
     * 新增或者删除
     * @param sql
     * @param params
     * @return
     */
    public Integer deleteOrUpDate(String sql,Map<String, Object> params){
        Query query =  entityManager.createNativeQuery(sql);
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query.executeUpdate();
    }

    /**
     * 获取记录合计
     * @param sql
     * @param params
     * @return
     */
    public BigDecimal getSumBy(String sql, Map<String, Object> params){
        Query query =  entityManager.createNativeQuery(sql);
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        BigDecimal bigDecimal  = (BigDecimal) query.getSingleResult();
        return bigDecimal == null ? new BigDecimal("0"):bigDecimal;
    }

    /**
     * 获取总数
     * @param sql
     * @param params
     * @return
     */
    public Integer getSumIntBy(String sql, Map<String, Object> params){
        Query query =  entityManager.createNativeQuery(sql);
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        BigDecimal bigDecimal  = (BigDecimal) query.getSingleResult();
        return bigDecimal==null?0:bigDecimal.intValue();
    }
}
