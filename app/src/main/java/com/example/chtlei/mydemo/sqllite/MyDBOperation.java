package com.example.chtlei.mydemo.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chtlei on 19-6-3.
 */

public class MyDBOperation {

    private static volatile MyDBOperation mInstance;
    SQLiteOpenHelper dbHelper;
    private static final String TABLE_NAME = "person";

    private MyDBOperation (Context context) {
        dbHelper = new MyDBHelper(context,"test.db");
    }

    public static MyDBOperation getInstance(Context context) {
        if (null == mInstance) {
            synchronized (MyDBOperation.class) {
                if (null == mInstance) {
                    mInstance = new MyDBOperation(context);
                }
            }
        }

        return mInstance;
    }

    public void insertItem () {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        try {
            // a. 创建ContentValues对象
            ContentValues values = new ContentValues();

            // b. 向该对象中插入键值对
            //其中，key = 列名，value = 插入的值
            //注：ContentValues内部实现 = HashMap，区别在于：ContenValues Key只能是String类型，Value可存储基本类型数据 & String类型
            for (int i = 1; i < 11; i++) {
                values.put("id", i);
                values.put("name", "lct" + i);
                values.put("address","wuhan" + i);

                db.insert(TABLE_NAME, null, values);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        // 注：也可采用SQL语句插入
//        String sql = "insert into user (id,name) values (1,'carson')";
//        db.execSQL(sql);
    }

    public void deleteItem () {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TABLE_NAME,"id=?",new String[]{"2"});

        // 注：也可采用SQL语句修改
//        String sql = "delete from person where id="1";
//        db.execSQL(sql);

        db.close();
    }

    public void updateItem () {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // a. 创建一个ContentValues对象
        ContentValues values = new ContentValues();

        values.put("name","test");
        values.put("address","testAddress");

        db.update(TABLE_NAME,values,"id=?",new String[]{"1"});

        //注：也可采用SQL语句修改
//        String sql = "update [person] set name = 'zhangsan' where id="1"";
//        db.execSQL(sql);

        db.close();
    }

    public void queryItem () {
        //查询无法用SQL语句,只能这么写
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] cloums = new String[]{
                "id",
                "name",
                "address"
        };

        Cursor c = db.query(TABLE_NAME,cloums,null,null,null,null,null);

        while (c.moveToNext()) {
            System.out.println("queryItem: " + c.getInt(0) + " " + c.getString(1) + " " + c.getString(2));
        }

        c.close();
        db.close();
    }
}
