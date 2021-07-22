package com.colman.pawnit.Model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.colman.pawnit.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    final static String USER_COLLECTION = "Users";

    private FirebaseModel() {
    }

    /*---------------------------------------------------------------------------Store--------------------------------------------------------------------------------------*/
    public interface getAllResellsListener {
        public void onComplete(List<ResellListing> resells);
    }

    public static void getAllResells(getAllResellsListener listener) {
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

    public static void getAllAuctions(getAllAuctionsListener listener) {
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

    public static void getAllPawns(getAllPawnsListener listener) {
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
                });
        ;
    }

    public static void saveListing(Listing listing, Model.myOnCompleteListener onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<DocumentReference> ref = null;

        if (listing instanceof ResellListing) {
            Model.instance.resellLoadingState.setValue(Model.LoadingState.loading);
            ResellListing resellListing = (ResellListing) listing;
            ref = db.collection(RESELL_LISTING_COLLECTION).add(resellListing);
        } else if (listing instanceof AuctionListing) {
            Model.instance.auctionLoadingState.setValue(Model.LoadingState.loading);
            AuctionListing auctionListing = (AuctionListing) listing;
            ref = db.collection(AUCTION_LISTING_COLLECTION).add(auctionListing);
        } else if (listing instanceof PawnListing) {
            Model.instance.pawnListingLoadingState.setValue(Model.LoadingState.loading);
            PawnListing pawnListing = (PawnListing) listing;
            ref = db.collection(PAWN_LISTING_COLLECTION).add(pawnListing);
        }

        if (ref != null) {
            ref.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    MyApplication.mainThreadHandler.post(() -> {
                        onCompleteListener.onComplete(documentReference.getId());
                    });
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

    public static void getAllPawnsForUser(String Uid, getAllPawnsListener listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USER_COLLECTION).document(Uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    User user = User.create(documentSnapshot.getData());
                    List<PawnListing> listings = new LinkedList<>();
                    for(String id:
                    user.getPawnListings()){
                        db.collection(PAWN_LISTING_COLLECTION).document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    PawnListing listing = PawnListing.create(documentSnapshot.getData());
                                    listings.add(listing);
                                }
                            }
                        });
                    }
                    listener.onComplete(listings);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    /*---------------------------------------------------------------------------Auth---------------------------------------------------------------------------------------*/
    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getUser() {
        return getAuth().getCurrentUser();
    }

    public static boolean isLoggedIn() {
        return getUser() != null;
    }

    public static void signUpUser(String email, String password, OnCompleteListener<AuthResult> listener) {
        getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public static void logIn(String email, String password, OnCompleteListener<AuthResult> listener) {
        getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public static void logOut() {
        getAuth().signOut();
    }

    public static void forgotPassword(String email, OnCompleteListener<Void> listener) {
        getAuth().sendPasswordResetEmail(email).addOnCompleteListener(listener);
    }

    public interface userOnCompleteListener {
        void onComplete();
    }

    public static void saveUser(User user, userOnCompleteListener onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USER_COLLECTION).document(user.getUid())
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public interface getUserDataListener {
        public void onComplete(User user);
    }

    public static void getUserData(getUserDataListener listener) {
        if (isLoggedIn()) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(USER_COLLECTION).document(getUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            User user = User.create(document.getData());
                            listener.onComplete(user);
                        }
                    }
                }
            });
        }
    }

    public static void updateUser(User user,Model.updateUserDataOnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USER_COLLECTION).document(getUser().getUid())
                .update(user.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        listener.onComplete();
    }

}
