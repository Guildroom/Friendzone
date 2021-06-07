package com.example.friendzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Login;
    private Button SignUp;
    private Button SignUpRetailer;
    private ProgressBar PBar;
    private FirebaseAuth fAuth;
    private String userID;
    private int attemps = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email = (EditText)findViewById(R.id.editTextTextPersonName);
        Password = (EditText)findViewById(R.id.editTextTextPassword);
        Login = (Button)findViewById(R.id.button);
        SignUp = (Button)findViewById(R.id.button3);
        SignUpRetailer = (Button)findViewById(R.id.button6);
        PBar = (ProgressBar)findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent login = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(login);
            finish();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String UserEmail = Email.getText().toString();
                String UserPassword = Password.getText().toString();

                if(TextUtils.isEmpty(UserEmail)){
                    Email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(UserPassword)){
                    Password.setError("Password is Required");
                    return;
                }
                login(Email.getText().toString(),Password.getText().toString());
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SigningUp();
            }
        });

        SignUpRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SigningUpRetailer();
            }
        });
    }

    private void login(String userEmail, String userPassword){
        fAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            PBar.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void SigningUp(){
        Intent intent2 = new Intent(MainActivity.this,ThirdActivity.class);
        startActivity(intent2);
        finish();
    }

    private void SigningUpRetailer(){
        Intent intent3 = new Intent(MainActivity.this,FourthActivity.class);
        startActivity(intent3);
        finish();
    }
}
