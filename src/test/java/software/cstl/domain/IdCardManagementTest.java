package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class IdCardManagementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdCardManagement.class);
        IdCardManagement idCardManagement1 = new IdCardManagement();
        idCardManagement1.setId(1L);
        IdCardManagement idCardManagement2 = new IdCardManagement();
        idCardManagement2.setId(idCardManagement1.getId());
        assertThat(idCardManagement1).isEqualTo(idCardManagement2);
        idCardManagement2.setId(2L);
        assertThat(idCardManagement1).isNotEqualTo(idCardManagement2);
        idCardManagement1.setId(null);
        assertThat(idCardManagement1).isNotEqualTo(idCardManagement2);
    }
}
