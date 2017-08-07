package com.xinayida.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Activity/Fragment初始化用到的注解
 * Created by ww on 2017/1/2.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AFInject {

    /**
     * 顶部局的id
     */
    int contentViewId() default -1;

    /**
     * 后退按钮id
     */
    int btnBackId() default -1;

    /**
     * 是否开启侧滑
     *
     * @return
     */
    boolean enableSlidr() default false;

}
