package com.mycontacts_2020_1_60_132;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.Manifest;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ContactFormActivity extends AppCompatActivity {
    ImageView Photo;
    EditText name,email,phonehome,phoneoffice;
    Button Save,Cancel;
    String ImageString;
    String id="";
    String Username="";
    String Useremail="";
    String home="";
    String office="";
    long ms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);
        Photo=findViewById(R.id.photo);
        name=findViewById(R.id.nametext);
        email=findViewById(R.id.emailtext);
        phonehome=findViewById(R.id.hometext);
        phoneoffice=findViewById(R.id.officetext);
        Save=findViewById(R.id.btnsave);
        Cancel=findViewById(R.id.btncancel);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Username =name.getText().toString();
              Useremail =email.getText().toString();
              home=phonehome.getText().toString();

                if(isValidName(Username) && isValidEmail(Useremail)&&isValidPhone(home)){
                    ms=System.currentTimeMillis();
                    id=Username+ms;
                    office=phoneoffice.getText().toString();
                    MyContactsDB db= new MyContactsDB(ContactFormActivity.this);
                    db.insertContact(id,Username,Useremail,home,office,ImageString);
                    Toast.makeText(ContactFormActivity.this, "New Contact Added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ContactFormActivity.this,HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(ContactFormActivity.this, "Please Provide Valid Inputs", Toast.LENGTH_SHORT).show();
                }


            }
        });
        Photo.setOnClickListener(view -> checkPermission());
            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ContactFormActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            });
    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(ContactFormActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactFormActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
        } else {
            choosePhoto();
        }
    }
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Image"), 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImg = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImg);
                    Photo.setImageBitmap(bitmap);
                    ImageString = ImageToBase64(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private String ImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream Encode = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, Encode);
        byte[] imageBytes = Encode.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
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

