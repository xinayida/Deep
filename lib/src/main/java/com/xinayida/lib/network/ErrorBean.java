package com.xinayida.lib.network;


import com.xinayida.lib.annotation.NotProguard;

/**
 * 请求返回错误实体
 * Created by stephan on 16/7/22.
 */
@NotProguard
public class ErrorBean {
    public String code;
    public String desc;
    public String detail;
}
