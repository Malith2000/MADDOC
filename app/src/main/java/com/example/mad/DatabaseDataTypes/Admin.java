package com.example.mad.DatabaseDataTypes;

/**
 * This POJO class is ued to parse the users when retrieving from the database.
 */
public class Admin {
    private  String Email;
    private  String Password;

    private Admin(){}

    public Admin(String email, String password) {
        this.Email = email;
        this.Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
