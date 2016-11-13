package com.example.controller;

import com.example.DataEditService;
import com.example.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by botter
 *
 * @Date 5/11/16.
 * @description
 */
@RestController
@RequestMapping("/")
public class DataEditController {

    @Autowired
    private DataEditService dataEditService;

    /***
     * 集中处理页面发过来的JSON
     * @param resourceInfoType
     * @param request
     * @return
     */

    @RequestMapping(value = "{resourceInfoType}/add", method = RequestMethod.POST , produces = "application/json")
    public String add(@PathVariable String resourceInfoType, HttpServletRequest request) {
        Map<String, String> paramsMap = RequestParamsUtils.requestToMap(request);
        return dataEditService.add(paramsMap, resourceInfoType, "add");
    }
}
