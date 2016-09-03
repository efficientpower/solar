package org.wjh.solar.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;

/**
 * mongodb DAO基类
 * 
 * @author wangjihui
 *
 * @param <T>
 */
public interface MongoBaseDao<T> {

    public T getById(ObjectId id);

    public void save(T t);

    public void saveOrUpdate(T t);

    public void update(T t);

    public void delete(ObjectId id);

    public List<T> list(int offset, int limit, String orderBy);
}
