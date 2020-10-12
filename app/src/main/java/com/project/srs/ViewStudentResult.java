package com.project.srs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ViewStudentResult extends AppCompatActivity {

    TextView tvStudentName, tvRegNum, tvMarks;
    String regnum, stdName, sub1, sub2, sub3, sub4, sub5, marks1, marks2, marks3, marks4, marks5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_result);
        tvStudentName = findViewById(R.id.tvStudentName);
        tvRegNum = findViewById(R.id.tvRegNum);
        tvMarks = findViewById(R.id.tvMarks);
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

        tvStudentName.setText(stdName);
        tvRegNum.setText(regnum);
        tvMarks.setText(sub1 + "\t\t" + marks1 +
                "\n" + sub2 + "\t\t" + marks2 +
                "\n" + sub3 + "\t\t" + marks3 +
                "\n" + sub4 + "\t\t" + marks4 +
                "\n" + sub5 + "\t\t" + marks5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            File f = Environment.getExternalStorageDirectory();
            String path = f.getAbsolutePath();
            path = path + "/SRS/" + tvRegNum.getText().toString();

            final File file = new File(path);
            if (file.exists()) file.delete();
            boolean res = file.createNewFile();

        } catch (Exception e) {
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        }


    }

    public void downloadResult(View view) throws IOException {
        File f = new File(Environment.getExternalStorageDirectory() + "/SRS");
        f.mkdirs();
        FileOutputStream fos = new FileOutputStream(f + "/" + tvRegNum.getText().toString()+".pdf");
        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeChars("" + tvRegNum.getText().toString() + "\n" + tvStudentName.getText().toString() + "\n" + tvMarks.getText().toString());
        dos.close();
        fos.close();
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void back(View view) {
        onBackPressed();
        finish();
    }

    public void logout(View view) {
        startActivity(new Intent(this,MainActivity.class));
        finishAffinity();
    }

    public void home(View view) {
        onBackPressed();
        finish();
    }
}