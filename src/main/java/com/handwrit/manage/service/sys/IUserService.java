package com.handwrit.manage.service.sys;

import com.handwrit.manage.entity.sys.User;
import com.handwrit.manage.utils.base.Message;

import java.util.List;
import java.util.Map;

/**
 * Created by 甘小涛哇 on 2019/10/14
 */
public interface IUserService {

    /**
     * 查询用户
     * @param map 条件
     * @return
     */
    List<User> selectList(Map map);

    /**
     * 保存用户信息
     * @param user
     */
    void insertUser(User user);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findUserByName(String username);

}
