package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.annotation.Nullable;

public class DetailAgent extends AppCompatActivity {

    private ImageView Image;
    private TextView Name;
    private TextView Age;
    private TextView Bio;
    private TextView City;
    private String userId;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private Button Chat;
    private Button Approach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agent);

        Image = findViewById(R.id.imageView3);
        Name = findViewById(R.id.Name);
        Age = findViewById(R.id.Age);
        Bio = findViewById(R.id.Bio);
        City = findViewById(R.id.City);
        Chat = findViewById(R.id.btnChat);
        Approach = findViewById(R.id.btnApproach);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String agentID = getIntent().getStringExtra("key");

        DocumentReference documentReference = fStore.collection("Users").document(agentID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    Name.setText(documentSnapshot.getString("FullName"));
                    Age.setText(documentSnapshot.getString("Age"));
                    City.setText(documentSnapshot.getString("City"));
                    Bio.setText(documentSnapshot.getString("Bio"));
                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent chatAgent = new Intent(DetailAgent.this,ProfileActivity.class);
                startActivity(chatAgent);
                finish();
            }
        });
        Approach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent approachAgent = new Intent(DetailAgent.this,AppointmentActivity.class).putExtra("key",agentID);
                startActivity(approachAgent);
                finish();
            }
        });
    }
}