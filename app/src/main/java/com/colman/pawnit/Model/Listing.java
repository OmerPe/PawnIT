package com.colman.pawnit.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "listing_table")
public class Listing {

    @PrimaryKey(autoGenerate = true)
    private String listingID;

    private String owner;//owner id
    private String title;
    private String description;
    private String location;
    private Date dateOpened;
    private List<String> images = new LinkedList<>();


    public Listing(String owner, String title, String description, String location, Date dateOpened, List<String> images) {
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.location = location;
        this.dateOpened = dateOpened;
        this.images = images;
    }

    public Listing(){

    }

    public void setListingID(String listingID) {
        this.listingID = listingID;
    }

    public String getListingID() {
        return listingID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
