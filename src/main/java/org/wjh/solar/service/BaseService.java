package org.wjh.solar.service;

import java.util.List;

/**
 * Service接口基类
 * 
 * @author wangjihui
 *
 * @param <T>
 */
public interface BaseService<T> {

    public T getById(Integer id);

    public int insert(T t);

    public void update(T t);

    public void delete(Integer id);

    public List<T> list(int offset, int limit, String orderBy);
}
