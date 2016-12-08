package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.omg.CORBA.OBJ_ADAPTER;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by yinhao on 2016/12/8.
 */
public class FormDB {

    final private static String COLNAME = "forms";

    /**
     * 添加表单模板，自动添加日期时间
     * @param title 标题
     * @param desc 描述
     */
    public static void AddForm(String title, String desc){
        Document doc = new Document("title",title).append("desc",desc).append("items", new ArrayList()).append("created",new Date()).append("updated",new Date());
        MongoUtil.insertOne(COLNAME, doc);
    }

    /**
     * 删除指定模板
     * @param fid 表单模板id
     * @return
     */
    public static DeleteResult DelForm(String fid){
        return MongoUtil.deleteOne(COLNAME, Filters.eq("_id",new ObjectId(fid)));
    }

    /**
     * 更新指定模板标题和描述，时间自动更新
     * @param fid 表单模板id
     * @param title 标题
     * @param desc 描述
     * @return
     */
    public static UpdateResult EditForm(String fid, String title, String desc) {
        Document doc = new Document("title", title).append("desc", desc).append("updated", new Date());
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(fid)), "$set", doc);
    }

    /**
     * 获取指定表单的所有内容
     * @param fid 表单模板id
     * @return
     */
    public static Document GetForm(String fid){
        return MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(fid)));
    }


    /**
     * 指定表单模板添加输入项
     * @param fid 表单模板id
     * @param name 显示的名称
     * @param label 对应数据集合的key
     * @param datatype 数据类型，text、list等
     * @param content 默认内容，text对应提示信息，list对应列表集合
     * @param seq 排序号
     * @param isneed 必填项标志
     * @return
     */
    public static UpdateResult AddItem(String fid, String name, String label, String datatype, Object content, int seq, boolean isneed) {
        Document doc = new Document("mid", new ObjectId()).append("name", name).append("label", label).append("datatype", datatype).append("content", content).append("seq", seq).append("isneed", isneed);
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(fid)), "$push", new Document("items", doc));
    }

    /**
     * 删除指定模板的指定项
     * @param fid 表单模板id
     * @param mid 输入项id
     * @return
     */
    public static UpdateResult DelItem(String fid, String mid){
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(fid)), "$pull", new Document("items", new Document("mid", new ObjectId(mid))));
    }

    /**
     * 更新输入项内容
     * @param fid 表单模板id
     * @param mid 指定输入项id
     * @param name 显示的名称
     * @param label 对应数据集合的key
     * @param datatype 数据类型，text、list等
     * @param content 默认内容，text对应提示信息，list对应列表集合
     * @param seq 排序号
     * @param isneed 必填项标志
     */
    public static void EditItem(String fid, String mid, String name, String label, String datatype, Object content, int seq, boolean isneed){
        Document doc = new Document("mid",new ObjectId(mid)).append("name",name).append("label", label).append("datatype",datatype).append("content",content).append("seq",seq).append("isneed",isneed);
        MongoUtil.updateOne(COLNAME, Filters.and(Filters.eq("_id",new ObjectId(fid)),Filters.eq("items.mid",new ObjectId(mid))),"$set",new Document("items.$",doc));
    }

    /**
     * 获取指定输入项的文档内容
     * @param fid 表单模板id
     * @param mid 指定输入项id
     * @return
     */
    public static Document GetItem(String fid, String mid){
        ArrayList<Document> list = (ArrayList<Document>) MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(fid))).get("items");
        for (int i=0;i<list.size();i++){
            if(list.get(i).get("mid").equals(new ObjectId(mid))){
                return list.get(i);
            }
        }
        return null;
    }

    /**
     * 列出当前系统的所有表单模板
     * @return
     */
    public static MongoCursor<Document> ListForms(){
        return MongoUtil.find(COLNAME, new Document());
    }

    /**
     * 测试函数
     * @param args
     */
    public static void main(String[] args) {

//        FormDB.AddForm("test","descrption");

        String fid = "58490ef28ebe033690d152d6";
        FormDB.AddItem(fid,"name","name","text","姓名",1,true);
        FormDB.AddItem(fid,"number","number","text","学号",2,true);
        ArrayList<String> al = new ArrayList<String>();
        al.add("man");al.add("woman");
        FormDB.AddItem(fid, "sex", "sex", "list", al, 3, true);
        FormDB.AddItem(fid,"note","note","text","备注",2,true);

        MongoCursor<Document> mc = FormDB.ListForms();
        while (mc.hasNext()){
            System.out.println(mc.next());
        }

        FormDB.DelItem(fid,"5849102d8ebe0320c41fa71b");
        FormDB.EditItem(fid,"5849102d8ebe0320c41fa717","nID","nID","text","学号",2,true);

        mc = FormDB.ListForms();
        while (mc.hasNext()){
            System.out.println(mc.next());
        }

    }
}
