package com.rps.action;

import com.mongodb.client.model.Filters;
import com.opensymphony.xwork2.ActionSupport;
import com.rps.jdbc.DeptDB;
import com.rps.jdbc.UserDB;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yinhao on 2016/12/31.
 */
public class DeptAction extends ActionSupport {

    private String number;
    private String name;
    private String _id;
    private String move;
    private String page;
    private String DEFAULT = DeptDB.DEFAULT;

    private String filter;
    private List<Document> depts;
    private Document dept;
    private List<Document> users;

    @Override
    public String execute() throws Exception {
        if (page!=null){
            if (page.equals("add")) return "add";
            dept = DeptDB.GetDeptByNumber(number);
            if (page.equals("edit") && dept!=null) return "edit";
            if (page.equals("dept") && dept!=null) {
                depts = DeptDB.GetDepts(new Document());
                users = (List<Document>) dept.get(DeptDB.USERS);
                if (filter!=null && !filter.equals("")){
                    users = new ArrayList<Document>();
                    List<Document> list = (List<Document>) dept.get(DeptDB.USERS);
                    Pattern pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
                    for (int i=0;i<list.size();i++){
                        Document doc = list.get(i);
                        if (pattern.matcher(doc.getString(UserDB.USERNAME)).find()||pattern.matcher(doc.getString(UserDB.REALNAME)).find()){
                            users.add(doc);
                        }
                    }
                }
                return "dept";
            }
        }
        if (filter!=null && !filter.equals("")) {
            Pattern pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
            depts = DeptDB.GetDepts(Filters.or(new Document(DeptDB.NUMBER, pattern), new Document(DeptDB.NAME, pattern)));
        }
        else depts = DeptDB.GetDepts(new Document());
        return "list";
    }

    public String add(){
        if (number!=null && !number.equals("") && name!=null){
            DeptDB.AddDept(number,name);
        }
        return SUCCESS;
    }

    public String del(){
        DeptDB.DelDept(number);
        return SUCCESS;
    }

    public String edit(){
        dept = DeptDB.GetDeptByNumber(number);
        if (dept!=null && name!=null){
            DeptDB.ReDept(number,name);
        }
        return SUCCESS;
    }

    public String move(){
        System.out.println(number+_id+move);
        if (_id!=null && move!=null && DeptDB.GetDeptNumbers(new Document()).contains(move)){
            UserDB.SetUserById(_id,new Document(UserDB.DEPT,move));
        }
        dept = DeptDB.GetDeptByNumber(number);
        depts = DeptDB.GetDepts(new Document());
        users = (List<Document>) dept.get(DeptDB.USERS);
        if (filter!=null && !filter.equals("")){
            users = new ArrayList<Document>();
            List<Document> list = (List<Document>) dept.get(DeptDB.USERS);
            Pattern pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
            for (int i=0;i<list.size();i++){
                Document doc = list.get(i);
                if (pattern.matcher(doc.getString(UserDB.USERNAME)).find()||pattern.matcher(doc.getString(UserDB.REALNAME)).find()){
                    users.add(doc);
                }
            }
        }
        return "dept";
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDEFAULT() {
        return DEFAULT;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    public List<Document> getDepts() {
        return depts;
    }

    public Document getDept() {
        return dept;
    }

    public List<Document> getUsers() {
        return users;
    }
}
