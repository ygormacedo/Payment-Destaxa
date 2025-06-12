package io.github.ygormacedo.Payments.api;

import io.github.ygormacedo.Payments.api.dto.PaymentRequest;
import io.github.ygormacedo.Payments.api.dto.PaymentResponse;
import io.github.ygormacedo.Payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PagamentoController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public PaymentRequest authorize(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}
