package com.ktiservice.patrimoine.infrastructure.persistence.adapters;

import com.ktiservice.patrimoine.domain.entities.User;
import com.ktiservice.patrimoine.domain.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.domain.ports.persistence.UserRepository;
import com.ktiservice.patrimoine.infrastructure.persistence.entities.UserJpaEntity;
import com.ktiservice.patrimoine.infrastructure.persistence.repositories.UserJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter for UserRepository.
 * Converts between domain User and JPA UserJpaEntity.
 */
@Slf4j
@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity jpaEntity = UserJpaEntity.fromDomainEntity(user);
        UserJpaEntity saved = userJpaRepository.save(jpaEntity);
        log.debug("User saved with email: {}", user.getEmail());
        return saved.toDomainEntity();
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userJpaRepository.findById(userId)
                .map(UserJpaEntity::toDomainEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserJpaEntity::toDomainEntity);
    }

    @Override
    public User update(User user) {
        if (!userJpaRepository.existsById(user.getId())) {
            throw new ResourceNotFoundException("User", user.getId().toString());
        }
        UserJpaEntity jpaEntity = UserJpaEntity.fromDomainEntity(user);
        UserJpaEntity updated = userJpaRepository.save(jpaEntity);
        log.debug("User updated: {}", user.getId());
        return updated.toDomainEntity();
    }

    @Override
    public void deleteById(UUID userId) {
        if (!userJpaRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", userId.toString());
        }
        userJpaRepository.deleteById(userId);
        log.debug("User deleted: {}", userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}