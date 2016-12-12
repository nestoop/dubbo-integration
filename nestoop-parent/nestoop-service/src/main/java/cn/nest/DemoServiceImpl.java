package cn.nest;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * Created by botter on 10/31/16.
 */
@Service(group = "demogroup")
public class DemoServiceImpl implements DemoService {
    @Override
    public String demo(String code) {
        return "test";
    }
}
