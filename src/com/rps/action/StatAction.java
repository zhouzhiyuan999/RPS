package com.rps.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by yinhao on 2017/1/2.
 */
public class StatAction extends ActionSupport {

    private String page;

    @Override
    public String execute() throws Exception {
        if (page!=null && page.equals("hello")) return "hello";
        return "hello";
    }

    public String getPage() {
        return page;
    }
}
