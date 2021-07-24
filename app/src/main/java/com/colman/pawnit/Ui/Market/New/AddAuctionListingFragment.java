package com.colman.pawnit.Ui.Market.New;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.Model;
import com.colman.pawnit.MyApplication;
import com.colman.pawnit.R;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class AddAuctionListingFragment extends Fragment {

    private AddAuctionListingViewModel mViewModel;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog edatePickerDialog;// end date
    private Button sdateButton;
    private Button edateButton , addBtn;
    List<Bitmap> selectedImages;
    ProgressBar progressBar;
    EditText title,startingPrice,description;

    LayoutInflater inf;
    LinearLayout gallery;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_auction_listing_fragment, container, false);

        gallery = view.findViewById(R.id.auction_gallery);
        inf = inflater;

        title = view.findViewById(R.id.add_auction_title);
        startingPrice = view.findViewById(R.id.add_auction_sprice);
        description = view.findViewById(R.id.add_auction_description);
        progressBar = view.findViewById(R.id.add_auction_progressbar);
        ImageButton addImages = view.findViewById(R.id.add_auction_imageV);
        initDatePicker();
        sdateButton = (Button) view.findViewById(R.id.auction_sdatebtn);
        sdateButton.setText(getTodaysDate());
        sdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(v);
            }
        });
        edateButton = (Button) view.findViewById(R.id.auction_edatebtn);
        edateButton.setText(getTodaysDate());
        edateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eopenDatePicker(v);
            }
        });

        addBtn = view.findViewById(R.id.add_auction_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAuction(v);
            }
        });
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImages();
            }
        });

        return view;
    }

    private void addAuction(View v){
            progressBar.setVisibility(View.VISIBLE);
            addBtn.setEnabled(false);

            AuctionListing auctionListing = new AuctionListing();
            auctionListing.setTitle(title.getText().toString().trim());
            auctionListing.setStartingPrice(Double.parseDouble(startingPrice.getText().toString().trim()));
            auctionListing.setDescription(description.getText().toString().trim());
            auctionListing.setDateOpened(Calendar.getInstance().getTime());
            auctionListing.setStartDate(getDate(sdateButton.getText().toString().trim()));
            auctionListing.setEndDate(getDate(edateButton.getText().toString().trim()));
            auctionListing.setOwnerId(Model.instance.getLoggedUser().getUid());

            Model.instance.saveListing(auctionListing,(listingId)->{
                if(listingId != null){
                    Model.instance.getUserFromDB(user -> {
                        if(user != null){
                            user.addAuctionListing(listingId);
                            Model.instance.updateUserData(user,()->{
                                Model.instance.userLoadingState.setValue(Model.LoadingState.loaded);
                            });
                        }
                    });
                }
            });
            Navigation.findNavController(v).navigateUp();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                sdateButton.setText(date);
            }
        };
        DatePickerDialog.OnDateSetListener edateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                edateButton.setText(date);
            }
        };

        Calendar startcal = Calendar.getInstance();
        int syear = startcal.get(Calendar.YEAR);
        int smonth = startcal.get(Calendar.MONTH);
        int sday = startcal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, syear, smonth, sday);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        edatePickerDialog = new DatePickerDialog(getContext(), style, edateSetListener, year, month, day);
        edatePickerDialog.getDatePicker().setMinDate(startcal.getTimeInMillis());
    }

    private String makeDateString(int day, int month, int year) {
        String m = "JAN";
        switch (month) {
            case 1:
                m = "JAN";
                break;
            case 2:
                m = "FEB";
                break;
            case 3:
                m = "MAR";
                break;
            case 4:
                m = "APR";
                break;
            case 5:
                m = "MAY";
                break;
            case 6:
                m = "JUN";
                break;
            case 7:
                m = "JUL";
                break;
            case 8:
                m = "AUG";
                break;
            case 9:
                m = "SEP";
                break;
            case 10:
                m = "OCT";
                break;
            case 11:
                m = "NOV";
                break;
            case 12:
                m = "DEC";
                break;
        }

        return m + " " + day + " " + year;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String[] dateParsed = date.split(" ");
        String month = getMonth(dateParsed[0]);
        String finalDate = dateParsed[1] + "/" + month + "/" + dateParsed[2];
        Date dateOfBirth = null;
        try {
            dateOfBirth = sdf.parse(finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateOfBirth != null) {
        }
        return dateOfBirth;
    }

    private String getMonth(String month) {
        if (month.equals("JAN")) {
            return "01";
        }
        if (month.equals("FEB")) {
            return "02";
        }
        if (month.equals("MAR")) {
            return "03";
        }
        if (month.equals("APR")) {
            return "04";
        }
        if (month.equals("MAY")) {
            return "05";
        }
        if (month.equals("JUN")) {
            return "06";
        }
        if (month.equals("JUL")) {
            return "07";
        }
        if (month.equals("AUG")) {
            return "08";
        }
        if (month.equals("SEP")) {
            return "09";
        }
        if (month.equals("OCT")) {
            return "10";
        }
        if (month.equals("NOV")) {
            return "11";
        }
        if (month.equals("DEC")) {
            return "12";
        }
        return "01";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
    public void eopenDatePicker(View view) {
        edatePickerDialog.show();
    }

    static final int PICK_IMAGE = 1;
    void addImages(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK) {
            try {
                selectedImages = new LinkedList<>();
                ClipData clipData = data.getClipData();
                if(clipData != null){//multiplte images
                    for (int i =0; i<clipData.getItemCount();i++){
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                        Bitmap image = BitmapFactory.decodeStream(is);
                        selectedImages.add(image);
                    }
                }else {//single image
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(imageStream);
                    selectedImages.add(image);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
            new Thread(()->{
                for (final Bitmap image : selectedImages){
                    MyApplication.mainThreadHandler.post(()->{
                        View im = inf.inflate(R.layout.image_item,gallery,false);
                        ImageView imV = im.findViewById(R.id.imageItem_imageV);
                        imV.setImageBitmap(image);
                        if(imV.getParent() != null){
                            ((ViewGroup)imV.getParent()).removeView(imV);
                        }
                        gallery.addView(imV);
                    });
                }

            }).start();

        }else {
            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}