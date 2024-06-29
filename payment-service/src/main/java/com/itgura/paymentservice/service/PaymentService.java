package com.itgura.paymentservice.service;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.paymentservice.dto.request.addSessionToMonthRequest;
import com.itgura.paymentservice.dto.request.getPaidMothRequest;
import com.itgura.paymentservice.dto.request.saveMonthlyPaymentRequest;

import java.util.UUID;

public interface PaymentService {
    String saveMonthlyPayment(saveMonthlyPaymentRequest data) throws BadRequestRuntimeException, ApplicationException;

    int[] getPaidMonths(getPaidMothRequest data) throws BadRequestRuntimeException;

    String addSessionToMonth(addSessionToMonthRequest data);

    String deleteSession(UUID data);
}
