package software.cstl.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DesignationSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DesignationSearchRepositoryMockConfiguration {

    @MockBean
    private DesignationSearchRepository mockDesignationSearchRepository;

}
