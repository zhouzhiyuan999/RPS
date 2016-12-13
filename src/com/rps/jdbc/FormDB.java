package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;

/**
 * ��ģ���� <br>
 * Created by yinhao on 2016/12/8.
 * @author yinhao
 * @version 1.0
 */
public class FormDB {

    final private static String COLNAME = "forms";

    /**
     * ��ӱ�ģ�壬�Զ���Ӵ����͸�������
     * @param title ģ�����
     * @param desc ģ������
     */
    public static void AddForm(String title, String desc){
        Document doc = new Document("title",title).append("desc",desc).append("items", new ArrayList()).append("created",new Date()).append("updated",new Date());
        MongoUtil.insertOne(COLNAME, doc);
    }

    /**
     * ɾ��ָ��ģ��
     * @param fid ��ģ��id
     * @return ��ɾ����ģ������0��1
     */
    public static long DelForm(String fid){
        return MongoUtil.deleteOne(COLNAME, Filters.eq("_id",new ObjectId(fid))).getDeletedCount();
    }

    /**
     * ����ָ��ģ����������������ʱ���Զ�����
     * @param fid ��ģ��id
     * @param title ģ�����
     * @param desc ģ������
     * @return ���޸ĵ�ģ������0��1
     */
    public static long EditForm(String fid, String title, String desc) {
        Document doc = new Document("title", title).append("desc", desc).append("updated", new Date());
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(fid)), "$set", doc).getModifiedCount();
    }

    /**
     * ��ȡָ��������������
     * @param fid ��ģ��id
     * @return ��ģ��
     */
    public static Document GetForm(String fid){
        return MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(fid)));
    }


    /**
     * ָ����ģ�����������
     * @param fid ��ģ��id
     * @param name ��ʾ������
     * @param label ��Ӧ���ݼ��ϵ�key
     * @param datatype �������ͣ�text��list��
     * @param content Ĭ�����ݣ�text��Ӧ��ʾ��Ϣ��list��Ӧ�б���
     * @param bind �Ƿ��ĳ��������$username��$date��$dept�ȵ�
     * @param seq �����
     * @param isneed �������־
     * @return �ɹ���ӵ���Ŀ��0��1
     */
    public static long AddItem(String fid, String name, String label, String datatype, Object content, String bind, int seq, boolean isneed) {
        Document doc = new Document("mid", new ObjectId()).append("name", name).append("label", label).append("datatype", datatype).append("content", content).append("bind",bind).append("seq", seq).append("isneed", isneed);
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(fid)), "$push", new Document("items", doc)).getModifiedCount();
    }

    /**
     * ɾ��ָ��ģ���ָ����
     * @param fid ��ģ��id
     * @param mid ������id
     * @return ��ɾ������Ŀ��0��1
     */
    public static long DelItem(String fid, String mid){
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id", new ObjectId(fid)), "$pull", new Document("items", new Document("mid", new ObjectId(mid)))).getModifiedCount();
    }

    /**
     * ��������������
     * @param fid ��ģ��id
     * @param mid ָ��������id
     * @param name ��ʾ������
     * @param label ��Ӧ���ݼ��ϵ�key
     * @param datatype �������ͣ�text��list��
     * @param content Ĭ�����ݣ�text��Ӧ��ʾ��Ϣ��list��Ӧ�б���
     * @param bind �Ƿ��ĳ��������$username��$date��$dept�ȵ�
     * @param seq �����
     * @param isneed �������־
     * @return ���ɹ��޸ĵ���Ŀ��0��1
     */
    public static long EditItem(String fid, String mid, String name, String label, String datatype, Object content, String bind, int seq, boolean isneed){
        Document doc = new Document("mid",new ObjectId(mid)).append("name",name).append("label", label).append("datatype",datatype).append("content",content).append("bind",bind).append("seq", seq).append("isneed",isneed);
        return MongoUtil.updateOne(COLNAME, Filters.and(Filters.eq("_id",new ObjectId(fid)),Filters.eq("items.mid",new ObjectId(mid))),"$set",new Document("items.$",doc)).getModifiedCount();
    }

    /**
     * ��ȡָ����������ĵ�����
     * @param fid ��ģ��id
     * @param mid ָ��������id
     * @return ����������
     */
    public static Document GetItem(String fid, String mid){
        ArrayList<Document> list = (ArrayList<Document>) MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(fid))).get("items");
        for (int i=0;i<list.size();i++){
            if(list.get(i).get("mid").equals(new ObjectId(mid))){
                return list.get(i);
            }
        }
        return null;
    }

    /**
     * ��ȡģ�������������
     * @param fid ��ģ��id
     * @return �������б�
     */
    public static ArrayList<Document> ListItems(String fid){
        return (ArrayList<Document>)MongoUtil.findOne(COLNAME, Filters.eq("_id",new ObjectId(fid))).get("items");
    }

    /**
     * ��ո�ģ�������������
     * @param fid ��ģ��id
     * @return ���޸ĵ���Ŀ
     */
    public static long DropItems(String fid){
        return MongoUtil.updateOne(COLNAME, Filters.eq("_id",new ObjectId(fid)),"$set",new Document("items",new ArrayList())).getModifiedCount();
    }

    /**
     * �г���ǰϵͳ�����б�ģ��
     * @return ����ģ�嵵��
     */
    public static MongoCursor<Document> ListForms(){
        return MongoUtil.find(COLNAME, new Document());
    }

}
