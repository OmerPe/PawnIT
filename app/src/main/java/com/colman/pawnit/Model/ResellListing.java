package com.colman.pawnitv2.Model;

import androidx.room.Entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity(tableName = "Resell_table")
public class ResellListing extends Listing {

    private double price;

    final static String PRICE = "price";

    public ResellListing(String owner, String title, String description, String location, Date dateOpened, List<String> images, double price) {
        super(owner, title, description, location, dateOpened, images, "Resell");
        this.price = price;
    }

    public ResellListing() {
        super();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public Map<String, Object> getJson(Listing listing) {
        Map<String, Object> json = super.getJson(listing);
        json.put(PRICE, price);
        return json;
    }

    public static ResellListing create(Map<String, Object> json) {
        Listing listing = Listing.createListing(json);

        return new ResellListing(listing.getOwnerId(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getLocation(),
                listing.getDateOpened(),
                listing.getImages(),
                (double) json.get(PRICE));
    }
}
