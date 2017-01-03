package com.rps.action;

import com.mongodb.client.model.Filters;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.rps.jdbc.DeptDB;
import com.rps.jdbc.FormDB;
import com.rps.jdbc.UserDB;
import org.bson.Document;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ThinkPad on 2016/12/13.
 * 模板Action
 */
public class TempAction extends ActionSupport {

    private String fid;
    private String title;
    private String desc;
    private String mid;
    private String name;
    private String label;
    private String datatype;
    private String content;
    private String bind;
    private Integer seq;
    private Boolean isneed;

    private String page;
    private String filter;

    private List<Document> forms;
    private List<Document> items;
    private Document form;
    private Document item;

    @Override
    public String execute() throws Exception {
        if (page!=null){
//            depts = DeptDB.GetDepts(new Document());
            if (page.equals("add")) return "add";
            if (page.equals("edit")) {
                form = FormDB.GetForm(fid);
                if (form!=null) return "edit";
            }
        }
        if (filter!=null && !filter.equals("")) {
            Pattern pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
            forms = FormDB.ListForms(Filters.or(new Document(FormDB.CREATED, pattern), new Document(FormDB.UPDATED, pattern)));
        }
        else forms = FormDB.ListForms(new Document());
        return "list";
    }

    /**
     * 添加一个模板
     * @return
     */
    public String add(){
        if (title!=null && desc!=null) {
            FormDB.AddForm(title, desc);
        }
        return SUCCESS;
    }

    /**
     * 修改模板信息
     * @return
     */
    public String edit(){
//        System.out.println(fid+title+desc);
        if (title!=null && desc!=null){
            FormDB.EditForm(fid,title,desc);
        }

        return SUCCESS;
    }

    /**
     * 删除模板
     * @return
     */
    public String del(){
        FormDB.DelForm(fid);
        return SUCCESS;
    }

    public String item(){
        form = FormDB.GetForm(fid);
        if (form!=null){
            if (page!=null){
                if (page.equals("add")) return "itemadd";
                if (page.equals("edit")) {
                    item = FormDB.GetItem(fid,mid);
                    if (item!=null) return "itemedit";
                }
            }
            if (filter!=null && !filter.equals("")) {
                Pattern pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
                items = new ArrayList<Document>();
                List<Document> list = (List<Document>) form.get("items");
                for (int i=0;i<list.size();i++){
                    Document doc = list.get(i);
                    if (pattern.matcher(doc.getString("name")).find()){
                        items.add(doc);
                    }
                }
            }
            else items = (List<Document>) form.get("items");
            return "itemlist";
        }
        return SUCCESS;
    }

    public String itemdel(){
        FormDB.DelItem(fid,mid);
        return item();
    }

    public String itemadd(){
//        System.out.println(name+label+datatype+content+bind+seq+isneed);
        if (name!=null && !name.equals("") && label!=null && !label.equals("") &&
                datatype!=null && content!=null && bind!=null &&
                seq!=null && isneed!=null){
            FormDB.AddItem(fid,name,label,datatype,content,bind,seq,isneed);
        }
        return item();
    }

    public String itemedit(){
        if (name!=null && !name.equals("") && label!=null && !label.equals("") &&
                datatype!=null && content!=null && bind!=null &&
                seq!=null && isneed!=null){
            FormDB.EditItem(fid,mid,name,label,datatype,content,bind,seq,isneed);
        }
        return item();
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public void setIsneed(Boolean isneed) {
        this.isneed = isneed;
    }

    public String getFid() {
        return fid;
    }

    public String getFilter() {
        return filter;
    }

    public List<Document> getForms() {
        return forms;
    }

    public List<Document> getItems() {
        return items;
    }

    public Document getForm() {
        return form;
    }

    public Document getItem() {
        return item;
    }
}
