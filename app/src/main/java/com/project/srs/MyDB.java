package com.project.srs;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {
    private Context c;
    private static final int VERSION = 1;
    private static final String DB_NAME = "MyDB";
    public static final String Student_Details = "Student_Details";

    public static final String StdRegNum = "RegisterNumber";

    public static final String teacherPassword = "teacherPassword";
    public static final String studentPassword = "studentPassword";
    public static final String StdName = "StdName";
    public static final String subName1 = "SUBName1";
    public static final String subName2 = "SUBName2";
    public static final String subName3 = "SUBName3";
    public static final String subName4 = "SUBName4";
    public static final String subName5 = "SUBName5";

    public static final String Marks1 = "Marks1";
    public static final String Marks2 = "Marks2";
    public static final String Marks3 = "Marks3";
    public static final String Marks4 = "Marks4";
    public static final String Marks5 = "Marks5";

    public static final String Teacher_Details = "Teacher_Details";
    public static final String TeacherName = "TeacherName";
    public static final String TeacherId = "TeacherId";

    public static final String AdminTable = "AdminTable";
    public static final String AdminName = "AdminName";
    public static final String adminPassword = "adminPassword";

    public MyDB(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry = "create table " + Student_Details + "(" + StdRegNum + " integer primary key," + StdName + " text,"
                + subName1 + " text ," + Marks1 + " integer, "
                + subName2 + " text ," + Marks2 + " integer, "
                + subName3 + " text ," + Marks3 + " integer, "
                + subName4 + " text ," + Marks4 + " integer,"
                + subName5 + " text ," + Marks5 + " integer,"
                + adminPassword + " text ,"
                + teacherPassword + " integer,"
                + studentPassword + " text)";
        sqLiteDatabase.execSQL(qry);

        String teacherTable = "create table " + Teacher_Details + "(" + TeacherId + " integer primary key," + TeacherName + " text," + teacherPassword + " text)";
        sqLiteDatabase.execSQL(teacherTable);

        String adminTable = "create table " + AdminTable + "(" + AdminName + " text," + adminPassword + " text)";
        sqLiteDatabase.execSQL(adminTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
