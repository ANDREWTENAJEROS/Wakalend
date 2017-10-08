package com.heroku.andrewtenajeros.wakalend;

public class Client {

    public String barangay;
    public String ClientID;
    public String firstname;
    public String lastname;
    public String district;
    public String loan ;
    public String balance;
    public String daystopay;


    public Client() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Client(String id, String firstname, String lastname, String barangay, String district, String loan, String days) {
        this.ClientID = id;
        this.barangay = barangay;
        this.firstname = firstname;
        this.lastname = lastname;
        this.district = district;
        this.loan = loan;
        this.balance = loan;
        this.daystopay = days;
    }

    public String getBarangay() {
        return barangay;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDistrict() {
        return district;
    }

    public String getLoan() {
        return loan;
    }

    public String getId() {
        return ClientID;
    }

    public String getBalance() {
        return balance;
    }
}