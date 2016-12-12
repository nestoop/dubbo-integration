package cn.nest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by botter on 11/1/16.
 */
@RestController
public class PathParamController {

    @RequestMapping("test/{id}")
    public void test(@PathVariable("id") String id, String name) {
        System.out.println("path: " + id + " name : " + name);
    }
}
