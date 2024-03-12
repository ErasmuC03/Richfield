package com.example.personalizedcontactsmanagementapp4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhoneNumber;
    private EditText editTextBirthday;
    private Button buttonSave;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhone);
        editTextBirthday = findViewById(R.id.editTextBirthday);
        buttonSave = findViewById(R.id.buttonSave);

        // Set click listener for the save button
        buttonSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        // Retrieve the entered contact details
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String birthday = editTextBirthday.getText().toString().trim();

        // Validate the input fields (you can add your validation logic here)

        // Create a ContactModel object with the entered details
        ContactModel contact = new ContactModel();
        contact.setName(name);
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setBirthday(birthday);

        // Call the DAO method to add the contact to the database
        ContactDAO contactDAO = new ContactDAO(this);
        long newRowId = contactDAO.addContact(contact);

        // Check if the contact was successfully added to the database
        if (newRowId != -1) {
            Toast.makeText(this, "Contact added successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add contact!", Toast.LENGTH_SHORT).show();
        }
    }
}
