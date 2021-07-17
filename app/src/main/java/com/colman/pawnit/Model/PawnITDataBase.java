package com.colman.pawnit.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.colman.pawnit.MyApplication;

@Database(entities = {Listing.class, AuctionListing.class, ResellListing.class, PawnListing.class, Offer.class, Pawn.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class PawnITDataBase extends RoomDatabase {

    private static PawnITDataBase instance;

    public abstract ListingDao listingDao();
    public abstract AuctionListingDao auctionListingDao();
    public abstract PawnListingDao pawnListingDao();
    public abstract ResellListingDao resellListingDao();
    public abstract PawnDao pawnDao();
    public abstract OfferDao offerDao();

    public static synchronized PawnITDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(MyApplication.context,
                    PawnITDataBase.class, "pawnit_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
