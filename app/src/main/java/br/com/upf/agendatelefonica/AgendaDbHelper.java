package br.com.upf.agendatelefonica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AgendaDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "agenda";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String ID = "_id";
    public static final String DATABASE_NAME = "Agenda.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ("+
             ID + " INTEGER PRIMARY KEY,"+
             COLUMN_NOME + " TEXT,"+
             COLUMN_TELEFONE + " TEXT)";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public AgendaDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void  onDowngrade(SQLiteDatabase db,int oldVersion, int newVersion ){
        onUpgrade(db, oldVersion,newVersion);
    }
}
