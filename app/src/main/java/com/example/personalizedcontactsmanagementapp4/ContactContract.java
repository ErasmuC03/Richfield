package com.example.personalizedcontactsmanagementapp4;

public class ContactContract {
    private ContactContract() {} // Private constructor to prevent instantiation

    public static class ContactEntry {
        public static final String TABLE_NAME = "Contacts";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_BIRTHDAY = "birthday";
    }
}