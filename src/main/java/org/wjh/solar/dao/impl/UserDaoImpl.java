package org.wjh.solar.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;
import org.wjh.solar.dao.UserDao;
import org.wjh.solar.domain.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SqlMapClientTemplate template;

    @Override
    public User getById(Integer id) {
        // TODO Auto-generated method stub
        return (User) template.queryForObject("User.getById", id);
    }

    @Override
    public int insert(User t) {
        // TODO Auto-generated method stub
        Integer result = (Integer) template.insert("User.insert", t);
        return result.intValue();
    }

    @Override
    public void update(User t) {
        // TODO Auto-generated method stub
        template.insert("User.update", t);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        template.insert("User.delete", id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> list(int offset, int limit, String orderBy) {
        // TODO Auto-generated method stub
        Map<String, Object> params = new HashMap<String, Object>(3);
        params.put("offset", offset);
        params.put("limit", limit);
        params.put("orderBy", orderBy);
        return (List<User>) template.queryForList("User.list", params);
    }

    @Override
    public User getByUserId(String userId) {
        // TODO Auto-generated method stub
        return (User) template.queryForObject("User.getByUserId", userId);
    }

    @Override
    public void deleteByUserId(String userId) {
        // TODO Auto-generated method stub
        template.insert("User.deleteByUserId", userId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUser() {
        // TODO Auto-generated method stub
        return (List<User>)template.queryForList("User.getAllUser");
    }

}
