package com.seek.spin.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by xiayangyang on 15/8/20.
 */
public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // 新增数据
    public void insert(String sql, Object[] object) {
        db.beginTransaction();//事务开始
        try {
            db.execSQL(sql, object);
            db.setTransactionSuccessful();// 事务成功
        } catch (Exception e) {
            Log.d("db", "db execute fail..");
        } finally {
            db.endTransaction();// 结束事务
        }
    }

    //删除数据
    public void delData(String name) {
        // ExecSQL("DELETE FROM info WHERE name ="+"'"+name+"'");
        String[] args = {name};
        db.delete(DBHelper.DB_TABLE_NAME, "name=?", args);
    }

    //修改数据
    public void updateData(String raw, String rawValue, String whereName) {
        String sql = "UPDATE info SET " + raw + " =" + " " + "'" + rawValue + "'" + " WHERE name =" + "'" + whereName
                + "'";
        ExecSQL(sql);
    }

    /**
     * 清空数据
     */
    public void clearData() {
        ExecSQL("DELETE FROM info");
    }

    private void ExecSQL(String sql) {
        try {
            db.execSQL(sql);
            Log.i("execSql: ", sql);
        } catch (Exception e) {
            Log.e("ExecSQL Exception", e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<String> searchData(final String name) {
        String sql = "SELECT * FROM info WHERE name =" + "'" + name + "'";
        return ExecSQLForMemberInfo(sql);
    }

    // 查询数据
    private ArrayList<String> ExecSQLForMemberInfo(String sql) {
        ArrayList<String> list = new ArrayList<String>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {

            list.add(String.valueOf("ss"));
        }
        c.close();
        return list;
    }

    // 执行sql返回游标
    private Cursor ExecSQLForCursor(String sql) {
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void closeDB() {
        db.close();
    }
}
