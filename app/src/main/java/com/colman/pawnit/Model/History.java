package com.colman.pawnit.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "History_Table")
public class History {

    @PrimaryKey
    @NonNull
    private String id;

    private String event;
    private String uid;
    private Date eventTime;

    final static String ID = "id";
    final static String EVENT = "event";
    final static String UID = "uid";
    final static String EVENT_TIME = "eventTime";

    public History() {
    }

    public History(String event, String uid, Date eventTime) {
        this.event = event;
        this.uid = uid;
        this.eventTime = eventTime;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Map<String, Object> getJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, id);
        json.put(EVENT, event);
        json.put(UID, uid);
        json.put(EVENT_TIME,eventTime.getTime());
        return json;
    }

    public static History create(Map<String, Object> json) {
        History history = new History((String) json.get(EVENT), (String) json.get(UID),((Timestamp)json.get(EVENT_TIME)).toDate());
        history.setId((String)json.get(ID));
        return history;
    }
}
