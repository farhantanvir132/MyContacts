package com.mycontacts_2020_1_60_132;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpContact extends AppCompatActivity {
    EditText name, email, phone, password, confrimpassword;
    TextView login;
    Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_contact);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confrimpassword = findViewById(R.id.conpass);
        Save = findViewById(R.id.signupbtn);
        login = findViewById(R.id.login);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("MyContacts", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                if(sp.contains("email") && sp.contains("password")){
                    Toast.makeText(SignUpContact.this,"You already have an account Login!",Toast.LENGTH_SHORT).show();
                }
                else if (name.getText().toString().isEmpty() || name.getText().toString().length() < 4
                        || email.getText().toString().isEmpty() || !isValidEmail(email.getText().toString()) ||
                        phone.getText().toString().isEmpty() || phone.getText().toString().length() < 11
                        || password.getText().toString().length() < 5
                ) {
                    Toast.makeText(SignUpContact.this, "Please Provide Valid Inputs", Toast.LENGTH_SHORT).show();
                } else {
                    String existingUsernames = "";
                    existingUsernames += name.getText().toString();
                    String Email = email.getText().toString();
                    String Phone = phone.getText().toString();
                    String Password = password.getText().toString();
                    e.putString("name", existingUsernames);
                    e.putString("email", Email);
                    e.putString("phone", Phone);
                    e.putString("password", Password);
                    e.apply();
                    Intent i = new Intent(SignUpContact.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
          login.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent i = new Intent(SignUpContact.this, Login.class);
                  startActivity(i);
                  finish();
              }
          });
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String ep = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        Pattern pattern = Pattern.compile(ep);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

