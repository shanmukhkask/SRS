package com.project.srs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeAdminActivity extends AppCompatActivity {

    Button btnAddResult, btnDownloadResult, btnViewResult, btnUploadSheets, btnModifyResult, btnSearchResult,
            btnDeleteResult, btnChangePwd, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_admin_page);
        btnAddResult = findViewById(R.id.btnAddResult);
        btnDownloadResult = findViewById(R.id.btnDownloadResult);
        btnViewResult = findViewById(R.id.btnViewResult);
        btnUploadSheets = findViewById(R.id.btnUploadSheets);
        btnModifyResult = findViewById(R.id.btnModifyResult);
        btnSearchResult = findViewById(R.id.btnSearchResult);
        btnDeleteResult = findViewById(R.id.btnDeleteResult);
        btnChangePwd = findViewById(R.id.btnChangePwd);
        btnLogout = findViewById(R.id.btnLogout);
    }

    public void addResult(View view) {
        Intent intent = new Intent(this, AddResult.class);
        startActivity(intent);
    }

    public void downloadResult(View view) {
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select * from Student_Details", null);
        if (c.moveToFirst()) {
            Intent intent = new Intent(this, ViewResultActivity.class);
            intent.putExtra("viewResult", "View Result");
            startActivity(intent);
        } else {
            Toast.makeText(this, "No Records", Toast.LENGTH_SHORT).show();
        }

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
            Toast.makeText(this, "No Records", Toast.LENGTH_SHORT).show();
        }

    }

    public void modifyResult(View view) {
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

    public void SearchResult(View view) {
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select * from Student_Details", null);
        if (c.moveToFirst()) {
            Intent intent = new Intent(this, SeachResultActivity.class);
            intent.putExtra("searchResult", "Search Result");
            startActivity(intent);
        }else {
            Toast.makeText(this, "No Records", Toast.LENGTH_SHORT).show();

        }

    }

    public void deleteResult(View view) {
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

    public void changePwd(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra("admin", "admin");
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    public void addTeacher(View view) {
        Intent intent = new Intent(this, AddTeacher.class);
        startActivity(intent);
    }
}
