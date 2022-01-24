package com.example.journeyjournal.Model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class JournalEntry implements Parcelable {


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

    //parcelable methods
    protected JournalEntry(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        imageUrl = in.readString();
        imagePath = in.readString();
        location = in.readString();
        description = in.readString();
        body = in.readString();
        date = in.readString();
    }

    public static final Creator<JournalEntry> CREATOR = new Creator<JournalEntry>() {
        @Override
        public JournalEntry createFromParcel(Parcel in) {
            return new JournalEntry(in);
        }

        @Override
        public JournalEntry[] newArray(int size) {
            return new JournalEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeString(imageUrl);
        dest.writeString(imagePath);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(body);
        dest.writeString(date);
    }



    //getters and setters
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

