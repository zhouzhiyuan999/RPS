package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinhao on 2016/12/31.
 */
public class DeptDB {
    private final static String COLNAME = "depts";

    public final static String NUMBER = "number";
    public final static String NAME = "name";
    public final static String USERS = "users";

    public final static String DEFAULT = "default";

    private DeptDB(){};

    public static void InitDept(){
        if (MongoUtil.findOne(COLNAME, Filters.eq(NUMBER, DEFAULT))==null) {
            Document doc = new Document(NUMBER,DEFAULT).append(NAME,DEFAULT);
            MongoUtil.insertOne(COLNAME,doc);
        }
    }

    /**
     * 添加一个部门
     * @param number 部门编号
     * @param name 部门名称
     * @return true表示新增部门成功，false表示部门已存在
     */
    public static boolean AddDept(String number, String name){
        if (MongoUtil.findOne(COLNAME, Filters.eq(NUMBER, number))==null) {
            Document doc = new Document(NUMBER,number).append(NAME,name);
            MongoUtil.insertOne(COLNAME,doc);
            return true;
        }
        return false;
    }

    /**
     * 删除一个部门,同时将该部门用户移到默认分组“无”
     * @param number 部门编号
     * @return 因删除部门被影响用户的记录数
     */
    public static long DelDept(String number){
        if (number!=null && !number.equals(DEFAULT)) {
            MongoUtil.deleteOne(COLNAME, Filters.eq(NUMBER, number));
            return UserDB.ClearDept(number);
        }
        return 0;
    }

    /**
     * 重命名一个部门，同时要更改相应用户的所属部门名
     * @param number 旧部门名
     * @param nname 新部门名
     * @return 被修改部门名称所影响的用户的记录数
     */
    public static long ReDept(String number, String nname){
        return MongoUtil.updateOne(COLNAME, Filters.eq(NUMBER, number), "$set", new Document(NAME,nname)).getModifiedCount();
    }

    /**
     * 获取当前系统中的部门信息
     * @param filter 过滤条件
     * @return 部门列表
     */
    public static List<Document> GetDepts(Bson filter){
        MongoCursor<Document> mcd = MongoUtil.find(COLNAME, filter);
        List<Document> ldoc = new ArrayList<Document>();
        while (mcd.hasNext()){
            Document doc = mcd.next();
            doc.append(USERS,UserDB.ListUsers(Filters.eq(UserDB.DEPT, doc.getString(NUMBER))));
            ldoc.add(doc);
        }
        return ldoc;
    }

    /**
     * 获取所有部门的编号信息
     * @param filter 过滤条件
     * @return 部门编号列表
     */
    public static List<String> GetDeptNumbers(Bson filter){
        MongoCursor<Document> mcd = MongoUtil.find(COLNAME, filter);
        List<String> ldoc = new ArrayList<String>();
        while (mcd.hasNext()){
            Document doc = mcd.next();
            ldoc.add(doc.getString(NUMBER));
        }
        return ldoc;
    }

    /**
     * 获取部门编号对应的部门名称
     * @param number 部门编号
     * @return 返回部门名称
     */
    public static Document GetDeptByNumber(String number){
        if (number==null) return null;
        Document doc = MongoUtil.findOne(COLNAME, Filters.eq(NUMBER, number));
        doc.append(USERS,UserDB.ListUsers(Filters.eq(UserDB.DEPT, number)));
        return doc;
    }

}
