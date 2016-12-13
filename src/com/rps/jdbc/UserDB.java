package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;

/**
 * ���ݿ��û������� <br>
 * Created by yinhao on 2016/12/8.
 * @author yinhao
 * @version 1.0
 */
public class UserDB {

    final private static String COLNAME = "users";

    private UserDB(){};

    /**
     * ��ʼ������Ա�û���Ϊadmin������Ϊ123456������Ĭ��Ϊ�� <br>
     * ����Ա��ɫ����ϵͳ���ҽ���һ��
     */
    public static void InitAdmin(){
        if (MongoUtil.findOne(COLNAME, Filters.eq("username", "admin"))==null) {
            ArrayList<String> dept = new ArrayList<String>();
            dept.add("��");
            Document doc = new Document("username","admin").append("password","123456").append("depts",dept);
            MongoUtil.insertOne(COLNAME, doc);
        }
    }

    /**
     * ���ù���Ա�û�������
     * @param password ����
     * @return ���޸ĵļ�¼��
     */
    public static long SetAdmin(String password){
        Document doc = new Document("password",password);
        return MongoUtil.updateOne(COLNAME, Filters.eq("username","admin"),"$set",doc).getModifiedCount();
    }

    /**
     * ��ȡ��ǰϵͳ�еĲ�����Ϣ
     * @return �����б�
     */
    public static ArrayList<String> GetDepts(){
        Document doc = MongoUtil.findOne(COLNAME, Filters.eq("username", "admin"));
        return (ArrayList<String>) doc.get("depts");
    }

    /**
     * ���һ������
     * @param dept ��������
     * @return ��ӵļ�¼����0��1
     */
    public static long AddDept(String dept){
        return MongoUtil.updateOne(COLNAME, Filters.eq("username","admin"),"$addToSet",new Document("depts",dept)).getModifiedCount();
    }

    /**
     * ɾ��һ������,ͬʱ���ò����û��Ƶ�Ĭ�Ϸ��顰�ޡ�
     * @param dept ��������
     * @return ��ɾ�����ű�Ӱ���û��ļ�¼��
     */
    public static long DelDept(String dept){
        if (!dept.equals("��")) {
            MongoUtil.updateOne(COLNAME, Filters.eq("username", "admin"), "$pull", new Document("depts", dept));
            return MongoUtil.updateMany(COLNAME, Filters.eq("dept", dept), "$set", new Document("dept", "��")).getModifiedCount();
        }
        return 0;
    }

    /**
     * ������һ�����ţ�ͬʱҪ������Ӧ�û�������������
     * @param old �ɲ�����
     * @param now �²�����
     * @return ���޸Ĳ���������Ӱ����û��ļ�¼��
     */
    public static long ReDept(String old, String now){
        if (!old.equals("��") && !now.equals("��")){
            MongoUtil.updateOne(COLNAME, Filters.and(Filters.eq("username", "admin"), Filters.eq("depts",old)),"$set",new Document("depts.$",now));
            return MongoUtil.updateMany(COLNAME, Filters.eq("dept", old), "$set", new Document("dept", now)).getModifiedCount();
        }
        return 0;
    }

    /**
     * ����һ���û�
     * @param username �û���
     * @param password ����
     * @param type �û�����
     * @param dept ��������
     * @return true��ʾ�����û��ɹ���false��ʾ�û��Ѵ���
     */
    public static boolean AddUser(String username, String password, String type, String dept){
        if (MongoUtil.findOne(COLNAME, Filters.eq("username", username))==null) {
            Document doc = new Document("username", username).append("password", password).append("type", type);
            if (!GetDepts().contains(dept)){dept="��";}
            doc.append("dept",dept);
            MongoUtil.insertOne(COLNAME,doc);
            return true;
        }
        return false;
    }

    /**
     * �����û�idɾ���û���admin����
     * @param oid �û�id
     * @return ��ɾ���ļ�¼����0��1
     */
    public static long DelUserById(String oid){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            return MongoUtil.deleteOne(COLNAME, Filters.eq("_id", new ObjectId(oid))).getDeletedCount();
        }
        return 0;
    }

    /**
     * �����û���ɾ���û���admin����
     * @param username �û���
     * @return ��ɾ���ļ�¼����0��1
     */
    public static long DelUserByName(String username){
        if (!username.equals("admin")){
            return MongoUtil.deleteOne(COLNAME, Filters.eq("username", username)).getDeletedCount();
        }
        return 0;
    }

    /**
     * �����û�id�����û����룬admin����
     * @param oid �û�id
     * @param password �û�����
     * @return ���ɹ��޸ĵ��û�����0��1
     */
    public static long SetUserById(String oid, String password){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            Document doc = new Document("password", password);
            return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(oid)), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * �����û�id�����û��������ԣ�����Ȩ���Լ��������ţ�admin����
     * @param oid �û�id
     * @param type �û�����
     * @param dept ��������
     * @return ���ɹ��޸ĵ��û�����0��1
     */
    public static long SetUserById(String oid, String type, String dept){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            Document doc = new Document("type", type);
            if (!GetDepts().contains(dept)){dept="��";}
            doc.append("dept",dept);
            return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(oid)), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * �����û�id�����û����롢�����Լ��������ţ�admin����
     * @param oid �û�id
     * @param password �û�����
     * @param type �û�����
     * @param dept ��������
     * @return ���ɹ��޸ĵ��û�����0��1
     */
    public static long SetUserById(String oid, String password, String type, String dept){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            Document doc = new Document("password", password).append("type", type);
            if (!GetDepts().contains(dept)){dept="��";}
            doc.append("dept",dept);
            return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(oid)), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * �����û��������û����룬admin����
     * @param username �û���
     * @param password �û�����
     * @return ���ɹ��޸ĵ��û�����0��1
     */
    public static long SetUserByName(String username, String password){
        if (!username.equals("admin")){
            Document doc = new Document("password", password);
            return MongoUtil.updateOne(COLNAME, Filters.eq("username", username), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * �����û��������û������Լ��������ţ�admin����
     * @param username �û���
     * @param type �û�����
     * @param dept ��������
     * @return ���ɹ��޸ĵ��û�����0��1
     */
    public static long SetUserByName(String username, String type, String dept){
        if (!username.equals("admin")){
            Document doc = new Document("type", type);
            if (!GetDepts().contains(dept)){dept="��";}
            doc.append("dept",dept);
            return MongoUtil.updateOne(COLNAME, Filters.eq("username", username), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * �����û��������û����롢�����Լ��������ţ�admin����
     * @param username �û���
     * @param password �û�����
     * @param type �û�����
     * @param dept ��������
     * @return ���ɹ��޸ĵ��û�����0��1
     */
    public static long SetUserByName(String username, String password, String type, String dept){
        if (!username.equals("admin")){
            Document doc = new Document("password", password).append("type", type);
            if (!GetDepts().contains(dept)){dept="��";}
            doc.append("dept",dept);
            return MongoUtil.updateOne(COLNAME, Filters.eq("username", username), "$set", doc).getModifiedCount();
        }
        return 0;
    }

    /**
     * �����û������Ҹ��û����ĵ���Ϣ��admin����
     * @param username �û���
     * @return �û�����
     */
    public static Document GetUserByName(String username){
        if (!username.equals("admin")){
            return MongoUtil.findOne(COLNAME, Filters.eq("username",username));
        }
        return null;
    }

    /**
     * �����û�id���Ҹ��û����ĵ���Ϣ��admin����
     * @param oid �û�id
     * @return �û�����
     */
    public static Document GetUserById(String oid){
        if (!MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(oid))).getString("username").equals("admin")){
            return MongoUtil.findOne(COLNAME, Filters.eq("_id", new ObjectId(oid)));
        }
        return null;
    }

    /**
     * �г���ǰ�����е������û���admin����
     * @return �����û��ĵ�����Ϣ
     */
    public static MongoCursor<Document> ListUsers(){
        return MongoUtil.find(COLNAME, Filters.ne("username", "admin"));
    }

}
