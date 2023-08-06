package com.ml.hotel.service;

import com.ml.hotel.model.Payment;
import com.ml.hotel.model.RoomBooking;
import com.ml.hotel.repository.PaymentRepository;
import com.ml.hotel.repository.RoomBookingRepository;
import com.ml.hotel.util.PaymentMethodEnum;
import com.ml.hotel.util.RoomStatusEnum;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RoomBookingRepository roomBookingRepository;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal("99.00");

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, RoomBookingRepository roomBookingRepository) {
        this.paymentRepository = paymentRepository;
        this.roomBookingRepository = roomBookingRepository;
    }

    public BigDecimal calculateTotalAmount(List<RoomBooking> roomBookings) {
        BigDecimal total = BigDecimal.ZERO;
        for (RoomBooking roomBooking : roomBookings) {
            total = total.add(Optional.ofNullable(roomBooking.getPrice()).orElse(DEFAULT_PRICE));
        }
    
        return total;
    }

    public Payment createPayment(Long roomId, PaymentMethodEnum paymentMethod) {
        List<RoomBooking> roomBookings = roomBookingRepository.findByRoomIdAndStatus(roomId, RoomStatusEnum.OCCUPIED);
        if (roomBookings.isEmpty()) {
            throw new EntityNotFoundException("No Occupied RoomBookings found for Room ID: " + roomId);
        }

        BigDecimal total = calculateTotalAmount(roomBookings);
    
        Payment payment = new Payment();
        payment.setRoomBookings(roomBookings);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(total);

        for (RoomBooking roomBooking : roomBookings) {
            roomBooking.setStatus(RoomStatusEnum.PAID);
            roomBookingRepository.save(roomBooking);
        }
    
        return paymentRepository.save(payment);
    }
    
}
