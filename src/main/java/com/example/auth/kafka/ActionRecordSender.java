package com.example.auth.kafka;

import com.example.avro.ActionRecord;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionRecordSender {

    private final KafkaTemplate<String, SpecificRecordBase> kafkaTemplate;

    @Value("${app.topic}")
    private String topic;

    public void sendMessage(ActionRecord message) {

        kafkaTemplate.send(topic, message.getUsername().toString(), message);
    }
}
