package monitoring.controller;

import lombok.RequiredArgsConstructor;
import monitoring.kafka.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class KafkaTestController {
    private final KafkaProducerService kafkaProducerService;


    @GetMapping("/api/kafka/produce")
    public ResponseEntity produce(){
        kafkaProducerService.produce("localtest", "hello");
        return ResponseEntity.ok().build();
    }
}
