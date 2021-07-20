package com.colman.pawnitv2.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity(tableName = "listings_Table")
public class Listing implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int listingID;

    private String ownerId;//owner id
    private String title;
    private String description;
    private String location;
    private Date dateOpened;
    private List<String> images = new LinkedList<>();
    private String type;

    final static String ID = "listingID";
    final static String OWNER_ID = "ownerId";
    final static String TITLE = "title";
    final static String DESCRIPTION = "description";
    final static String LOCATION = "location";
    final static String DATE_OPENED = "dateOpened";
    final static String IMAGES = "images";
    final static String TYPE = "type";


    public Listing(String ownerId, String title, String description, String location, Date dateOpened, List<String> images, String type) {
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.dateOpened = dateOpened;
        this.images = images;
        this.location = location;
        this.type = type;
    }

    public Listing() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Map<String, Object> getJson(Listing listing) {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, listingID);
        json.put(OWNER_ID, ownerId);
        json.put(TITLE, title);
        json.put(DESCRIPTION, description);
        json.put(LOCATION, location);
        json.put(DATE_OPENED, dateOpened.getTime());
        json.put(IMAGES, images.toString().substring(1, images.size() - 1));
        json.put(TYPE, type);

        return json;
    }

    static public Listing createListing(@NonNull Map<String, Object> json) {
        Listing listing = new Listing((String) json.get(OWNER_ID),
                (String) json.get(TITLE),
                (String) json.get(DESCRIPTION),
                (String) json.get(LOCATION), new Date((long) json.get(DATE_OPENED)),
                new ArrayList<String>(Arrays.asList(((String) json.get(IMAGES)).split(","))),
                (String) json.get(TYPE));
        listing.setListingID((int) json.get(ID));
        return listing;
    }
}