package software.cstl.repository.search;

import software.cstl.domain.Designation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Designation} entity.
 */
public interface DesignationSearchRepository extends ElasticsearchRepository<Designation, Long> {
}
