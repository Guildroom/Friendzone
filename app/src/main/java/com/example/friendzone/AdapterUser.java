package com.example.friendzone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterUser extends ArrayAdapter<UserList> {
    public AdapterUser(@NonNull Context context, ArrayList<UserList> UserListArrayList) {
        super(context, 0, UserListArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListAgent = convertView;
        if (ListAgent == null) {
            ListAgent = LayoutInflater.from(getContext()).inflate(R.layout.agent_user, parent, false);
        }

        UserList userList = getItem(position);
        ImageView Image = ListAgent.findViewById(R.id.idIVimage);
        TextView Name = ListAgent.findViewById(R.id.Nama);
        TextView Bio = ListAgent.findViewById(R.id.Biografi);
        String userID = userList.getID();

        Name.setText(userList.getFullName());
        Bio.setText(userList.getBio());

        ListAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), DetailAgent.class).putExtra("key",userID));
            }
        });
        return ListAgent;
    }
}
