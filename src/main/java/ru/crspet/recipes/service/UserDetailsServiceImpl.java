package ru.crspet.recipes.service;

import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.crspet.recipes.config.UserDetailsImpl;
import ru.crspet.recipes.model.User;
import ru.crspet.recipes.repository.UserRepository;


@Service ("dbUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + username);
        }

        return new UserDetailsImpl(user);
    }

}
