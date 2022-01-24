package com.example.journeyjournal.Model;

public class JournalEntry {


    String title, publisher, imageUrl, imagePath, location, description, body, date;


    public JournalEntry(String title, String publisher, String imageUrl, String imagePath, String location, String description, String body, String date) {
        this.title = title;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
        this.location = location;
        this.description = description;
        this.body = body;
        this.date = date;
    }


    public JournalEntry() {
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    @Override
    public String toString() {
        return "JournalEntry{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

