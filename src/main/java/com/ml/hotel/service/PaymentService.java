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

    /**
     * Calculate the total amount for a list of room bookings.
     *
     * @param roomBookings The list of room bookings for which the total amount
     *                     needs to be calculated.
     * @return The total amount calculated based on the prices of the room bookings.
     */
    private BigDecimal calculateTotalAmount(List<RoomBooking> roomBookings) {
        return roomBookings.stream()
                .map(roomBooking -> roomBooking.getPrice())
                .map(price -> price != null ? price : DEFAULT_PRICE)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Create a payment for a given room and payment method.
     *
     * @param roomId        The ID of the room for which the payment is being
     *                      created.
     * @param paymentMethod The payment method chosen for the payment.
     * @return The created Payment object.
     * @throws EntityNotFoundException If no occupied room bookings are found for
     *                                 the given room ID.
     */
    public Payment createPayment(Long roomId, PaymentMethodEnum paymentMethod) {
        List<RoomBooking> roomBookings = roomBookingRepository.findByRoomIdAndStatus(roomId, RoomStatusEnum.OCCUPIED);
        if (roomBookings.isEmpty()) {
            throw new EntityNotFoundException("No Occupied RoomBookings found for Room ID: " + roomId);
        }

        BigDecimal total = calculateTotalAmount(roomBookings);

        Payment payment = new Payment(null, roomBookings, total, paymentMethod);

        roomBookings.forEach(roomBooking -> roomBooking.setStatus(RoomStatusEnum.PAID));
        roomBookingRepository.saveAll(roomBookings);

        return paymentRepository.save(payment);
    }

    /**
     * Get the unpaid amount for a specific room ID.
     *
     * @param roomId The ID of the room for which the unpaid amount needs to be
     *               retrieved.
     * @return The unpaid amount for the room.
     * @throws EntityNotFoundException If no pending payments are found for the
     *                                 given room ID.
     */
    public BigDecimal getUnpaidAmountByRoomId(Long roomId) {
        List<RoomBooking> roomBookings = roomBookingRepository.findByRoomIdAndStatus(roomId, RoomStatusEnum.OCCUPIED);
        if (roomBookings.isEmpty()) {
            throw new EntityNotFoundException("No pending payments found for Room ID: " + roomId);
        }

        return calculateTotalAmount(roomBookings);
    }

    /**
     * Get the unpaid amount for a specific person ID.
     *
     * @param personId The ID of the person for which the unpaid amount needs to be
     *                 retrieved.
     * @return The unpaid amount for the person.
     * @throws EntityNotFoundException If no pending payments are found for the
     *                                 given person ID.
     */
    public BigDecimal getUnpaidAmountByPersonId(Long personId) {
        List<RoomBooking> roomBookings = roomBookingRepository.findByPersonIdAndStatus(personId,
                RoomStatusEnum.OCCUPIED);

        if (roomBookings.isEmpty()) {
            throw new EntityNotFoundException("No pending payments found for Person ID: " + personId);
        }

        return calculateTotalAmount(roomBookings);
    }

}
