package com.example.onandroidnoimage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DpHelper extends SQLiteOpenHelper {
    private static final String DBName = "mydb.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "VeTau";
    private static final String ID = "_id";
    private static final String GADI = "gadi";
    private static final String GADEN = "gaden";
    private static final String DONGIA = "dongia";
    private static final String CHIEU = "chieu";
    private  SQLiteDatabase myDB;
    public DpHelper(@Nullable Context context)
    {
        super(context, DBName, null, VERSION);
    }
    public DpHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    public static String getID() {
        return ID;
    }

    public static String getGADI() {
        return GADI;
    }

    public static String getGADEN() {
        return GADEN;
    }

    public static String getDONGIA() {
        return DONGIA;
    }

    public static String getCHIEU() {
        return CHIEU;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable = "CREATE TABLE " + TABLE_NAME +
                "( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GADI + " TEXT NOT NULL, " +
                GADEN + " TEXT NOT NULL, " +
                DONGIA + " LONG NOT NULL, " +
                CHIEU + " INTEGER NOT NULL " +
                ")";
        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public  void openDB(){

        myDB = getWritableDatabase();
    }
    public  void closeDB(){
        if(myDB!=null && myDB.isOpen()){
            myDB.close();
        }
    }

    //CAU LENH SQL
    public void Insert(Ticket t){
        ContentValues values = new ContentValues();
        values.put(GADI,t.getGadi());
        values.put(GADEN,t.getGaden());
        values.put(DONGIA,t.getDongia());
        values.put(CHIEU,t.getChieu());
        myDB.insert(TABLE_NAME, null, values);
    }
    public long Update(int id, String gadi, String gaden, long dongia, int chieu){
        ContentValues values = new ContentValues();
        values.put(GADI,gadi);
        values.put(GADEN,gaden);
        values.put(DONGIA,dongia);
        values.put(CHIEU,chieu);
        String where = ID + " = " + id;
        return myDB.update(TABLE_NAME, values, where, null);
    }
    public long Delete(int id){
        String where = ID + " = " + id;
        return myDB.delete(TABLE_NAME,where,null);
    }
    public Cursor getAllRecord(){
        String query = "SELECT * FROM " + TABLE_NAME;
        return myDB.rawQuery(query,null);
    }
    public void runQuery(String query){
        myDB.execSQL(query);
    }

    public Cursor getRecordbyID(String gaden){
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + GADEN +" LIKE '" +gaden+"%'";
        return myDB.rawQuery(query,null);
    }
}
