package com.colman.pawnit.Model;

import android.location.Location;

import androidx.room.Entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity(tableName = "Resell_table")
public class ResellListing extends Listing {

    private double price;

    final static String PRICE = "price";
    final static String TYPE = "Resell";

    public ResellListing(String owner, String title, String description, Location location, Date dateOpened, List<String> images, double price) {
        super(owner, title, description, location, dateOpened, images, TYPE);
        this.price = price;
    }

    public ResellListing() {
        super();
        super.setType(TYPE);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public Map<String, Object> getJson() {
        Map<String, Object> json = super.getJson();
        json.put(PRICE, price);
        return json;
    }

    public static ResellListing create(Map<String, Object> json) {
        Listing listing = Listing.createListing(json);
        ResellListing tl = new ResellListing(listing.getOwnerId(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getLocation(),
                listing.getDateOpened(),
                listing.getImages(),
                (double)json.get(PRICE));
        tl.setListingID(listing.getListingID());
        return tl;
    }
}
