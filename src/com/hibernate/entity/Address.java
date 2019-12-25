package com.hibernate.entity;

public class Address {

    private int id;
    
    private String addressName;

    private User user;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", addressName=" + addressName + "]";
    }
    
}
