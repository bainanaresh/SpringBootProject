package com.baina.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.baina.auth.entity.AppUser;
import com.baina.auth.repository.AppUserRepository;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            AppUser admin = new AppUser("admin", passwordEncoder.encode("admin123"), Set.of("ADMIN", "USER"));
            userRepository.save(admin);
        }
        if (userRepository.findByUsername("user").isEmpty()) {
            AppUser user = new AppUser("user", passwordEncoder.encode("user123"), Set.of("USER"));
            userRepository.save(user);
        }
    }
}
