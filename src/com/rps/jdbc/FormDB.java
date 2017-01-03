package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 表单模板类 <br>
 * Created by yinhao on 2016/12/8.
 * @author yinhao
 * @version 1.0
 */
public class FormDB {

    private final static String COLNAME = "forms";
//    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static String TITLE = "title";
    public final static String DESC = "desc";
    public final static String ITEMS = "items";
    public final static String CREATED = "created";
    public final static String UPDATED = "updated";
    /**
     * 添加表单模板，自动添加创建和更新日期
     * @param title 模板标题
     * @param desc 模板描述
     */
    public static void AddForm(String title, String desc){
        Document doc = new Document(TITLE,title).append(DESC,desc).append(ITEMS, new ArrayList()).append(CREATED,new Date()).append(UPDATED,new Date());
        MongoUtil.insertOne(COLNAME, doc);
    }

    /**
     * 删除指定模板
     * @param fid 表单模板id
     * @return 被删除的模板数，0或1
     */
    public static long DelForm(String fid){
        if (fid==null || !ObjectId.isValid(fid)) return 0;
        return MongoUtil.deleteOne(COLNAME, Filters.eq("_id",new ObjectId(fid))).getDeletedCount();
    }

    /**
     * 更新指定模板标题和描述，更新时间自动更新
     * @param fid 表单模板id
     * @param title 模板标题
     * @param desc 模板描述
     * @return 被修改的模板数，0或1
     */
    public static long EditForm(String fid, String title, String desc) {
        if (fid==null || !ObjectId.isValid(fid)) return 0;
        Document doc = new Document("title", title).append("desc", desc).append("updated", new Date());
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(fid)), "$set", doc).getModifiedCount();
    }

    /**
     * 获取指定表单的所有内容
     * @param fid 表单模板id
     * @return 表单模板
     */
    public static Document GetForm(String fid){
        if (fid==null || !ObjectId.isValid(fid)) return null;
        Document doc = MongoUtil.findOne(COLNAME, Filters.eq("_id", new ObjectId(fid)));
        doc.put("_id",doc.get("_id").toString());
        return doc;
    }


    /**
     * 指定表单模板添加输入项
     * @param fid 表单模板id
     * @param name 显示的名称
     * @param label 对应数据集合的key
     * @param datatype 数据类型，text、list等
     * @param content 默认内容，text对应提示信息，list对应列表集合
     * @param bind 是否绑定某个变量，$username、$date、$dept等等
     * @param seq 排序号
     * @param isneed 必填项标志
     * @return 成功添加的数目，0或1
     */
    public static long AddItem(String fid, String name, String label, String datatype, Object content, String bind, int seq, boolean isneed) {
        if (fid==null || !ObjectId.isValid(fid)) return 0;
        Document doc = new Document("mid", new ObjectId()).append("name", name).append("label", label).append("datatype", datatype).append("content", content).append("bind",bind).append("seq", seq).append("isneed", isneed);
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(fid)), "$push", new Document("items", doc)).getModifiedCount();
    }

    /**
     * 删除指定模板的指定项
     * @param fid 表单模板id
     * @param mid 输入项id
     * @return 被删除的数目，0或1
     */
    public static long DelItem(String fid, String mid){
        if (fid==null || !ObjectId.isValid(fid) || mid==null || !ObjectId.isValid(mid)) return 0;
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(fid)), "$pull", new Document("items", new Document("mid", new ObjectId(mid)))).getModifiedCount();
    }

    /**
     * 更新输入项内容
     * @param fid 表单模板id
     * @param mid 指定输入项id
     * @param name 显示的名称
     * @param label 对应数据集合的key
     * @param datatype 数据类型，text、list等
     * @param content 默认内容，text对应提示信息，list对应列表集合
     * @param bind 是否绑定某个变量，$username、$date、$dept等等
     * @param seq 排序号
     * @param isneed 必填项标志
     * @return 被成功修改的数目，0或1
     */
    public static long EditItem(String fid, String mid, String name, String label, String datatype, Object content, String bind, int seq, boolean isneed){
        if (fid==null || !ObjectId.isValid(fid) || mid==null || !ObjectId.isValid(mid)) return 0;
        Document doc = new Document("mid",new ObjectId(mid)).append("name",name).append("label", label).append("datatype",datatype).append("content",content).append("bind",bind).append("seq", seq).append("isneed",isneed);
        return MongoUtil.updateOne(COLNAME, Filters.and(Filters.eq("_id",new ObjectId(fid)),Filters.eq("items.mid",new ObjectId(mid))),"$set",new Document("items.$",doc)).getModifiedCount();
    }

    /**
     * 获取指定输入项的文档内容
     * @param fid 表单模板id
     * @param mid 指定输入项id
     * @return 输入项属性
     */
    public static Document GetItem(String fid, String mid){
        if (fid==null || !ObjectId.isValid(fid) || mid==null || !ObjectId.isValid(mid)) return null;
        ArrayList<Document> list = (ArrayList<Document>) MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(fid))).get("items");
        for (int i=0;i<list.size();i++){
            if(list.get(i).get("mid").equals(new ObjectId(mid))){
                return list.get(i);
            }
        }
        return null;
    }

    /**
     * 获取模板的所有输入项
     * @param fid 表单模板id
     * @return 输入项列表
     */
    public static ArrayList<Document> ListItems(String fid){
        return (ArrayList<Document>)MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(fid))).get("items");
    }

    /**
     * 清空该模板的所有输入项
     * @param fid 表单模板id
     * @return 被修改的数目
     */
    public static long DropItems(String fid){
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id",new ObjectId(fid)),"$set",new Document("items",new ArrayList())).getModifiedCount();
    }

    /**
     * 列出当前系统的所有表单模板
     * @return 所有模板档案
     */
    public static List<Document> ListForms(Bson filter){
        MongoCursor<Document> mcd = MongoUtil.find(COLNAME, filter);
        List<Document> ldoc = new ArrayList<Document>();
        while (mcd.hasNext()){
            Document doc = mcd.next();
            doc.put(CREATED, MongoUtil.FORMAT.format(doc.get(CREATED)));
            doc.put(UPDATED, MongoUtil.FORMAT.format(doc.get(UPDATED)));
            doc.put("_id",doc.get("_id").toString());
            ldoc.add(doc);
        }
        return ldoc;
    }

}
