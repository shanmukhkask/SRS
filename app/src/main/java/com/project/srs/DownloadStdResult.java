package com.project.srs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DownloadStdResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_std_result);
    }

    public void home(View view) {
        finish();
        onBackPressed();
    }

    public void back(View view) {
        finish();
        onBackPressed();
    }
    public void logout(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }
}