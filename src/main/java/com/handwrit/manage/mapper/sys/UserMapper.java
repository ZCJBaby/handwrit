package com.handwrit.manage.mapper.sys;

import com.handwrit.manage.entity.sys.User;
import com.handwrit.manage.utils.base.TkMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper extends TkMapper<User> {

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
    void saveUser(User user);
    /**
     * 查询用户名是否存在，若存在，不允许注册
     * 注解@Param(value) 若value与可变参数相同，注解可省略
     * 注解@Results  列名和字段名相同，注解可省略
     * @param username
     * @return
     */
    @Select(value = "select u.username,u.password from sys_user u where u.username=#{username}")
    @Results
            ({@Result(property = "username",column = "username"),
                    @Result(property = "password",column = "password")})
    User findUserByName(@Param("username") String username);

    /**
     * 注册  插入一条user记录
     * @param user
     * @return
     */
    @Insert("insert into sys_user(username,password,create_time) values(#{username},#{password},NOW())")
    //加入该注解可以保存对象后，查看对象插入id
//    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void regist(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    @Select("select u.id from sys_user u where u.username = #{username} and password = #{password}")
    Integer login(User user);


}