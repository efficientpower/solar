package org.wjh.solar.dao;

import java.util.List;

/**
 * 数据库DAO基类
 * 
 * @author wangjihui
 *
 * @param <T>
 */
public interface BaseDao<T> {

    public T getById(Integer id);

    public int insert(T t);

    public void update(T t);

    public void delete(Integer id);

    public List<T> list(int offset, int limit, String orderBy);
}
