package com.kaichaohulian.baocms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;


import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.MessageEntity;
import com.kaichaohulian.baocms.entity.UserInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库实现
 * Created by ljl on 2016/12/11.
 */
public class DataHelper {
    // 数据库名称
    private static String DB_NAME = "buyers.db";
    // 数据库版本
    private static int DB_VERSION = 2;
    private SQLiteDatabase db;
    private SqliteHelper dbHelper;

    public DataHelper(Context context) {
        dbHelper = new SqliteHelper(context, DB_NAME, null, DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public void Close() {
        db.close();
        dbHelper.close();
    }

    /**
     * 获取用户数据
     * @return UserInfo
     */
    public UserInfo GetUser(String UserId) {
        Log.e("GetUser", UserId);
        UserInfo user = null;
        Cursor cursor = db.query(SqliteHelper.TB_USER, null, " userId = ? " , new String[]{UserId}, null, null, UserInfo.ID + " DESC");
        if(cursor.moveToFirst()){
            user = new UserInfo();
            user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            user.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
            user.setAvatar(cursor.getString(cursor.getColumnIndex("avatar")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setThermalSignatrue(cursor.getString(cursor.getColumnIndex("thermalSignatrue")));
            user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));
            user.setAccountNumber(cursor.getString(cursor.getColumnIndex("accountNumber")));
            user.setBackAvatar(cursor.getString(cursor.getColumnIndex("backAvatar")));
            user.setBalance(cursor.getString(cursor.getColumnIndex("balance")));
            user.setCreatedTime(cursor.getString(cursor.getColumnIndex("createdTime")));
            user.setCreator(cursor.getString(cursor.getColumnIndex("creator")));
            user.setDistrictId(cursor.getString(cursor.getColumnIndex("districtId")));
            user.setQrCode(cursor.getString(cursor.getColumnIndex("qrCode")));
            user.setUserEmail(cursor.getString(cursor.getColumnIndex("userEmail")));
            user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setLastModifiedTime(cursor.getString(cursor.getColumnIndex("lastModifiedTime")));
            user.setLastModifier(cursor.getString(cursor.getColumnIndex("lastModifier")));
            user.setLocked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("isLocked"))));
            user.setLoginFailedCount(cursor.getInt(cursor.getColumnIndex("loginFailedCount")));
            user.setPayPassword(cursor.getString(cursor.getColumnIndex("payPassword")));
        }
        cursor.close();
        return user;
    }

    /**
     * 获取用户数据
     * @return List<UserInfo>
     */
    public List<UserInfo> GetUserList() {
        List<UserInfo> userList = new ArrayList<UserInfo>();
        Cursor cursor = db.query(SqliteHelper.TB_USER, null, null , null, null,
                null, UserInfo. ID + " DESC");
        cursor.moveToFirst();

        while (!cursor.isAfterLast() && (cursor.getString(1) != null )) {
            UserInfo user = new UserInfo();
            user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            user.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
            user.setAvatar(cursor.getString(cursor.getColumnIndex("avatar")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setThermalSignatrue(cursor.getString(cursor.getColumnIndex("thermalSignatrue")));
            user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));
            user.setAccountNumber(cursor.getString(cursor.getColumnIndex("accountNumber")));
            user.setBackAvatar(cursor.getString(cursor.getColumnIndex("backAvatar")));
            user.setBalance(cursor.getString(cursor.getColumnIndex("balance")));
            user.setCreatedTime(cursor.getString(cursor.getColumnIndex("createdTime")));
            user.setCreator(cursor.getString(cursor.getColumnIndex("creator")));
            user.setDistrictId(cursor.getString(cursor.getColumnIndex("districtId")));
            user.setQrCode(cursor.getString(cursor.getColumnIndex("qrCode")));
            user.setUserEmail(cursor.getString(cursor.getColumnIndex("userEmail")));
            user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setLastModifiedTime(cursor.getString(cursor.getColumnIndex("lastModifiedTime")));
            user.setLastModifier(cursor.getString(cursor.getColumnIndex("lastModifier")));
            user.setLocked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("isLocked"))));
            user.setLoginFailedCount(cursor.getInt(cursor.getColumnIndex("loginFailedCount")));
            user.setPayPassword(cursor.getString(cursor.getColumnIndex("payPassword")));
            userList.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }

    /**
     * 判断User表中的是否包含某个UserID的记录
     * @param UserId
     * @return
     */
    public Boolean HaveUserInfo(String UserId) {
        Boolean b = false;
        Cursor cursor = db.query(SqliteHelper. TB_USER, null, UserInfo.USERID + "=?", new String[]{UserId}, null, null, null );
        b = cursor.moveToFirst();
        Log. e("HaveUserInfo", b.toString());
        cursor.close();
        return b;
    }

    // 更新users表的记录，根据UserId更新用户昵称和用户图标
    public int UpdateUserInfo(String userName, Bitmap userIcon, String UserId) {
        ContentValues values = new ContentValues();
        values.put(UserInfo. USERNAME, userName);
        // BLOB类型
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 将Bitmap压缩成PNG编码，质量为100%存储
        userIcon.compress(Bitmap.CompressFormat.PNG, 100, os);
        // 构造SQLite的Content对象，这里也可以使用raw
        values.put(UserInfo.AVATAR, os.toByteArray());
        int id = db.update(SqliteHelper. TB_USER, values, UserInfo.USERID + "=?" , new String[]{UserId});
        Log. e("UpdateUserInfo2", id + "");
        return id;
    }

    /**
     * 更新User表的记录
     * @param user 修改对象
     * @return
     */
    public int UpdateUserInfo(UserInfo user) {
        ContentValues values = new ContentValues();
        values.put(UserInfo.USERID, user.getUserId());
        values.put(UserInfo.AVATAR, user.getAvatar());
        values.put(UserInfo.ACCOUNTNUMBER, user.getAccountNumber());
        values.put(UserInfo.BACKAVATAR, user.getBackAvatar());
        values.put(UserInfo.BALANCE, user.getBalance());
        values.put(UserInfo.CREATEDTIME, user.getCreatedTime());
        values.put(UserInfo.CREATOR, user.getCreator());
        values.put(UserInfo.DISTRICTID, user.getDistrictId());
        values.put(UserInfo.ISLOCKED, user.isLocked());
        values.put(UserInfo.LASTMODIFIEDTIME, user.getLastModifiedTime());
        values.put(UserInfo.LASTMODIFIER, user.getLastModifier());
        values.put(UserInfo.LOGINFAILEDCOUNT, user.getLoginFailedCount());
        values.put(UserInfo.USEREMAIL, user.getUserEmail());
        values.put(UserInfo.USERNAME, user.getUsername());
        values.put(UserInfo.QRCODE, user.getQrCode());
        values.put(UserInfo.PHONENUMBER, user.getPhoneNumber());
        values.put(UserInfo.SEX, user.getSex());
        values.put(UserInfo.THERMALSIGNATRUE, user.getThermalSignatrue());
        values.put(UserInfo.PASSWORD, user.getPassword());
        values.put(UserInfo.PAYPASSWORD, user.getPayPassword());
        int id = db.update(SqliteHelper.TB_USER, values, UserInfo.USERID + "=" + user.getUserId(), null);
        Log. e("UpdateUserInfo", id + "");
        return id;
    }

    /**
     * 添加users表的记录
     * @param user
     * @return
     */
    public int SaveUserInfo(UserInfo user) {
        ContentValues values = new ContentValues();
        values.put(UserInfo.USERID, user.getUserId());
        values.put(UserInfo.AVATAR, user.getAvatar());
        values.put(UserInfo.ACCOUNTNUMBER, user.getAccountNumber());
        values.put(UserInfo.BACKAVATAR, user.getBackAvatar());
        values.put(UserInfo.BALANCE, user.getBalance());
        values.put(UserInfo.CREATEDTIME, user.getCreatedTime());
        values.put(UserInfo.CREATOR, user.getCreator());
        values.put(UserInfo.DISTRICTID, user.getDistrictId());
        values.put(UserInfo.ISLOCKED, user.isLocked());
        values.put(UserInfo.LASTMODIFIEDTIME, user.getLastModifiedTime());
        values.put(UserInfo.LASTMODIFIER, user.getLastModifier());
        values.put(UserInfo.LOGINFAILEDCOUNT, user.getLoginFailedCount());
        values.put(UserInfo.USEREMAIL, user.getUserEmail());
        values.put(UserInfo.USERNAME, user.getUsername());
        values.put(UserInfo.QRCODE, user.getQrCode());
        values.put(UserInfo.PHONENUMBER, user.getPhoneNumber());
        values.put(UserInfo.SEX, user.getSex());
        values.put(UserInfo.THERMALSIGNATRUE, user.getThermalSignatrue());
        values.put(UserInfo.PASSWORD, user.getPassword());
        values.put(UserInfo.PAYPASSWORD, user.getPayPassword());
        Long uid = db.insert(SqliteHelper.TB_USER, UserInfo.USERID, values);
        Log. e("SaveUserInfo", uid + "  " + user.getUserId());
        return user.getUserId();
    }

    /**
     * 删除User表的记录
     */
    public int DelUserInfo(String UserId) {
        int id = db.delete(SqliteHelper. TB_USER, UserInfo. USERID + "=?", new String[]{UserId});
        Log. e("DelUserInfo", id + "");
        return id;
    }

    /**
     * 查找User 集合中某一位用户
     * @param userID 查找对象的用户ID
     * @param userList User集合
     * @return
     */
    public static UserInfo getUserById(String userID,List<UserInfo> userList){
        UserInfo userInfo = null;
        int size = userList.size();
        for( int i=0 ; i < size ; i++ ){
            if(userID.equals(userList.get(i).getUserId())){
                userInfo = userList.get(i);
                break;
            }
        }
        return userInfo;
    }
    /*******************************************************************  以上是用户表操作方法 **************************************************************************************/


    /******************************************************************   分割线   *************************************************************************************************/
    /**
     * 添加一条数据
     * @param Message
     * @return
     */
    public MessageEntity SaveMessage(MessageEntity Message) {
        ContentValues values = new ContentValues();
        values.put(MessageEntity.MSGID, Message.getMsgId());
        values.put(MessageEntity.TOID, Message.getToId());
        values.put(MessageEntity.NAME, Message.getName());
        values.put(MessageEntity.AVATAR, "");
        values.put(MessageEntity.CREATEDTIME, Message.getCreatedTime());
        values.put(MessageEntity.MESSAGE, Message.getMessage());
        values.put(MessageEntity.ISSEND, Message.getIsSend());
        values.put(MessageEntity.ISREND, Message.getIsRend());
        values.put(MessageEntity.ISTOP, false);
        values.put(MessageEntity.TYPE, Message.getType());
        values.put(MessageEntity.DIRECTION, Message.getDirection());
        values.put(MessageEntity.MYUSERID, MyApplication.getInstance().UserInfo.getUserId());
        Long id = db.insert(SqliteHelper.TB_MESSAGE, MessageEntity.ID, values);
        Log.e("SaveMessage", id + "  " + Message.getMsgId());
        return Message;
    }

    /**
     * 删除Message表的记录
     */
    public int DelMessage(String msgId) {
        int id = db.delete(SqliteHelper.TB_MESSAGE, MessageEntity.MSGID + "=?", new String[]{msgId});
        Log. e("DelMessage", id + "");
        return id;
    }

    /**
     * 更新User表的记录
     * @param Message 修改对象
     * @return
     */
    public int UptateMessage(MessageEntity Message) {
        ContentValues values = new ContentValues();
        values.put(MessageEntity.TOID, Message.getToId());
        values.put(MessageEntity.NAME, Message.getName());
        values.put(MessageEntity.AVATAR, Message.getAvatar());
        values.put(MessageEntity.CREATEDTIME, Message.getCreatedTime());
        values.put(MessageEntity.MESSAGE, Message.getMessage());
        values.put(MessageEntity.ISSEND, Message.getIsSend());
        values.put(MessageEntity.ISREND, Message.getIsRend());
        values.put(MessageEntity.ISTOP, Message.getIsTop());
        values.put(MessageEntity.TYPE, Message.getType());
        values.put(MessageEntity.MYUSERID, Message.getMyUserId());
        int id = db.update(SqliteHelper.TB_MESSAGE, values, UserInfo.ID + "=" + Message.get_id(), null);
        Log. e("UpdateUserInfo", id + "");
        return id;
    }

    /**
     * 获取某个人聊天消息
     * @return List<MessageEntity>
     */
    public List<MessageEntity> GetUserMessageList(String toId) {
        List<MessageEntity> MessageList = new ArrayList<MessageEntity>();
        Cursor cursor = db.query(SqliteHelper.TB_MESSAGE, null, MessageEntity.TOID + "=?" , new String[]{toId}, null, null, UserInfo.ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null )) {
            MessageEntity Message = new MessageEntity();
            Message.set_id(cursor.getInt(cursor.getColumnIndex(MessageEntity.ID)));
            Message.setMsgId(cursor.getString(cursor.getColumnIndex(MessageEntity.MSGID)));
            Message.setToId(cursor.getString(cursor.getColumnIndex(MessageEntity.TOID)));
            Message.setName(cursor.getString(cursor.getColumnIndex(MessageEntity.NAME)));
            Message.setAvatar(cursor.getString(cursor.getColumnIndex(MessageEntity.AVATAR)));
            Message.setCreatedTime(cursor.getString(cursor.getColumnIndex(MessageEntity.CREATEDTIME)));
            Message.setIsRend(cursor.getInt(cursor.getColumnIndex(MessageEntity.ISREND)));
            Message.setIsSend(cursor.getString(cursor.getColumnIndex(MessageEntity.ISSEND)));
            Message.setIsTop(cursor.getInt(cursor.getColumnIndex(MessageEntity.ISTOP)));
            Message.setMessage(cursor.getString(cursor.getColumnIndex(MessageEntity.MESSAGE)));
            Message.setType(cursor.getString(cursor.getColumnIndex(MessageEntity.TYPE)));
            Message.setDirection(cursor.getString(cursor.getColumnIndex(MessageEntity.DIRECTION)));
            Message.setMyUserId(cursor.getString(cursor.getColumnIndex(MessageEntity.MYUSERID)));
            MessageList.add(Message);
            cursor.moveToNext();
        }
        cursor.close();
        return MessageList;
    }

    /**
     * 获取聊天消息列表
     * @return List<MessageEntity>
     */
    public List<MessageEntity> GetMessageList(String toId) {
        List<MessageEntity> MessageList = new ArrayList<MessageEntity>();
        Cursor cursor = db.query(SqliteHelper.TB_MESSAGE, null, MessageEntity.TOID + "=?" , new String[]{toId}, null, null, UserInfo.ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null )) {
            MessageEntity Message = new MessageEntity();
            Message.set_id(cursor.getInt(cursor.getColumnIndex(MessageEntity.ID)));
            Message.setMsgId(cursor.getString(cursor.getColumnIndex(MessageEntity.MSGID)));
            Message.setToId(cursor.getString(cursor.getColumnIndex(MessageEntity.TOID)));
            Message.setName(cursor.getString(cursor.getColumnIndex(MessageEntity.NAME)));
            Message.setAvatar(cursor.getString(cursor.getColumnIndex(MessageEntity.AVATAR)));
            Message.setCreatedTime(cursor.getString(cursor.getColumnIndex(MessageEntity.CREATEDTIME)));
            Message.setIsRend(cursor.getInt(cursor.getColumnIndex(MessageEntity.ISREND)));
            Message.setIsSend(cursor.getString(cursor.getColumnIndex(MessageEntity.ISSEND)));
            Message.setIsTop(cursor.getInt(cursor.getColumnIndex(MessageEntity.ISTOP)));
            Message.setMessage(cursor.getString(cursor.getColumnIndex(MessageEntity.MESSAGE)));
            Message.setType(cursor.getString(cursor.getColumnIndex(MessageEntity.TYPE)));
            Message.setDirection(cursor.getString(cursor.getColumnIndex(MessageEntity.DIRECTION)));
            Message.setMyUserId(cursor.getString(cursor.getColumnIndex(MessageEntity.MYUSERID)));
            MessageList.add(Message);
            cursor.moveToNext();
        }
        cursor.close();
        return MessageList;
    }

}
