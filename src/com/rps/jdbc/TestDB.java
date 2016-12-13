package com.rps.jdbc;

import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.jws.soap.SOAPBinding;

/**
 * 测试类
 * Created by yinhao on 2016/12/13.
 */
public class TestDB {

    public static void UserTest(){
        UserDB.InitAdmin();
        UserDB.AddDept("world");
        System.out.println(UserDB.GetUserByName("yinhao"));
        System.out.println(UserDB.GetDepts().toString());
        MongoCursor<Document> mc = UserDB.ListUsers();
        while (mc.hasNext()){
            System.out.println(mc.next());
        }
    }

    public static void main(String[] args){
//        FormDB.AddForm("test","test");
        FormDB.AddItem("584fed438ebe0321dc8974ba","日期","date","text","年月日","",0,true);
        FormDB.AddItem("584fed438ebe0321dc8974ba","姓名","name","text","username","$user",1,true);
        System.out.println(FormDB.ListItems("584fed438ebe0321dc8974ba").toString());
//        FormDB.DropItems("584fed438ebe0321dc8974ba");
        MongoCursor<Document> mc = FormDB.ListForms();
        while (mc.hasNext()){
            System.out.println(mc.next());
        }

    }
}
