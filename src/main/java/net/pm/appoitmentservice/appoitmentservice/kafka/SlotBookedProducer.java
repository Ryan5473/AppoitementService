package net.pm.appoitmentservice.appoitmentservice.kafka;

import net.pm.appoitmentservice.appoitmentservice.dto.SlotBookedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SlotBookedProducer {

    private final KafkaTemplate<String, SlotBookedEvent> kafkaTemplate;

    public SlotBookedProducer(KafkaTemplate<String, SlotBookedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSlotBookedEvent(SlotBookedEvent event) {
        kafkaTemplate.send("slot-booked-topic", event);
    }
}
