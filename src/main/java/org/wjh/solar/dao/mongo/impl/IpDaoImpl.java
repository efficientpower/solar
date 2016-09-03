package org.wjh.solar.dao.mongo.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.wjh.solar.dao.mongo.IpDao;
import org.wjh.solar.domain.mongo.Ip;
import org.wjh.solar.morphia.IpMorphiaBean;

import com.google.code.morphia.Datastore;

@Repository
public class IpDaoImpl implements IpDao {

    @Autowired
    private IpMorphiaBean ipMorphiaBean;

    /**
     * 
     * 初始化时创建索引,如果有索引则不会再创建索引,多次调用不会影响性能
     */
    public void createIndex() {
        Datastore ds = ipMorphiaBean.getDataStore();
        ipMorphiaBean.map(Ip.class);
        ds.ensureIndexes();
    }

    @Override
    public Ip getById(ObjectId id) {
        // TODO Auto-generated method stub
        Datastore ds = ipMorphiaBean.getDataStore();
        return ds.get(Ip.class, id);
    }

    @Override
    public void save(Ip t) {
        // TODO Auto-generated method stub
        Datastore ds = ipMorphiaBean.getDataStore();
        ds.save(t);
        createIndex();
    }

    @Override
    public void saveOrUpdate(Ip t) {
        // TODO Auto-generated method stub
        Datastore ds = ipMorphiaBean.getDataStore();
        createIndex();
    }

    @Override
    public void update(Ip t) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(ObjectId id) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Ip> list(int offset, int limit, String orderBy) {
        // TODO Auto-generated method stub
        return null;
    }

}
