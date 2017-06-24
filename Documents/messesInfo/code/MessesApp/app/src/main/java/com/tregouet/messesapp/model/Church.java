package com.tregouet.messesapp.model;

import java.util.ArrayList;

/**
 * Created by mariececile.tregouet on 18/06/2017.
 */
public class Church {

    private int id;
    private String name;
    private String image;
    private String address;
    private String zipcode;
    private String city;
    private float latitude;
    private float longitude;
    private String phone;
    private String mail;
    private String website;
    private String facebook;
    private Activity activity;
    private ArrayList<Schedule> events;
    private ArrayList<Schedule> groups;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Schedule> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Schedule> events) {
        this.events = events;
    }

    public ArrayList<Schedule> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Schedule> groups) {
        this.groups = groups;
    }
}
