package com.mmm.mmmtest.data_access.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mmm.mmmtest.data_access.DbHelperFactory;
import com.mmm.mmmtest.data_access.DbHelperFactory.DbHelper;
import com.mmm.mmmtest.data_access.entity.Product;
import static com.mmm.mmmtest.data_access.DbHelperFactory.DbHelper.ProductContract;


public class ProductDao implements Dao<Product>{

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ProductDao(Context context){
        this.dbHelper = DbHelperFactory.getDbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    @Override
    public boolean insert(Product toInsert) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.ProductContract.COLUMN_NAME, toInsert.getName());
        values.put(DbHelper.ProductContract.COLUMN_PRICE, toInsert.getPrice());
        values.put(DbHelper.ProductContract.COLUMN_IMAGE_PATH, toInsert.getImagePath());
        long newRowId = db.insert(DbHelper.ProductContract.TABLE_NAME, null, values);
        return newRowId > 0;
    }

    @Override
    public boolean update(Product toUpdate) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean delete(Object id) {
        String whereClause = DbHelper.ProductContract._ID + " = ?";
        int deletedRows = db.delete(DbHelper.ProductContract.TABLE_NAME, whereClause, new String[]{ id.toString() });
        return deletedRows == 1;
    }

    @Override
    public Product find(Object id) {
        Product pro = null;
        String whereClause = DbHelper.ProductContract._ID + " = ?";
        String[] columns = {
                DbHelper.ProductContract._ID,
                DbHelper.ProductContract.COLUMN_NAME,
                DbHelper.ProductContract.COLUMN_PRICE,
                DbHelper.ProductContract.COLUMN_IMAGE_PATH
        };

        Cursor cursor = null;
        try {
            cursor = db.query(DbHelper.ProductContract.TABLE_NAME, columns, whereClause, new String[]{id.toString()}, null, null, null);
            if (cursor.moveToNext()) {
                pro = new Product();
                pro.setId((int) id);
                pro.setName(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.ProductContract.COLUMN_NAME)));
                pro.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.ProductContract.COLUMN_PRICE)));
                pro.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.ProductContract.COLUMN_IMAGE_PATH)));
            }
        } finally{
            if(cursor != null)
                cursor.close();
        }

        return pro;
    }

    @Override
    public List<Product> findAll() {
        List<Product> lPro = new ArrayList<>();
        String[] columns = {
                ProductContract._ID,
                ProductContract.COLUMN_NAME,
                ProductContract.COLUMN_PRICE,
                ProductContract.COLUMN_IMAGE_PATH
        };
        Cursor cursor = null;
        try{
            cursor = db.query(ProductContract.TABLE_NAME, columns, null, null, null, null, null);

            final int ID_INDEX = cursor.getColumnIndexOrThrow(ProductContract._ID);
            final int NAME_INDEX = cursor.getColumnIndexOrThrow(ProductContract.COLUMN_NAME);
            final int PRICE_INDEX = cursor.getColumnIndexOrThrow(ProductContract.COLUMN_PRICE);
            final int IMAGE_PATH_INDEX = cursor.getColumnIndexOrThrow(ProductContract.COLUMN_IMAGE_PATH);

            Product pro = null;
            while(cursor.moveToNext()){
                pro = new Product();
                pro.setId(cursor.getInt(ID_INDEX));
                pro.setName(cursor.getString(NAME_INDEX));
                pro.setPrice(cursor.getDouble(PRICE_INDEX));
                pro.setImagePath(cursor.getString(IMAGE_PATH_INDEX));
                lPro.add(pro);
            }
        } finally{
            if(cursor != null)
                cursor.close();
        }

        return lPro;
    }

    @Override
    public boolean close(){
        db.close();
        dbHelper.close();
        return true;
    }

    @Override
    public DbHelper getDbHelper() {
        return this.dbHelper;
    }
}