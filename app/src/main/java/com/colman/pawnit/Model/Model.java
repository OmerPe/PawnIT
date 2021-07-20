package com.colman.pawnitv2.Model;

import androidx.lifecycle.LiveData;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    public interface OnCompleteListener {
        void onComplete();
    }

    private ListingDao listingDao;
    private AuctionListingDao auctionListingDao;
    private ResellListingDao resellListingDao;
    private PawnListingDao pawnListingDao;
    private PawnDao pawnDao;
    private OfferDao offerDao;
    private HistoryDao historyDao;

    public static final Model instance = new Model();
    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    private Model() {
        PawnITDataBase dataBase = PawnITDataBase.getInstance();
        listingDao = dataBase.listingDao();
        auctionListingDao = dataBase.auctionListingDao();
        resellListingDao = dataBase.resellListingDao();
        pawnListingDao = dataBase.pawnListingDao();
        pawnDao = dataBase.pawnDao();
        offerDao = dataBase.offerDao();
        historyDao = dataBase.historyDao();
    }

    public LiveData<List<Listing>> getMarketList() {
        return ZipLiveData.create(
                auctionListingDao.getAllAuctions(),
                resellListingDao.getAllResells(),
                (auction, resell) -> {
                    final List<Listing> listings = new LinkedList<>();
                    listings.addAll(auction);
                    listings.addAll(resell);
                    return listings;
                });
    }

    public LiveData<List<PawnListing>> getAllPawnListings() {
        return pawnListingDao.getAllPawns();
    }

    public LiveData<List<Pawn>> getAllPawnsForUser(String uid) {
        return pawnDao.getAllPawnsForUser(uid);
    }

    public LiveData<List<History>> getAllHistoryForUser(String uid) {
        return historyDao.getAllHistoryForUser(uid);
    }

    public LiveData<List<Offer>> getAllOffersForListing(int listingID) {
        return offerDao.getAllOffersForListing(listingID);
    }


}
