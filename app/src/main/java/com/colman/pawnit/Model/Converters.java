package com.colman.pawnit.Model;

import android.location.Location;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static List<String> stringToList(String string) {
        if (string.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(string.split(","));
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        if (list.size() == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (String s :
                    list) {
                sb.append(s).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }

    @TypeConverter
    public static String locationToString(Location location){
        return location.getLatitude()+","+location.getLongitude();
    }

    @TypeConverter
    public static Location stringToLocation(String latlng){
        Location location = new Location("");
        String[] split = latlng.split(",");
        location.setLatitude(Double.parseDouble(split[0]));
        location.setLatitude(Double.parseDouble(split[1]));
        return location;
    }
}

