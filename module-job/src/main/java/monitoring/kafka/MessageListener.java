package monitoring.kafka;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {


    @KafkaListener(topics = "localtest", groupId = "local-group", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(
        ConsumerRecord<String, String> data,
        Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
        @Header(value = KafkaHeaders.MESSAGE_KEY,required = false) String messageKey
        ) {
        try{
            log.info("{}", data.toString());
            log.info("partition : {}", partition);
            log.info("partition : {}", data.partition());
            log.info("topuc : {}", topic);
            log.info("topuc : {}", data.topic());
            log.info("message key {}", messageKey);
            log.info("message key {}", data.key());

            acknowledgment.acknowledge();

        }catch(Exception e){
            log.error("consumer consuming exception : " + e);
        }

    }

    @KafkaListener(topics = "localtest", groupId = "local-batch-group1", containerFactory = "kafkaBatchListenerContainerFactory")
    public void onMessage(
        List<ConsumerRecord<String, String>> datas
    ){
        log.info("=== batch start===");
        for (ConsumerRecord<String, String> data : datas) {
            log.info("batch data : {}", data.toString());
        }
        log.info("=== batch end===");
    }
}

