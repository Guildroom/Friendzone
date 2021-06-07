package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class StartActivity extends AppCompatActivity {
    private Button BoyFriend;
    private Button GirlFriend;
    private ImageButton BackProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        BoyFriend = findViewById(R.id.BoyFriend);
        GirlFriend = findViewById(R.id.GirlFriend);
        BackProfile = findViewById(R.id.BackProfile);

        BoyFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( StartActivity.this , BoyFriendActivity.class));
                finish();
            }
        });
        GirlFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this , GirlFriendActivity.class));
                finish();
            }
        });
        BackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this , ProfileActivity.class));
                finish();
            }
        });
    }
}