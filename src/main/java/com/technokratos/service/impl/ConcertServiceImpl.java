package com.technokratos.service.impl;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.exception.ConcertNotFoundException;
import com.technokratos.model.ConcertEntity;
import com.technokratos.repository.ConcertRepository;
import com.technokratos.service.ConcertService;
import com.technokratos.util.mapper.ConcertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;

    private final ConcertMapper concertMapper;

    @Override
    public ConcertResponse createConcert(ConcertRequest newConcert) {
        ConcertEntity concert = concertRepository.save(concertMapper.toEntity(newConcert));
        return concertMapper.toResponse(concert);
    }

    @Override
    public ConcertResponse getConcertById(UUID concertId) {
        return concertMapper.toResponse(getById(concertId));
    }

    @Transactional
    @Override
    public ConcertResponse updateConcert(UUID concertId, ConcertRequest updatedConcert) {
        ConcertEntity concert = getById(concertId);
        concertMapper.updateEntity(concert, updatedConcert);
        return concertMapper.toResponse(concert);
    }

    @Override
    public void deleteConcert(UUID concertId) {
        if (!concertRepository.existsById(concertId)) {
            throw new ConcertNotFoundException(concertId);
        }
        concertRepository.deleteById(concertId);
    }

    private ConcertEntity getById(UUID id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException(id));
    }
}
