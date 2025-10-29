package com.ktiservice.patrimoine.mappers;

import com.ktiservice.patrimoine.dto.users.CreateUserRequest;
import com.ktiservice.patrimoine.dto.users.UserResponse;
import com.ktiservice.patrimoine.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for User entity and DTOs.
 * Handles conversion between domain User and DTOs.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert User domain entity to UserResponse DTO.
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "isActive", source = "active")
    @Mapping(target = "createdAt", source = "createdAt")
    UserResponse toResponse(User user);

    /**
     * Map CreateUserRequest to User domain entity.
     * Note: Password should be hashed before creating entity.
     */
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    User fromCreateRequest(CreateUserRequest request);
}

