package com.mmm.mmmtest.data_access.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mmm.mmmtest.data_access.DbHelperFactory;
import com.mmm.mmmtest.data_access.DbHelperFactory.DbHelper;
import com.mmm.mmmtest.data_access.DbHelperFactory.DbHelper.UserContract;
import com.mmm.mmmtest.data_access.entity.User;

import java.util.List;

public class UserDao implements Dao<User> {

    DbHelper dbHelper;
    SQLiteDatabase db;

    public UserDao(Context context){
        dbHelper = DbHelperFactory.getDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public User login(String username, String password){
        User user = null;
        String whereClause = UserContract.COLUMN_USERNAME + " = ? AND " + UserContract.COLUMN_PASSWORD + " = ?";
        String[] columns = {
                UserContract._ID,
                UserContract.COLUMN_USERNAME,
                UserContract.COLUMN_PASSWORD
        };

        Cursor cursor = null;

        try{
            cursor = db.query(UserContract.TABLE_NAME, columns, whereClause, new String[] { username, password }, null, null, null);
            if(cursor.moveToNext()){
                user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(UserContract._ID)));
                user.setUsername(username);
                user.setPassword(password);
            }
        } finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return user;
    }

    @Override
    public boolean insert(User toInsert) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean update(User toUpdate) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(Object id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public User find(Object id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean close() {
        db.close();
        dbHelper.close();
        return true;
    }

    @Override
    public DbHelper getDbHelper() {
        return this.dbHelper;
    }
    public SQLiteDatabase getDb() { return this.db; }
}
