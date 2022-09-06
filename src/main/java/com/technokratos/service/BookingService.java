package com.technokratos.service;

import com.technokratos.model.ConcertEntity;
import com.technokratos.model.UserEntity;

import java.util.UUID;

public interface BookingService {

    UUID addBooking(UserEntity user, ConcertEntity concert);

}
