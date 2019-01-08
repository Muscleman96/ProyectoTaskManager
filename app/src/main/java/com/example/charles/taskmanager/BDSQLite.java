package com.example.charles.taskmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BDSQLite extends SQLiteOpenHelper {


    final String sql = "create table eventos(" + // Private
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombreEvento varchar(40)," +
            "ubicacion varchar(60)," +
            "fechadesde date," +
            "horadesde time,"+
            "fechahasta date,"+
            "horahasta time,"+
            "descripcion varchar(60))";

    public BDSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
