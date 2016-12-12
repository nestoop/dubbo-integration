package cn.nest.quartz;

import cn.nest.Counter;
import cn.nest.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created by botter
 *
 * @Date 8/12/16.
 * @description
 */
@Configuration
@EnableScheduling
@Component
public class SpringQuartzCounter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private Counter counter;

    public  int i = 1;

    @Scheduled(fixedRate = 1000 * 10)
    public void quartzReportTime() {
        redisService.lock("counter", 3);
        counter.add(Integer.parseInt(redisService.get("counter")));
        System.out.println("]>>>>>" + counter.getI());
        redisService.unLock("counter");
        System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "]>>>>>" + i);
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void quartzCurrentTime() {
        redisService.lock("counter", 3);
        counter.add(Integer.parseInt(redisService.get("counter")));
        System.out.println("------------" + counter.getI());
        redisService.unLock("counter");
        System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "]-----" + i);
    }

}
