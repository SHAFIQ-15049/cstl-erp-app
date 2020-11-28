package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class ServiceHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceHistory.class);
        ServiceHistory serviceHistory1 = new ServiceHistory();
        serviceHistory1.setId(1L);
        ServiceHistory serviceHistory2 = new ServiceHistory();
        serviceHistory2.setId(serviceHistory1.getId());
        assertThat(serviceHistory1).isEqualTo(serviceHistory2);
        serviceHistory2.setId(2L);
        assertThat(serviceHistory1).isNotEqualTo(serviceHistory2);
        serviceHistory1.setId(null);
        assertThat(serviceHistory1).isNotEqualTo(serviceHistory2);
    }
}
