package com.fingo.registration.controllers;

import com.fingo.registration.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Piotr Stoklosa
 */
@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> register(@RequestParam String name) {
        registrationService.registerUser(name);
        return Map.of("status", "OK", "count", registrationService.countAllRegistrationsByName(name));
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String name) {
        registrationService.deleteUser(name);
    }

    @GetMapping("stats")
    public Map<String, Integer> stats(@RequestParam(required = false) String mode) {
        return switch (String.valueOf(mode).toUpperCase()) {
            case "NULL" -> registrationService.getFirstThreeUsers();
            case "ALL" -> registrationService.getAllUsers();
            case "IGNORE_CASE" -> registrationService.getAllIgnoredCaseUsers();
            default -> throw new IllegalArgumentException("There is no such mode!");
        };

    }

}
