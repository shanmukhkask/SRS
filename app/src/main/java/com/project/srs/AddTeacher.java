package com.project.srs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddTeacher extends AppCompatActivity {

    EditText etTeacherName, etTeacherId;
    String teacherName, teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        etTeacherName = findViewById(R.id.etTeacherName);
        etTeacherId = findViewById(R.id.etTeacherId);
    }

    public void addTeacher(View view) {
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getWritableDatabase();
        teacherName = etTeacherName.getText().toString().trim();
        teacherId = etTeacherId.getText().toString().trim();
        if (teacherName != null && teacherId != null && !teacherName.equals("") && !teacherId.equals("")) {
            ContentValues cv = new ContentValues();
            cv.put(MyDB.TeacherName, teacherName);
            cv.put(MyDB.TeacherId, Integer.valueOf(teacherId));
            cv.put(MyDB.teacherPassword, "welcome");

            long l = sqLiteDatabase.insert(MyDB.Teacher_Details, null, cv);
            if (l >= 0) {
                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();

                etTeacherId.setText("");
                etTeacherName.setText("");
                etTeacherName.requestFocus();
            } else {
                Toast.makeText(this, "Error in Insertion. Make sure TeacherId unique", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter All Details", Toast.LENGTH_SHORT).show();
        }
    }

    public void home(View view) {
        finish();
        onBackPressed();
    }

    public void back(View view) {
        finish();
        onBackPressed();
    }

    public void logout(View view) {
        startActivity(new Intent(this,MainActivity.class));
        finishAffinity();
    }
}