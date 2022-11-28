package monitoring.repository;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import monitoring.domain.Section;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomSectionRepositoryImpl implements CustomSectionRepository
{
    private final EntityManager em;

    @Override
    public List<Section> findAllWithJoin() {
        return em.createQuery(
                "select s from Section s join fetch s.downStation join fetch s.upStation join fetch s.line"
                , Section.class)
            .getResultList();

    }
}
