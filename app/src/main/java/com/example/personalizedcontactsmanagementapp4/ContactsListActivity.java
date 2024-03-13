package com.example.personalizedcontactsmanagementapp4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedcontactsmanagementapp4.ContactAdapter;
import com.example.personalizedcontactsmanagementapp4.ContactDAO;
import com.example.personalizedcontactsmanagementapp4.ContactModel;

import java.util.List;

public class ContactsListActivity extends AppCompatActivity
        implements ContactAdapter.OnDeleteButtonClickListener, ContactAdapter.OnEditButtonClickListener {

    private RecyclerView recyclerView;
    private Button buttonAddContact;
    private ContactAdapter contactAdapter;
    private ContactDAO contactDAO;

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextBirthday;

    private long editedContactId = -1; // Initialize to -1 when no contact is being edited


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);


        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextBirthday = findViewById(R.id.editTextBirthday);

        recyclerView = findViewById(R.id.recyclerViewContacts);
        buttonAddContact = findViewById(R.id.buttonAddContact);

        // Set click listener for the "Add Contact" button
        buttonAddContact.setOnClickListener(v -> onAddContactClicked());

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(this, this);
        recyclerView.setAdapter(contactAdapter);

        // Initialize ContactDAO
        contactDAO = new ContactDAO(this);

        // Load contacts from the database and display them
        displayContacts();
    }

    // Method to handle "Add Contact" button click
    private void onAddContactClicked() {
        // You can navigate to the AddContactActivity here if needed
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Add Contact button clicked", Toast.LENGTH_SHORT).show();
    }

    // Method to retrieve contacts from the database and display them in the RecyclerView
    private void displayContacts() {
        try {
            // Retrieve contacts from the database
            List<ContactModel> contacts = contactDAO.getAllContacts();

            // Update the RecyclerView adapter with the retrieved contacts
            contactAdapter.setContacts(contacts);
            contactAdapter.updateContactsList(contacts);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading contacts", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle delete button click
    @Override
    public void onDeleteButtonClicked(int position) {
        // Get the contact at the specified position
        ContactModel contactToDelete = contactAdapter.getContacts().get(position);

        // Delete the contact from the database using its ID
        long deletedRows = contactDAO.deleteContact(contactToDelete.getId());

        // Check if the contact was successfully deleted
        if (deletedRows > 0) {
            // Remove the contact from the RecyclerView and update the UI
            contactAdapter.getContacts().remove(position);
            contactAdapter.notifyItemRemoved(position);
            Toast.makeText(this, "Contact deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete contact", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveEditedContact(ContactModel editedContact) {
        try {
            // Update the contact in the database
            int rowsAffected = contactDAO.updateContact(editedContact);

            // Check if the update was successful
            if (rowsAffected > 0) {
                // Notify the user that the contact was updated successfully
                Toast.makeText(this, "Contact updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Notify the user that the update failed
                Toast.makeText(this, "Failed to update contact", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that occur during the update operation
            Toast.makeText(this, "Error updating contact", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveContact(View view) {
        // Retrieve the entered contact details
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phoneNumber = editTextPhone.getText().toString().trim();
        String birthday = editTextBirthday.getText().toString().trim();

        // Validate the input fields (you can add your validation logic here)

        // Create a ContactModel object with the entered details
        ContactModel contact = new ContactModel();
        contact.setId(editedContactId); // Set the ID of the contact being edited
        contact.setName(name);
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setBirthday(birthday);

        // Call the DAO method to add or update the contact in the database
        int rowsAffected = contactDAO.updateContact(contact); // Use updateContact method

        // Check if the contact was successfully added or updated in the database
        if (rowsAffected > 0) {
            Toast.makeText(this, "Contact saved successfully!", Toast.LENGTH_SHORT).show();
            // Clear the input fields
            clearInputFields();
            // Refresh the contact list
            displayContacts();
            // Reset the editedContactId after saving
            editedContactId = -1;
        } else {
            Toast.makeText(this, "Failed to save contact!", Toast.LENGTH_SHORT).show();
        }
    }


    // Method to clear input fields after saving a contact
    private void clearInputFields() {
        editTextName.setText("");
        editTextEmail.setText("");
        editTextPhone.setText("");
        editTextBirthday.setText("");
    }


    // Implement the onEditButtonClicked method
    @Override
    public void onEditButtonClicked(int position) {
        // Handle edit button click here
        ContactModel contactToEdit = contactAdapter.getContacts().get(position);
        editTextName.setText(contactToEdit.getName());
        editTextEmail.setText(contactToEdit.getEmail());
        editTextPhone.setText(contactToEdit.getPhoneNumber());
        editTextBirthday.setText(contactToEdit.getBirthday());
        editedContactId = contactToEdit.getId();
        Toast.makeText(this, "Edit button clicked for position: " + position, Toast.LENGTH_SHORT).show();
    }
}
