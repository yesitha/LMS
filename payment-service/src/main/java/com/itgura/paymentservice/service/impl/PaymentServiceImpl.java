package com.itgura.paymentservice.service.impl;

import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.paymentservice.dto.request.saveMonthlyPaymentRequest;
import com.itgura.paymentservice.entity.Transaction;
import com.itgura.paymentservice.repository.TransactionRepository;
import com.itgura.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public String saveMonthlyPayment( saveMonthlyPaymentRequest data) throws BadRequestRuntimeException {
        double classMonthlyPayment = getMonthlyPayment(data.getClassId());

        int[] months = data.getPaymentMonths();
        for (int month : months) {
            Transaction transaction = new Transaction();
            transaction.setStudentEmail(data.getStudentEmail());
            transaction.setNote(data.getNote());
            transaction.setPaymentYearFor(Calendar.getInstance().get(Calendar.YEAR));
            transaction.setPaymentMonthFor(month);
            if (data.getPaymentAmount() / months.length == classMonthlyPayment) {
                transaction.setAmount(classMonthlyPayment);
                transactionRepository.save(transaction);
            }else {
                throw new BadRequestRuntimeException("Payment amount is not correct");
            }


        }
        return "Payment saved successfully";
        //todo: cron job to update studentTransactionContent table
    }

    private double getMonthlyPayment(UUID classId) {
        // todo:get class monthly payment from class service
        return 100;
    }
}
