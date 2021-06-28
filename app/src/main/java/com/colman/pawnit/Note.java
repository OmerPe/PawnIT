package com.colman.pawnit;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.colman.pawnit.Model.User;

import java.net.PasswordAuthentication;
import java.util.Date;
import java.util.List;

@Entity(tableName= "item_table")
public class Note {

    @PrimaryKey(autoGenerate= true)
    private int id;

    private String title;

    private String description;

    private User owner;

    private String location;

    private double price;

    private Date date;

    private List<String> images;

    public Note(String title, String description, User owner, String location, double price, Date date, List<String> images) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.location = location;
        this.price = price;
        this.date = date;
        this.images = images;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getImages() {
        return images;
    }

    public User getOwner() {
        return owner;
    }
}
