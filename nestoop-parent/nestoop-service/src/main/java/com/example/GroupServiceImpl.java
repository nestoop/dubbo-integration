package com.example;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * Created by botter on 11/1/16.
 */
@Service(group = "group-1")
public class GroupServiceImpl implements  GroupService{

    @Override
    public String groupName(String name) {
        return "group name: " + name;
    }
}
