package com.project.srs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WelcomeStudent extends AppCompatActivity {

    TextView tvRegNum, tvWelcomeStd;
    public static String regnum;
   public String stdName, sub1, sub2, sub3, sub4, sub5, marks1, marks2, marks3, marks4, marks5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_student);

        tvRegNum = findViewById(R.id.tvRegNum);
        tvWelcomeStd = findViewById(R.id.tvWelcomeStd);

        regnum = getIntent().getStringExtra("regNum");

        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("select * from Student_Details where RegisterNumber = " + WelcomeStudent.regnum, null);
        if (c.moveToFirst()) {
            regnum = c.getString(0);
            stdName = c.getString(1);
            sub1 = c.getString(2);
            marks1 = c.getString(3);
            sub2 = c.getString(4);
            marks2 = c.getString(5);
            sub3 = c.getString(6);
            marks3 = c.getString(7);
            sub4 = c.getString(8);
            marks4 = c.getString(9);
            sub5 = c.getString(10);
            marks5 = c.getString(11);
        }
        c.close();
        tvRegNum.setText(regnum);
        tvWelcomeStd.setText("Welcome \n" + stdName);

    }

    public void changePwd(View view) {
        Intent intent = new Intent(WelcomeStudent.this, ChangePasswordActivity.class);
        intent.putExtra("student", "student");
        startActivity(intent);
    }

    public void logout(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

    public void downloadResult(View view) throws IOException {

        File f = new File(Environment.getExternalStorageDirectory() + "/SRS");
        f.mkdirs();
        FileOutputStream fos = new FileOutputStream(f + "/" + tvRegNum.getText().toString() + ".pdf");
        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeChars("" + regnum + "\n" + stdName + "\n" + sub1 + "\t\t" + marks1 +
                "\n" + sub2 + "\t\t" + marks2 +
                "\n" + sub3 + "\t\t" + marks3 +
                "\n" + sub4 + "\t\t" + marks4 +
                "\n" + sub5 + "\t\t" + marks5);
        dos.close();
        fos.close();
        Toast.makeText(this, "Saved successfully in SRS directory", Toast.LENGTH_SHORT).show();
       // finish();
        startActivity(new Intent(this,DownloadStdResult.class));
    }

    public void viewResult(View view) {
        Intent intent = new Intent(this, ViewStudentResult.class);
        intent.putExtra("student", "student");
        startActivity(intent);
    }

}

