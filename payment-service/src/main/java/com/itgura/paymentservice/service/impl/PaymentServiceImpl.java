package com.itgura.paymentservice.service.impl;

import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.paymentservice.dto.request.saveMonthlyPaymentRequest;
import com.itgura.paymentservice.entity.Transaction;
import com.itgura.paymentservice.repository.TransactionRepository;
import com.itgura.paymentservice.service.PaymentService;
import com.itgura.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.UUID;



@Service
public class PaymentServiceImpl implements PaymentService {
//    @Value("${apiGateway.url}")
//    private String apiGatewayUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public String saveMonthlyPayment( saveMonthlyPaymentRequest data) throws BadRequestRuntimeException, ApplicationException {
        double classMonthlyPayment = getMonthlyPayment(data.getClassId());

        int[] months = data.getPaymentMonths();
        for (int month : months) {
            Transaction transaction = new Transaction();
            transaction.setStudentEmail(data.getStudentEmail());
            transaction.setNote(data.getNote());
            transaction.setPaymentYearFor(Calendar.getInstance().get(Calendar.YEAR));
            transaction.setPaymentMonthFor(month);
            transaction.setClassId(data.getClassId());
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

    private double getMonthlyPayment(UUID classId) throws ApplicationException {
        String url = "http://resource-management/class/" + classId + "/getClassFee";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + UserUtil.extractToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.GET, entity, Double.class);
        return response.getBody();
    }
}
