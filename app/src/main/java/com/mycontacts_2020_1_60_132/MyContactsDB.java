package com.mycontacts_2020_1_60_132;
import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class MyContactsDB extends SQLiteOpenHelper {

    public MyContactsDB(Context context) {

        super(context, "MyContactsDB.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DB@OnCreate");
        String sql = "CREATE TABLE MyContacts ("
                + "id TEXT PRIMARY KEY,"
                + "name TEXT,"
                + "email TEXT,"
                + "phonehome TEXT,"
                + "phoneoffice TEXT,"
                + "photo TEXT"
                + ")";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Write code to modify database schema here");
    }
    public void insertContact(String id, String name,String Email, String Phonehome, String Phoneoffice, String Photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cols = new ContentValues();
        cols.put("id", id);
        cols.put("name",name);
        cols.put("email",Email);
        cols.put("phonehome",Phonehome);
        cols.put("phoneoffice",Phoneoffice);
        cols.put("photo",Photo);
        db.insert("MyContacts", null ,  cols);
        db.close();
    }
    public void updateContact(String id, String name,String Email, String Phonehome, String Phoneoffice){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cols = new ContentValues();
        cols.put("name",name);
        cols.put("email",Email);
        cols.put("phonehome",Phonehome);
        cols.put("phoneoffice",Phoneoffice);
        db.update("MyContacts", cols, "id=?", new String[ ] {id} );
        db.close();
    }
    public void deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("MyContacts", "id=?", new String[ ] {id} );
        db.close();
    }
    public Cursor displayContact(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=null;
        try {
            res = db.rawQuery(query, null);
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
