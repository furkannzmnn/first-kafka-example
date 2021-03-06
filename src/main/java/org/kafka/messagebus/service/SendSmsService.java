package org.kafka.messagebus.service;

import org.kafka.messagebus.request.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class SendSmsService {

    private static final Logger log = LoggerFactory.getLogger(SendSmsService.class);

    @Autowired
    private KafkaTemplate<String , SmsRequest> kafkaTemplate;


    public void sendSms(String phoneNumber, String message) {
        ListenableFuture<SendResult<String, SmsRequest>> listenableFuture = kafkaTemplate.send("sms", new SmsRequest(phoneNumber, message));
        listenableFuture.addCallback(
                result -> {
                    assert result != null;
                    log.info("Sent message to Kafka:{} " , result.getProducerRecord().value());
                }
                ,
                ex -> log.error("Exception while sending message to Kafka:{} " , ex.getMessage())

        );
    }

    @KafkaListener(topics = "sms", containerFactory = "greetingKafkaListenerContainerFactory",groupId = "test-consumer-group" )
    public void listenTopic(@Payload SmsRequest message) throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
                log.info("Received message from Kafka:{} " , message.getPhoneNumber());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }).thenApply(s -> "Done")
                .exceptionally(Throwable::getMessage);

        String s = future.get();
        log.error("Received message from Kafka:{} " , s);
    }






}
