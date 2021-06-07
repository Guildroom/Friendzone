package com.example.friendzone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GirlFriendActivity extends AppCompatActivity {
    ListView ViewAgent2;
    FirebaseFirestore fStore;
    ArrayList<UserList> UserListAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl_friend);

        ViewAgent2 = findViewById(R.id.ListAgent2);
        UserListAgent = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();

        loadDatainListview();
    }

    private void loadDatainListview() {
        fStore.collection("Users")
                .whereEqualTo("Retailer", "Yes").whereEqualTo("Gender", "Female")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                UserList userlist = d.toObject(UserList.class);
                                UserListAgent.add(userlist);
                            }
                            AdapterUser adapter = new AdapterUser(GirlFriendActivity.this, UserListAgent);
                            ViewAgent2.setAdapter(adapter);
                        } else {
                            Toast.makeText(GirlFriendActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GirlFriendActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}