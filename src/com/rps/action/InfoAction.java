package com.rps.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.rps.jdbc.UserDB;
import org.bson.Document;
import java.util.Map;

/**
 * Created by yinhao on 2017/1/2.
 */
public class InfoAction extends ActionSupport {

    private String page;
    private String password;
    private String realname;

    private Document data;

    private Document profile(){
        Map session = ActionContext.getContext().getSession();
        String user = (String) session.get(LogAction.UNAME);
        return UserDB.GetUserByName(user);
    }

    @Override
    public String execute() throws Exception {
        if (page!=null){
            if (page.equals("upnew")) return "pass";
            if (page.equals("hello")) return "hello";
        }
        data = profile();
        return "show";
    }

    public String update(){
        data = profile();
        Document doc = new Document();
        if (password!=null && !password.equals("")) doc.append(UserDB.PASSWORD,password);
        if (realname!=null) doc.append(UserDB.REALNAME,realname);
        UserDB.SetUserById(data.getString("_id"),doc);
        return SUCCESS;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Document getData() {
        return data;
    }
}
