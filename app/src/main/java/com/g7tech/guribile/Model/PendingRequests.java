package com.g7tech.guribile.Model;

public class PendingRequests {

    private int id;
    private String title;
    private String message;
    private String status;
    private String latitude;
    private String longitude;
    private String created_at;

    public PendingRequests(int id, String title, String message, String status, String latitude, String longitude, String created_at) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage()   {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCreated_at() {
        return created_at;
    }
}