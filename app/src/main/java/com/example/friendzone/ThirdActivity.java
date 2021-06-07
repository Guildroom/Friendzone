package com.example.friendzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {

    private EditText NameUser;
    private EditText EmailUser;
    private EditText PasswordUser;
    private EditText ConfirmPasswordUser;
    private Button SignUpUser;
    private Button LogInUser;
    private ProgressBar PBar;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        NameUser = (EditText)findViewById(R.id.editTextTextPersonName3);
        EmailUser = (EditText)findViewById(R.id.editTextTextPersonName4);
        PasswordUser = (EditText)findViewById(R.id.editTextTextPassword2);
        ConfirmPasswordUser = (EditText)findViewById(R.id.editTextTextPassword3);
        SignUpUser = (Button)findViewById(R.id.button4);
        LogInUser = (Button)findViewById(R.id.button7);
        PBar = (ProgressBar)findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        SignUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //Register(NameUser.getText().toString(),EmailUser.getText().toString(),
                //PasswordUser.getText().toString(),ConfirmPasswordUser.getText().toString());
                String Name = NameUser.getText().toString();
                String Email = EmailUser.getText().toString();
                String Password = PasswordUser.getText().toString();
                String ConfirmPassword = ConfirmPasswordUser.getText().toString();

                if(TextUtils.isEmpty(Name)){
                    NameUser.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(Email)){
                    EmailUser.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    PasswordUser.setError("Password is Required");
                    return;
                }
                else if(Password.length()<8){
                    PasswordUser.setError("Minimum Password is 8 Characters");
                    return;
                }
                if(TextUtils.isEmpty(ConfirmPassword)){
                    ConfirmPasswordUser.setError("Confirm Password is Required");
                    return;
                }
                if(!TextUtils.equals(Password,ConfirmPassword)){
                    ConfirmPasswordUser.setError("Confirm Password is Incorrect");
                    return;
                }

                PBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("FullName",Name);
                            user.put("Email",Email);
                            user.put("Retailer","No");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            });
                            Toast.makeText(ThirdActivity.this,"Sign Up Successful",Toast.LENGTH_LONG).show();
                            Intent intent3 = new Intent(ThirdActivity.this, RegisterData.class);
                            startActivity(intent3);
                            finish();
                        }
                        else{
                            Toast.makeText(ThirdActivity.this,"Sign Up Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        LogInUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent5 = new Intent(ThirdActivity.this, MainActivity.class);
                startActivity(intent5);
                finish();
            }
        });

    }

    private void Register(String name, String email, String pass, String conpass){
        if(name.equals(""))
            Toast.makeText(ThirdActivity.this,"Name Required",Toast.LENGTH_SHORT).show();
        else if(email.equals(""))
            Toast.makeText(ThirdActivity.this,"Email Required",Toast.LENGTH_SHORT).show();
        else if(pass.equals(""))
            Toast.makeText(ThirdActivity.this,"Password Required",Toast.LENGTH_SHORT).show();
        else if(pass.length()<6)
            Toast.makeText(ThirdActivity.this,"Password Length Require 6 or More",Toast.LENGTH_SHORT).show();
        else if(conpass.equals(""))
            Toast.makeText(ThirdActivity.this,"Confirm Password Required",Toast.LENGTH_SHORT).show();
        else if(!(pass).equals(conpass))
            Toast.makeText(ThirdActivity.this,"Confirm Password Incorrect",Toast.LENGTH_SHORT).show();
        else{
            Intent intent3 = new Intent(ThirdActivity.this, SecondActivity.class);
            startActivity(intent3);
        }
    }
}
