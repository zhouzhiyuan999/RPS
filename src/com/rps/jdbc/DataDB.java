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
 * �û�ƾ֤������ <br/>
 * Created by yinhao on 2016/12/8.
 * @author yinhao
 * @version 1.0
 */
public class DataDB {

    final private static String COLNAME = "data";

    /**
     * ��Ӷ�Ӧ�����ĵ�
     * @param fid ��ģ��id
     * @param uid �û�id
     * @param doc ������
     * @param status ��ǰ�ĵ�״̬
     */
    public static void AddData(String fid, String uid, Document doc, String status){
        Document newdoc = new Document("fid",new ObjectId(fid)).append("uid",new ObjectId(uid)).append("data",doc).append("status",status).append("created",new Date()).append("updated", new Date());
        MongoUtil.insertOne(COLNAME, newdoc);
    }

    /**
     * ɾ��ָ���ĵ�����
     * @param did �ĵ�����id
     * @return
     */
    public static DeleteResult DelData(String did){
        return MongoUtil.deleteOne(COLNAME, Filters.eq("did",new ObjectId(did)));
    }

    /**
     * ���±�����
     * @param did ��ģ��id
     * @param doc �����������ĵ�
     * @param status ��ǰ������״̬
     * @return
     */
    public static UpdateResult EditData(String did, Document doc, String status){
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id",new ObjectId(did)),"$set", new Document("data",doc).append("status",status));
    }

    /**
     * ��ȡָ����һ���ĵ�
     * @param did �ĵ�����id
     * @return
     */
    public static Document GetData(String did){
        return MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(did)));
    }

    /**
     * ��ȡģ��������ĵ�����
     * @param fid ��ģ��id
     * @return
     */
    public static MongoCursor<Document> GetDataByForm(String fid){
        return MongoUtil.find(COLNAME, Filters.eq("fid", new ObjectId(fid)));
    }

    /**
     * ��ȡ�û��������ĵ�����
     * @param uid �û�id
     * @return
     */
    public static MongoCursor<Document> GetDataByUser(String uid){
        return MongoUtil.find(COLNAME, Filters.eq("uid", new ObjectId(uid)));
    }

    /**
     * ��ȡϵͳ�ڵ������ĵ�����
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
