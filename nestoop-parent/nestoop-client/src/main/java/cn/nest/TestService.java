package cn.nest;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by botter on 11/1/16.
 */
@Service
public class TestService {

    @Reference(group = "demogroup")
    private DemoService demoService;

    @Reference(group = "group-1")
    private GroupService groupService;

    public void test () {
        System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "]" + demoService.demo("test"));
    }

    public void testGroup () {
        System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "]" + groupService.groupName("group-1"));
    }
}
