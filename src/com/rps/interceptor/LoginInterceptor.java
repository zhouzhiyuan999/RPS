package com.rps.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.rps.action.LogAction;

import java.util.Map;

/**
 * Created by yinhao on 2016/12/30.
 */
public class LoginInterceptor extends AbstractInterceptor {
    private int authority = 0;

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map session = ActionContext.getContext().getSession();
        Integer access = (Integer) session.get(LogAction.ACCESS);
//        byte right = (byte) (authority&access);
        if (access!=null && (authority&access)!=0){
            return actionInvocation.invoke();
        }
        return "index";
    }
}
