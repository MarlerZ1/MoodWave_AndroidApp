package com.example.moodwave.data.models.Requests;

public class RegistrationRequest {
    private String first_name;
    private String last_name;
    private String username;
    private  String email;
    private String password;

    public RegistrationRequest(String firstName, String lastName, String username, String email, String password) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }
}
