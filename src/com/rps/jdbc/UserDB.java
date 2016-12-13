package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;

/**
 * 数据库用户管理类 <br>
 * Created by yinhao on 2016/12/8.
 * @author yinhao
 * @version 1.0
 */
public class UserDB {

    final private static String COLNAME = "users";

    private UserDB(){};

    /**
     * 初始化管理员用户名为admin，密码为123456，部门默认为无 <br>
     * 管理员角色整个系统有且仅有一个
     */
    public static void InitAdmin(){
        if (MongoUtil.findOne(COLNAME, Filters.eq("username", "admin"))==null) {
            ArrayList<String> dept = new ArrayList<String>();
            dept.add("无");
            Document doc = new Document("username","admin").append("password","123456").append("depts",dept);
            MongoUtil.insertOne(COLNAME, doc);
        }
    }

    /**
     * 重置管理员用户的密码
     * @param password 密码
     * @return 被修改的记录数
     */
    public static long SetAdmin(String password){
        Document doc = new Document("password",password);
        return MongoUtil.updateOne(COLNAME, Filters.eq("username","admin"),"$set",doc).getModifiedCount();
    }

    /**
     * 获取当前系统中的部门信息
     * @return 部门列表
     */
    public static ArrayList<String> GetDepts(){
        Document doc = MongoUtil.findOne(COLNAME, Filters.eq("username", "admin"));
        return (ArrayList<String>) doc.get("depts");
    }

    /**
     * 添加一个部门
     * @param dept 部门名称
     * @return 添加的记录数，0或1
     */
    public static long AddDept(String dept){
        return MongoUtil.updateOne(COLNAME, Filters.eq("username","admin"),"$addToSet",new Document("depts",dept)).getModifiedCount();
    }

    /**
     * 删除一个部门,同时将该部门用户移到默认分组“无”
     * @param dept 部门名称
     * @return 因删除部门被影响用户的记录数
     */
    public static long DelDept(String dept){
        if (!dept.equals("无")) {
            MongoUtil.updateOne(COLNAME, Filters.eq("username", "admin"), "$pull", new Document("depts", dept));
            return MongoUtil.updateMany(COLNAME, Filters.eq("dept", dept), "$set", new Document("dept", "无")).getModifiedCount();
        }
        return 0;
    }

    /**
     * 重命名一个部门，同时要更改相应用户的所属部门名
     * @param old 旧部门名
     * @param now 新部门名
     * @return 被修改部门名称所影响的用户的记录数
     */
    public static long ReDept(String old, String now){
        if (!old.equals("无") && !now.equals("无")){
            MongoUtil.updateOne(COLNAME, Filters.and(Filters.eq("username", "admin"), Filters.eq("depts",old)),"$set",new Document("depts.$",now));
            return MongoUtil.updateMany(COLNAME, Filters.eq("dept", old), "$set", new Document("dept", now)).getModifiedCount();
        }
        return 0;
    }

    /**
     * 新增一个用户
     * @param username 用户名
     * @param password 密码
     * @param type 用户类型
     * @param dept 部门名称
     * @return true表示新增用户成功，false表示用户已存在
     */
    public static boolean AddUser(String username, String password, String type, String dept){
        if (MongoUtil.findOne(COLNAME, Filters.eq("username", username))==null) {
            Document doc = new Document("username", username).append("password", password).append("type", type);
            if (!GetDepts().contains(dept)){dept="无";}
            doc.append("dept",dept);
            MongoUtil.insertOne(COLNAME,doc);
            return true;
        }
        return false;
    }

    /**
     * 根据用户id删除用户，admin除外
     * @param oid 用户id
     * @return 被删除的记录数，0或1
     */
    public static long DelUserById(String oid){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            return MongoUtil.deleteOne(COLNAME, Filters.eq("_id", new ObjectId(oid))).getDeletedCount();
        }
        return 0;
    }

    /**
     * 根据用户名删除用户，admin除外
     * @param username 用户名
     * @return 被删除的记录数，0或1
     */
    public static long DelUserByName(String username){
        if (!username.equals("admin")){
            return MongoUtil.deleteOne(COLNAME, Filters.eq("username", username)).getDeletedCount();
        }
        return 0;
    }

    /**
     * 根据用户id更改用户密码，admin除外
     * @param oid 用户id
     * @param password 用户密码
     * @return 被成功修改的用户数，0或1
     */
    public static long SetUserById(String oid, String password){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            Document doc = new Document("password", password);
            return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(oid)), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * 根据用户id更改用户基本属性，包括权限以及所属部门，admin除外
     * @param oid 用户id
     * @param type 用户类型
     * @param dept 部门名称
     * @return 被成功修改的用户数，0或1
     */
    public static long SetUserById(String oid, String type, String dept){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            Document doc = new Document("type", type);
            if (!GetDepts().contains(dept)){dept="无";}
            doc.append("dept",dept);
            return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(oid)), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * 根据用户id更新用户密码、类型以及所属部门，admin除外
     * @param oid 用户id
     * @param password 用户密码
     * @param type 用户类型
     * @param dept 部门名称
     * @return 被成功修改的用户数，0或1
     */
    public static long SetUserById(String oid, String password, String type, String dept){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            Document doc = new Document("password", password).append("type", type);
            if (!GetDepts().contains(dept)){dept="无";}
            doc.append("dept",dept);
            return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(oid)), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * 根据用户名更新用户密码，admin除外
     * @param username 用户名
     * @param password 用户密码
     * @return 被成功修改的用户数，0或1
     */
    public static long SetUserByName(String username, String password){
        if (!username.equals("admin")){
            Document doc = new Document("password", password);
            return MongoUtil.updateOne(COLNAME, Filters.eq("username", username), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * 根据用户名更新用户类型以及所属部门，admin除外
     * @param username 用户名
     * @param type 用户类型
     * @param dept 部门名称
     * @return 被成功修改的用户数，0或1
     */
    public static long SetUserByName(String username, String type, String dept){
        if (!username.equals("admin")){
            Document doc = new Document("type", type);
            if (!GetDepts().contains(dept)){dept="无";}
            doc.append("dept",dept);
            return MongoUtil.updateOne(COLNAME, Filters.eq("username", username), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * 根据用户名更新用户密码、类型以及所属部门，admin除外
     * @param username 用户名
     * @param password 用户密码
     * @param type 用户类型
     * @param dept 部门名称
     * @return 被成功修改的用户数，0或1
     */
    public static long SetUserByName(String username, String password, String type, String dept){
        if (!username.equals("admin")){
            Document doc = new Document("password", password).append("type", type);
            if (!GetDepts().contains(dept)){dept="无";}
            doc.append("dept",dept);
            return MongoUtil.updateOne(COLNAME, Filters.eq("username", username), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * 根据用户名查找该用户的文档信息，admin除外
     * @param username 用户名
     * @return 用户档案
     */
    public static Document GetUserByName(String username){
        if (!username.equals("admin")){
            return MongoUtil.findOne(COLNAME, Filters.eq("username",username));
        }
        return null;
    }

    /**
     * 根据用户id查找该用户的文档信息，admin除外
     * @param oid 用户id
     * @return 用户档案
     */
    public static Document GetUserById(String oid){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            return MongoUtil.findOne(COLNAME, Filters.eq("_id", new ObjectId(oid)));
        }
        return null;
    }

    /**
     * 列出当前集合中的所有用户，admin除外
     * @return 所有用户的档案信息
     */
    public static MongoCursor<Document> ListUsers(){
        return MongoUtil.find(COLNAME, Filters.ne("username", "admin"));
    }

}
