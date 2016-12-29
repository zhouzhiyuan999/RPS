package com.rps.action;

import com.opensymphony.xwork2.ActionSupport;
import com.rps.jdbc.UserDB;
import org.bson.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

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
public class Login extends ActionSupport {
    private static final long serialVersionUID = 5386429367989562172L;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private String username;
    private String password;
    private String isuser;

    private String data;
//    private Map<String,Object> map;


    @Override
    public String execute() throws Exception {
        System.out.println("进入Action！");
        System.out.println(username+password+isuser);
        Document user = UserDB.GetUserByName(username);
//        this.data="hello,world";
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("code",1);
        map.put("data",user);
        map.put("url","login.html");
//        map.put("msg","登录成功！");
//        map.put("username",username);
        JSONObject jsonObject=JSONObject.fromObject(map);
        this.data=jsonObject.toString();
        System.out.println("data: "+data);
        return SUCCESS;

/*
//        String username=getUsername();
        if (username != null && !"".equals(username) && password != null
                && !"".equals(password)){
            //检查用户是否存在

            Document user = UserDB.GetUserByName(username);
            System.out.println(user);
            if (user!=null&&user.getString("password").equals(password)){
                System.out.println("阶段1");
               if (user.getString("type").equals(isuser)&&isuser.equals("admin")){
                   System.out.println("阶段2");
//                   return "admin";

                   data=user.toJson();
                   return SUCCESS;
               }
                if (user.getString("type").equals(isuser)&&isuser.equals("user")){
                    return "login";
                }
            }
        }
        return "login";
*/
    }

//    public String getUsername() {
//        return username;
//    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getIsuser() {
//        return isuser;
//    }

    public void setIsuser(String isuser) {
        this.isuser = isuser;
    }

//    public Map<String, Object> getMap() {
//        return map;
//    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
