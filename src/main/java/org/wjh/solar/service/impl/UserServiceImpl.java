package org.wjh.solar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wjh.solar.dao.UserDao;
import org.wjh.solar.domain.User;
import org.wjh.solar.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    
    @Override
    public User getById(Integer id) {
        // TODO Auto-generated method stub
        return userDao.getById(id);
    }

    @Override
    public int insert(User t) {
        // TODO Auto-generated method stub
        return userDao.insert(t);
    }

    @Override
    public void update(User t) {
        // TODO Auto-generated method stub
        userDao.update(t);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        userDao.delete(id);
    }

    @Override
    public List<User> list(int offset, int limit, String orderBy) {
        // TODO Auto-generated method stub
        return userDao.list(offset, limit, orderBy);
    }

}
