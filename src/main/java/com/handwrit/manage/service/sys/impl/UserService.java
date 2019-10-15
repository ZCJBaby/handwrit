package com.handwrit.manage.service.sys.impl;

import com.handwrit.manage.entity.sys.User;
import com.handwrit.manage.mapper.sys.UserMapper;
import com.handwrit.manage.service.sys.IUserService;
import com.handwrit.manage.utils.base.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 甘小涛哇 on 2019/10/14
 */
@Service
public class UserService implements IUserService {

    @Resource
    private UserMapper userMapperp;

    /**
     * 查询用户
     * @param map 条件
     * @return
     */
    public List<User> selectList(Map map){
        return userMapperp.selectList(map);
    };

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User findUserByName(String username){
        return userMapperp.findUserByName(username);
    }
    /**
     * 保存用户信息
     * @param user
     */
    public void insertUser(User user){
        userMapperp.insert(user);
    }

}
