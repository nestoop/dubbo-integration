package cn.nest.annotation;

import java.lang.annotation.*;

/**
 * Created by perk on 2016/12/16.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BeanTransfer {
    String type() default "JSON";

    String desc() default "no description";
}
