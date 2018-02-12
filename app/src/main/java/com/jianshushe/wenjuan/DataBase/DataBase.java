package com.jianshushe.wenjuan.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.DataBase
 * 文件名：DataBase
 * 创建者：jiushaba
 * 创建时间：2018/1/6 0006上午 1:42
 * 描述： TODO
 */
public class DataBase extends SQLiteOpenHelper{
    public DataBase(Context context) {
        super(context, "wenjuan", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table xinxi (id text ,name text DEFAULT NULL,sex integer DEFAULT NULL,age integer DEFAULT NULL,professional text DEFAULT NULL,address text DEFAULT NULL,x double,y double ,submit_address text)");
        sqLiteDatabase.execSQL("create table form (id text primary key , surveyId integer,startdate text,enddate text,spotName text )");
        sqLiteDatabase.execSQL("create table answers( questionId integer ,answer text ,formId text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
