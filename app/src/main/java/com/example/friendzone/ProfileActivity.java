package com.example.friendzone;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class ProfileActivity extends AppCompatActivity {
    private ImageView Image;
    private TextView Name;
    private TextView Age;
    private TextView Bio;
    private TextView City;
    private TextView Phone;
    private String userId;
    private Button Change;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private Button LogOut;
    private Button List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Image = findViewById(R.id.imageView3);
        Name = findViewById(R.id.ChangeName);
        Age = findViewById(R.id.changeAge);
        Bio = findViewById(R.id.ChangeBio);
        City = findViewById(R.id.ChangeCity);
        Change = findViewById(R.id.Change);
        Phone = findViewById(R.id.Changephone);
        LogOut = findViewById(R.id.LogOut);
        List = findViewById(R.id.List);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    Phone.setText(documentSnapshot.getString("PhoneNumber"));
                    Name.setText(documentSnapshot.getString("FullName"));
                    Age.setText(documentSnapshot.getString("Age"));
                    City.setText(documentSnapshot.getString("City"));
                    Bio.setText(documentSnapshot.getString("Bio"));
                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent Change = new Intent(ProfileActivity.this,ChangeProfileActivity.class);
                startActivity(Change);
                finish();
            }
        });
        List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent List = new Intent(ProfileActivity.this,StartActivity.class);
                startActivity(List);
                finish();
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                fAuth.signOut();
                Intent logout = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(logout);
                finish();
            }
        });
    }
}