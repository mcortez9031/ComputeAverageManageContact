package com.example.computeaveragemanagecontacts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactsAdded extends AppCompatActivity {

    EditText etName, etPhone, etEmail, etAge;
    Button btnSubmit, btnBack;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts_added);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etContactName);
        etPhone = findViewById(R.id.etContactNumber);
        etEmail = findViewById(R.id.etEmail);
        etAge = findViewById(R.id.etAge);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);

        builder = new AlertDialog.Builder(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitContact();
    }
    public void submitContact(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String ageStr = etAge.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || ageStr.isEmpty()) {
                    displayMessage("Input Error", "Please fill all fields");
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    displayMessage("Invalid Email", "Please enter a valid email address.");
                    return;
                }

                try {
                    int age = Integer.parseInt(ageStr);

                    if (age < 0 || age > 120) {
                        displayMessage("Invalid Age", "Please enter a realistic age (0-120).");
                        return;
                    }

                    ContInfo newContact = new ContInfo(name, phone, email, age);
                    Contacts.contactList.add(newContact);

                    Toast.makeText(ContactsAdded.this, "Contact Saved Successfully!", Toast.LENGTH_SHORT).show();

                    finish();

                } catch (NumberFormatException e) {
                    displayMessage("Format Error", "Age must be a whole number.");
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