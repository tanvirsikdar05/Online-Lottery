package com.lottery.sikka.LocalDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;


    private static final String LOTTERY_TABLE_NAME = "lottery";
    public static final String LOTTERY_ID = "ID";
    public static final String LOTTERY_NAME_KEY = "lotteryname";
    public static final String LOTTERY_NUMBER_KEY = "lotterynumber";
    public static final String BUY_DATE_KEY = "buyingdate";
    public static final String DRAW_DATE_KEY = "drawdate";
    public static final String STATUS_KEY = "status";


    private static final String CREATE_LOTTERY_TABLE =
            "CREATE TABLE " + LOTTERY_TABLE_NAME + "(" +
                    LOTTERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    LOTTERY_NAME_KEY + " TEXT NOT NULL, " +
                    LOTTERY_NUMBER_KEY + " TEXT NOT NULL, " +
                    BUY_DATE_KEY + " TEXT NOT NULL, " +
                    DRAW_DATE_KEY + " TEXT NOT NULL, " +
                    STATUS_KEY + " TEXT NOT NULL," +
                    "UNIQUE (" + LOTTERY_NUMBER_KEY + ")" + ");";
    private static final String DROP_LOTTERY_TABLE = "DROP TABLE IF EXISTS " + LOTTERY_TABLE_NAME;
    private static final String SELECT_LOTTERY_TABLE = "SELECT * FROM " + LOTTERY_TABLE_NAME;

    //payment request db

    private static final String PAYMENT_TABLE_NAME = "payment";
    public static final String PAYMENT_ID = "ID";
    public static final String PAYMENT_TRX_KEY = "TRX";
    public static final String PAYMENT_DATE_KEY = "DATE";
    public static final String PAYMENT_BANKNAME_KEY = "BANKNAME";
    public static final String PAYMENT_STATUS_KEY = "STATUS";
    public static final String PAYMENT_TYPE_KEY = "TYPE";
    public static final String PAYMENT_PRICE_KEY = "PRICE";


    private static final String CREATE_PAYMENT_TABLE =
            "CREATE TABLE " + PAYMENT_TABLE_NAME + "(" +
                    PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    PAYMENT_TRX_KEY + " TEXT NOT NULL, " +
                    PAYMENT_DATE_KEY + " TEXT NOT NULL, " +
                    PAYMENT_BANKNAME_KEY + " TEXT NOT NULL, " +
                    PAYMENT_STATUS_KEY + " TEXT NOT NULL, " +
                    PAYMENT_TYPE_KEY + " TEXT NOT NULL," +
                    PAYMENT_PRICE_KEY + " TEXT NOT NULL" +");";
    private static final String DROP_PAYMENT_TABLE = "DROP TABLE IF EXISTS " + PAYMENT_TABLE_NAME;
    private static final String SELECT_PAYMENT_TABLE = "SELECT * FROM " + PAYMENT_TABLE_NAME;



    //notification db

    private static final String NOTIFICATION_TABLE_NAME = "NOTIFICATION";
    public static final String NOTIFICATION_ID = "ID";
    public static final String NOTIFICATION_TITLE_KEY = "TITLE";
    public static final String NOTIFICATION_BODY_KEY = "MASSAGE_BODY";
    public static final String NOTIFICATION_DATE_KEY = "DATE";



    private static final String CREATE_NOTIFICATION_TABLE =
            "CREATE TABLE " + NOTIFICATION_TABLE_NAME + "(" +
                    NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    NOTIFICATION_BODY_KEY + " TEXT NOT NULL, " +
                    NOTIFICATION_TITLE_KEY + " TEXT NOT NULL, " +
                    NOTIFICATION_DATE_KEY + " TEXT NOT NULL " +");";
    private static final String DROP_NOTIFICATION_TABLE = "DROP TABLE IF EXISTS " + NOTIFICATION_TABLE_NAME;
    private static final String SELECT_NOTIFICATION_TABLE = "SELECT * FROM " + NOTIFICATION_TABLE_NAME;





    public DBhelper(@Nullable Context context) {
        super(context, "sikka.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(CREATE_LOTTERY_TABLE);
            sqLiteDatabase.execSQL(CREATE_PAYMENT_TABLE);
            sqLiteDatabase.execSQL(CREATE_NOTIFICATION_TABLE);


        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_LOTTERY_TABLE);
        sqLiteDatabase.execSQL(DROP_PAYMENT_TABLE);
        sqLiteDatabase.execSQL(DROP_NOTIFICATION_TABLE);

    }
    public long add_buying_lottery(String name,String number,String buydate,String drawdate,String status){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LOTTERY_NAME_KEY,name);
        values.put(LOTTERY_NUMBER_KEY,number);
        values.put(BUY_DATE_KEY,buydate);
        values.put(DRAW_DATE_KEY,drawdate);
        values.put(STATUS_KEY,status);
        return database.insert(LOTTERY_TABLE_NAME,null,values);
    }
    public int update_lottery(String id,String number){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LOTTERY_NUMBER_KEY,number);
        return database.update(LOTTERY_TABLE_NAME,values,LOTTERY_ID + "=?",new String[]{id});
    }
    public Cursor getLotteryData(){
        SQLiteDatabase database=this.getReadableDatabase();
        return database.rawQuery(SELECT_LOTTERY_TABLE,null);
    }

    public long addPaymentData(String trx,String date,String bankname,String status,String type,String price){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAYMENT_TRX_KEY,trx);
        values.put(PAYMENT_DATE_KEY,date);
        values.put(PAYMENT_BANKNAME_KEY,bankname);
        values.put(PAYMENT_TYPE_KEY,type);
        values.put(PAYMENT_STATUS_KEY,status);
        values.put(PAYMENT_PRICE_KEY,price);
        return database.insert(PAYMENT_TABLE_NAME,null,values);
    }
    public Cursor getPaymentData(){
        SQLiteDatabase database=this.getReadableDatabase();
        return database.rawQuery(SELECT_PAYMENT_TABLE,null);
    }
    public long addnotificationdata(String title,String body,String date){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_TITLE_KEY,title);
        values.put(NOTIFICATION_BODY_KEY,body);
        values.put(NOTIFICATION_DATE_KEY,date);
        return database.insert(NOTIFICATION_TABLE_NAME,null,values);
    }
    public Cursor getnotification(){
        SQLiteDatabase database=this.getReadableDatabase();
        return database.rawQuery(SELECT_NOTIFICATION_TABLE,null);
    }

}
