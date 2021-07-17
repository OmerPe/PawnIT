package com.colman.pawnit.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "listings_Table")
public class Listing {

    @PrimaryKey(autoGenerate = true)
    private int listingID;

    private String ownerId;//owner id
    private String title;
    private String description;
    private String location;
    private Date dateOpened;
    private List<String> images = new LinkedList<>();


    public Listing(String ownerId, String title, String description, String location, Date dateOpened, List<String> images) {
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.dateOpened = dateOpened;
        this.images = images;
        this.location = location;
    }

    public Listing(){

    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getListingID() {
        return listingID;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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
