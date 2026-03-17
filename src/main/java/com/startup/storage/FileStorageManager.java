package com.startup.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.startup.models.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileStorageManager implements StorageManager {

    private final String dataFilePath;
    private final Gson gson;

    public FileStorageManager(String dataFilePath) {
        this.dataFilePath = dataFilePath;
        this.gson = new Gson();
        ensureFileExists();
    }

    private void ensureFileExists() {
        File file = new File(dataFilePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                // Initialize with an empty array
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(User user) {
        List<User> users = loadAllUsers();
        
        // Remove existing user to update
        users.removeIf(u -> u.getUsername().equals(user.getUsername()));
        users.add(user);

        try (Writer writer = new FileWriter(dataFilePath)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User loadUser(String username) {
        List<User> users = loadAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> loadAllUsers() {
        try (Reader reader = new FileReader(dataFilePath)) {
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> users = gson.fromJson(reader, userListType);
            if (users == null) {
                return new ArrayList<>();
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
