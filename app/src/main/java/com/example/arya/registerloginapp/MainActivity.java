package com.example.arya.registerloginapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {
    TextView tvShow, tvDataBase;
    Button btLogout, btDetails;
    SharedPreferences prefs;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    String SaveUName, SavePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = (TextView) findViewById(R.id.tvShow);
        btDetails = (Button) findViewById(R.id.btDetails);
        btLogout = (Button) findViewById(R.id.btLogout);
        tvDataBase = (TextView) findViewById(R.id.tvDataBase);
        List<User> users = databaseHandler.getAllUsers();
        StringBuilder user = new StringBuilder();
        prefs = getSharedPreferences("inUser", MODE_PRIVATE);
        SaveUName = String.valueOf(prefs.getString("uName", "a"));
        SavePassword = String.valueOf(prefs.getString("pass", "a"));
        tvShow.setText("Welcome . . " + SaveUName);
        for (User cn : users) {
            user.append("Id: " + cn.getId() + " ,Name: "
                    + cn.getName() + " ,Phone: " + cn.getEmail() + "\n");
            tvDataBase.setText(user);
        }
        if (SaveUName.equals("a") || SavePassword.equals("a")) {
            Intent intent = new Intent(MainActivity.this, Register.class);

            startActivity(intent);
        }
        btDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               User user = databaseHandler.getUser(SaveUName);
                tvShow.setText("Welcome " + user.getName().toString());
            }
        });
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logMeOut();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            logMeOut();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void logMeOut() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_exit_dialog);
        Button btYes = (Button) dialog.findViewById(R.id.btYes);
        Button btNo = (Button) dialog.findViewById(R.id.btNo);
        Button btExit = (Button) dialog.findViewById(R.id.btExit);
        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("uName", "a");
                editor.putString("pass", "a");
                editor.commit();
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                MainActivity.this.finishAffinity();
            }
        });
        dialog.show();
    }

}
