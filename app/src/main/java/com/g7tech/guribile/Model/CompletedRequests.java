package com.g7tech.guribile.Model;

public class CompletedRequests {

    private int id;
    private String title;
    private String message;
    private String status;
    private String created_at;

    public CompletedRequests(int id, String title, String message, String status, String created_at) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.status = status;
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

    public String getCreated_at() {
        return created_at;
    }
}
