package com.example.BatchProcess.model;

public class User {
    private int userId;
    private String userName;
    private String userAdd;
    private String userContact;

    
  

    public User() {
    }

    public User(int userId, String userName, String userAdd, String userContact) {
        this.userId = userId;
        this.userName = userName;
        this.userAdd = userAdd;
        this.userContact = userContact;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAdd() {
        return userAdd;
    }

    public void setUserAdd(String userAdd) {
        this.userAdd = userAdd;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", userAdd=" + userAdd + ", userContact="
                + userContact + "]";
    }

    
    
}
