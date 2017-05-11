package com.example.arya.registerloginapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//Sanyam Arya
public class Register extends Activity {
    EditText etName, etEmail, etPhone, etPassword;
    Button btSubmit, btLogin;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    SharedPreferences prefs;

    @SuppressLint("NewApi")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Register.this.finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("inUser", MODE_PRIVATE);
        setContentView(R.layout.activity_register);
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPhone = (EditText) findViewById(R.id.etPhone);
        btLogin = (Button) findViewById(R.id.btLogin);
        btSubmit = (Button) findViewById(R.id.btSubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputValidity()) {
                    User user = new User();
                    user.setName(etName.getText().toString());
                    user.setEmail(etEmail.getText().toString());
                    user.setPassword(etPassword.getText().toString());
                    databaseHandler.addUser(user);
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Register.this);
                dialog.setContentView(R.layout.login_dialog);
                final EditText etEmail = (EditText) dialog.findViewById(R.id.etEmailLogin);
                final EditText etPassword = (EditText) dialog.findViewById(R.id.etPasswordLogin);
                Button btLogin = (Button) dialog.findViewById(R.id.btLoginLogin);
                dialog.setTitle("Enter you data");
                btLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (databaseHandler.checkUser(etEmail.getText().toString().trim(),
                                etPassword.getText().toString().trim())) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("uName", etEmail.getText().toString());
                            editor.putString("pass", etPassword.getText().toString());
                            editor.commit();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Register.this, "Wrong Credential",
                                    Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private boolean checkInputValidity() {
        String name, email, phone, password;
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            Toast.makeText(Register.this, "No field can be left empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(Register.this, "email not valid", Toast.LENGTH_SHORT).show();
            etEmail.setText("");
            etEmail.setHint("Invalid Email");
            etEmail.setHintTextColor(Color.parseColor("#FF0000"));
            return false;
        } else {
            return true;
        }
    }
}
