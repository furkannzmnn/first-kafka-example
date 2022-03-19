package org.kafka.messagebus.configure;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaBean {

    @Value(value = "${kafka.bootstrap.servers}")
    private String bootstrapServers;

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public KafkaBean(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> map = new HashMap<>();
        map.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(map);
    }


    @Bean
    public NewTopic topic() {
        return new NewTopic("test", 1, (short) 1);
    }


}
