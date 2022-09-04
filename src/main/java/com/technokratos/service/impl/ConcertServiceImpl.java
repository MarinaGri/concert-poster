package com.technokratos.service.impl;

import com.technokratos.dto.request.ConcertRequest;
import com.technokratos.dto.response.ConcertResponse;
import com.technokratos.dto.response.page.ConcertPage;
import com.technokratos.exception.ConcertNotFoundException;
import com.technokratos.exception.TicketsSoldOutException;
import com.technokratos.model.ConcertEntity;
import com.technokratos.model.UserEntity;
import com.technokratos.repository.ConcertRepository;
import com.technokratos.service.BookingService;
import com.technokratos.service.ConcertService;
import com.technokratos.service.UserService;
import com.technokratos.util.mapper.ConcertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;

    private final ConcertMapper concertMapper;

    private final UserService userService;

    private final BookingService bookingService;

    @Override
    public ConcertResponse createConcert(ConcertRequest newConcert) {
        ConcertEntity concert = concertRepository.save(concertMapper.toEntity(newConcert));
        return concertMapper.toResponse(concert);
    }

    @Override
    public ConcertResponse getConcertById(UUID concertId) {
        return concertMapper.toResponse(getById(concertId));
    }

    @Override
    public ConcertPage getConcertPage(Pageable pageable) {
        Page<ConcertEntity> concertPage = concertRepository.findAll(pageable);
        return ConcertPage.builder()
                .concerts(concertMapper.toResponses(concertPage.getContent()))
                .currentPage(pageable.getPageNumber())
                .totalPages(concertPage.getTotalPages())
                .build();
    }

    @Transactional
    @Override
    public ConcertResponse updateConcert(UUID concertId, ConcertRequest updatedConcert) {
        ConcertEntity concert = getById(concertId);
        concertMapper.updateEntity(concert, updatedConcert);
        return concertMapper.toResponse(concert);
    }

    @Transactional
    @Override
    public void deleteConcert(UUID concertId) {
        if (!concertRepository.existsById(concertId)) {
            throw new ConcertNotFoundException(concertId);
        }
        concertRepository.deleteById(concertId);
    }

    @Transactional
    @Override
    public void addBookingInfo(UUID concertId, UUID userId) {
        ConcertEntity concert = getById(concertId);
        Integer ticketsNumber = concert.getTicketsNumber();

        if (ticketsNumber == 0) {
            throw new TicketsSoldOutException("Tickets are sold out for the concert: " + concert.getName());
        }

        UserEntity user = userService.getUserById(userId);
        bookingService.addBooking(user, concert);
        concert.setTicketsNumber(--ticketsNumber);
    }

    private ConcertEntity getById(UUID id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException(id));
    }
}
