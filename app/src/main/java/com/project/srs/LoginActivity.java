package com.project.srs;

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

public class LoginActivity extends AppCompatActivity {

    TextView tvName;
    EditText etPassword, etUserName;
    String admin = "";
    String student = "";
    String teacher = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        tvName = findViewById(R.id.tvName);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        admin = getIntent().getStringExtra("admin");
        student = getIntent().getStringExtra("student");
        teacher = getIntent().getStringExtra("teacher");
        if (admin != null && admin.equalsIgnoreCase("admin")) {
            tvName.setText(admin);
        } else if (student != null && student.equalsIgnoreCase("student")) {
            tvName.setText(student);
        } else if (teacher != null && teacher.equalsIgnoreCase("teacher")) {
            tvName.setText(teacher);
        }

    }

    public void login(View view) {
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();

        if (admin != null && admin.equalsIgnoreCase("admin")) {
            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter all values", Toast.LENGTH_SHORT).show();
            } else {
                Cursor teacherDb = sqLiteDatabase.rawQuery("select * from AdminTable", null);
                if (teacherDb.moveToFirst()) {

                    String cols[] = {MyDB.AdminName, MyDB.adminPassword};
                    String sel = MyDB.AdminName + " = ? and " + MyDB.adminPassword + " = ?";
                    String sel_args[] = {userName, password};
                    Cursor c = sqLiteDatabase.query(MyDB.AdminTable, cols, sel, sel_args, null, null, null, null);
                    if (c.moveToFirst()) {
                        finish();
                        Intent adminIntent = new Intent(this, WelcomeAdminActivity.class);

                        startActivity(adminIntent);

                    } else {
                        Toast.makeText(this, "Invalid userName or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No Teachers Found", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (teacher != null && teacher.equalsIgnoreCase("teacher")) {
            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter all values", Toast.LENGTH_SHORT).show();
            } else {
                Cursor teacherDb = sqLiteDatabase.rawQuery("select * from Teacher_Details", null);
                if (teacherDb.moveToFirst()) {

                    String cols[] = {MyDB.TeacherId, MyDB.teacherPassword};
                    String sel = MyDB.TeacherId + " = ? and " + MyDB.teacherPassword + " = ?";
                    String sel_args[] = {userName, password};
                    Cursor c = sqLiteDatabase.query(MyDB.Teacher_Details, cols, sel, sel_args, null, null, null, null);
                    if (c.moveToFirst()) {
                        finish();
                        Intent adminIntent = new Intent(this, WelcomeTeacher.class);
                        adminIntent.putExtra("teacherId", userName);
                        startActivity(adminIntent);

                    } else {
                        Toast.makeText(this, "Invalid userName or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No Teachers Found", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (student != null && student.equalsIgnoreCase("student")) {
            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter all values", Toast.LENGTH_SHORT).show();
            } else {
                Cursor cursor = sqLiteDatabase.rawQuery("select * from Student_Details", null);
                if (cursor.moveToFirst()) {
                    String cols[] = {MyDB.StdRegNum, MyDB.studentPassword};
                    String sel = MyDB.StdRegNum + " = ? and " + MyDB.studentPassword + " = ?";
                    String sel_args[] = {userName, password};
                    Cursor c = sqLiteDatabase.query(MyDB.Student_Details, cols, sel, sel_args, null, null, null, null);

                    if (student != null && student.equalsIgnoreCase("student")) {

                        if (c.moveToFirst()) {
                            finish();
                            Intent adminIntent = new Intent(this, WelcomeStudent.class);
                            adminIntent.putExtra("regNum", userName);
                            startActivity(adminIntent);

                        } else {
                            Toast.makeText(this, "Invalid userName or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "No Students Records Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
        if (admin != null && admin.equalsIgnoreCase("admin")) {
            intent.putExtra("userType", admin);
        } else if (student != null && student.equalsIgnoreCase("student")) {
            intent.putExtra("userType", student);
        } else if (teacher != null && teacher.equalsIgnoreCase("teacher")) {
            intent.putExtra("userType", teacher);
        }
        startActivity(intent);
    }
}
