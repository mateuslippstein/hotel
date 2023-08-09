package com.ml.hotel.controller;

import com.ml.hotel.model.Payment;
import com.ml.hotel.service.PaymentService;
import com.ml.hotel.util.PaymentMethodEnum;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestParam Long roomId, @RequestParam PaymentMethodEnum paymentMethod) {
        try {
            Payment payment = paymentService.createPayment(roomId, paymentMethod);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/unpaid-amount")
    public ResponseEntity<?> getUnpaidAmount(@RequestParam Long roomId){
        try {
            BigDecimal unpaidAmount = paymentService.getUnpaidAmount(roomId);
            return new ResponseEntity<BigDecimal>(unpaidAmount, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
