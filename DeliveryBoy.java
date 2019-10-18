package com.example.pitneybowes.Classes;

import java.util.HashMap;

public class DeliveryBoy {
    public String name;
    public String address;
    public String phoneNumber;
    public String emailId;
    public String age;
    public String joiningDate;
    public String Id;
    public String avgRating;
    public HashMap<String,String> deliveryGiven;//list of delivery id given to delivery boy upto the present time ,Date and time
    public DeliveryBoy() {
    }

    public DeliveryBoy(String name, String address, String phoneNumber, String emailId, String age, String joiningDate, String id, String avgRating, HashMap<String, String> deliveryGiven) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.age = age;
        this.joiningDate = joiningDate;
        Id = id;
        this.avgRating = avgRating;
        this.deliveryGiven = deliveryGiven;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public HashMap<String, String> getDeliveryGiven() {
        return deliveryGiven;
    }

    public void setDeliveryGiven(HashMap<String, String> deliveryGiven) {
        this.deliveryGiven = deliveryGiven;
    }
}
