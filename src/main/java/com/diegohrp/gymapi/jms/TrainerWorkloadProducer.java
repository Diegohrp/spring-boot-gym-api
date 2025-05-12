package com.diegohrp.gymapi.jms;

import com.diegohrp.gymapi.dto.trainer.TrainerWorkloadDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;

@Service
@RequiredArgsConstructor
public class TrainerWorkloadProducer {
    private final JmsTemplate jmsTemplate;
    @Value("${queue.name}")
    private String queueName;

    public void sendWorkload(TrainerWorkloadDto dto) {
        jmsTemplate.convertAndSend(queueName, dto, message -> {
            message.setStringProperty("X-Transaction-Id", MDC.get("transactionId"));
            message.setJMSRedelivered(true);
            message.setIntProperty("JMSXDeliveryCount", 6);
            return message;
        });
    }
}
