package com.example.dao;

import com.example.model.entities.User;
import com.example.model.entities.UserExample;
import com.example.model.mapper.RoleMapper;
import com.example.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by botter
 *
 * @Date 11/2/16.
 * @description
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    public void getUser(String name) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(name);
        List<User> userList = userMapper.selectByExample(userExample);

        System.out.println("password: " + userList.get(0).getPassword());
    }
}
