package com.ktiservice.patrimoine.services.security;

import com.ktiservice.patrimoine.dto.auth.AuthRequest;
import com.ktiservice.patrimoine.dto.auth.AuthResponse;
import com.ktiservice.patrimoine.dto.auth.RegisterRequest;
import com.ktiservice.patrimoine.entities.UserJpaEntity;
import com.ktiservice.patrimoine.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserJpaRepository userRepository;

    public AuthResponse authenticate(AuthRequest request) {
        try {
            // Authentification de l'utilisateur
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            // Récupération de l'utilisateur dans la base de données
            UserJpaEntity user =
                    userRepository
                            .findByEmail(request.getEmail())
                            .orElseThrow(
                                    () ->
                                            new UsernameNotFoundException(
                                                    "L'utilisateur avec l'adresse e-mail "
                                                            + request.getEmail()
                                                            + " n'a pas été trouvé. "
                                                            + "Veuillez vérifier l'adresse saisie et réessayer."));

            // Génération du token JWT
            String jwtToken = jwtService.generateToken(user);

            // Réponse réussie
            return AuthResponse.builder().accessToken(jwtToken).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void register(RegisterRequest registerRequest) {
        UserJpaEntity user =
                UserJpaEntity.builder()
                        .email(registerRequest.getEmail())
                        .role(registerRequest.getRole())
                        .firstName(registerRequest.getFirstName() == null ? "" : registerRequest.getFirstName())
                        .lastName(registerRequest.getLastName())
                        .phoneNumber(registerRequest.getPhoneNumber())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .build();
        userRepository.save(user);
    }

}
