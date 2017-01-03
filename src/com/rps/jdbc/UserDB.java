package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.security.spec.PSSParameterSpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据库用户管理类 <br>
 * Created by yinhao on 2016/12/8.
 * @author yinhao
 * @version 1.0
 */
public class UserDB {

    private final static String COLNAME = "users";
//    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String REALNAME = "realname";
    public final static String ROLE = "role";
    public final static String DEPT = "dept";
    public final static String LASTTIME = "lasttime";


    private UserDB(){};

    /**
     * 初始化管理员用户名为admin，密码为123456，部门默认为无 <br>
     * 管理员角色整个系统有且仅有一个
     */
    public static void InitAdmin(){
        if (MongoUtil.findOne(COLNAME, Filters.eq(USERNAME, "admin"))==null) {
            Document doc = new Document(USERNAME,"admin").append(PASSWORD,"123456").append(REALNAME,"系统管理员").append(ROLE,"admin").append(LASTTIME,new Date());
            MongoUtil.insertOne(COLNAME, doc);
            DeptDB.InitDept();
        }
    }

    /**
     * 删除一个部门,同时将该部门用户移到默认分组“无”
     * @param number 部门名称
     * @return 因删除部门被影响用户的记录数
     */
    public static long ClearDept(String number){
        if (!number.equals(DeptDB.DEFAULT)) {
            return MongoUtil.updateMany(COLNAME, Filters.eq(DEPT, number), "$set", new Document(DEPT,DeptDB.DEFAULT)).getModifiedCount();
        }
        return 0;
    }

    /**
     * 新增一个用户
     * @param username 用户名
     * @param password 密码
     * @param realname 真实姓名
     * @param role 用户类型
     * @param number 部门编号
     * @return true表示新增用户成功，false表示用户已存在
     */
    public static boolean AddUser(String username, String password, String realname, String role, String number){
        if (MongoUtil.findOne(COLNAME, Filters.eq(USERNAME, username))==null) {
            Document doc = new Document(USERNAME, username).append(PASSWORD, password).append(REALNAME,realname).append(ROLE, role);
            if (!DeptDB.GetDeptNumbers(new Document()).contains(number)){number="";}
            doc.append(DEPT,number);
            doc.append(LASTTIME,new Date());
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
        if (oid==null || !ObjectId.isValid(oid)) return 0;
        Document doc = MongoUtil.findOne(COLNAME, Filters.eq("_id", new ObjectId(oid)));
        if (doc!=null && !doc.getString(USERNAME).equals("admin")){
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
        if (username==null) return 0;
        if (!username.equals("admin")){
            return MongoUtil.deleteOne(COLNAME, Filters.eq(USERNAME, username)).getDeletedCount();
        }
        return 0;
    }

    /**
     * 根据用户id更改用户密码
     * @param oid 用户id
     * @param doc 修改的内容封装成文档
     * @return 被成功修改的用户数，0或1
     */
    public static long SetUserById(String oid, Document doc){
        if (oid==null || !ObjectId.isValid(oid)) return 0;
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(oid)), "$set", doc).getModifiedCount();
    }

    /**
     * 根据用户名更新用户密码，admin除外
     * @param username 用户名
     * @param doc 修改的内容封装成文档
     * @return 被成功修改的用户数，0或1
     */
    public static long SetUserByName(String username, Document doc){
        return MongoUtil.updateOne(COLNAME, Filters.eq(USERNAME, username), "$set", doc).getModifiedCount();
    }

    /**
     * 根据用户名查找该用户的文档信息
     * @param username 用户名
     * @return 用户档案
     */
    public static Document GetUserByName(String username){
        if (username==null) return null;
        Document doc =  MongoUtil.findOne(COLNAME, Filters.eq(USERNAME,username));
        if (doc!=null){
            doc.put(LASTTIME, MongoUtil.FORMAT.format(doc.get(LASTTIME)));
            doc.put("_id",doc.get("_id").toString());
        }
        return doc;
    }

    /**
     * 根据用户id查找该用户的文档信息
     * @param oid 用户id
     * @return 用户档案
     */
    public static Document GetUserById(String oid){
        if (oid==null || !ObjectId.isValid(oid)) return null;
        Document doc = MongoUtil.findOne(COLNAME, Filters.eq("_id", new ObjectId(oid)));
        if (doc!=null){
            doc.put(LASTTIME, MongoUtil.FORMAT.format(doc.get(LASTTIME)));
            doc.put("_id",doc.get("_id").toString());
        }
        return doc;
    }

    /**
     * 列出当前集合中的所有用户
     * @return 所有用户的档案信息
     */
    public static List<Document> ListUsers(Bson filter){
        MongoCursor<Document> mcd = MongoUtil.find(COLNAME, filter);
        List<Document> ldoc = new ArrayList<Document>();
        while (mcd.hasNext()){
            Document doc = mcd.next();
            doc.put(LASTTIME, MongoUtil.FORMAT.format(doc.get(LASTTIME)));
            doc.put("_id",doc.get("_id").toString());
            ldoc.add(doc);
        }
        return ldoc;
    }

}