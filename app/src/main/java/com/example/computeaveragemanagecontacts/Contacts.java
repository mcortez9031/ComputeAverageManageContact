package com.example.computeaveragemanagecontacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    ListView lvContacts;
    Button btnAddContact, btnExportContact;

    public static ArrayList<ContInfo> contactList = new ArrayList<>();
    ArrayAdapter<ContInfo> adapter;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvContacts = findViewById(R.id.lvContactDetails);
        btnAddContact = findViewById(R.id.btnAddContact);
        btnExportContact = findViewById(R.id.btnExport);

        builder = new AlertDialog.Builder(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        lvContacts.setAdapter(adapter);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contacts.this, ContactsAdded.class);
                startActivity(intent);
            }
        });

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Contacts.this, ContactDetails.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });

        exportContact();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
    public void exportContact(){
        btnExportContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contactList.isEmpty()) {
                    Toast.makeText(Contacts.this, "Nothing to export!", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuilder data = new StringBuilder();
                data.append("--- CONTACT LIST ---\n\n");

                for (ContInfo c : contactList) {
                    data.append("Name: ").append(c.getName()).append("\n");
                    data.append("Phone: ").append(c.getPhone()).append("\n");
                    data.append("Email: ").append(c.getEmail()).append("\n");
                    data.append("--------------------\n");
                }

                try {
                    java.io.File file = new java.io.File(getExternalFilesDir(null), "contacts.txt");
                    java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
                    fos.write(data.toString().getBytes());
                    fos.close();

                    displayMessage("Export Successful", "File saved to: " + file.getAbsolutePath());
                } catch (Exception e) {
                    displayMessage("Export Failed", "Error: " + e.getMessage());
                }
            }
        });
    }
    public void displayMessage(String title, String message){
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}