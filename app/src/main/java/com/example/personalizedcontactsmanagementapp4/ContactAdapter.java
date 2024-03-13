package com.example.personalizedcontactsmanagementapp4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedcontactsmanagementapp4.ContactModel;
import com.example.personalizedcontactsmanagementapp4.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactModel> contacts = new ArrayList<>();
    private OnDeleteButtonClickListener deleteButtonClickListener;
    private OnEditButtonClickListener editButtonClickListener;

    public ContactAdapter(ContactsListActivity deleteListener) {
    }

    public void updateContactsList(List<ContactModel> updatedContacts) {
        this.contacts = updatedContacts;
        notifyDataSetChanged(); // Notify adapter that dataset has changed
    }


    // Interface for delete button click listener
    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClicked(int position);
    }

    // Interface for edit button click listener
    public interface OnEditButtonClickListener {
        void onEditButtonClicked(int position);
    }

    // Constructor to set the button click listeners
    public ContactAdapter(OnDeleteButtonClickListener deleteListener, OnEditButtonClickListener editListener) {
        this.deleteButtonClickListener = deleteListener;
        this.editButtonClickListener = editListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactModel contact = contacts.get(position);
        holder.textViewName.setText(contact.getName());
        holder.textViewEmail.setText(contact.getEmail());
        holder.textViewPhoneNumber.setText(contact.getPhoneNumber());
        holder.textViewBirthday.setText(contact.getBirthday());

        // Set click listener for delete button
        holder.buttonDelete.setOnClickListener(v -> {
            if (deleteButtonClickListener != null) {
                deleteButtonClickListener.onDeleteButtonClicked(position);
            }
        });

        // Set click listener for edit button
        holder.buttonEdit.setOnClickListener(v -> {
            if (editButtonClickListener != null) {
                editButtonClickListener.onEditButtonClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<ContactModel> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public List<ContactModel> getContacts() {
        return contacts;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        public View buttonDelete, buttonEdit;
        TextView textViewName, textViewEmail, textViewPhoneNumber, textViewBirthday;

        public ContactViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            textViewBirthday = itemView.findViewById(R.id.textViewBirthday);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
        }
    }
}
