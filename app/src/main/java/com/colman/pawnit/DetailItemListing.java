package com.colman.pawnit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.colman.pawnit.Model.Listing;

import org.w3c.dom.Text;

public class DetailItemListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_listing);

        Intent i = getIntent();
        Listing currentListing = (Listing)i.getSerializableExtra("listing");


/*        TextView textView = findViewById(R.id.);
        textView.setText(currentListing.getTitle());*/

    }
}