package com.colman.pawnit.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.colman.pawnit.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class FirebaseModel {
    final static String RESELL_LISTING_COLLECTION = "Resell_listings";
    final static String AUCTION_LISTING_COLLECTION = "Auction_listings";
    final static String PAWN_LISTING_COLLECTION = "Pawn_listings";

    public interface getAllResellsListener {
        public void onComplete(List<ResellListing> resells);
    }

    public void getAllResells(getAllResellsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(RESELL_LISTING_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<ResellListing> listings = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ResellListing resellListing = ResellListing.create(document.getData());
                                listings.add(resellListing);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(listings);
                    }
                });
    }

    public interface getAllAuctionsListener {
        public void onComplete(List<AuctionListing> resells);
    }

    public void getAllAuctions(getAllAuctionsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(AUCTION_LISTING_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<AuctionListing> listings = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AuctionListing listing = AuctionListing.create(document.getData());
                                listings.add(listing);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(listings);
                    }
                });
    }

    public interface getAllPawnsListener {
        public void onComplete(List<PawnListing> resells);
    }

    public void getAllPawns(getAllPawnsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PAWN_LISTING_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<PawnListing> listings = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PawnListing listing = PawnListing.create(document.getData());
                                listings.add(listing);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(listings);
                    }
                });;
    }

    public void saveListing(Listing listing, Model.myOnCompleteListener onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<Void> ref = null;
        if (listing instanceof ResellListing) {
            ResellListing resellListing = (ResellListing) listing;
            ref = db.collection(RESELL_LISTING_COLLECTION).document(Integer.toString(resellListing.getListingID()))
                    .set(resellListing);
        } else if (listing instanceof AuctionListing) {
            AuctionListing auctionListing = (AuctionListing) listing;
            ref = db.collection(AUCTION_LISTING_COLLECTION).document(Integer.toString(auctionListing.getListingID()))
                    .set(auctionListing);
        } else if (listing instanceof PawnListing) {
            PawnListing pawnListing = (PawnListing) listing;
            ref = db.collection(PAWN_LISTING_COLLECTION).document(Integer.toString(pawnListing.getListingID()))
                    .set(pawnListing);
        }

        if (ref != null) {
            ref.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    onCompleteListener.onComplete();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "documentation failed");
                        }
                    });
        }

    }

}
