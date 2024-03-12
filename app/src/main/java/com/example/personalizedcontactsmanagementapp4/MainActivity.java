package com.example.personalizedcontactsmanagementapp4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the save button by its ID
        Button saveButton = findViewById(R.id.buttonSave);

        // Set OnClickListener to handle button clicks
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click action here
                saveContact(v); // Call your method to handle the click action
            }
        });
    }


    // Method to handle button click and navigate to ContactsListActivity
    public void showContactsList(View view) {
        try {
            Intent intent = new Intent(this, ContactsListActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Error navigating to ContactsListActivity: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showDatePicker(View view) {
        try {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        EditText editTextBirthday = findViewById(R.id.editTextBirthday);
                        editTextBirthday.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                    }, year, month, day);
            datePickerDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "Error showing date picker dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveContact(View view) {
        // Retrieve the entered contact details
        EditText editTextName = findViewById(R.id.editTextName);
        String name = editTextName.getText().toString().trim();

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        String email = editTextEmail.getText().toString().trim();

        EditText editTextPhone = findViewById(R.id.editTextPhone);
        String phone = editTextPhone.getText().toString().trim();

        EditText editTextBirthday = findViewById(R.id.editTextBirthday);
        String birthday = editTextBirthday.getText().toString().trim();

        // Validate the input fields (you can add your validation logic here)

        // Create a ContactModel object with the entered details
        ContactModel contact = new ContactModel();
        contact.setName(name);
        contact.setEmail(email);
        contact.setPhoneNumber(phone);
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


  /*  // Method to handle save button click
    public void onSaveButtonClick(View view) {
        try {
            // Get entered values
            EditText editTextName = findViewById(R.id.editTextName);
            String name = editTextName.getText().toString().trim();

            EditText editTextEmail = findViewById(R.id.editTextEmail);
            String email = editTextEmail.getText().toString().trim();

            EditText editTextPhone = findViewById(R.id.editTextPhone);
            String phone = editTextPhone.getText().toString().trim();

            EditText editTextBirthday = findViewById(R.id.editTextBirthday);
            String birthday = editTextBirthday.getText().toString().trim();

            // Perform validation and save contact information
            // For demonstration purposes, we'll just display a toast message with the entered values
            String message = "Name: " + name + "\n" +
                    "Email: " + email + "\n" +
                    "Phone: " + phone + "\n" +
                    "Birthday: " + birthday;

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Error saving contact information: " + e.getMessage());
            e.printStackTrace();
        }
    }*/
}
