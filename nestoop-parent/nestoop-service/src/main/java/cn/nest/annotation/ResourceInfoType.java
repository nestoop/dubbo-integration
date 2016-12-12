package cn.nest.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by botter
 * @Date 7/11/16.
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ResourceInfoType {

    String value() default "";

    String description() default "";
}
