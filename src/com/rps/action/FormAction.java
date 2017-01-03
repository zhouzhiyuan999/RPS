package com.rps.action;

import com.mongodb.client.model.Filters;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.rps.comparator.SeqComparator;
import com.rps.jdbc.DataDB;
import com.rps.jdbc.FormDB;
import com.rps.jdbc.MongoUtil;
import com.rps.jdbc.UserDB;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by yinhao on 2017/1/2.
 */
public class FormAction extends ActionSupport {

    private String fid;
    private String did;
    private String page;
    private String filter;

    private List<Document> datas;
    private List<Document> forms;
    private Document data;
    private Document form;

    private Document profile(){
        Map session = ActionContext.getContext().getSession();
        String user = (String) session.get(LogAction.UNAME);
        return UserDB.GetUserByName(user);
    }

    @Override
    public String execute() throws Exception {
        if (page!=null) {
            form = FormDB.GetForm(fid);
            if (form != null && page.equals("add")) return "add";
            data = DataDB.GetData(did);
            if (data != null && page.equals("edit")) {
                form = FormDB.GetForm(data.get("fid").toString());
                List<String> line = new ArrayList<String>();
                List<Document> items = (List<Document>) form.get("items");
                Collections.sort(items,new SeqComparator());   /////////
                Iterator<Document> itt = items.iterator();
                while (itt.hasNext()){
                    Document doct = itt.next();
                    String value = (String) ((Document)data.get(DataDB.DATA)).get(doct.get("mid").toString());
                    line.add(value);
                }
                data.put(DataDB.DATA,line);
                return "edit";
            }
        }
        if (filter!=null && !filter.equals("")) {
            Pattern pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
            forms = FormDB.ListForms(Filters.or(new Document(FormDB.CREATED, pattern), new Document(FormDB.UPDATED, pattern)));
        }
        else forms = FormDB.ListForms(new Document());
        return "list";
    }

    public String check(){
        form = FormDB.GetForm(fid);
        if (form!=null){
            String uid = profile().getString("_id");
            Bson bson = Filters.and(new Document("uid", new ObjectId(uid)),new Document("fid", new ObjectId(fid)));
            List<Document> items = (List<Document>) form.get("items");
            Collections.sort(items,new SeqComparator());   ////////
            datas = DataDB.ListDatas(bson);
            Iterator<Document> itd = datas.iterator();
            while (itd.hasNext()){
                List<String> line = new ArrayList<String>();
                Document docd = itd.next();
                Iterator<Document> itt = items.iterator();
                while (itt.hasNext()){
                    Document doct = itt.next();
                    String value = (String) ((Document)docd.get(DataDB.DATA)).get(doct.get("mid").toString());
                    line.add(value);
                }
                docd.put(DataDB.DATA,line);
            }
            System.out.println(datas);
            return "data";
        }
        return SUCCESS;
    }

    public String add(){
        form = FormDB.GetForm(fid);
        if (form!=null){
            Document doc = new Document();
            Map params = ActionContext.getContext().getParameters();
            List<Document> items = (List<Document>) form.get("items");
            Iterator<Document> it = items.iterator();
            while (it.hasNext()){
                Document item = it.next();
                String mid = item.get("mid").toString();
                String label = item.getString("label");
                if (params.keySet().contains(label)){
                    String[] vaule = (String[]) params.get(label);
                    doc.append(mid,vaule[0]);
                }else{
                    doc.append(mid, "");
                }
            }
            DataDB.AddData(form.getString("_id"), profile().getString("_id"), doc, "");
        }
        return SUCCESS;
    }

    public String del(){
        String uid = profile().getString("_id");
        data = DataDB.GetData(did);
        if (data!=null && data.get("uid").toString().equals(uid)) {
            fid = data.get("fid").toString();
            DataDB.DelData(did);
        }
        return check();
    }

    public String edit() throws ParseException {
        data = DataDB.GetData(did);
        if (data!=null) {
            fid = data.get("fid").toString();
            form = FormDB.GetForm(fid);
            Document doc = new Document();
            Map params = ActionContext.getContext().getParameters();
            List<Document> items = (List<Document>) form.get("items");
            Iterator<Document> it = items.iterator();
            while (it.hasNext()) {
                Document item = it.next();
                String mid = item.get("mid").toString();
                String label = item.getString("label");
                if (params.keySet().contains(label)) {
                    String[] vaule = (String[]) params.get(label);
                    doc.append(mid, vaule[0]);
                } else {
                    doc.append(mid, "");
                }
            }
            data.remove("_id");
            data.put(DataDB.DATA,doc);
            data.put(DataDB.CREATED, MongoUtil.FORMAT.parse(data.getString(DataDB.CREATED)));
            data.put(DataDB.UPDATED,new Date());
            DataDB.SetData(did,data,"");
        }
        return check();
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    public List<Document> getDatas() {
        return datas;
    }

    public Document getData() {
        return data;
    }

    public List<Document> getForms() {
        return forms;
    }

    public Document getForm() {
        return form;
    }
}
