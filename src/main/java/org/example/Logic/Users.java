package org.example.Logic;

import java.util.ArrayList;

public class Users {
    private static ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }
}
