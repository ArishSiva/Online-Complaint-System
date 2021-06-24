package com.example.ocs.Models;

import com.google.firebase.Timestamp;

public class Note {
    public String compliant;
    public String compliantLevel;
    public String compliantType;
    public String compliant_id;
    public Timestamp created;
    public String email;
    public String imageDownUrl;
    public String location;
    public String mobileNumber;
    public String name;
    public String status;
    public String street;
    public String user_id;

    public Note() {
    }

    public Note(String name2, String email2, String mobileNumber2, String street2, String location2, String compliantType2, String compliantLevel2, String compliant2, Timestamp created2, String user_id2, String imageDownUrl2, String compliant_id2, String status2) {
        this.name = name2;
        this.email = email2;
        this.mobileNumber = mobileNumber2;
        this.street = street2;
        this.location = location2;
        this.compliantType = compliantType2;
        this.compliantLevel = compliantLevel2;
        this.compliant = compliant2;
        this.created = created2;
        this.user_id = user_id2;
        this.imageDownUrl = imageDownUrl2;
        this.compliant_id = compliant_id2;
        this.status = status2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber2) {
        this.mobileNumber = mobileNumber2;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street2) {
        this.street = street2;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location2) {
        this.location = location2;
    }

    public String getCompliantType() {
        return this.compliantType;
    }

    public void setCompliantType(String compliantType2) {
        this.compliantType = compliantType2;
    }

    public String getCompliantLevel() {
        return this.compliantLevel;
    }

    public void setCompliantLevel(String compliantLevel2) {
        this.compliantLevel = compliantLevel2;
    }

    public String getCompliant() {
        return this.compliant;
    }

    public void setCompliant(String compliant2) {
        this.compliant = compliant2;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created2) {
        this.created = created2;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id2) {
        this.user_id = user_id2;
    }

    public String getImageDownUrl() {
        return this.imageDownUrl;
    }

    public void setImageDownUrl(String imageDownUrl2) {
        this.imageDownUrl = imageDownUrl2;
    }

    public String getCompliant_id() {
        return this.compliant_id;
    }

    public void setCompliant_id(String compliant_id2) {
        this.compliant_id = compliant_id2;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public String toString() {
        return "Note{name='" + this.name + '\'' + ", email='" + this.email + '\'' + ", mobileNumber='" + this.mobileNumber + '\'' + ", street='" + this.street + '\'' + ", location='" + this.location + '\'' + ", compliantType='" + this.compliantType + '\'' + ", compliantLevel='" + this.compliantLevel + '\'' + ", compliant='" + this.compliant + '\'' + ", created=" + this.created + ", user_id='" + this.user_id + '\'' + ", imageDownUrl='" + this.imageDownUrl + '\'' + ", status='" + this.status + '\'' + ", compliant_id='" + this.compliant_id + '\'' + '}';
    }
}
