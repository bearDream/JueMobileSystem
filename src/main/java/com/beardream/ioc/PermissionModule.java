package com.beardream.ioc;

import java.lang.annotation.*;

/**
 * Created by soft01 on 2017/5/7.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface PermissionModule {
    String text() default "";
}
