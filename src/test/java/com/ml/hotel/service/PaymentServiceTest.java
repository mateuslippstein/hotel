package com.ml.hotel.service;

import com.ml.hotel.model.Payment;
import com.ml.hotel.model.RoomBooking;
import com.ml.hotel.repository.PaymentRepository;
import com.ml.hotel.repository.RoomBookingRepository;
import com.ml.hotel.util.PaymentMethodEnum;
import com.ml.hotel.util.RoomStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RoomBookingRepository roomBookingRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePayment_Success() {
        Long roomId = 1L;
        PaymentMethodEnum paymentMethod = PaymentMethodEnum.CREDIT_CARD;

        List<RoomBooking> roomBookings = new ArrayList<>();
        RoomBooking roomBooking = new RoomBooking();
        roomBooking.setPrice(BigDecimal.valueOf(50.00));
        roomBookings.add(roomBooking);

        when(roomBookingRepository.findByRoomIdAndStatus(roomId, RoomStatusEnum.OCCUPIED)).thenReturn(roomBookings);

        // Mock the save method to return the same Payment object that is passed as an
        // argument
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.createPayment(roomId, paymentMethod);

        assertNotNull(payment);
        assertEquals(paymentMethod, payment.getPaymentMethod());
        assertEquals(BigDecimal.valueOf(50.00), payment.getAmount());

        assertEquals(RoomStatusEnum.PAID, roomBooking.getStatus());

        verify(roomBookingRepository, times(1)).findByRoomIdAndStatus(roomId, RoomStatusEnum.OCCUPIED);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testCreatePayment_NoRoomBookings() {
        Long roomId = 1L;
        PaymentMethodEnum paymentMethod = PaymentMethodEnum.CREDIT_CARD;

        when(roomBookingRepository.findByRoomIdAndStatus(roomId, RoomStatusEnum.OCCUPIED))
                .thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> paymentService.createPayment(roomId, paymentMethod));

        verify(roomBookingRepository, times(1)).findByRoomIdAndStatus(roomId, RoomStatusEnum.OCCUPIED);
        verify(paymentRepository, times(0)).save(any());
    }

}
