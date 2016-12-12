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
 * 数据库操作工具类
 * Created by yinhao on 2016/12/8.
 */
public class MongoUtil {

    final private static String host = "localhost";
    final private static int port = 27017;
    final private static String dbname = "rps_db";

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

    public static Document findOne(String colname, Bson filter){
        return getCollection(colname).find(filter).first();
    }

    public static MongoCursor<Document> find(String colname, Bson filter){
        return getCollection(colname).find(filter).iterator();
    }

    public static int getCount(String colname){
        return (int) getCollection(colname).count();
    }

    public static void insertOne(String colname, Document doc){
        getCollection(colname).insertOne(doc);
    }

    public static void insertMany(String colname, List<Document> ldoc){
        getCollection(colname).insertMany(ldoc);
    }

    public static UpdateResult updateOne(String colname, Bson filter, String Modify, Document newdoc){
        return getCollection(colname).updateOne(filter, new Document(Modify, newdoc));
    }

    public static UpdateResult updateMany(String colname, Bson filter, String Modify, Document newdoc){
        return getCollection(colname).updateMany(filter, new Document(Modify, newdoc));
    }

    public static DeleteResult deleteOne(String colname, Bson filter){
        return getCollection(colname).deleteOne(filter);
    }

    public static DeleteResult deleteMany(String colname, Bson filter){
        return getCollection(colname).deleteMany(filter);
    }

    public void dropCollection(String colname){
        getCollection(colname).drop();
    }
}
