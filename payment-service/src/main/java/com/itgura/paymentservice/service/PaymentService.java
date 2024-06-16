package com.itgura.paymentservice.service;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.paymentservice.dto.request.saveMonthlyPaymentRequest;
public interface PaymentService {
    String saveMonthlyPayment(saveMonthlyPaymentRequest data) throws BadRequestRuntimeException, ApplicationException;
}
