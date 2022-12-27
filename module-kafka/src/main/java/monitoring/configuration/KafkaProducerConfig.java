package monitoring.configuration;

import java.util.Map;
import javax.annotation.PreDestroy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {
    private static final String TOPIC_NAME = "test";
    @Value("${kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @Qualifier("simpleProducer")
    public KafkaProducer simpleProducer(){
        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer(configs);
    }

    @Bean
    @Qualifier("pangyoCustomProducer")
    public KafkaProducer pangyoCustomProducer() {
        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);

        return new KafkaProducer(configs);
    }

    @PreDestroy
    public void destroy(){
        Map<String, KafkaProducer> kafkaProducers = applicationContext.getBeansOfType(
            KafkaProducer.class);

        kafkaProducers.values()
            .forEach(
            kafkaProducer -> kafkaProducer.close()
        );

        //안전한 종료를 위해 close메서드 호출
        ////close메서드는 producer 내의 모든 데이터를 모두 broker로 전달 후 종료한다.
    }
}
