package io.github.ygormacedo.Payments.service;


import io.github.ygormacedo.Payments.api.dto.PaymentRequest;
import io.github.ygormacedo.Payments.api.dto.PaymentResponse;
import io.github.ygormacedo.Payments.authorizer.AuthorizerService;
import io.github.ygormacedo.Payments.authorizer.ISO8583Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    @Autowired
    private AuthorizerService authorizerService;

    @Async
    public PaymentResponse processPayment(PaymentRequest request) {

        ISO8583Message iso8583Message = convertToISO8583(request);

        ISO8583Message response = authorizerService.sendToAuthorizer(iso8583Message);

        return convertToPaymentResponse(response);
    }

    private ISO8583Message convertToISO8583(PaymentRequest request) {
        ISO8583Message message = new ISO8583Message();
        message.setMTI("0200");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        DateTimeFormatter gmtFormatter = DateTimeFormatter.ofPattern("MMddHHmmss");

        message.setField(2, request.getCardNumber());
        message.setField(3, request.getInstallments() == 1 ? "003000" : "003001");
        message.setField(4, request.getValue().toString());
        message.setField(7, now.format(gmtFormatter));
        message.setField(11, String.format("%06d", System.currentTimeMillis() % 1000000));
        message.setField(12, now.format(timeFormatter));
        message.setField(13, now.format(dateFormatter));
        message.setField(14, String.format("%02d%02d", request.getExpYear() % 100, request.getExpMonth()));
        message.setField(22, "012");
        message.setField(42, request.getMerchantId());
        message.setField(48, request.getCvv());
        message.setField(67, String.format("%02d", request.getInstallments()));

        return message;
    }

        private PaymentResponse convertToPaymentResponse(ISO8583Message response){
            if (response == null) {
                // Timeout
                PaymentResponse paymentResponse = new PaymentResponse();
                paymentResponse.setResponseCode("408"); // Request Timeout
                return paymentResponse;
            }

            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentId(response.getField(127));
            paymentResponse.setValue(new BigDecimal(response.getField(4)));
            paymentResponse.setResponseCode(response.getField(39));
            paymentResponse.setAuthorizationCode(response.getField(38));

            // Formata data e hora
            String date = response.getField(13);
            String time = response.getField(12);
            paymentResponse.setTransactionDate(String.format("%s-%s-%s",
                    date.substring(0, 2), date.substring(2, 4), "24"));
            paymentResponse.setTransactionHour(String.format("%s:%s:%s",
                    time.substring(0, 2), time.substring(2, 4), time.substring(4, 6)));

            return paymentResponse;

    }

}
