package com.example.ocs.Models;

public class FeedbackModel {
    public String compliant_id;
    public String email;
    public String feedback;
    public String name;
    public String user;

    public FeedbackModel() {
    }

    public FeedbackModel(String name2, String user2, String compliant_id2, String email2, String feedback2) {
        this.name = name2;
        this.user = user2;
        this.compliant_id = compliant_id2;
        this.email = email2;
        this.feedback = feedback2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getCompliant_id() {
        return this.compliant_id;
    }

    public void setCompliant_id(String compliant_id2) {
        this.compliant_id = compliant_id2;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public void setFeedback(String feedback2) {
        this.feedback = feedback2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user2) {
        this.user = user2;
    }

    public String toString() {
        return "FeedbackModel{name='" + this.name + '\'' + ", compliant_id='" + this.compliant_id + '\'' + ", feedback='" + this.feedback + '\'' + ", email='" + this.email + '\'' + ", user='" + this.user + '\'' + '}';
    }
}
