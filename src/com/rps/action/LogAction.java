package com.rps.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.rps.jdbc.UserDB;
import org.bson.Document;

import java.util.Date;
import java.util.Map;

/**
 * Created by ThinkPad on 2016/12/12.
 * 登录处理的action
 *
 */

/**
 * 返回的数据格式：{
 *     "code":0,error 1,success
 *     "data":{}
 *     "url":"String..."
 * }
 */
public class LogAction extends ActionSupport {

    public final static String UNAME = "USER_NAME";
    public final static String ACCESS = "USER_ACCESS";

    private String username;
    private String password;

    private Document data;

    @Override
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        String user = (String) session.get(UNAME);
        Integer access = (Integer) session.get(ACCESS);
        if (user!=null){
            data = UserDB.GetUserByName(username);
            if (access == 4) return "admin";
            if (access == 2) return "manager";
            if (access == 1) return "user";
        }
        return "login";
    }

    /**
     * 登录表单提交处理
     * @return
     */
    public String login(){
        Map session = ActionContext.getContext().getSession();
        if (username!=null && !username.equals("") && password!=null && !password.equals("")){
            data = UserDB.GetUserByName(username);
            if (data != null && data.getString(UserDB.PASSWORD).equals(password)) {
                UserDB.SetUserByName(username,new Document(UserDB.LASTTIME,new Date()));
                session.put(UNAME,username);
                if (data.getString(UserDB.ROLE).equals(RegAction.ADMIN)) {session.put(ACCESS,4);return "admin";}
                if (data.getString(UserDB.ROLE).equals(RegAction.MANAGER)) {session.put(ACCESS,2);return "manager";}
                if (data.getString(UserDB.ROLE).equals(RegAction.USER)) {session.put(ACCESS,1);return "user";}
            }
        }
        return SUCCESS;
    }

    /**
     * 退出清空会话
     * @return
     */
    public String logout(){
        Map session = ActionContext.getContext().getSession();
        session.remove(UNAME);
        session.remove(ACCESS);
        return SUCCESS;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Document getData() {
        return data;
    }

}
