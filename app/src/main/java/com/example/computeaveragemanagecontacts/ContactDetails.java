package com.example.computeaveragemanagecontacts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactDetails extends AppCompatActivity {

    TextView tvInfo;
    ImageView ivCall, ivDelete;
    Button back;
    int index;
    AlertDialog.Builder builder;
    ContInfo currentContact;
    ArrayAdapter<ContInfo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvInfo = findViewById(R.id.tvContactDetails);
        ivCall = findViewById(R.id.imgvCall);
        ivDelete = findViewById(R.id.imgvDelete);
        back = findViewById(R.id.btnBack);

        builder = new AlertDialog.Builder(this);

        index = getIntent().getIntExtra("index", -1);
        currentContact = Contacts.contactList.get(index);

        tvInfo.setText("Name: " + currentContact.getName() +
                "\nPhone: " + currentContact.getPhone() +
                "\nEmail: " + currentContact.getEmail() +
                "\nAge: " + currentContact.getAge());

        callAction();

        deleteContact();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void callAction() {
        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Make a Call")
                        .setMessage("Do you want to call " + currentContact.getName() + "?")
                        .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ContactDetails.this, "Calling " + currentContact.getPhone() + "...", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    public void deleteContact() {
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm Delete").setMessage("Are you sure you want to delete " + currentContact.getName() + "?")
                        .setCancelable(false).setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Contacts.contactList.remove(index);
                                Toast.makeText(ContactDetails.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // This tells the ListView to refresh because a contact might have been deleted
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}