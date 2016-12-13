package com.rps.jdbc;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.List;

/**
 * ���ݿ���������� <br>
 * Created by yinhao on 2016/12/8.
 * @author yinhao
 * @version 1.0
 */
public class MongoUtil {

    final private static String host = "localhost";
    final private static int port = 27017;
    final private static String dbname = "rps_db";

    private MongoUtil(){}

    private static MongoCollection<Document> getCollection(String colname){
        MongoCollection<Document> col = null;
        try {
            MongoClient mongoClient = new MongoClient(host,port);
            MongoDatabase database = mongoClient.getDatabase(dbname);
            col = database.getCollection(colname);
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return col;
    }

    /**
     * ������ǰ�����и��ݸ���ɸѡ�����µ��ĵ���
     * @param colname ��������
     * @param filter ɸѡ����
     * @return �������ĵ�������
     */
    public static long count(String colname, Bson filter){
        return getCollection(colname).count(filter);
    }

    /**
     * ɾ������������ɸѡ�����Ķ���ĵ�
     * @param colname ��������
     * @param filter ɸѡ����
     * @return ɾ�������Ľ��
     */
    public static DeleteResult deleteMany(String colname, Bson filter){
        return getCollection(colname).deleteMany(filter);
    }

    /**
     * ɾ������������ɸѡ�����ĵ�һ���ĵ�
     * @param colname ��������
     * @param filter ɸѡ����
     * @return ɾ�������Ľ��
     */
    public static DeleteResult deleteOne(String colname, Bson filter){
        return getCollection(colname).deleteOne(filter);
    }

    /**
     * �����ݿ���ɾ��ָ������
     * @param colname ��������
     */
    public static void drop(String colname){
        getCollection(colname).drop();
    }

    /**
     * ��ѯ��������������ɸѡ�������ĵ�
     * @param colname ��������
     * @param filter ɸѡ����
     * @return �ĵ����Ϲ��
     */
    public static MongoCursor<Document> find(String colname, Bson filter){
        return getCollection(colname).find(filter).iterator();
    }

    /**
     * ��ѯ����������ɸѡ�����ĵ�һ���ĵ�
     * @param colname ��������
     * @param filter ɸѡ����
     * @return �ĵ�����
     */
    public static Document findOne(String colname, Bson filter){
        return getCollection(colname).find(filter).first();
    }

    /**
     * һ���Բ������ĵ���������
     * @param colname ��������
     * @param docs ����ĵ����б����
     */
    public static void insertMany(String colname, List<Document> docs){
        getCollection(colname).insertMany(docs);
    }

    /**
     * ���뵥���ĵ���������
     * @param colname ��������
     * @param doc �ĵ�����
     */
    public static void insertOne(String colname, Document doc){
        getCollection(colname).insertOne(doc);
    }

    /**
     * �滻����������ɸѡ�����ĵ����ĵ�
     * @param colname ��������
     * @param filter ɸѡ����
     * @param doc �ĵ�����
     * @return �滻���
     */
    public static UpdateResult replaceOne(String colname, Bson filter, Document doc){
        return getCollection(colname).replaceOne(filter, doc);
    }

    /**
     * ���¼���������ɸѡ�����Ķ���ĵ�������
     * @param colname ��������
     * @param filter ɸѡ����
     * @param op Ҫ���µĲ����취
     * @param value ���µ�value����
     * @return ���½��
     */
    public static UpdateResult updateMany(String colname, Bson filter, String op, Object value){
        return getCollection(colname).updateMany(filter, new Document(op, value));
    }

    /**
     * ���¼���������ɸѡ�����ĵ�һ���ĵ�������
     * @param colname ��������
     * @param filter ɸѡ����
     * @param op Ҫ���µ�keyֵ
     * @param value ���µ�value����
     * @return ���½��
     */
    public static UpdateResult updateOne(String colname, Bson filter, String op, Object value){
        return getCollection(colname).updateOne(filter, new Document(op, value));
    }








}
