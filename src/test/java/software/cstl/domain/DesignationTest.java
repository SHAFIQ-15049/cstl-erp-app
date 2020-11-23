package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class DesignationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Designation.class);
        Designation designation1 = new Designation();
        designation1.setId(1L);
        Designation designation2 = new Designation();
        designation2.setId(designation1.getId());
        assertThat(designation1).isEqualTo(designation2);
        designation2.setId(2L);
        assertThat(designation1).isNotEqualTo(designation2);
        designation1.setId(null);
        assertThat(designation1).isNotEqualTo(designation2);
    }
}
