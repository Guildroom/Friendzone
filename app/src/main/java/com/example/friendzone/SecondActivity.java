package com.example.friendzone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SecondActivity extends AppCompatActivity {

    private TextView userName;
    private TextView email;
    private TextView phoneNumber;
    private TextView retailer;
    private Button LogOut;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        userName = (TextView)findViewById(R.id.UserName);
        email = (TextView)findViewById(R.id.Email);
        phoneNumber = (TextView)findViewById(R.id.phone);
        retailer = (TextView)findViewById(R.id.Retailer);
        LogOut = (Button)findViewById(R.id.button2);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    Log.d("TAG","Error:"+error.getMessage());
                }
                else{
                    userName.setText(value.getString("FullName"));
                    email.setText(value.getString("Email"));
                    phoneNumber.setText(value.getString("Phone Number"));
                    String ret = value.getString("Retailer");
                    if(ret.equals("No")){
                        retailer.setText("As Customer");
                    }
                    else
                        retailer.setText("As Retailer");
                }}
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                fAuth.signOut();
                Intent logout = new Intent(SecondActivity.this,MainActivity.class);
                startActivity(logout);
                finish();
            }
        });
    }
}
