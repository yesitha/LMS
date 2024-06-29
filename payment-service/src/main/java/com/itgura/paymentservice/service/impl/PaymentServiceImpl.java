package com.itgura.paymentservice.service.impl;

import com.itgura.dto.AppResponse;
import com.itgura.exception.ApplicationException;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.paymentservice.dto.request.addSessionToMonthRequest;
import com.itgura.paymentservice.dto.request.getPaidMothRequest;
import com.itgura.paymentservice.dto.request.saveMonthlyPaymentRequest;
import com.itgura.paymentservice.entity.StudentTransactionContent;
import com.itgura.paymentservice.entity.Transaction;
import com.itgura.paymentservice.repository.StudentTransactionContentRepository;
import com.itgura.paymentservice.repository.TransactionRepository;
import com.itgura.paymentservice.service.PaymentService;
import com.itgura.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Year;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


@Service
public class PaymentServiceImpl implements PaymentService {
//    @Value("${apiGateway.url}")
//    private String apiGatewayUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StudentTransactionContentRepository studentTransactionContentRepository;

    @Override
    public String saveMonthlyPayment(saveMonthlyPaymentRequest data) throws BadRequestRuntimeException, ApplicationException {
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
                Transaction transaction1= transactionRepository.save(transaction);
                //todo: cron job to update studentTransactionContent table
                List<UUID> sessionIds = findAllSessionsInMonth(data.getClassId(), Calendar.getInstance().get(Calendar.YEAR), month);
                for (UUID sessionId : sessionIds) {
                    StudentTransactionContent studentTransactionContent = new StudentTransactionContent();
                    studentTransactionContent.setStudentEmail(data.getStudentEmail());
                    studentTransactionContent.setContentId(sessionId);
                    studentTransactionContent.setTransaction(transaction1);
                }


            } else {
                throw new BadRequestRuntimeException("Payment amount is not correct");
            }


        }
        return "Payment saved successfully";

    }

    private List<UUID> findAllSessionsInMonth(UUID classId, int year, int month) throws ApplicationException {
        String url = "http://lms-gateway/resource-management/session/get-all/in-month-and-class/" + classId + "/" + year + "/" + month;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + UserUtil.extractToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<AppResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, AppResponse.class);
            AppResponse response = responseEntity.getBody();

            if (response == null || response.getData() == null) {
                throw new ApplicationException("Error while getting sessions in month: response or data is null");
            }

            System.out.println("Sessions for Month "+response.getData());

            return (List<UUID>) response.getData();

        } catch (HttpClientErrorException.Forbidden e) {
            throw new ApplicationException("Access is forbidden: " + e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new ApplicationException("Client error: " + e.getStatusCode() + " " + e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException("Server error: " + e.getMessage());
        }
    }

    @Override
    public int[] getPaidMonths(getPaidMothRequest data) throws BadRequestRuntimeException {
        int[] transactions = transactionRepository.findMonthsByStudentEmailAndClassId(data.getStudentEmail(), data.getClassId(), Year.now().getValue());
        if(transactions != null){
            return transactions;
        }else{
            throw new BadRequestRuntimeException("No transactions found");
        }
    }

    @Override
    @Transactional
    public String addSessionToMonth(addSessionToMonthRequest data) {
        List<UUID> transactionIds = transactionRepository.findByMonthAndYearAndClassId(data.getMonth(), data.getYear(), data.getClassId());
        for (UUID transactionId : transactionIds) {
            Transaction transaction = transactionRepository.findById(transactionId).get();
            StudentTransactionContent studentTransactionContent = new StudentTransactionContent();
            studentTransactionContent.setStudentEmail(transaction.getStudentEmail());
            studentTransactionContent.setContentId(data.getSessionId());
            studentTransactionContent.setTransaction(transaction);
            studentTransactionContentRepository.save(studentTransactionContent);
        }

        return "Session added to month successfully";
    }

    @Override
    @Transactional
    public String deleteSession(UUID data) {
        studentTransactionContentRepository.deleteByContentId(data);
        return "Session deleted successfully";
    }

    private double getMonthlyPayment(UUID classId) throws ApplicationException {

        String url = "http://lms-gateway/resource-management/class/getClassFee/" + classId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + UserUtil.extractToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);


        try {
            ResponseEntity<AppResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, AppResponse.class);
            AppResponse response = responseEntity.getBody();

            if (response == null || response.getData() == null) {
                throw new ApplicationException("Error while getting monthly payment: response or data is null");
            }

            return (double) response.getData();


        } catch (HttpClientErrorException.Forbidden e) {
            throw new ApplicationException("Access is forbidden: " + e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new ApplicationException("Client error: " + e.getStatusCode() + " " + e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException("Server error: " + e.getMessage());
        }
    }


}
