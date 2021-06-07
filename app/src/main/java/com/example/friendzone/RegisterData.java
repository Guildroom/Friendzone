package com.example.friendzone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class RegisterData extends AppCompatActivity {

    private EditText NameUser;
    private EditText AgeUser;
    private EditText CityUser;
    private EditText PhoneNumberUser;
    private RadioGroup Gender;
    private RadioButton GenderButton;
    private Button Continue;
    private ProgressBar PBar;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private String Email;
    private String Retailer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

        NameUser = (EditText)findViewById(R.id.editTextTextPersonName);
        AgeUser = (EditText)findViewById(R.id.editTextAge);
        CityUser = (EditText)findViewById(R.id.editTextCity);
        PhoneNumberUser = (EditText)findViewById(R.id.editTextPhone);
        Gender = (RadioGroup) findViewById(R.id.radioGroup);
        Continue = (Button) findViewById(R.id.cont);
        PBar = (ProgressBar)findViewById(R.id.progressBar);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        int genderID = Gender.getCheckedRadioButtonId();
        GenderButton = findViewById(genderID);

        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                NameUser.setText(value.getString("FullName"));
                Email = value.getString("Email");
                Retailer = value.getString("Retailer");
            }
        });

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = NameUser.getText().toString();
                String Age = AgeUser.getText().toString();
                String City = CityUser.getText().toString();
                String PhoneNumber = PhoneNumberUser.getText().toString();
                String genderText = GenderButton.getText().toString();
                int AgeNumber = Integer.parseInt(Age);

                if(TextUtils.isEmpty(Name)){
                    NameUser.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(Age)){
                    AgeUser.setError("Age is Required");
                    return;
                }
                else if(AgeNumber<=17){
                    AgeUser.setError("Age is less than 17 years old");
                    return;
                }
                if(TextUtils.isEmpty(City)) {
                    CityUser.setError("City is Required");
                    return;
                }
                if(TextUtils.isEmpty(PhoneNumber)) {
                    AgeUser.setError("Phone Number is Required");
                    return;
                }

                PBar.setVisibility(View.VISIBLE);
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("FullName",Name);
                user.put("Email",Email);
                user.put("Age",Age);
                user.put("City",City);
                user.put("Gender",genderText);
                user.put("PhoneNumber",PhoneNumber);
                user.put("Retailer",Retailer);
                user.put("Bio","");
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Intent SaveData = new Intent(RegisterData.this, ProfileActivity.class);
                        startActivity(SaveData);
                        finish();
                    }
                });
            }
        });
    }
}
