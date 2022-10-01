package ru.crspet.recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.crspet.recipes.model.User;
import ru.crspet.recipes.service.RegistrationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController( RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("register")
    public ResponseEntity httpRegister(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "validation error");
        }
        return registrationService.userRegister(user);
    }

}
