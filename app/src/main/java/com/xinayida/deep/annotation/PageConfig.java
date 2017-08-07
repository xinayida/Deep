package com.xinayida.deep.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ww on 2017/7/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PageConfig {
    /**
     * 顶部局的id
     *
     * @return
     */
    int contentViewId() default -1;

    /**
     * toolbar的标题
     *
     * @return
     */
    String title() default "";

}
