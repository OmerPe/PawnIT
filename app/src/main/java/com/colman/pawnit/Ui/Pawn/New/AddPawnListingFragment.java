package com.colman.pawnit.Ui.Pawn.New;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.PawnListing;
import com.colman.pawnit.MyApplication;
import com.colman.pawnit.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class AddPawnListingFragment extends Fragment {

    String id;

    private AddPawnListingViewModel mViewModel;
    DatePickerDialog datePickerDialog;
    Button sdateButton, addBtn, chooseLocation;
    ImageButton addImages;
    EditText title, requested, interestRate, description;
    ProgressBar progressBar;

    LayoutInflater inf;
    LinearLayout gallery;
    List<Bitmap> selectedImages;

    double lat, lng;
    static final int PICK_LOCATION = 2;

    public static AddPawnListingFragment newInstance() {
        return new AddPawnListingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_pawn_listing_fragment, container, false);

        inf = inflater;
        chooseLocation = view.findViewById(R.id.add_pawn_locationBtn);
        title = view.findViewById(R.id.add_pawn_title);
        requested = view.findViewById(R.id.add_pawn_requested);
        interestRate = view.findViewById(R.id.add_pawn_interest);
        description = view.findViewById(R.id.add_pawn_description);
        progressBar = view.findViewById(R.id.add_pawn_progressbar);
        addImages = view.findViewById(R.id.add_pawn_imageB);
        gallery = view.findViewById(R.id.pawn_gallery);
        sdateButton = (Button) view.findViewById(R.id.pawn_datebtn);
        addBtn = view.findViewById(R.id.add_pawn_add_btn);

        id = (String) getArguments().get("listingID");
        if (id != null) {
            chooseLocation.setEnabled(false);
            addImages.setEnabled(false);
            Model.instance.getPawnListing(id, (listing) -> {
                if(listing != null){
                    PawnListing pl = (PawnListing) listing;
                    title.setText(pl.getTitle());
                    requested.setText("" + pl.getLoanAmountRequested());
                    interestRate.setText("" + pl.getInterestRate());
                    description.setText(pl.getDescription());
                    if (pl.getImages() != null && pl.getImages().size() != 0 &&
                            pl.getImages().get(0) != null && !pl.getImages().get(0).isEmpty()) {
                        View im = inf.inflate(R.layout.image_item, gallery, false);
                        ImageView imV = im.findViewById(R.id.imageItem_imageV);
                        Picasso.get().load(pl.getImages().get(0)).placeholder(R.drawable.placeholder).into(imV);
                        if (imV.getParent() != null) {
                            ((ViewGroup) imV.getParent()).removeView(imV);
                        }
                        gallery.addView(imV);
                    }
                }else {
                    Navigation.findNavController(view).navigateUp();
                }
            });
        }

        addImages.setOnClickListener(v -> {
            addImages();
        });

        initDatePicker();
        sdateButton.setText(getTodaysDate());
        sdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(v);
            }
        });

        chooseLocation.setOnClickListener(v -> {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(getActivity()), PICK_LOCATION);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);

                PawnListing pawnListing = new PawnListing();
                pawnListing.setTitle(title.getText().toString().trim());
                pawnListing.setLoanAmountRequested(Double.parseDouble(requested.getText().toString().trim()));
                pawnListing.setInterestRate(Double.parseDouble(interestRate.getText().toString().trim()));
                pawnListing.setDescription(description.getText().toString().trim());
                pawnListing.setDateOpened(Calendar.getInstance().getTime());
                pawnListing.setWhenToGet(getDate(sdateButton.getText().toString().trim()));
                pawnListing.setOwnerId(Model.instance.getLoggedUser().getUid());
                Location location = new Location("");
                location.setLatitude(lat);
                location.setLongitude(lng);
                pawnListing.setLocation(location);

                if (id == null)
                    createListing(pawnListing, v);
                else
                    updateListing(pawnListing, v);
            }
        });

        return view;
    }

    private void updateListing(PawnListing pawnListing, View v) {
        Model.instance.getPawnListing(id, listing -> {
            PawnListing pl = (PawnListing) listing;
            pl.setTitle(pawnListing.getTitle());
            pl.setLoanAmountRequested(pawnListing.getLoanAmountRequested());
            pl.setInterestRate(pawnListing.getInterestRate());
            pl.setWhenToGet(pawnListing.getWhenToGet());
            pl.setDescription(pawnListing.getDescription());

            Model.instance.updateListing(id, pl, () -> {
                Model.instance.pawnListingLoadingState.setValue(Model.LoadingState.loaded);
                Navigation.findNavController(v).navigateUp();
            });
        });

    }

    private void createListing(PawnListing pawnListing, View v) {
        Model.instance.saveListing(pawnListing, (listingId) -> {
            if (listingId != null) {
                if (selectedImages != null) {
                    Model.instance.uploadImage(selectedImages.get(0), listingId, Model.LISTINGS_DIR, url -> {
                        LinkedList<String> list = new LinkedList<>();
                        list.add(url);
                        pawnListing.setImages(list);
                        pawnListing.setListingID(listingId);
                        Model.instance.updateListing(listingId, pawnListing, () -> {
                            Navigation.findNavController(v).navigateUp();
                            Model.instance.pawnListingLoadingState.setValue(Model.LoadingState.loaded);
                        });
                    });
                } else {
                    pawnListing.setListingID(listingId);
                    Model.instance.updateListing(listingId, pawnListing, () -> {
                        Model.instance.pawnListingLoadingState.setValue(Model.LoadingState.loaded);
                        Navigation.findNavController(v).navigateUp();
                    });
                }

                Model.instance.getUserFromDB(user -> {
                    if (user != null) {
                        user.addPawnListing(listingId);
                        Model.instance.updateUserData(user, () -> {
                            Model.instance.userLoadingState.setValue(Model.LoadingState.loaded);
                        });
                    }
                });
            }
        });
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

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
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

    static final int PICK_IMAGE = 1;

    void addImages() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK) {
            try {
                selectedImages = new LinkedList<>();

                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(imageStream);
                selectedImages.add(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
            new Thread(() -> {
                for (final Bitmap image : selectedImages) {
                    MyApplication.mainThreadHandler.post(() -> {
                        View im = inf.inflate(R.layout.image_item, gallery, false);
                        ImageView imV = im.findViewById(R.id.imageItem_imageV);
                        imV.setImageBitmap(image);
                        if (imV.getParent() != null) {
                            ((ViewGroup) imV.getParent()).removeView(imV);
                        }
                        gallery.addView(imV);
                    });
                }
            }).start();

        }

        if (requestCode == PICK_LOCATION && resultCode == getActivity().RESULT_OK) {
            Place place = PlacePicker.getPlace(data, getContext());
            lat = place.getLatLng().latitude;
            lng = place.getLatLng().longitude;
        }
    }

}