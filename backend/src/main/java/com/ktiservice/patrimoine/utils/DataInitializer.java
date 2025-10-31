package com.ktiservice.patrimoine.utils;

import com.ktiservice.patrimoine.dto.auth.RegisterRequest;
import com.ktiservice.patrimoine.enums.Role;
import com.ktiservice.patrimoine.repository.user.UserJpaRepository;
import com.ktiservice.patrimoine.services.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AuthService authService;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void run(String... args) throws Exception {

        if(userJpaRepository.count() == 0) {
            authService.register(RegisterRequest.builder().email("admin@gmail.com").role(Role.ADMIN).password("123456789").phoneNumber("+237658326698").firstName("user").lastName("admin").build());
            authService.register(RegisterRequest.builder().email("manager@gmail.com").role(Role.MANAGER).password("123456789").phoneNumber("+237674586232").firstName("user").lastName("manager").build());
            authService.register(RegisterRequest.builder().email("tourist@gmail.com").role(Role.TOURIST).password("123456789").phoneNumber("+237625417896").firstName("user").lastName("tourist").build());
            authService.register(RegisterRequest.builder().email("visitor@gmail.com").role(Role.VISITOR).password("123456789").phoneNumber("+237625143285").firstName("user").lastName("visitor").build());
            authService.register(RegisterRequest.builder().email("guide@gmail.com").role(Role.GUIDE).password("123456789").phoneNumber("+237680256398").firstName("user").lastName("guide").build());
        }

    }
}
