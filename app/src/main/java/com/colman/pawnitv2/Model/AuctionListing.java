package com.colman.pawnitv2.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Entity(tableName = "auction_listings_table")
public class AuctionListing extends Listing {

    private Date endDate;
    private double startingPrice;
    private double currentPrice;

    final static String END_DATE = "endDate";
    final static String STARTING_PRICE = "startingPrice";
    final static String CURRENT_PRICE = "currentPrice";


    public AuctionListing() {
        super();
    }

    public AuctionListing(String owner, String title, String description, String location, Date dateOpened, List<String> images, Date endDate, double startingPrice, double currentPrice) {
        super(owner, title, description, location, dateOpened, images, "Auction");
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public Map<String, Object> getJson(Listing listing) {
        Map<String, Object> json = super.getJson(listing);

        json.put(END_DATE, endDate.getTime());
        json.put(STARTING_PRICE, startingPrice);
        json.put(CURRENT_PRICE, currentPrice);

        return json;
    }

    static public AuctionListing create(@NonNull Map<String, Object> json) {
        Listing listing = Listing.createListing(json);
        return new AuctionListing(listing.getOwnerId(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getLocation(),
                listing.getDateOpened(),
                listing.getImages(),
                new Date((long) json.get(END_DATE)),
                (double) json.get(STARTING_PRICE),
                (double) json.get(CURRENT_PRICE));
    }
}
