package com.example.demo.batch;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/batch")
public class BatchController {

    private final SubscriptionOrderBatch subscriptionOrderBatch;

    public BatchController(SubscriptionOrderBatch subscriptionOrderBatch) {
        this.subscriptionOrderBatch = subscriptionOrderBatch;
    }

    @PostMapping("/subscription-order")
    public ResponseEntity<BatchResult> run(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {

        LocalDate date = (targetDate != null) ? targetDate : LocalDate.now();
        BatchResult result = subscriptionOrderBatch.execute(date);
        return ResponseEntity.ok(result);
    }
}
