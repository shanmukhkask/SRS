package com.project.srs;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ViewResultActivity extends AppCompatActivity {

    TextView tvStudentName, tvResult;
    String admin = "";
    String student = "";
    String teacher = "", viewResult = "", deleteResult = "", searchResult = "";
    EditText etRegNum;
    String regnum, stdName, sub1, sub2, sub3, sub4, sub5, marks1, marks2, marks3, marks4, marks5;
    LinearLayout linearLayout;
    TextView tvSub1, tvSub2, tvSub3, tvSub4, tvSub5, tvSubMarks1, tvSubMarks2, tvSubMarks3, tvSubMarks4, tvSubMarks5;
    String totalResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_result);

        etRegNum = findViewById(R.id.etRegNum);
        tvStudentName = findViewById(R.id.tvStudentName);

//        tvMarks = findViewById(R.id.tvMarks);
        tvResult = findViewById(R.id.tvResult);
        linearLayout = findViewById(R.id.linearLayout);
        tvSub1 = findViewById(R.id.tvSub1);
        tvSub2 = findViewById(R.id.tvSub2);
        tvSub3 = findViewById(R.id.tvSub3);
        tvSub4 = findViewById(R.id.tvSub4);
        tvSub5 = findViewById(R.id.tvSub5);
        tvSubMarks1 = findViewById(R.id.tvSubMarks1);
        tvSubMarks2 = findViewById(R.id.tvSubMarks2);
        tvSubMarks3 = findViewById(R.id.tvSubMarks3);
        tvSubMarks4 = findViewById(R.id.tvSubMarks4);
        tvSubMarks5 = findViewById(R.id.tvSubMarks5);

        admin = getIntent().getStringExtra("admin");
        student = getIntent().getStringExtra("student");
        teacher = getIntent().getStringExtra("teacher");
        viewResult = getIntent().getStringExtra("viewResult");
        deleteResult = getIntent().getStringExtra("deleteResult");
        searchResult = getIntent().getStringExtra("searchResult");

        if (viewResult != null && viewResult.equalsIgnoreCase("View Result")) {
            tvResult.setText(viewResult);
        } else if (deleteResult != null && deleteResult.equalsIgnoreCase("Delete Result")) {
            tvResult.setText(deleteResult);
        } else if (searchResult != null && searchResult.equalsIgnoreCase("Search Result")) {
            tvResult.setText(searchResult);
        }
//
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            File f = Environment.getExternalStorageDirectory();
            String path = f.getAbsolutePath();
            path = path + "/SRS/" + etRegNum.getText().toString();

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
        FileOutputStream fos = new FileOutputStream(f + "/" + etRegNum.getText().toString().trim() + ".pdf");
        DataOutputStream dos = new DataOutputStream(fos);
         dos.writeChars("" + etRegNum.getText().toString() + "\n" + tvStudentName.getText().toString() + "\n" + totalResult);
        dos.close();
        fos.close();
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }


    public void back(View view) {
        finish();
        onBackPressed();
    }

    public void home(View view) {
        finish();
        onBackPressed();

    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    public void submit(View view) {
        regnum = etRegNum.getText().toString().trim();

        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();

        if ((viewResult != null && viewResult.equalsIgnoreCase("View Result"))
                || searchResult != null && searchResult.equalsIgnoreCase("Search Result")) {
            if (regnum != null && !regnum.equals("")) {

                Cursor c = sqLiteDatabase.rawQuery("select * from Student_Details where RegisterNumber = " + regnum, null);
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
                    linearLayout.setVisibility(View.VISIBLE);

                    tvStudentName.setText(stdName);
                    tvSub1.setText(sub1);
                    tvSub2.setText(sub2);
                    tvSub3.setText(sub3);
                    tvSub4.setText(sub4);
                    tvSub5.setText(sub5);
                    tvSubMarks1.setText(marks1);
                    tvSubMarks2.setText(marks2);
                    tvSubMarks3.setText(marks3);
                    tvSubMarks4.setText(marks4);
                    tvSubMarks5.setText(marks5);

                    totalResult=(sub1 + "\t\t" + marks1 +
                            "\n" + sub2 + "\t\t" + marks2 +
                            "\n" + sub3 + "\t\t" + marks3 +
                            "\n" + sub4 + "\t\t" + marks4 +
                            "\n" + sub5 + "\t\t" + marks5);
                } else {
                    Toast.makeText(this, "enter correct reg num", Toast.LENGTH_SHORT).show();
                }
                c.close();
            } else {
                linearLayout.setVisibility(View.GONE);
            }

        } else if (deleteResult != null && deleteResult.equalsIgnoreCase("Delete Result")) {
            String query = "select * from Student_Details where RegisterNumber = " + regnum;
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.getCount() <= 0) {
                cursor.close();
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            } else {
                // if (sqLiteDatabase.getData(query).getC)
                sqLiteDatabase.rawQuery("delete from Student_Details where RegisterNumber = " + regnum, null);
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                etRegNum.setText("");
            }
        }
    }
}
