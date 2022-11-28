package monitoring.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import monitoring.domain.Section;
import monitoring.dto.SectionDetailDTO;
import monitoring.repository.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {
    private final SectionRepository sectionRepository;

    public List<SectionDetailDTO> details(){
        List<Section> sections = sectionRepository.findAllWithJoin();
        return sections.stream()
            .map(SectionDetailDTO::from)
            .collect(Collectors.toList());
    }

    public void sleep(){
        try {
            Thread.sleep(3_000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
