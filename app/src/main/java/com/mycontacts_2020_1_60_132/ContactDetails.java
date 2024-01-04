package com.mycontacts_2020_1_60_132;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactDetails extends AppCompatActivity {
    EditText name,email,phonehome,phoneoffice;
    Button Update,Cancel;
    String id="";
    String Username="";
    String Useremail="";
    String home="";
    String office="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        name=findViewById(R.id.nametext);
        email=findViewById(R.id.emailtext);
        phonehome=findViewById(R.id.hometext);
        phoneoffice=findViewById(R.id.officetext);
        Update=findViewById(R.id.btnupdate);
        Cancel=findViewById(R.id.btncancel);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = extras.getString("id");
        Username = extras.getString("name");
        Useremail = extras.getString("email");
        home= extras.getString("homephone");
        office=extras.getString("officephone");
        name.setText(Username);
        email.setText(Useremail);
        phonehome.setText(home);
        phoneoffice.setText(office);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name =name.getText().toString();
                String Email =email.getText().toString();
                String Home=phonehome.getText().toString();
                String Office=phoneoffice.getText().toString();

                if(isValidName(Username) && isValidEmail(Useremail)&&isValidPhone(home)){
                    office=phoneoffice.getText().toString();
                    MyContactsDB db= new MyContactsDB(ContactDetails.this);
                    db.updateContact(id,Name,Email,Home,Office);
                    Toast.makeText(ContactDetails.this, "Contact Updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ContactDetails.this,HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(ContactDetails.this, "Please Provide Valid Inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactDetails.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private boolean isValidName(String Name) {
        return !Name.isEmpty() && Name.matches("[a-zA-Z ]+");
    }

    private boolean isValidEmail(String Email) {
        return !Email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }
    private boolean isValidPhone(String Phone) {
        return !Phone.isEmpty() && Phone.matches("[0-9]+") && Phone.length() == 11;
    }
}