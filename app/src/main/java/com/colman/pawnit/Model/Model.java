package com.colman.pawnit.Model;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.colman.pawnit.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    FirebaseModel firebaseModel = new FirebaseModel();

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
                    Collections.sort(listings,(listing1,listing2) ->{
                        if(listing1.getDateOpened() == null || listing2.getDateOpened() == null){
                            return 0;
                        }
                        return (-1)*listing1.getDateOpened().compareTo(listing2.getDateOpened());
                    });
                    return listings;
                });
    }

    public LiveData<List<Listing>> getAllListings(){
        return ZipLiveData.create(
                getMarketList(),
                getAllPawnListings(),
                (market, pawn) -> {
                    final List<Listing> listings = new LinkedList<>();
                    if(market != null){
                        listings.addAll(market);
                    }
                    if(pawn != null){
                        listings.addAll(pawn);
                    }
                    Collections.sort(listings,(listing1,listing2) ->{
                        if(listing1.getDateOpened() == null || listing2.getDateOpened() == null){
                            return 0;
                        }
                        return (-1)*listing1.getDateOpened().compareTo(listing2.getDateOpened());
                    });
                    return listings;
                });
    }

    MutableLiveData<List<ResellListing>> allResellListings = new MutableLiveData<List<ResellListing>>(new LinkedList<ResellListing>());
    public LiveData<List<ResellListing>> getResellData() {
        firebaseModel.getAllResells((resells)->{
            allResellListings.setValue(resells);
        });
        return allResellListings;
    }

    MutableLiveData<List<AuctionListing>> allAuctionListings = new MutableLiveData<List<AuctionListing>>(new LinkedList<AuctionListing>());
    public LiveData<List<AuctionListing>> getAuctionData(){
        firebaseModel.getAllAuctions((auctions)->{
            allAuctionListings.setValue(auctions);
        });
        return allAuctionListings;
    }

    MutableLiveData<List<PawnListing>> allPawnListings = new MutableLiveData<List<PawnListing>>(new LinkedList<PawnListing>());
    public LiveData<List<PawnListing>> getAllPawnListings() {
        firebaseModel.getAllPawns((pawns)->{
            allPawnListings.setValue(pawns);
        });
        return allPawnListings;
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
//        if(listing instanceof ResellListing){
//            executorService.execute(()->{
//                resellListingDao.insertAll((ResellListing) listing);
//                MyApplication.mainThreadHandler.post(onCompleteListener::onComplete);
//            });
//        }else if(listing instanceof AuctionListing){
//            executorService.execute(()->{
//                auctionListingDao.insertAll((AuctionListing) listing);
//                MyApplication.mainThreadHandler.post(onCompleteListener::onComplete);
//            });
//
//        }else if(listing instanceof PawnListing){
//            executorService.execute(()->{
//                pawnListingDao.insertAll((PawnListing) listing);
//                MyApplication.mainThreadHandler.post(onCompleteListener::onComplete);
//            });
//
//        }
        firebaseModel.saveListing(listing,()->{
            onCompleteListener.onComplete();
        });
    }
}
