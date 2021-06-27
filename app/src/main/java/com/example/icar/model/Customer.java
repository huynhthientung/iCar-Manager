package com.example.icar.model;

public class Customer {
    public String uid;
    public String full_name;
    public String email;
    public String phone;
    public String address;
    public String birthday;
    public boolean gender;

    public Customer(String uid, String full_name, String email, String phone, String address, String birthday, boolean gender) {
        this.uid = uid;
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "uid='" + uid + '\'' +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender=" + gender +
                '}';
    }

    public Customer() {
    }
}
