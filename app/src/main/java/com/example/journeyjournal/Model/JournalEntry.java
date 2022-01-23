package com.example.journeyjournal.Model;

public class JournalEntry {
    private String title, description,body, coordinates,imageUrl,date ;


    public JournalEntry(String title, String description, String body, String coordinates, String imageUrl, String date) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.coordinates = coordinates;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public JournalEntry(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

