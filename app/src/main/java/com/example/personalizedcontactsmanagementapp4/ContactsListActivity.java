package com.example.personalizedcontactsmanagementapp4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

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

    // Implement the onEditButtonClicked method
    @Override
    public void onEditButtonClicked(int position) {
        // Handle edit button click here
        Toast.makeText(this, "Edit button clicked for position: " + position, Toast.LENGTH_SHORT).show();
    }
}
