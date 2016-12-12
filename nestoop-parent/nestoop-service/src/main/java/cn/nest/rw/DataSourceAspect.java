package cn.nest.rw;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by botter
 *
 * @Date 11/2/16.
 * @description
 */
@Component
@Aspect
public class DataSourceAspect {

    public static final ThreadLocal<String> handler = new ThreadLocal<>();

    public static String currentDataSource() {
        return handler.get();
    }

    @Before("execution(* com.example.model.mapper.*.*(..))")
    public void setDataSource(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("method name :" + methodName);

        String datasourceName = RoutingDataSource.MASTER;
        if (methodName.startsWith("select") || methodName.startsWith("count")) {
            datasourceName = RoutingDataSource.SLAVE;
        }

        System.out.println("[datasouceName] " + datasourceName);
        handler.set(datasourceName);
    }

}
