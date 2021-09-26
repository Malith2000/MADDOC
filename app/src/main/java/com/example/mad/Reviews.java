package com.example.mad;

public class Reviews {
    String Name,Email,Date,Message;

    public Reviews(){

    }



    public void setEmail(String email) {
        Email = email;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setName(String name) { Name = name; }

    public Reviews(String name, String email, String date, String message) {
        Name = name;
        Email = email;
        Date = date;
        Message = message;
    }


    public String getName() { return Name; }

    public String getEmail() {
        return Email;
    }

    public String getDate() {
        return Date;
    }

    public String getMessage() {
        return Message;
    }

}
