package com.beardream.ioc;

import java.lang.annotation.*;

/**
 * Created by soft01 on 2017/5/7.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface PermissionMethod {
    String text() default "";
}
