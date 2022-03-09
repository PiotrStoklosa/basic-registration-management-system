package com.fingo.registration.services;

import com.fingo.registration.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Piotr Stoklosa
 */
@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public void registerUser(String name) {
        if (registrationRepository.isUserPresent(name)) {
            registrationRepository.registerExistingUser(name);
        } else {
            registrationRepository.registerNewUser(name);
        }
    }

    public int countAllRegistrationsByName(String name) {
        return registrationRepository.getNumberOfUserRegistrations(name);
    }

    public void deleteUser(String name) {
        registrationRepository.deleteUser(name);
    }

    public Map<String, Integer> getFirstThreeUsers() {
        return getSortedUsers(3);
    }

    public Map<String, Integer> getAllUsers() {
        return getSortedUsers(registrationRepository.getAmountOfUsers());
    }

    public Map<String, Integer> getAllIgnoredCaseUsers() {
        return getSortedIgnoredCaseUsers(registrationRepository.getAmountOfUsers());
    }

    private Map<String, Integer> getSortedUsers(int count) {
        return registrationRepository.getAllUsers().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    private Map<String, Integer> getSortedIgnoredCaseUsers(int count) {
        return registrationRepository.getAllUsers().entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(), Map.Entry::getValue, Integer::sum, LinkedHashMap::new))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(), Map.Entry::getValue, Integer::sum, LinkedHashMap::new));
    }
}
