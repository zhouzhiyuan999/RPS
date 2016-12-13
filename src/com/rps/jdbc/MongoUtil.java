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
 * 数据库操作工具类 <br>
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
     * 计数当前集合中根据给定筛选条件下的文档数
     * @param colname 集合名称
     * @param filter 筛选条件
     * @return 集合中文档的数量
     */
    public static long count(String colname, Bson filter){
        return getCollection(colname).count(filter);
    }

    /**
     * 删除集合中满足筛选条件的多个文档
     * @param colname 集合名称
     * @param filter 筛选条件
     * @return 删除操作的结果
     */
    public static DeleteResult deleteMany(String colname, Bson filter){
        return getCollection(colname).deleteMany(filter);
    }

    /**
     * 删除集合中满足筛选条件的第一个文档
     * @param colname 集合名称
     * @param filter 筛选条件
     * @return 删除操作的结果
     */
    public static DeleteResult deleteOne(String colname, Bson filter){
        return getCollection(colname).deleteOne(filter);
    }

    /**
     * 从数据库中删除指定集合
     * @param colname 集合名称
     */
    public static void drop(String colname){
        getCollection(colname).drop();
    }

    /**
     * 查询集合中所有满足筛选条件的文档
     * @param colname 集合名称
     * @param filter 筛选条件
     * @return 文档集合光标
     */
    public static MongoCursor<Document> find(String colname, Bson filter){
        return getCollection(colname).find(filter).iterator();
    }

    /**
     * 查询集合中满足筛选条件的第一个文档
     * @param colname 集合名称
     * @param filter 筛选条件
     * @return 文档对象
     */
    public static Document findOne(String colname, Bson filter){
        return getCollection(colname).find(filter).first();
    }

    /**
     * 一次性插入多个文档到集合中
     * @param colname 集合名称
     * @param docs 多个文档的列表对象
     */
    public static void insertMany(String colname, List<Document> docs){
        getCollection(colname).insertMany(docs);
    }

    /**
     * 插入单个文档到集合中
     * @param colname 集合名称
     * @param doc 文档对象
     */
    public static void insertOne(String colname, Document doc){
        getCollection(colname).insertOne(doc);
    }

    /**
     * 替换集合中满足筛选条件的单个文档
     * @param colname 集合名称
     * @param filter 筛选条件
     * @param doc 文档对象
     * @return 替换结果
     */
    public static UpdateResult replaceOne(String colname, Bson filter, Document doc){
        return getCollection(colname).replaceOne(filter, doc);
    }

    /**
     * 更新集合中满足筛选条件的多个文档的内容
     * @param colname 集合名称
     * @param filter 筛选条件
     * @param op 要更新的操作办法
     * @param value 更新的value内容
     * @return 更新结果
     */
    public static UpdateResult updateMany(String colname, Bson filter, String op, Object value){
        return getCollection(colname).updateMany(filter, new Document(op, value));
    }

    /**
     * 更新集合中满足筛选条件的第一个文档的内容
     * @param colname 集合名称
     * @param filter 筛选条件
     * @param op 要更新的key值
     * @param value 更新的value内容
     * @return 更新结果
     */
    public static UpdateResult updateOne(String colname, Bson filter, String op, Object value){
        return getCollection(colname).updateOne(filter, new Document(op, value));
    }








}
