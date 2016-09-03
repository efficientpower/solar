package org.wjh.solar.dao;

import java.util.List;

import org.wjh.solar.domain.User;

public interface SearchUserDao {
    public void add(User user);

    public void update(User user);

    public void delete(User user);

    public User getByUserId(String userId);

    public List<User> getByName(String name);
}
