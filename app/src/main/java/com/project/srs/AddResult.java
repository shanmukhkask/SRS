package com.project.srs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amitshekhar.DebugDB;

public class AddResult extends AppCompatActivity {

    EditText etStdName, etRegNum, etSub1, etSub2, etSub3, etSub4, etSub5, etSubMarks1, etSubMarks2,
            etSubMarks3, etSubMarks4, etSubMarks5, stdRegNum;
    String studentName, sub1, sub2, sub3, sub4, sub5, marks1, marks2, marks3, marks4, marks5, regNum;
    String modifyResult;
    TextView tvResult;
    LinearLayout ll, modifyLL;

    Button btnSubmit, btnAddRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);

        stdRegNum = findViewById(R.id.stdRegNum);
        btnAddRecord = findViewById(R.id.btnAddRecord);
        btnSubmit = findViewById(R.id.btnSubmit);
        ll = findViewById(R.id.ll);
        modifyLL = findViewById(R.id.modifyLL);
        tvResult = findViewById(R.id.tvResult);
        etStdName = findViewById(R.id.etStdName);
        etRegNum = findViewById(R.id.etRegNum);
        etSub1 = findViewById(R.id.etSub1);
        etSub2 = findViewById(R.id.etSub2);
        etSub3 = findViewById(R.id.etSub3);
        etSub4 = findViewById(R.id.etSub4);
        etSub5 = findViewById(R.id.etSub5);
        etSubMarks1 = findViewById(R.id.etSubMarks1);
        etSubMarks2 = findViewById(R.id.etSubMarks2);
        etSubMarks3 = findViewById(R.id.etSubMarks3);
        etSubMarks4 = findViewById(R.id.etSubMarks4);
        etSubMarks5 = findViewById(R.id.etSubMarks5);

        modifyResult = getIntent().getStringExtra("modifyResult");

        DebugDB.getAddressLog();
        if (modifyResult != null && modifyResult.equalsIgnoreCase("Modify Result")) {
            tvResult.setText(modifyResult);
            ll.setVisibility(View.VISIBLE);
            modifyLL.setVisibility(View.GONE);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyDB myDB = new MyDB(AddResult.this);
                    final SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();

                    if (stdRegNum.getText().toString().trim() != null) {

                        btnAddRecord.setText("Modify Record");
                        Cursor c = sqLiteDatabase.rawQuery("select * from Student_Details where RegisterNumber = " + stdRegNum.getText().toString().trim(), null);
                        if (c.moveToFirst()) {
                            modifyLL.setVisibility(View.VISIBLE);
                            etRegNum.setVisibility(View.GONE);
                            regNum = c.getString(0);
                            studentName = c.getString(1);
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
                        } else {
                            Toast.makeText(AddResult.this, "enter correct reg num", Toast.LENGTH_SHORT).show();
                        }
                        c.close();

                    }
                    etStdName.setText(studentName);
                    etSub1.setText(sub1);
                    etSub2.setText(sub2);
                    etSub3.setText(sub3);
                    etSub4.setText(sub4);
                    etSub5.setText(sub5);
                    etSubMarks1.setText(marks1);
                    etSubMarks2.setText(marks2);
                    etSubMarks3.setText(marks3);
                    etSubMarks4.setText(marks4);
                    etSubMarks5.setText(marks5);
                    if (btnAddRecord.getText().toString().equalsIgnoreCase("Modify Record")) {
                        btnAddRecord.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (studentName != null && regNum != null && sub1 != null && sub2 != null && sub3 != null && sub4 != null && sub5 != null
                                        && marks1 != null && marks2 != null && marks3 != null && marks4 != null && marks5 != null) {

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(MyDB.StdName, etStdName.getText().toString().trim());
                                    //contentValues.put(MyDB.StdRegNum, regNum);
                                    contentValues.put(MyDB.Marks1, etSubMarks1.getText().toString().trim());
                                    contentValues.put(MyDB.Marks2, etSubMarks2.getText().toString().trim());
                                    contentValues.put(MyDB.Marks3, etSubMarks3.getText().toString().trim());
                                    contentValues.put(MyDB.Marks4, etSubMarks4.getText().toString().trim());
                                    contentValues.put(MyDB.Marks5, etSubMarks5.getText().toString().trim());
                                    contentValues.put(MyDB.subName1, etSub1.getText().toString().trim());
                                    contentValues.put(MyDB.subName2, etSub2.getText().toString().trim());
                                    contentValues.put(MyDB.subName3, etSub3.getText().toString().trim());
                                    contentValues.put(MyDB.subName4, etSub4.getText().toString().trim());
                                    contentValues.put(MyDB.subName5, etSub5.getText().toString().trim());
                                    long l = sqLiteDatabase.update(MyDB.Student_Details, contentValues, MyDB.StdRegNum + "=" + stdRegNum.getText().toString().trim(), null);
                                    if (l >= 0) {
                                        Toast.makeText(AddResult.this, "Updated", Toast.LENGTH_SHORT).show();
                                        etSubMarks1.setText("");
                                        etSubMarks2.setText("");
                                        etSubMarks3.setText("");
                                        etSubMarks4.setText("");
                                        etSubMarks5.setText("");
                                        etSub1.setText("");
                                        etSub2.setText("");
                                        etSub3.setText("");
                                        etSub4.setText("");
                                        etSub5.setText("");
                                        etRegNum.setText("");
                                        etStdName.setText("");
                                    } else {
                                        Toast.makeText(AddResult.this, "Error in Inserting. Make sure registration number is unique", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }

                }
            });


        }
    }

    public void addRecord(View view) {
        studentName = etStdName.getText().toString().trim();
        regNum = etRegNum.getText().toString().trim();
        sub1 = etSub1.getText().toString().trim();
        sub2 = etSub2.getText().toString().trim();
        sub3 = etSub3.getText().toString().trim();
        sub4 = etSub4.getText().toString().trim();
        sub5 = etSub5.getText().toString().trim();
        marks1 = etSubMarks1.getText().toString().trim();
        marks2 = etSubMarks2.getText().toString().trim();
        marks3 = etSubMarks3.getText().toString().trim();
        marks4 = etSubMarks4.getText().toString().trim();
        marks5 = etSubMarks5.getText().toString().trim();

        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getWritableDatabase();


        if (studentName != null && regNum != null && sub1 != null && sub2 != null && sub3 != null && sub4 != null && sub5 != null
                && marks1 != null && marks2 != null && marks3 != null && marks4 != null && marks5 != null) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(MyDB.StdName, studentName);
            contentValues.put(MyDB.StdRegNum, Integer.valueOf(regNum));
            contentValues.put(MyDB.Marks1, marks1);
            contentValues.put(MyDB.Marks2, marks2);
            contentValues.put(MyDB.Marks3, marks3);
            contentValues.put(MyDB.Marks4, marks4);
            contentValues.put(MyDB.Marks5, marks5);
            contentValues.put(MyDB.subName1, sub1);
            contentValues.put(MyDB.subName2, sub2);
            contentValues.put(MyDB.subName3, sub3);
            contentValues.put(MyDB.subName4, sub4);
            contentValues.put(MyDB.subName5, sub5);
            contentValues.put(MyDB.studentPassword, "welcome");

            long l = sqLiteDatabase.insert(MyDB.Student_Details, null, contentValues);
            if (l >= 0) {
                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
                etSubMarks1.setText("");
                etSubMarks2.setText("");
                etSubMarks3.setText("");
                etSubMarks4.setText("");
                etSubMarks5.setText("");
                etSub1.setText("");
                etSub2.setText("");
                etSub3.setText("");
                etSub4.setText("");
                etSub5.setText("");
                etRegNum.setText("");
                etStdName.setText("");
            } else {
                Toast.makeText(this, "Error in Inserting. Make sure registration number is unique", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter All Values", Toast.LENGTH_SHORT).show();
        }
    }

    public void home(View view) {
        finish();
        onBackPressed();
    }

    public void back(View view) {
        onBackPressed();
    }

    public void logout(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }
}