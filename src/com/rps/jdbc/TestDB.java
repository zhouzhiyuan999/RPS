package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * 测试类
 * Created by yinhao on 2016/12/13.
 */
public class TestDB {

    public static void UserTest(){
        UserDB.InitAdmin();
        DeptDB.AddDept("1234","world");
        System.out.println(UserDB.GetUserByName("yinhao"));
        System.out.println(DeptDB.GetDepts(new Document()).toString());
        Iterator<Document> it = UserDB.ListUsers(new Document()).iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }

    public static void main(String[] args){
        System.out.println(DeptDB.GetDeptByNumber("123"));
//        FormDB.AddForm("TEST","TEST");
//        UserTest();
//        System.out.println(FormDB.GetForm("5867585c9f004c0058a7c34d"));
//        FormDB.AddItem("5867585c9f004c0058a7c34d","name","name","text","","",1,true);
//        FormDB.AddForm("test","test");
//        FormDB.AddItem("584fed438ebe0321dc8974ba","日期","date","text","年月日","",0,true);
//        FormDB.AddItem("584fed438ebe0321dc8974ba","姓名","name","text","username","$user",1,true);
//        System.out.println(FormDB.ListItems("584fed438ebe0321dc8974ba").toString());
//        FormDB.DropItems("584fed438ebe0321dc8974ba");
//        Iterator<Document> mc = FormDB.ListForms().iterator();
//        while (mc.hasNext()){
//            System.out.println(mc.next());
//        }
//        UserDB.InitAdmin();
//
//        Iterator<Document> mc = UserDB.ListUsers(new Document("username",Pattern.compile("y",Pattern.CASE_INSENSITIVE))).iterator();
//        while (mc.hasNext()){
//            System.out.println(mc.next());
//        }
//        UserDB.DelUserById("586625d29f004c75fc492423");
//        System.out.println(ObjectId.isValid("586625d29f004c75fc492423"));
    }
}
