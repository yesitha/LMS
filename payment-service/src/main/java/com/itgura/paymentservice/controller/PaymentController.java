package com.itgura.paymentservice.controller;

import com.itgura.dto.AppRequest;
import com.itgura.dto.AppResponse;
import com.itgura.exception.BadRequestRuntimeException;
import com.itgura.exception.ValueNotExistException;
import com.itgura.paymentservice.dto.request.addSessionToMonthRequest;
import com.itgura.paymentservice.dto.request.getPaidMothRequest;
import com.itgura.paymentservice.dto.request.saveMonthlyPaymentRequest;
import com.itgura.paymentservice.dto.response.PaymentDTO;
import com.itgura.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment-service")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private final PaymentService paymentService;

    @Value("${payhere.merchentSecretCode}")
    private String merchantSecret;
    @Value("${payhere.merchentID}")
    private String merchantID;

    @PutMapping("auth/calculateHash")
    public PaymentDTO calculateHash(@RequestParam("amount") double amount) {

        String merahantID = merchantID;
        String orderID = Long.toString(System.currentTimeMillis());
        double amounts         = amount;
        String currency       = "LKR";
        DecimalFormat df       = new DecimalFormat("0.00");
        String amountFormatted = df.format(amounts);
        String hash    = getMd5(merahantID + orderID + amountFormatted + currency + getMd5(merchantSecret));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setOrderId(orderID);
        paymentDTO.setHash(hash);
        paymentDTO.setAmount(String.format("%.2f", amounts));

        return paymentDTO;

    }

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/saveMonthlyPayment")
    public AppResponse<String> saveMonthlyPayment(@Valid @RequestBody AppRequest<saveMonthlyPaymentRequest> request) {

        try {
            String s = paymentService.saveMonthlyPayment(request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }



    }



    @PostMapping("/getPaidMonths")
    public AppResponse<int[]> getPaidMonths(@Valid @RequestBody AppRequest<getPaidMothRequest> request) {

        try {
            int[] res = paymentService.getPaidMonths(request.getData());
            return AppResponse.ok(res);
        } catch (BadRequestRuntimeException e) {
            return AppResponse.error(null, e.getMessage(), "Bad Request", "400", "");
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }

    }

    @PostMapping("/addSessionToMonth")
    public AppResponse<String> addSessionToMonth(@Valid @RequestBody AppRequest<addSessionToMonthRequest> request) {

        try {
            String s = paymentService.addSessionToMonth(request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }

    }

    @PostMapping("/deleteSession")
    public AppResponse<String> deleteSession(@Valid @RequestBody AppRequest<UUID> request) {

        try {
            String s = paymentService.deleteSession(request.getData());
            return AppResponse.ok(s);
        } catch (Exception e) {
            return AppResponse.error(null, e.getMessage(), "Server Error", "500", "");
        }

    }


}
