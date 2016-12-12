package cn.nest.resorces.service;

import cn.nest.annotation.ResourceInfoType;

/**
 * Created by botter
 *
 * @Date 7/11/16.
 * @description
 */
@ResourceInfoType(value = "user")
public class UserService {

    public String getUserName(String code) {
        System.out.println("code : " + code);
        return "tomcat";
    }
}
