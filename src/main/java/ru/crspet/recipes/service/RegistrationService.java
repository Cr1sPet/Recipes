package ru.crspet.recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.crspet.recipes.model.User;
import ru.crspet.recipes.repository.UserRepository;

@Service
public class RegistrationService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity userRegister(User user) {
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

}
