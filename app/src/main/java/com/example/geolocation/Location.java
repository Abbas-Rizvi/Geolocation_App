package com.example.geolocation;

public class Location {



    // local variables
    private int id;
    private float latitude;
    private float longitude;

    private String address;


    // constructors
    // ---------------------------
    public Location(int id, Float latitude, Float longitude, String address){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;

    }

    public Location(Float latitude, Float longitude, String address){
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;

    }



    // getter and setters
    // ---------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
