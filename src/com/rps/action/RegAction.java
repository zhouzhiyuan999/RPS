package com.rps.action;

import com.mongodb.client.model.Filters;
import com.opensymphony.xwork2.ActionSupport;
import com.rps.jdbc.DeptDB;
import com.rps.jdbc.UserDB;
import org.bson.Document;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ThinkPad on 2016/12/13.
 * 处理注册Action
 */
public class RegAction extends ActionSupport {

    public final static String MANAGER = "manager";
    public final static String USER = "user";
    public final static String ADMIN = "admin";

    private String username;
    private String password;
    private String realname;
    private String dept;
    private String role;
    private String _id;
    private String page;

    private String filter;
    private Document user;
    private List<Document> users;
    private List<Document> depts;

    @Override
    public String execute() throws Exception {
        if (page!=null){
            if (page.equals("hello")) return "hello";
            depts = DeptDB.GetDepts(new Document());
            if (page.equals("add")) return "add";
            if (page.equals("edit")) {
                user = UserDB.GetUserById(_id);
                if (user!=null) return "edit";
            }
        }
        if (filter!=null && !filter.equals("")) {
            Pattern pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
            users = UserDB.ListUsers(Filters.or(new Document(UserDB.USERNAME, pattern),new Document(UserDB.REALNAME,pattern)));
        }
        else users = UserDB.ListUsers(new Document());
        return "list";
    }

    /**
     * 添加用户，输入四个参数
     * @return
     */
    public String add() {
        if (username!=null && !username.equals("") &&
                password!=null && !password.equals("") &&
                realname!=null &&
                dept!=null &&
                role!=null ){
            role = role.equals(MANAGER)?MANAGER:USER;
            dept = DeptDB.GetDeptNumbers(new Document()).contains(dept)?dept:"";
            UserDB.AddUser(username,password,realname,role,dept);
        }
        return SUCCESS;
    }

    /**
     * 删除用户，提供用户_id
     * @return
     */
    public String del() {
        UserDB.DelUserById(_id);
        return SUCCESS;
    }

    /**
     * 更改用户信息，提供用户_id，密码 或 角色和部门 或 都有
     * @return
     */
    public String edit() {
        user = UserDB.GetUserById(_id);
        if (user!=null){
            Document doc = new Document();
            if (user.getString(UserDB.ROLE).equals(ADMIN)){
                if (password!=null && !password.equals("")) doc.append(UserDB.PASSWORD,password);
                if (realname!=null) doc.append(UserDB.REALNAME,realname);
            }else{
                if (password!=null && !password.equals("")) doc.append(UserDB.PASSWORD,password);
                if (realname!=null) doc.append(UserDB.REALNAME,realname);
                if (role!=null){
                    if (role.equals(USER)) doc.append(UserDB.ROLE,role);
                    if (role.equals(MANAGER)) doc.append(UserDB.ROLE,role);
                }
                if (dept!=null && DeptDB.GetDeptNumbers(new Document()).contains(dept)) doc.append(UserDB.DEPT,dept);
            }
            UserDB.SetUserById(_id,doc);
        }
        return SUCCESS;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Document getUser() {
        return user;
    }

    public List<Document> getUsers() {
        return users;
    }

    public List<Document> getDepts() {
        return depts;
    }
}
