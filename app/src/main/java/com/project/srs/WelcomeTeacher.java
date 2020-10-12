package com.project.srs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeTeacher extends AppCompatActivity {

    TextView tvTeacherId, tvTeacherName;
    public static String teacherId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_teacher);
        tvTeacherId = findViewById(R.id.tvTeacherId);
        tvTeacherName = findViewById(R.id.tvTeacherName);

        teacherId = getIntent().getStringExtra("teacherId");
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();
        Cursor teacherDb = sqLiteDatabase.rawQuery("select * from Teacher_Details where TeacherId = " + teacherId, null);
        if (teacherDb.moveToFirst()) {
            tvTeacherId.setText("Your Id:\t" + teacherDb.getString(0));
            tvTeacherName.setText("Your Name:\t"+teacherDb.getString(1));
        }
        teacherDb.close();

    }

    public void teacherLogout(View view) {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void changePwd(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra("teacher", "teacher");
        startActivity(intent);
    }

    public void viewResult(View view) {
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select * from Student_Details", null);
        if (c.moveToFirst()) {
            Intent intent = new Intent(this, ViewResultActivity.class);
            intent.putExtra("viewResult", "View Result");
            startActivity(intent);
        } else {
            Toast.makeText(this, "No Records Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void downloadResult(View view) {
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select * from Student_Details", null);
        if (c.moveToFirst()) {

            Intent intent = new Intent(this, ViewResultActivity.class);
            intent.putExtra("deleteResult", "Delete Result");
            startActivity(intent);
        } else {
            Toast.makeText(this, "No Records", Toast.LENGTH_SHORT).show();

        }
    }

    public void update(View view) {
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select * from Student_Details", null);
        if (c.moveToFirst()) {
            Intent intent = new Intent(this, AddResult.class);
            intent.putExtra("modifyResult", "Modify Result");
            startActivity(intent);
        } else {
            Toast.makeText(this, "No Records", Toast.LENGTH_SHORT).show();
        }
    }
}
