package org.wjh.solar.service;

import org.wjh.solar.domain.User;

/**
 * User service
 * 
 * @author wangjihui
 *
 */
public interface UserService extends BaseService<User> {
    public User getByUserId(String userId);
}
