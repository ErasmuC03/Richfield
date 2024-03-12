package com.example.personalizedcontactsmanagementapp4;

public class ContactModel {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String birthday;

    public ContactModel() {
        // Default constructor
    }

    public ContactModel(String name, String email, String phoneNumber, String birthday) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    // Getter and setter for ID
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter and setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for Phone Number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter and setter for Birthday
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
