package com.mmm.mmmtest.data_access;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;

public final class DbHelperFactory {

    private static DbHelper dbHelper;

    public static DbHelper getDbHelper(Context context) {
        if(dbHelper != null)
            return dbHelper;
        return new DbHelper(context.getApplicationContext());
    }

    private DbHelperFactory() {
    }

    public static class DbHelper extends SQLiteOpenHelper {

        public static final int VERSION = 1;
        public static final String DATABASE_NAME = "Test.db";


        public static final class UserContract implements BaseColumns{
            public static final String TABLE_NAME = "User";
            public static final String COLUMN_USERNAME = "Username";
            public static final String COLUMN_PASSWORD = "Password";
        }

        public static final class ProductContract implements BaseColumns{
            public static final String TABLE_NAME = "Product";
            public static final String COLUMN_NAME = "Name";
            public static final String COLUMN_PRICE = "Price";
            public static final String COLUMN_IMAGE_PATH = "ImagePath";
        }


        private static final String CREATE_SQL =
                String.format(
                        "CREATE TABLE %s(" +
                                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s VARCHAR(200), " +
                                "%s VARCHAR(200)" +
                                "); " +
                        "CREATE TABLE %s(" +
                                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s VARCHAR(200), " +
                                "%s DECIMAL(12,2), " +
                                "%s TEXT" +
                                ");",
                        UserContract.TABLE_NAME, UserContract._ID, UserContract.COLUMN_USERNAME, UserContract.COLUMN_PASSWORD,
                        ProductContract.TABLE_NAME, ProductContract._ID, ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH);

        private static final String DROP_SQL =
                String.format("DROP TABLE IF EXISTS %s;" +
                              "DROP TABLE IF EXISTS %s;", UserContract.TABLE_NAME, ProductContract.TABLE_NAME);

        private DbHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        private String[] getDefaultData(){

            return new String[] {
                    String.format("INSERT INTO User(%s, %s) VALUES ('Johny', 'Cage');", UserContract.COLUMN_USERNAME, UserContract.COLUMN_PASSWORD),
                    String.format("INSERT INTO Product (%s, %s, %s) VALUES('AirPods', 74.4, 'https://cdn.shopify.com/s/files/1/0892/6470/products/airpodsfront1.png');",
                            ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH),
                    String.format("INSERT INTO Product (%s, %s, %s) VALUES('Google Glasses', 192.4, 'https://hypb.imgix.net/image/2017/07/google-glass-enterprise-edition-1.jpg');",
                            ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH),
                    String.format("INSERT INTO Product (%s, %s, %s) VALUES('Pods Headphones', 121.98, 'https://product.hstatic.net/1000129940/product/3_c14c28e14e824e8f8b61bf4d9b9ddd6b_1024x1024.jpg');",
                            ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH),
                    String.format("INSERT INTO Product (%s, %s, %s) VALUES('Fitbit Blaze', 843.2, 'https://www.focuscamera.com/media/product/561/fitbit-blaze-smart-fitness-watch-large-black-fb502sbkl-d57.jpg');",
                            ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH),
                    String.format("INSERT INTO Product (%s, %s, %s) VALUES('Nike Apple Watch', 233.48, 'https://i2.linio.com/p/e130762defd2396597bc75de4763546a-product.jpg');",
                            ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH),
                    String.format("INSERT INTO Product (%s, %s, %s) VALUES('Hourglass', 763.11, 'https://images-na.ssl-images-amazon.com/images/I/41XqUMSMawL._SL500_AC_SS350_.jpg');",
                            ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH),
                    String.format("INSERT INTO Product (%s, %s, %s) VALUES('Water Gun', 87.2, 'https://images-na.ssl-images-amazon.com/images/I/41wyWkCxvWL.jpg');",
                            ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH),
                    String.format("INSERT INTO Product (%s, %s, %s) VALUES('Pocket Watch', 90.3, 'https://cdn.shopify.com/s/files/1/1255/7129/products/avignon-black-front_1024x1024.png');",
                            ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH),
                    String.format("INSERT INTO Product (%s, %s, %s) VALUES('Cosmonaut Helmet', 777.33, 'https://i.pinimg.com/736x/6d/26/67/6d2667885e651a61ef5a6010e3ce7d07--astronaut-helmet-space-shuttles.jpg')",
                            ProductContract.COLUMN_NAME, ProductContract.COLUMN_PRICE, ProductContract.COLUMN_IMAGE_PATH)
            };

            //return inserts;//TextUtils.join("", inserts);
        }

        //WHEN onCreate IS CALLED A DB.JOURNAL FILE IS CREATED TOO, IT SEEMS THAT ERROR 14 WILL BE THROWN IF THIS FILE DOES NOT EXIST
        //ONCREAT IS ONLY EXECUTED ONCE IF THE DATABASE DOESN'T EXIST
        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL(CREATE_SQL);
            for(String sql : CREATE_SQL.split(";"))
                db.execSQL(sql);
            for(String sql : getDefaultData())
                db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            for(String sql : CREATE_SQL.split(";"))
                db.execSQL(sql);
        }

        public void recreate(SQLiteDatabase db){
            for(String sql : DROP_SQL.split(";"))
                db.execSQL(sql);
            for(String sql : CREATE_SQL.split(";"))
                db.execSQL(sql);
            for(String sql : getDefaultData())
                db.execSQL(sql);
        }
    }

}
