package com.example.contact;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private int favorite = 0;
    private int blocked = 0;

    public Contact() {}

    public Contact(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact(int id, String name, String phoneNumber, String email) {
        this(id, name, phoneNumber);
        this.email = email;
    }

    public Contact(String name, String phoneNumber, String email) {
        this(name, phoneNumber);
        this.email = email;
    }

    public Contact(int id, String name, String phoneNumber, String email, int favorite, int blocked) {
        this(id, name, phoneNumber, email);
        this.favorite = favorite;
        this.blocked = blocked;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getFavorite() { return favorite; }
    public void setFavorite(int favorite) { this.favorite = favorite; }

    public int getBlocked() { return blocked; }
    public void setBlocked(int blocked) { this.blocked = blocked; }

    @Override
    public String toString() {
        return id + ": " + name + " - " + phoneNumber;
    }
}
