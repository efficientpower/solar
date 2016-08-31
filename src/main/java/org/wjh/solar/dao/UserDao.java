package org.wjh.solar.dao;

import java.util.List;

import org.wjh.solar.domain.User;

/**
 * 用户表接口
 * 
 * @author wangjihui
 *
 */
public interface UserDao extends BaseDao<User> {

    public User getByUserId(String userId);

    public void deleteByUserId(String userId);
    
    public List<User> getAllUser();
}
