package com.project.srs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String per[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int req_codes = 121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int res = ContextCompat.checkSelfPermission(this, per[0]);
        if (res == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, per, req_codes);

        }
    }

    public void admin(View view) {
        MyDB myDB = new MyDB(this);
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("select * from AdminTable",null);
        if (c.moveToFirst()){
            Intent adminIntent = new Intent(this, LoginActivity.class);
            adminIntent.putExtra("admin", "admin");
            startActivity(adminIntent);
        }else {
            ContentValues cv = new ContentValues();
            cv.put(MyDB.AdminName, "admin");
            cv.put(MyDB.adminPassword, "admin");

            long l=sqLiteDatabase.insert(MyDB.AdminTable, null, cv);
            if (l>0){
                Intent adminIntent = new Intent(this, LoginActivity.class);
                adminIntent.putExtra("admin", "admin");
                startActivity(adminIntent);
            }else {
                Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void student(View view) {
        Intent adminIntent = new Intent(this, LoginActivity.class);
        adminIntent.putExtra("student", "student");
        startActivity(adminIntent);
    }

    public void teacher(View view) {
        Intent adminIntent = new Intent(this, LoginActivity.class);
        adminIntent.putExtra("teacher", "teacher");
        startActivity(adminIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == req_codes) {
            if (permissions[0].equals(per[0])) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    finish();
                    Toast.makeText(MainActivity.this, "Permission required", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }
}