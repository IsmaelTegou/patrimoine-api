package com.ktiservice.patrimoine.infrastructure.utils;

import com.ktiservice.patrimoine.domain.entities.User;
import com.ktiservice.patrimoine.domain.enums.Role;
import com.ktiservice.patrimoine.domain.ports.persistence.UserRepository;
import com.ktiservice.patrimoine.infrastructure.persistence.repositories.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserJpaRepository userJpaRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {

        if(userJpaRepository.count() == 0) {

            //Creation d'un admin de test
            User userAdmin = User.create(
                    "admin@gmail.com",
                    passwordEncoder.encode("123456789"),
                    "user",
                    "admin"
            );
            userAdmin.setPhoneNumber("+237658326698");
            userAdmin.setRole(Role.ADMINISTRATOR);
            userAdmin.setActive(true);
            userRepository.save(userAdmin);

            //Creation d'un manager de test
            User userManager = User.create(
                    "manager@gmail.com",
                    passwordEncoder.encode("123456789"),
                    "user",
                    "manager"
            );
            userManager.setPhoneNumber("+237658326698");
            userManager.setRole(Role.SITE_MANAGER);
            userManager.setActive(true);
            userRepository.save(userManager);

            //Création d'un touriste de test
            User userTourist = User.create(
                    "tourist@gmail.com",
                    passwordEncoder.encode("123456789"),
                    "user",
                    "tourist"
            );
            userTourist.setPhoneNumber("+237658326698");
            userTourist.setRole(Role.TOURIST);
            userTourist.setActive(true);
            userRepository.save(userTourist);

            //Création d'un visiteur de test
            User userVisitor = User.create(
                    "visitor@gmail.com",
                    passwordEncoder.encode("123456789"),
                    "user",
                    "visitor"
            );
            userVisitor.setPhoneNumber("+237658326698");
            userVisitor.setRole(Role.ANONYMOUS_VISITOR);
            userVisitor.setActive(true);
            userRepository.save(userVisitor);

            //Création d'un guide touristique de test
            User userGuide = User.create(
                    "guide@gmail.com",
                    passwordEncoder.encode("123456789"),
                    "user",
                    "guide"
            );
            userGuide.setPhoneNumber("+237658326698");
            userGuide.setRole(Role.GUIDE);
            userGuide.setActive(true);
            userRepository.save(userGuide);
        }

    }
}