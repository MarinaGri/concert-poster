package com.technokratos.service.impl;

import com.technokratos.model.BookingEntity;
import com.technokratos.model.ConcertEntity;
import com.technokratos.model.UserEntity;
import com.technokratos.repository.BookingRepository;
import com.technokratos.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public UUID addBooking(UserEntity user, ConcertEntity concert) {
        return bookingRepository.save(BookingEntity.builder()
                .user(user)
                .concert(concert)
                .build()
        ).getId();
    }
}
