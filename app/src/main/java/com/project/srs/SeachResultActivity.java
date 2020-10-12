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

public class SeachResultActivity extends AppCompatActivity {
    EditText etSearch;
    TextView tvSearchResult;
    String search = "";
    MyDB myDB;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        etSearch = findViewById(R.id.etSearch);
        tvSearchResult=findViewById(R.id.tvSearchResult);
    }

    public void search(View view) {
        MyDB myDB=new MyDB(this);
        SQLiteDatabase sqLiteDatabase=myDB.getReadableDatabase();
        search=etSearch.getText().toString().trim();

        if (search!=null){
            Cursor c=sqLiteDatabase.rawQuery("select * from Student_Details where StdName  LIKE '%" + search + "%' ",null);
            boolean res=c.moveToFirst();
            if (res){
                String data=c.getString(1);
                String sub=c.getString(2);
                String marks=c.getString(3);
                tvSearchResult.setText(data+"\n"+sub+"\n"+marks);
            }else {
                Toast.makeText(this, "Data empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    public void home(View view) {
        finish();
        onBackPressed();
    }

    public void back(View view) {
        finish();
        onBackPressed();
    }
}
