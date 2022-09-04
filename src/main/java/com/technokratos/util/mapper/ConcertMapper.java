package com.technokratos.util.mapper;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.model.ConcertEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConcertMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "booking", ignore = true)
    ConcertEntity toEntity(ConcertRequest request);

    ConcertResponse toResponse(ConcertEntity entity);

    List<ConcertResponse> toResponses(List<ConcertEntity> entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "booking", ignore = true)
    void updateEntity(@MappingTarget ConcertEntity entity, ConcertRequest request);

}
