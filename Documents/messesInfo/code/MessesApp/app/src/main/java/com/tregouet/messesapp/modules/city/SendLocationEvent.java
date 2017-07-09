package com.tregouet.messesapp.modules.city;

/**
 * Created by mariececile.tregouet on 25/06/2017.
 */
public class SendLocationEvent {

    private double latitude;
    private double longitude;
    private String zipcode;
    private String address;
    private String city;
    private String country;

    public SendLocationEvent(double latitude, double longitude, String zipcode, String address, String city, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipcode = zipcode;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
