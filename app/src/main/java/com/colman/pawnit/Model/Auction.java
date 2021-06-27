package com.colman.pawnit.Model;

import java.util.Date;
import java.util.List;

public class Auction {
    private User postUser;
    private User highestBidder;
    private double currentBid;
    private double minPrice;
    private Date startDate;
    private Date endDate;
    private List<String> images;

}
