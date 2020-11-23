package software.cstl.repository.search;

import software.cstl.domain.Grade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Grade} entity.
 */
public interface GradeSearchRepository extends ElasticsearchRepository<Grade, Long> {
}
