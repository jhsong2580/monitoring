package monitoring.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    @Qualifier("mykafka")
    private final KafkaProducer kafkaProducer;

    public void produce(String topic, String message){
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);

        kafkaProducer.send(record);
        logger.info("{}", record);
    }
}
