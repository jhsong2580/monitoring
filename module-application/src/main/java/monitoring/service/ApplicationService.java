package monitoring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import monitoring.domain.Station;
import monitoring.dto.StationDetailDTO;
import monitoring.kafka.KafkaProducerService;
import monitoring.repository.StationRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final StationRepository stationRepository;
    private final KafkaProducerService kafkaProducerService;
    private boolean appendOnce = false;

    @Cacheable(value = "station", key = "'stations'")
    @Transactional
    public List<StationDetailDTO> details() {
        List<Station> stations = stationRepository.findAll();

        return stations.stream()
            .map(
                station -> new StationDetailDTO(station.getName())
            ).collect(Collectors.toList());
    }

    public void sleep() {
        try {
            kafkaProducerService.produce("localtest", "hello!!!!");
            Thread.sleep(3_000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void append() {
        if (appendOnce) {
            return;
        }
        appendOnce = true;

        List<Station> datas = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            datas.add(new Station(UUID.randomUUID().toString()));
        }

        stationRepository.saveAll(datas);
    }
}
