package com.startup.storage;

import com.startup.models.User;
import java.util.List;

public interface StorageManager {
    void saveUser(User user);
    User loadUser(String username);
    List<User> loadAllUsers();
}
