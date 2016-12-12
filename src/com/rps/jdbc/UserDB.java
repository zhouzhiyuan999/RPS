package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.jws.soap.SOAPBinding;

/**
 * Created by yinhao on 2016/12/8.
 */
public class UserDB {

    final private static String COLNAME = "users";

    /**
     * 新增一个用户，若已存在则返回false
     * @param username 用户名
     * @param password 密码
     * @param type 用户类型
     * @return true or false
     */
    public static boolean AddUser(String username, String password, String type){
        if (MongoUtil.findOne(COLNAME, Filters.eq("username", username))==null) {
            Document doc = new Document("username", username).append("password", password).append("type", type);
            MongoUtil.insertOne(COLNAME,doc);
            return true;
        }
        return false;
    }

    /**
     * 更新用户密码和类型
     * @param oid
     * @param password
     * @param type
     * @return
     */
    public static UpdateResult SetUser(String oid, String password, String type){
        Document doc = new Document("password",password).append("type",type);
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(oid)), "$set", doc);
    }

    /**
     * 根据用户名查找该用户的文档信息
     * @param username
     * @return
     */
    public static Document GetUserByName(String username){
        return MongoUtil.findOne(COLNAME, Filters.eq("username",username));
    }

    /**
     * 根据用户id查找该用户的文档信息
     * @param oid
     * @return
     */
    public static Document GetUserById(String oid){
        return MongoUtil.findOne(COLNAME, Filters.eq("_id", new ObjectId(oid)));
    }

    /**
     * 根据用户id删除用户
     * @param oid
     * @return
     */
    public static DeleteResult DelUser(String oid){
        return MongoUtil.deleteOne(COLNAME, Filters.eq("_id", new ObjectId(oid)));
    }

    /**
     * 列出当前集合中的所有用户
     * @return
     */
    public static MongoCursor<Document> ListUsers(){
        return MongoUtil.find(COLNAME, new Document());
    }

    /**
     * 测试函数
     * @param args
     */
    public static void main(String[] args) {
        UserDB.AddUser("yinhao","123456","admin");
        UserDB.AddUser("longlong","123456","admin");
        UserDB.AddUser("zhouzhiyuan","123456","admin");
        MongoCursor<Document> mc = UserDB.ListUsers();
        while (mc.hasNext()){
            System.out.println(mc.next());
        }

        Document doc = UserDB.GetUserByName("zhouzhiyuan");
        UserDB.DelUser(doc.get("_id").toString());
        doc = UserDB.GetUserByName("yinhao");
        UserDB.SetUser(doc.get("_id").toString(),"654321","admin");

        mc = UserDB.ListUsers();
        while (mc.hasNext()){
            System.out.println(mc.next());
        }
    }
}
