package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class DivisionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Division.class);
        Division division1 = new Division();
        division1.setId(1L);
        Division division2 = new Division();
        division2.setId(division1.getId());
        assertThat(division1).isEqualTo(division2);
        division2.setId(2L);
        assertThat(division1).isNotEqualTo(division2);
        division1.setId(null);
        assertThat(division1).isNotEqualTo(division2);
    }
}
