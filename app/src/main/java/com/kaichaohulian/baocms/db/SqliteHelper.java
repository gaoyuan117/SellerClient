package com.kaichaohulian.baocms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kaichaohulian.baocms.entity.MessageEntity;
import com.kaichaohulian.baocms.entity.UserInfo;

/**
 * 数据库基类
 * Created by ljl on 2016/12/11.
 */
public class SqliteHelper extends SQLiteOpenHelper {
    // 用户表
    public static final String TB_USER = "User";
    // 消息表
    public static final String TB_MESSAGE = "Message";
    /**
     * 创建数据库
     *
     * @param context
     * @param factory 模式
     * @param version 版本
     */
    public SqliteHelper(Context context, String DB_NAME, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
    }

    /**
     * 创建表
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 用户表
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                TB_USER + "(" +
                UserInfo.ID + " integer primary key," +
                UserInfo.USERID + " varchar," +
                UserInfo.CREATEDTIME + " varchar," +
                UserInfo.CREATOR + " varchar," +
                UserInfo.ISLOCKED + " varchar," +
                UserInfo.USERNAME + " varchar," +
                UserInfo.LASTMODIFIEDTIME + " varchar," +
                UserInfo.LASTMODIFIER + " varchar," +
                UserInfo.PASSWORD + " varchar," +
                UserInfo.ACCOUNTNUMBER + " varchar," +
                UserInfo.QRCODE + " varchar," +
                UserInfo.DISTRICTID + " varchar," +
                UserInfo.SEX + " varchar," +
                UserInfo.THERMALSIGNATRUE + " varchar," +
                UserInfo.PHONENUMBER + " varchar," +
                UserInfo.USEREMAIL + " varchar," +
                UserInfo.BALANCE + " varchar," +
                UserInfo.AVATAR + " varchar," +
                UserInfo.BACKAVATAR + " varchar," +
                UserInfo.LOGINFAILEDCOUNT + " integer," +
                UserInfo.PAYPASSWORD + " integer" +
                ")"
        );

        // 用户表
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                TB_MESSAGE + "(" +
                MessageEntity.ID + " integer primary key," +
                MessageEntity.MSGID + " varchar," +
                MessageEntity.TOID + " varchar," +
                MessageEntity.CREATEDTIME + " varchar," +
                MessageEntity.AVATAR + " varchar," +
                MessageEntity.NAME + " varchar," +
                MessageEntity.ISSEND + " varchar," +
                MessageEntity.ISREND + " integer," +
                MessageEntity.ISTOP + " integer," +
                MessageEntity.MESSAGE + " varchar," +
                MessageEntity.TYPE + " varchar," +
                MessageEntity.DIRECTION + " varchar," +
                MessageEntity.MYUSERID + " varchar" +
                ")"
        );
//        createAddress(db);
        Log.e("Database", "onCreate");
    }

    private void createAddress(SQLiteDatabase db) {
        db.execSQL("");
    }

    /**
     * 版本更新
     *
     * @param db
     * @param oldVersion 老版本号
     * @param newVersion 新版本号
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_USER);
        onCreate(db);
        Log.e("Database", "onUpgrade");
    }

}
