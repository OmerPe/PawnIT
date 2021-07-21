package com.colman.pawnit.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Entity(tableName = "auction_listings_table")
public class AuctionListing extends Listing {

    private Date startDate;
    private Date endDate;
    private double startingPrice;
    private double currentPrice;

    final static String END_DATE = "endDate";
    final static String START_DATE = "startDate";
    final static String STARTING_PRICE = "startingPrice";
    final static String CURRENT_PRICE = "currentPrice";


    public AuctionListing() {
        super();
    }

    public AuctionListing(String owner, String title, String description, String location, Date dateOpened, List<String> images, Date endDate,Date startDate, double startingPrice, double currentPrice) {
        super(owner, title, description, location, dateOpened, images, "Auction");
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
    public Map<String, Object> getJson() {
        Map<String, Object> json = super.getJson();

        json.put(END_DATE, endDate.getTime());
        json.put(START_DATE,startDate.getTime());
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
                ((Timestamp)json.get(START_DATE)).toDate(),
                ((Timestamp)json.get(END_DATE)).toDate(),
                (double)json.get(STARTING_PRICE),
                (double) json.get(CURRENT_PRICE));
    }
}
