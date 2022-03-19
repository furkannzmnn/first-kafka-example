package org.kafka.messagebus.api;

import org.kafka.messagebus.service.SendSmsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
public class SendSmsApi {

    private final SendSmsService smsService;

    public SendSmsApi(SendSmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public void sendSms(String phoneNumber, String message) {
        smsService.sendSms(phoneNumber, message);
    }




}
