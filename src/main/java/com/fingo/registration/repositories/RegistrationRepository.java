package com.fingo.registration.repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr Stoklosa
 */
@Repository
public class RegistrationRepository {

    Map<String, Integer> users = new HashMap<>();

    public void registerNewUser(String name) {
        users.put(name, 1);
    }

    public boolean isUserPresent(String name) {
        return users.containsKey(name);
    }

    public void deleteUser(String name) {
        users.remove(name);
    }

    public Map<String, Integer> getAllUsers() {
        return users;
    }

    public void registerExistingUser(String name) {
        users.put(name, users.get(name) + 1);
    }

    public int getNumberOfUserRegistrations(String name) {
        return users.get(name);
    }

    public int getAmountOfUsers() {
        return users.size();
    }

}
