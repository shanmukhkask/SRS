package com.project.srs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    String userType = "";
    EditText etUserId, etNewPassword, etReEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etUserId = findViewById(R.id.etUserId);
        etNewPassword = findViewById(R.id.etNewPassword);
        etReEnterPassword = findViewById(R.id.etReEnterPassword);

        userType = getIntent().getStringExtra("userType");
        if (userType != null && userType.equals("admin")) {
            etUserId.setHint("Enter User Name");
        }

    }

    public void forgotPassword(View view) {
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();

        String userId = etUserId.getText().toString().trim();
        String newPwd = etNewPassword.getText().toString().trim();
        String reEnter = etReEnterPassword.getText().toString().trim();
        ContentValues contentValues = new ContentValues();
        if (userId.isEmpty() || newPwd.isEmpty() || reEnter.isEmpty()) {
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        } else {
            if (userType != null && userType.equalsIgnoreCase("student")) {

                if (newPwd.equals(reEnter)) {
                    contentValues.put(MyDB.studentPassword, newPwd);
                    String whr = MyDB.StdRegNum + "=?";
                    String whr_args[] = {userId};
                    long cno = sqLiteDatabase.update(MyDB.Student_Details, contentValues, whr, whr_args);
                    if (cno > 0) {
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                        finish();
                        etReEnterPassword.setText("");
                        etNewPassword.setText("");

                    } else {
                        Toast.makeText(this, "Enter correct User Name", Toast.LENGTH_SHORT).show();
                    }
                    //   contentValues.put(MyDB.studentPassword, newPwd);
//                    Cursor c = sqLiteDatabase.rawQuery("update Student_Details set studentPassword = " + newPwd + " where RegisterNumber = " + userId, null);
//                    if (c.moveToFirst()) {
//                        Toast.makeText(this, "Password Updated", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "Enter Correct reg num", Toast.LENGTH_SHORT).show();
//
//                    }
//                    c.close();
                } else {
                    Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
                }
            } else if (userType != null && userType.equalsIgnoreCase("teacher")) {

                if (newPwd.equals(reEnter)) {
                    contentValues.put(MyDB.teacherPassword, newPwd);
                    String whr = MyDB.TeacherId + "=?";
                    String whr_args[] = {userId};
                    long cno = sqLiteDatabase.update(MyDB.Teacher_Details, contentValues, whr, whr_args);
                    if (cno > 0) {
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                        finish();
                        etReEnterPassword.setText("");
                        etNewPassword.setText("");

                    } else {
                        Toast.makeText(this, "Enter correct User Name", Toast.LENGTH_SHORT).show();
                    }
                   // Cursor c = sqLiteDatabase.rawQuery("update Teacher_Details set teacherPassword = " + newPwd + " where TeacherId = " + userId, null);

                    // Cursor c = sqLiteDatabase.rawQuery("update teacherPassword from Teacher_Details where TeacherId = " + userId, null);
//                    if (c.moveToFirst()) {
//                        Toast.makeText(this, "Password Updated", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "Enter Correct reg num", Toast.LENGTH_SHORT).show();
//
//                    }
//                    c.close();
//                    contentValues.put(MyDB.teacherPassword, newPwd);
//                    long l = sqLiteDatabase.update(MyDB.Teacher_Details, contentValues, MyDB.TeacherId + "=" + WelcomeTeacher.teacherId, null);

                } else {
                    Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
                }
            } else if (userType != null && userType.equalsIgnoreCase("admin")) {

                if (newPwd.equals(reEnter)) {
                    contentValues.put(MyDB.adminPassword, newPwd);
                    String whr = MyDB.AdminName + "=?";
                    String whr_args[] = {userId};
                    long cno = sqLiteDatabase.update(MyDB.AdminTable, contentValues, whr, whr_args);

                    if (cno > 0) {
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(this, "Enter correct User Name", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Password mismatch", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void back(View view) {
        finish();
        onBackPressed();
    }
}