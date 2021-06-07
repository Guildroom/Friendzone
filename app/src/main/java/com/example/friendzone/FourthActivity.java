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

public class FourthActivity extends AppCompatActivity {

    private EditText NameRetailer;
    private EditText EmailRetailer;
    private EditText PasswordRetailer;
    private EditText ConfirmPasswordRetailer;
    private Button SignUpRetailer;
    private Button LogInRetailer;
    private ProgressBar PBar;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        NameRetailer = (EditText)findViewById(R.id.editTextTextPersonName6);
        EmailRetailer = (EditText)findViewById(R.id.editTextTextPersonName7);
        PasswordRetailer = (EditText)findViewById(R.id.editTextTextPassword6);
        ConfirmPasswordRetailer = (EditText)findViewById(R.id.editTextTextPassword7);
        SignUpRetailer = (Button)findViewById(R.id.button5);
        LogInRetailer = (Button)findViewById(R.id.button8);
        PBar = (ProgressBar)findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        SignUpRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String Name = NameRetailer.getText().toString();
                String Email = EmailRetailer.getText().toString();
                String Password = PasswordRetailer.getText().toString();
                String ConfirmPassword = ConfirmPasswordRetailer.getText().toString();

                if(TextUtils.isEmpty(Name)){
                    NameRetailer.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(Email)){
                    EmailRetailer.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    PasswordRetailer.setError("Password is Required");
                    return;
                }
                else if(Password.length()<8){
                    PasswordRetailer.setError("Minimum Password is 8 Characters");
                    return;
                }
                if(TextUtils.isEmpty(ConfirmPassword)){
                    ConfirmPasswordRetailer.setError("Confirm Password is Required");
                    return;
                }
                if(!TextUtils.equals(Password,ConfirmPassword)){
                    ConfirmPasswordRetailer.setError("Confirm Password is Incorrect");
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
                            user.put("Retailer","Yes");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            });
                            Toast.makeText(FourthActivity.this,"Sign Up Successful",Toast.LENGTH_LONG).show();
                            Intent intent3 = new Intent(FourthActivity.this, RegisterData.class);
                            startActivity(intent3);
                            finish();
                        }
                        else{
                            Toast.makeText(FourthActivity.this,"Sign Up Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        LogInRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent6 = new Intent(FourthActivity.this, MainActivity.class);
                startActivity(intent6);
                finish();
            }
        });
    }

    private void RegisterRetailer(String name, String email, String pass, String conpass){
        if(name.equals(""))
            Toast.makeText(FourthActivity.this,"Name Required",Toast.LENGTH_SHORT).show();
        else if(email.equals(""))
            Toast.makeText(FourthActivity.this,"Email Required",Toast.LENGTH_SHORT).show();
        else if(pass.equals(""))
            Toast.makeText(FourthActivity.this,"Password Required",Toast.LENGTH_SHORT).show();
        else if(conpass.equals(""))
            Toast.makeText(FourthActivity.this,"Confirm Password Required",Toast.LENGTH_SHORT).show();
        else if(!(pass).equals(conpass))
            Toast.makeText(FourthActivity.this,"Confirm Password Incorrect",Toast.LENGTH_SHORT).show();
        else{
            Intent intent4 = new Intent(FourthActivity.this, SecondActivity.class);
            startActivity(intent4);
        }
    }
}
