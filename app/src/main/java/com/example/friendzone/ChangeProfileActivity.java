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


public class ChangeProfileActivity extends AppCompatActivity {
    private ImageView Image;
    private EditText Name;
    private EditText Age;
    private EditText Bio;
    private EditText City;
    private EditText Phone;
    private String userId;
    private Button Change;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        Image = findViewById(R.id.ImageProfile);
        Name = findViewById(R.id.ChangeName2);
        Age = findViewById(R.id.changeAge2);
        Bio = findViewById(R.id.ChangeBio2);
        City = findViewById(R.id.ChangeCity2);
        Change = findViewById(R.id.Change2);
        Phone = findViewById(R.id.Changephone2);
        Back = findViewById(R.id.Back2);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

          /*StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(Image);
            }
        });*/

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
            public void onClick(View v) {
                String txtName = Name.getText().toString();
                String txtAge = Age.getText().toString();
                String txtBio = Bio.getText().toString();
                String txtPhone = Phone.getText().toString();
                String txtCity = City.getText().toString();

                if ((TextUtils.isEmpty(txtName)) || (TextUtils.isEmpty(txtCity))
                        || TextUtils.isEmpty(txtAge) || TextUtils.isEmpty(txtPhone)) {
                    Toast.makeText(ChangeProfileActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    DocumentReference docRef = fStore.collection("Users").document(user.getUid());
                    Map<String, Object> edited = new HashMap<>();
                    edited.put("FullName", txtName);
                    edited.put("PhoneNumber", txtPhone);
                    edited.put("Age", txtAge);
                    edited.put("Bio", txtBio);
                    edited.put("City", txtCity);
                    docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ChangeProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    });
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent List = new Intent(ChangeProfileActivity.this,ProfileActivity.class);
                startActivity(List);
                finish();
            }
        });

    }
}