package com.startup.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String password; // Simple for phase 1, can be hashed later
    private List<StartupIdea> savedIdeas;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.savedIdeas = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<StartupIdea> getSavedIdeas() {
        return savedIdeas;
    }

    public void addIdea(StartupIdea idea) {
        this.savedIdeas.add(idea);
    }
}
