package com.ktiservice.patrimoine.application.mappers;

import com.ktiservice.patrimoine.domain.entities.Review;
import com.ktiservice.patrimoine.infrastructure.persistence.entities.ReviewJpaEntity;
import com.ktiservice.patrimoine.infrastructure.web.dto.reviews.CreateReviewRequest;
import com.ktiservice.patrimoine.infrastructure.web.dto.reviews.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "siteId", source = "request.siteId")
    @Mapping(target = "rating", source = "request.rating")
    @Mapping(target = "comment", source = "request.comment")
    @Mapping(target = "approved", expression = "java(false)")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Review fromCreateRequest(CreateReviewRequest request, java.util.UUID userId);

    ReviewResponse toResponse(Review review);

    Review toDomainEntity(ReviewJpaEntity jpaEntity);

    ReviewJpaEntity toJpaEntity(Review review);
}
