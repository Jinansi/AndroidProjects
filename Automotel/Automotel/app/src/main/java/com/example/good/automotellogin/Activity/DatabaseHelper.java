package com.example.good.automotellogin.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOOD on 23/03/2016.
 */

public class DatabaseHelper {

    DatabaseAdapter databaseAdapter;
    public DatabaseHelper(Context context){
        databaseAdapter = new DatabaseAdapter(context);
    }
    public long insertOrder(int quantity,int price,String Itemname){
        SQLiteDatabase sqLiteDatabase = databaseAdapter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseAdapter.Item_name,Itemname);
        contentValues.put(DatabaseAdapter.price, price);
        contentValues.put(DatabaseAdapter.quantity, quantity);
        Log.i(getClass().getSimpleName(),"contentValues" + contentValues);
        Long id = sqLiteDatabase.insert(DatabaseAdapter.Order_Table, null, contentValues);
        Log.i(getClass().getSimpleName(),"Data inserted.."+ id);
        sqLiteDatabase.close();
        return id;

    }
    public Cursor getData()
    {
        String selectquery = "SELECT * FROM " + DatabaseAdapter.Order_Table;
        SQLiteDatabase db = databaseAdapter.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectquery, null);
        Log.i(getClass().getSimpleName(),"cursor initialized");
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.UID_Order));
            Log.i(getClass().getSimpleName(),"ID" + id);
            String name = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.Item_name));
            Log.i(getClass().getSimpleName(),"name" + name);
            int price = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.price));
            Log.i(getClass().getSimpleName(),"Location " +price);
            int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.quantity));
            Log.i(getClass().getSimpleName(),"quantity " + quantity);
        }

        return cursor;
    }
//    public List<String> getData(){
//        List<String> orderlist = new ArrayList<>();
//        String selectquery = "SELECT * FROM " + DatabaseAdapter.Order_Table;
//        //  String[] columns = {OrderDatabase.UID,OrderDatabase.Name,OrderDatabase.quantity,OrderDatabase.price};
//        SQLiteDatabase db = databaseAdapter.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectquery, null);
//        //  Cursor cursor = db.query(OrderDatabase.TABLE_NAME,columns,null,null,null,null,null);
//        Log.i(getClass().getSimpleName(), "Cursor initialized..");
//        while(cursor.moveToNext()){
//            int id = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.UID_Order));
//            Log.i(getClass().getSimpleName(),"ID" + id);
//            String name = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.Item_name));
//            Log.i(getClass().getSimpleName(),"name" + name);
//            int price = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.price));
//            Log.i(getClass().getSimpleName(),"Location " +price);
//            int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.quantity));
//            Log.i(getClass().getSimpleName(),"quantity " + quantity);
//            orderlist.add(name);
//            orderlist.add(String.valueOf(price));
//            orderlist.add(String.valueOf(quantity));
//        }
//        return orderlist;
//    }

    public  void insertData(String name,String Location){
        SQLiteDatabase sqLiteDatabase = databaseAdapter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
      //  contentValues.put(DatabaseAdapter.UID_Favorite,id);
        contentValues.put(DatabaseAdapter.Res_Name,name);
        contentValues.put(DatabaseAdapter.Location,Location);
        Long id= sqLiteDatabase.insert(DatabaseAdapter.Favorites_Table, null, contentValues);
        Log.i(getClass().getSimpleName(),"data inserted" +id);

    }
    public Cursor getAllFav()
    {
        String selectquery = "SELECT * FROM " + DatabaseAdapter.Favorites_Table;
        SQLiteDatabase db = databaseAdapter.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectquery, null);
        Log.i(getClass().getSimpleName(),"cursor initialized");
        while(cursor.moveToNext()) {
            //      int index1 = cursor.getColumnIndex(FavoriteDatabase.UID);
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.UID_Favorite));
            Log.i(getClass().getSimpleName(), "ID" + id);
            String name = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.Res_Name));
            Log.i(getClass().getSimpleName(), "name" + name);
            String location = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.Location));
            Log.i(getClass().getSimpleName(), "Location" + location);
        }
        return  cursor;
    }

//    public List<String> getAllData(){
//        List<String> AddReslist = new ArrayList<>();
//        String[] columns ={DatabaseAdapter.UID_Favorite,DatabaseAdapter.Res_Name,DatabaseAdapter.Location};
//        SQLiteDatabase db = databaseAdapter.getReadableDatabase();
//        Cursor cursor = db.query(DatabaseAdapter.Favorites_Table, columns, null, null, null, null, null);
//        Log.i(getClass().getSimpleName(), "Cursor initialized.." + cursor);
//        //    StringBuffer stringBuffer = new StringBuffer();
//        while(cursor.moveToNext()){
//            //      int index1 = cursor.getColumnIndex(FavoriteDatabase.UID);
//            int id = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.UID_Favorite));
//            Log.i(getClass().getSimpleName(),"ID" + id);
//            String name = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.Res_Name));
//            Log.i(getClass().getSimpleName(),"name" + name);
//            String location = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.Location));
//            Log.i(getClass().getSimpleName(),"Location" +location);
////            int cid = cursor.getInt(0);
////            String name = cursor.getString(1);
////            String location = cursor.getString(2);
//            //     stringBuffer.append("cid:"+cid+ " Name :" + name + "Location" + location);
//            AddReslist.add(name);
//            AddReslist.add(location);
//
//        }
//        return AddReslist;
//    }

    class DatabaseAdapter extends  SQLiteOpenHelper{
        private static final String DATABASE_NAME = "AutomotelOrder";
        private static final String Favorites_Table = "Favorite";
        private static final String Order_Table = "'Order'";
        private static final int version = 1;
        private static final String UID_Order = "_id";
        private static final String UID_Favorite = "_id";
        private static final String Item_name = "Item_name";
        private static final String price = "price";
        private static final String quantity = "quantity";

        private static final String Res_Name = "ResName";
        private static final String Location = "ResLocation";
        private static final String FAV_TABLE = "CREATE TABLE " + Favorites_Table + "(" + UID_Favorite + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Res_Name + " TEXT," + Location + " TEXT)";
        //  private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
        private static final String ORDER_TABLE = "CREATE TABLE " + Order_Table + "(" + UID_Order + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Item_name + " TEXT," + price + " INTEGER," + quantity + " INTEGER)";

        public DatabaseAdapter(Context context) {
            super(context, DATABASE_NAME, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Log.i(getClass().getSimpleName(), "Inside onCreate");
                db.execSQL(FAV_TABLE);
                db.execSQL(ORDER_TABLE);

                Log.i(getClass().getSimpleName(), "onCreate Called..");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Log.i(getClass().getSimpleName(), "Inside onUpgrade");
                db.execSQL("DROP TABLE IF EXISTS " + Order_Table);
                db.execSQL("DROP TABLE IF EXISTS " + Favorites_Table);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
