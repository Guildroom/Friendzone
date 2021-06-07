package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RetailerActivity extends AppCompatActivity {
    TextView Nama;
    TextView Gender;
    TextView NoHP;
    TextView Age;
    TextView Biodata;
    Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer);

        Nama = findViewById(R.id.NamaAgent);
        Gender = findViewById(R.id.GenderAgent);
        NoHP = findViewById(R.id.NoHp);
        Age = findViewById(R.id.UmurAgent);
        Biodata = findViewById(R.id.BiodataAgent);
        Back = findViewById(R.id.Back);


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( RetailerActivity.this , StartActivity.class));
                finish();
            }
        });
    }
}