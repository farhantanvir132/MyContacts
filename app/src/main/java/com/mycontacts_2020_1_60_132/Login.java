package com.mycontacts_2020_1_60_132;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText password,email;

    Button Save;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        Save=findViewById(R.id.loginbtn);
        signup=findViewById(R.id.signup);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("MyContacts", MODE_PRIVATE);
                String Useremail=sp.getString("email","");
                String Password=sp.getString("password","");
                if(!sp.contains("email") && !sp.contains("password")){
                    Toast.makeText(Login.this,"You do not have any account Signup!",Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().toString().isEmpty()||
                        password.getText().toString().length()<5||
                        !password.getText().toString().equals(Password) || !email.getText().toString().equals(Useremail)

                )
                {
                    Toast.makeText(Login.this,"Please Provide Valid Inputs",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(Login.this,HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
      signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUpContact.class);
                startActivity(i);
                finish();
            }
        });
    }
}