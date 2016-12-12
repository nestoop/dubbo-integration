package cn.nest.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by botter
 * @Date 5/11/16.
 * @description 处理httprequest的数据
 */
public class RequestParamsUtils {

    public static Map<String, String> requestToMap(HttpServletRequest httpServletRequest) {
        Map<String, String> paramsMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        if (parameterNames  != null) {
            for (String name : Collections.list(parameterNames)) paramsMap.put(name, httpServletRequest.getParameter(name));
        }
        return paramsMap;
    }
}
