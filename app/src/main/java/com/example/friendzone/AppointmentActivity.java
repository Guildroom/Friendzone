package com.example.friendzone;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AppointmentActivity extends AppCompatActivity {

    private EditText appDate;
    private EditText appTime;
    private EditText appLocation;
    private EditText appDetails;
    private Button Send;
    private Button Cancel;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID1;
    private String userID2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        appDate = (EditText)findViewById(R.id.editTextDate);
        appTime = (EditText)findViewById(R.id.editTextTime);
        appLocation = (EditText)findViewById(R.id.editTextLocation);
        appDetails = (EditText)findViewById(R.id.editTextDetails);
        Cancel = (Button)findViewById(R.id.buttonCancel);
        Send = (Button)findViewById(R.id.buttonSend);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userID1 = fAuth.getCurrentUser().getUid();

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = appDate.getText().toString();
                String Time = appTime.getText().toString();
                String Location = appLocation.getText().toString();
                String Details = appDetails.getText().toString();

                if(TextUtils.isEmpty(Date)) {
                    appDate.setError("Date is Required");
                    return;
                }
                if(TextUtils.isEmpty(Time)){
                    appTime.setError("Time is required");
                    return;
                }
                if(TextUtils.isEmpty(Location)) {
                    appLocation.setError("Location is Required");
                    return;
                }
                if(TextUtils.isEmpty(Details)){
                    appDetails.setError("Appointment details is Required");
                    return;
                }

                userID1 = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("Appointments").document(userID1);
                Map<String,Object> appointment = new HashMap<>();
                appointment.put("Sender",userID1);
                appointment.put("receiver",userID2);
                appointment.put("Date",Date);
                appointment.put("Time",Time);
                appointment.put("Location",Location);
                appointment.put("Details",Details);
                appointment.put("Approval","");
                documentReference.set(appointment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Appointment added with ID: " + documentReference.getId());
                        Intent makeAppointment = new Intent(AppointmentActivity.this, ProfileActivity.class);
                        startActivity(makeAppointment);
                        finish();
                    }
                });
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AppointmentActivity.this, ProfileActivity.class);
            }
        });
    }
}