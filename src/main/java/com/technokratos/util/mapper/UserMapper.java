package com.technokratos.util.mapper;

import com.technokratos.dto.request.UserRequest;
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
    UserEntity toEntity(UserRequest request);

}
