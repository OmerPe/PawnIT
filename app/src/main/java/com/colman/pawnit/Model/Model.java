package com.colman.pawnit.Model;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.colman.pawnit.MyApplication;

import java.util.ArrayList;
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
                getAuctionData(),
                getResellData(),
                (auction, resell) -> {
                    final List<Listing> listings = new LinkedList<>();
                    if(auction != null){
                        listings.addAll(auction);
                    }
                    if(resell != null){
                        listings.addAll(resell);
                    }
                    return listings;
                });
    }

    public LiveData<List<ResellListing>> getResellData(){
        return resellListingDao.getAllResells();
    }

    public LiveData<List<AuctionListing>> getAuctionData(){
        return auctionListingDao.getAllAuctions();
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

    public interface myOnCompleteListener{
        void onComplete();
    }

    public void saveListing(Listing listing,myOnCompleteListener onCompleteListener){
        if(listing instanceof ResellListing){
            executorService.execute(()->{
                resellListingDao.insertAll((ResellListing) listing);
                MyApplication.mainThreadHandler.post(onCompleteListener::onComplete);
            });
        }else if(listing instanceof AuctionListing){
            executorService.execute(()->{
                auctionListingDao.insertAll((AuctionListing) listing);
                MyApplication.mainThreadHandler.post(onCompleteListener::onComplete);
            });

        }else if(listing instanceof PawnListing){
            executorService.execute(()->{
                pawnListingDao.insertAll((PawnListing) listing);
                MyApplication.mainThreadHandler.post(onCompleteListener::onComplete);
            });

        }
    }
}
