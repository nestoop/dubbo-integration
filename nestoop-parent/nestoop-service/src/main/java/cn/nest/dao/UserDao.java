package cn.nest.dao;

import cn.nest.model.entities.UserExample;
import cn.nest.model.entities.User;
import cn.nest.model.mapper.RoleMapper;
import cn.nest.model.mapper.UserMapper;
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
