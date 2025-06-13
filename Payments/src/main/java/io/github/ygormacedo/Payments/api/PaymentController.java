package io.github.ygormacedo.Payments.api;

import io.github.ygormacedo.Payments.api.dto.PaymentRequest;
import io.github.ygormacedo.Payments.api.dto.PaymentResponse;
import io.github.ygormacedo.Payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/authorization")
    public ResponseEntity<PaymentResponse> authorizePayment(
            @RequestHeader("x-identifier") String merchantId,
            @RequestBody PaymentRequest request) {

        request.setMerchantId(merchantId);
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.ok(response);
    }
}
