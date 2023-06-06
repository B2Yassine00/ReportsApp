package com.example.reportsapp;

public class Report {

    private String reportId;
    private String userOwner;
    private String imageUrl;
    private String comment;
    private String address;
    private double latitude;
    private double longitude;

    private String created_on;

    public Report() {
    }

    public Report(String reportId, String userOwner, String imageUrl, String comment, String address, double latitude, double longitude,String created_on) {
        this.reportId = reportId;
        this.userOwner = userOwner;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_on = created_on;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(String userOwner) {
        this.userOwner = userOwner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

}
