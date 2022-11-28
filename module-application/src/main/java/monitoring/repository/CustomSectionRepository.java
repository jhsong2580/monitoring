package monitoring.repository;

import java.util.List;
import monitoring.domain.Section;

public interface CustomSectionRepository {
    List<Section> findAllWithJoin();
}
