package com.colman.pawnit.Ui.Market.New;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.Model;
import com.colman.pawnit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAuctionListingFragment extends Fragment {

    private AddAuctionListingViewModel mViewModel;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog edatePickerDialog;// end date
    private Button sdateButton;
    private Button edateButton;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_auction_listing_fragment, container, false);

        HorizontalScrollView images = view.findViewById(R.id.add_auction_addimage);
        EditText title = view.findViewById(R.id.add_auction_title);
        EditText startingPrice = view.findViewById(R.id.add_auction_sprice);
        EditText description = view.findViewById(R.id.add_auction_description);
        ProgressBar progressBar = view.findViewById(R.id.add_auction_progressbar);
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

        Button addBtn = view.findViewById(R.id.add_auction_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);

                AuctionListing auctionListing = new AuctionListing();
                auctionListing.setTitle(title.getText().toString().trim());
                auctionListing.setStartingPrice(Double.parseDouble(startingPrice.getText().toString().trim()));
                auctionListing.setDescription(description.getText().toString().trim());
                auctionListing.setDateOpened(Calendar.getInstance().getTime());

                Model.instance.saveListing(auctionListing, () -> {

                });
                Navigation.findNavController(v).navigateUp();
            }

        });
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "dor has covid", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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
    public void addImages(){

    }
}