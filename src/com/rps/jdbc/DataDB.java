package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 用户凭证数据类 <br/>
 * Created by yinhao on 2016/12/8.
 * @author yinhao
 * @version 1.0
 */
public class DataDB {

    private final static String COLNAME = "data";
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static String DATA = "data";
    public final static String STATUS = "status";
    public final static String CREATED = "created";
    public final static String UPDATED = "updated";

    /**
     * 添加对应表单的文档
     * @param fid 表单模板id
     * @param uid 用户id
     * @param doc 表单内容
     * @param status 当前文档状态
     */
    public static void AddData(String fid, String uid, Document doc, String status){
        Document newdoc = new Document("fid",new ObjectId(fid)).append("uid",new ObjectId(uid)).append(DATA, doc).append(STATUS,status).append(CREATED,new Date()).append(UPDATED, new Date());
        MongoUtil.insertOne(COLNAME, newdoc);
    }

    /**
     * 删除指定文档数据
     * @param did 文档数据id
     * @return
     */
    public static long DelData(String did){
        if (did==null || !ObjectId.isValid(did)) return 0;
        return MongoUtil.deleteOne(COLNAME, Filters.eq("_id",new ObjectId(did))).getDeletedCount();
    }

    /**
     * 更新表单内容
     * @param did 表单模板id
     * @param doc 表单数据内容文档
     * @param status 当前表单内容状态
     * @return
     */
    public static long SetData(String did, Document doc, String status){
        if (did==null || !ObjectId.isValid(did)) return 0;
        doc.put(STATUS,status);
        return MongoUtil.replaceOne(COLNAME,Filters.eq("_id",new ObjectId(did)),doc).getModifiedCount();
//        return MongoUtil.updateOne(COLNAME, Filters.eq("_id",new ObjectId(did)),"$set", new Document("data",doc).append("status",status)).getModifiedCount();
    }

    /**
     * 获取指定的一份文档
     * @param did 文档数据id
     * @return
     */
    public static Document GetData(String did){
        if (did==null || !ObjectId.isValid(did)) return null;
        Document doc = MongoUtil.findOne(COLNAME, Filters.eq("_id", new ObjectId(did)));
        doc.put(CREATED, MongoUtil.FORMAT.format(doc.get(CREATED)));
        doc.put(UPDATED, MongoUtil.FORMAT.format(doc.get(UPDATED)));
        doc.put("_id",doc.get("_id").toString());
        return doc;
    }

    /**
     * 获取模板的所有文档数据
     * @param fid 表单模板id
     * @return
     */
    public static List<Document> GetDataByForm(String fid){
        if (fid==null || !ObjectId.isValid(fid)) return null;
        MongoCursor<Document> mcd = MongoUtil.find(COLNAME, Filters.eq("fid",new ObjectId(fid)));
        List<Document> ldoc = new ArrayList<Document>();
        while (mcd.hasNext()){
            Document doc = mcd.next();
            doc.put(CREATED, MongoUtil.FORMAT.format(doc.get(CREATED)));
            doc.put(UPDATED, MongoUtil.FORMAT.format(doc.get(UPDATED)));
            doc.put("_id",doc.get("_id").toString());
            ldoc.add(doc);
        }
        return ldoc;
//        return MongoUtil.find(COLNAME, Filters.eq("fid", new ObjectId(fid)));
    }

    /**
     * 获取用户的所有文档数据
     * @param uid 用户id
     * @return
     */
    public static List<Document> GetDataByUser(String uid){
        if (uid==null || !ObjectId.isValid(uid)) return null;
        MongoCursor<Document> mcd = MongoUtil.find(COLNAME, Filters.eq("uid",new ObjectId(uid)));
        List<Document> ldoc = new ArrayList<Document>();
        while (mcd.hasNext()){
            Document doc = mcd.next();
            doc.put(CREATED, MongoUtil.FORMAT.format(doc.get(CREATED)));
            doc.put(UPDATED, MongoUtil.FORMAT.format(doc.get(UPDATED)));
            doc.put("_id",doc.get("_id").toString());
            ldoc.add(doc);
        }
        return ldoc;
//        return MongoUtil.find(COLNAME, Filters.eq("uid", new ObjectId(uid)));
    }

    /**
     * 获取系统内的所有文档数据
     * @return
     */
    public static List<Document> ListDatas(Bson filter){
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

    public static void main(String[] args) {
        DataDB.AddData("5848da9a8ebe033a2870e57d","5848cb848ebe0310a459599d",new Document("key","value"),"pending");

        Iterator<Document> mc = DataDB.ListDatas(new Document()).iterator();
        while (mc.hasNext()){
            System.out.println(mc.next());
        }
    }

}
