package com.colman.pawnit.Model;

import androidx.room.Entity;

import java.util.Date;
import java.util.List;

@Entity(tableName = "Resell_table")
public class ResellListing extends Listing{
    private double price;

    public ResellListing(String owner, String title, String description, String location, Date dateOpened, List<String> images, double price) {
        super(owner, title, description, location, dateOpened, images);
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
}
