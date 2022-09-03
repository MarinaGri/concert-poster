package com.technokratos.util.mapper;

import com.technokratos.dto.request.UserExtendedRequest;
import com.technokratos.dto.response.UserResponse;
import com.technokratos.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "hashPassword", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    UserEntity toEntity(UserExtendedRequest request);

    UserResponse toResponse(UserEntity entity);

}
