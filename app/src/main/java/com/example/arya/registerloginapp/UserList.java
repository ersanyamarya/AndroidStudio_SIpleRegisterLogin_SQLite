package com.example.arya.registerloginapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserList extends Activity {
    ArrayList<String> line1 = new ArrayList<String>();
    ArrayList<String> row2 = new ArrayList<String>();


    ListView listView;
    ListViewAdapter listViewAdapter;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        listView = (ListView) findViewById(R.id.lvUsers);
        listViewAdapter = new ListViewAdapter(this, line1, row2);
        List<User> users = databaseHandler.getAllUsers();
        for (User cn : users) {
            line1.add(cn.getName().toString().trim());
            row2.add(cn.getEmail().toString().trim());
        }
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                final Dialog dialog = new Dialog(UserList.this);
                dialog.setContentView(R.layout.confirm_del);
                final Button btYes = (Button) dialog.findViewById(R.id.btYes);
                final Button btNo = (Button) dialog.findViewById(R.id.btNo);
                btYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email = row2.get(pos);
                        databaseHandler.deleteUser(email);
                        line1.remove(pos);
                        row2.remove(pos);
                        listView.setAdapter(listViewAdapter);
                        dialog.dismiss();
                        Toast.makeText(UserList.this,"User Deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                btNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });
    }
}
