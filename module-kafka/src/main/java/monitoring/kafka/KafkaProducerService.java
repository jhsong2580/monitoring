package monitoring.kafka;

import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component

@Slf4j
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaProducer kafkaCustomProducer;
    private final KafkaProducer kafkaNormalProducer ;

    public KafkaProducerService(
        @Qualifier("pangyoCustomProducer") KafkaProducer kafkaCustomProducer,
        @Qualifier("simpleProducer") KafkaProducer kafkaNormalProducer
    ) {
        this.kafkaCustomProducer = kafkaCustomProducer;
        this.kafkaNormalProducer = kafkaNormalProducer;
    }

    public void produce(String topic, String message) {

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);

        kafkaCustomProducer.send(record);
        logger.info("{}", record);
    }

    //./bin/kafka-console-consumer.sh  --bootstrap-server sjh-1:9092 --topic localtest --from-beginning --property print.key-true --property key.separator=','
    public void produce(String topic, String message, String key){
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key,
            message);

        kafkaCustomProducer.send(record);
        logger.info("{}", record);
    }

    // partition number를 직접 설정하면 key에 대한 hash값이 아닌(파티셔너가 동작하지 않음) 직접 넣을 파티션을 정함.
    public void produce(String topic, String message, String key, int partitionNumber){
        ProducerRecord<String, String> record = new ProducerRecord<>(topic,
            partitionNumber, key, message);
        kafkaCustomProducer.send(record);
        logger.info("{}", record);
    }

    public void produceAndSyncCallback(String topic, String message){
        ProducerRecord<Object, String> record = new ProducerRecord<>(topic,
            message);
        try {
            RecordMetadata metadata = (RecordMetadata) kafkaNormalProducer.send(record).get();
            // kafka bootstrap 설정과 Producer에 acks 가 1이여야 (동기화 설정을 켜야) 동작한다.
            //// application이 올라올때 producer 설정에 "acks = 1" 이 동작할때 확인할수 있다.
            //// acks=1 일때 offset이 -1로 설정되어 나온다. 0#-1 -> 0번 파티션에 저장은 했지만 응답을 받지 못해 offset은 알수 없다.
            //metaData -> localtest-0@22
            //// localtest : message
            ///  0@22 : 0 partition의 22번 Offset

            logger.info("{}", metadata.toString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
