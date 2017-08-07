package com.xinayida.deep.flux.action;

/**
 * Created by ww on 2017/7/25.
 */

public interface ActionType {
    String LOAD_JOBS_MENU = "load_jobs_menu";//加载工作目录
    String LOAD_JOB_CONTENTS = "load_job_contents";//加载工作对应的内容
    String LOAD_TAGS = "load_tags";//修改标签

    String TOGGLE_DRAWER = "toggle_drawer";
}
