package com.example.foodscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";
    private static final String TABLE_HISTORY = "Products";
    private static final String TABLE_FAV = "Favorites";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static String IMAGE_URL = "IMAGE_URL";
    private static String SUGAR = "SUGAR";
    private static String CARBS = "CARBS";
    private static String FAT = "FAT";
    private static String SALT = "SALT";
    private static String ENERGY = "ENERGY";
    private static String SODIUM = "SODIUM";
    private static String FATL = "FATL";
    private static String SALTL = "SALTL";
    private static String SUGARL = "SUGARL";

    public Database(@Nullable Context context) {
        super(context, TABLE_HISTORY, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableHistory = "CREATE TABLE " + TABLE_HISTORY + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                IMAGE_URL + " TEXT, "+
                SUGAR + " DOUBLE, "+
                CARBS + " DOUBLE, "+
                FAT + " DOUBLE, "+
                SALT + " DOUBLE, "+
                ENERGY + " DOUBLE, "+
                SODIUM + " DOUBLE)";
        db.execSQL(createTableHistory);

        String createTableFav = "CREATE TABLE " + TABLE_FAV + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                IMAGE_URL + " TEXT, "+
                SUGAR + " DOUBLE, "+
                CARBS + " DOUBLE, "+
                FAT + " DOUBLE, "+
                SALT + " DOUBLE, "+
                ENERGY + " DOUBLE, "+
                SODIUM + " DOUBLE)";
        db.execSQL(createTableFav);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query  = "DROP TABLE IF  EXISTS " + TABLE_HISTORY;
        db.execSQL(query);
        query  = "DROP TABLE IF  EXISTS " + TABLE_FAV;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean addProductHistory(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, product.getName());
        contentValues.put(IMAGE_URL, product.getImageUrl());
        contentValues.put(SUGAR, product.getSugar());
        contentValues.put(CARBS, product.getCarbs());
        contentValues.put(FAT, product.getFat());
        contentValues.put(SALT, product.getSalt());
        contentValues.put(ENERGY, product.getEnergy());
        contentValues.put(SODIUM, product.getSodium());
        contentValues.put(FATL, product.getFatL());
        contentValues.put(SALTL, product.getSaltL());
        contentValues.put(SUGARL, product.getSugarsL());

        Log.d(TAG, "addProduct: Adding " + product.getImageUrl() + " to " + TABLE_HISTORY);
        long result = db.insert(TABLE_HISTORY, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean addProductFav(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, product.getName());
        contentValues.put(IMAGE_URL, product.getImageUrl());
        contentValues.put(SUGAR, product.getSugar());
        contentValues.put(CARBS, product.getCarbs());
        contentValues.put(FAT, product.getFat());
        contentValues.put(SALT, product.getSalt());
        contentValues.put(ENERGY, product.getEnergy());
        contentValues.put(SODIUM, product.getSodium());
        contentValues.put(FATL, product.getFatL());
        contentValues.put(SALTL, product.getSaltL());
        contentValues.put(SUGARL, product.getSugarsL());

        Log.d(TAG, "addProduct: Adding " + product.getImageUrl() + " to " + TABLE_FAV);
        long result = db.insert(TABLE_FAV, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getProductsHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_HISTORY, null);
        return data;
    }

    public Cursor getProductsFav() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_FAV, null);
        return data;
    }
}