package com.colman.pawnit.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.colman.pawnit.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity(tableName = "listings_Table")
public class Listing implements Serializable {

    @PrimaryKey
    @NonNull
    private String listingID;

    private String ownerId;//owner id
    private String title;
    private String description;
    private Location location;
    private Date dateOpened;
    private List<String> images = new LinkedList<>();
    private String type;
    private Long lastUpdated;

    final static String ID = "listingID";
    final static String OWNER_ID = "ownerId";
    final static String TITLE = "title";
    final static String DESCRIPTION = "description";
    final static String LOCATION = "location";
    final static String DATE_OPENED = "dateOpened";
    final static String IMAGES = "images";
    final static String TYPE = "type";
    final static String LAST_UPDATED = "lastUpdated";
    final static String LAST_UPDATED_TIME = "ListingLastUpdate";


    public Listing(String ownerId, String title, String description, Location location, Date dateOpened, List<String> images, String type) {
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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setListingID(String listingID) {
        this.listingID = listingID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getListingID() {
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

    public Map<String, Object> getJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, listingID);
        json.put(OWNER_ID, ownerId);
        json.put(TITLE, title);
        json.put(DESCRIPTION, description);
        json.put(LOCATION, location.getLatitude()+","+location.getLongitude());
        json.put(DATE_OPENED, dateOpened);
        json.put(IMAGES, images);
        json.put(TYPE, type);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());

        return json;
    }

    static public Listing createListing(@NonNull Map<String, Object> json) {
        Location location = null;
        if(json.get(LOCATION) != null){
            location = new Location("");
            String[] split = ((String)json.get(LOCATION)).split(",");
            location.setLatitude(Double.parseDouble(split[0]));
            location.setLongitude(Double.parseDouble(split[1]));
        }

        Listing listing = new Listing((String) json.get(OWNER_ID),
                (String) json.get(TITLE),
                (String) json.get(DESCRIPTION),
                location,
                ((Timestamp)json.get(DATE_OPENED)).toDate(),
                (ArrayList<String>)json.get(IMAGES),
                (String) json.get(TYPE));
        listing.setListingID((String)json.get(ID));

        Timestamp ts = (Timestamp)json.get(LAST_UPDATED);
        if(ts != null){
            listing.setLastUpdated(ts.getSeconds());
        }else {
            listing.setLastUpdated((long)0);
        }


        return listing;
    }

    public static void setLocalLastUpdateTime(Long ts){
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(LAST_UPDATED_TIME,ts);
        editor.commit();
    }

    public static Long getLocalLastUpdateTime(){
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong(LAST_UPDATED_TIME,0);
    }
}
