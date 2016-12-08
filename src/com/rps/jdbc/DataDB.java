package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BSON;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.Date;

/**
 * Created by yinhao on 2016/12/8.
 */
public class DataDB {

    final private static String COLNAME = "data";

    /**
     * 添加对应表单的文档
     * @param fid 表单模板id
     * @param uid 用户id
     * @param doc 表单内容
     * @param status 当前文档状态
     */
    public static void AddData(String fid, String uid, Document doc, String status){
        Document newdoc = new Document("fid",new ObjectId(fid)).append("uid",new ObjectId(uid)).append("data",doc).append("status",status).append("created",new Date()).append("updated", new Date());
        MongoUtil.insertOne(COLNAME, newdoc);
    }

    /**
     * 删除指定文档数据
     * @param did 文档数据id
     * @return
     */
    public static DeleteResult DelData(String did){
        return MongoUtil.deleteOne(COLNAME, Filters.eq("did",new ObjectId(did)));
    }

    /**
     * 更新表单内容
     * @param did 表单模板id
     * @param doc 表单数据内容文档
     * @param status 当前表单内容状态
     * @return
     */
    public static UpdateResult EditData(String did, Document doc, String status){
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id",new ObjectId(did)),"$set", new Document("data",doc).append("status",status));
    }

    /**
     * 获取指定的一份文档
     * @param did 文档数据id
     * @return
     */
    public static Document GetData(String did){
        return MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(did)));
    }

    /**
     * 获取模板的所有文档数据
     * @param fid 表单模板id
     * @return
     */
    public static MongoCursor<Document> GetDataByForm(String fid){
        return MongoUtil.find(COLNAME, Filters.eq("fid", new ObjectId(fid)));
    }

    /**
     * 获取用户的所有文档数据
     * @param uid 用户id
     * @return
     */
    public static MongoCursor<Document> GetDataByUser(String uid){
        return MongoUtil.find(COLNAME, Filters.eq("uid", new ObjectId(uid)));
    }

    /**
     * 获取系统内的所有文档数据
     * @return
     */
    public static MongoCursor<Document> ListDatas(){
        return MongoUtil.find(COLNAME, new Document());
    }

    public static void main(String[] args) {
        DataDB.AddData("5848da9a8ebe033a2870e57d","5848cb848ebe0310a459599d",new Document("key","value"),"pending");

        MongoCursor<Document> mc = DataDB.ListDatas();
        while (mc.hasNext()){
            System.out.println(mc.next());
        }
    }

}
