package com.colman.pawnit.Model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();
    private List<User> userList = new LinkedList<>();
    private List<Listing> listings = new LinkedList<>();
}
