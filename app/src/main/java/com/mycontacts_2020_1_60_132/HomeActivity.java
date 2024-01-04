package com.mycontacts_2020_1_60_132;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    LinearLayout ConList;
    TextView name, phone;
    ImageView img, Home, addcon,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ConList = findViewById(R.id.ConList);
        addcon = findViewById(R.id.addcontact);
        Home = findViewById(R.id.home);
        logout=findViewById(R.id.logout);
        LoadData();
        addcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactFormActivity.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void LoadData() {
        MyContactsDB db = new MyContactsDB(this);
        Cursor cur = db.displayContact("SELECT * FROM MyContacts");
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(0);
                String Name = cur.getString(1);
                String Email = cur.getString(2);
                String Homephone = cur.getString(3);
                String Officephone = cur.getString(4);
                String Photo = cur.getString(5);
                Bitmap decodedPhoto = Base64ToImage(Photo);
                View ContactCard = getLayoutInflater().inflate(R.layout.contactlist, null);
                name = ContactCard.findViewById(R.id.NameTextview);
                phone = ContactCard.findViewById(R.id.PhoneTextview);
                img = ContactCard.findViewById(R.id.imageView);
                img.setImageBitmap(decodedPhoto);
                name.setText(Name);
                phone.setText(Homephone);
                ConList.addView(ContactCard);
                ContactCard.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DeleteDialog(HomeActivity.this, id);
                        return true;
                    }
                });
                ContactCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(HomeActivity.this, ContactDetails.class);
                        Bundle b = new Bundle();
                        b.putString("id", id);
                        b.putString("name", Name);
                        b.putString("email",Email);
                        b.putString("homephone", Homephone);
                        b.putString("officephone",Officephone);
                        i.putExtras(b);
                        startActivity(i);
                    }
                });
            }
        }
        cur.close();
        db.close();
    }

    private Bitmap Base64ToImage(String base64String) {
        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    public void DeleteDialog(final Context context, String id) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.deletedialog);
        Button Remove = dialog.findViewById(R.id.remove);
        if (Remove != null) {
            Remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyContactsDB db = new MyContactsDB(context);
                    db.deleteContact(id);
                    dialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Contact Removed Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, HomeActivity.class);
                    context.startActivity(i);
                    ((Activity) context).finish();
                }
            });
        } else {
            System.out.println("Remove is null");//for debugging
        }
        dialog.show();
    }

}