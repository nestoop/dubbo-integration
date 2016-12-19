package cn.nest.annotation;

import java.lang.annotation.*;

/**
 * Created by perk on 2016/12/16.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ColumTransfer {

    String value() default "";
}
