package com.project.srs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etUserName, etNewPassword, etReEnterPassword;
    String teacher, student, admin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        etUserName = findViewById(R.id.etUserName);
        etNewPassword = findViewById(R.id.etNewPassword);
        etReEnterPassword = findViewById(R.id.etReEnterPassword);

        teacher = getIntent().getStringExtra("teacher");
        student = getIntent().getStringExtra("student");
        admin = getIntent().getStringExtra("admin");

        if (student != null && !student.isEmpty() && student.equalsIgnoreCase("student")) {
            etUserName.setText(WelcomeStudent.regnum);
            etUserName.setFocusable(false);
        } else if (teacher != null && teacher.equalsIgnoreCase("teacher")) {
            etUserName.setText(WelcomeTeacher.teacherId);
            etUserName.setFocusable(false);
        }

    }

    public void logout(View view) {

        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();

    }

    public void home(View view) {
        finish();
        onBackPressed();
    }

    public void changePwd(View view) {
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();

        String userName = etUserName.getText().toString().trim();
        String newPwd = etNewPassword.getText().toString().trim();
        String reEnter = etReEnterPassword.getText().toString().trim();

        ContentValues contentValues = new ContentValues();


        if (student != null && !userName.equals("") && student.equalsIgnoreCase("student")) {

            if (!newPwd.equals("") && !reEnter.equals("") && newPwd.equals(reEnter)) {
                contentValues.put(MyDB.studentPassword, newPwd);
                long l = sqLiteDatabase.update(MyDB.Student_Details, contentValues, MyDB.StdRegNum + "=" + WelcomeStudent.regnum, null);
                etReEnterPassword.setText("");
                etNewPassword.setText("");
            } else {
                Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
            }
        } else if (teacher != null && !userName.equals("") && teacher.equalsIgnoreCase("teacher")) {

            if (!newPwd.equals("") && !reEnter.equals("") && newPwd.equals(reEnter)) {
                contentValues.put(MyDB.teacherPassword, newPwd);
                long l = sqLiteDatabase.update(MyDB.Teacher_Details, contentValues, MyDB.TeacherId + "=" + WelcomeTeacher.teacherId, null);
                etReEnterPassword.setText("");
                etNewPassword.setText("");
            } else {
                Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter All Details", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        onBackPressed();
        finish();
    }
}
