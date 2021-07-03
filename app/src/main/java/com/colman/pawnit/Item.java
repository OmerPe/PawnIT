package com.colman.pawnit;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName="item_tablev2")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String ownerId;

    private String description;

    private double price;

    private String date;
    @Ignore
    private List<String> images;

    public Item() {
    }

    public Item(String title, String ownerId, String description, double price, String date, List<String> images) {
        this.title = title;
        this.ownerId = ownerId;
        this.description = description;
        this.price=price;
        this.date = date;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
    public double getPrice(){
        return price;
    }
    public List<String> getImages() {
        return images;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
