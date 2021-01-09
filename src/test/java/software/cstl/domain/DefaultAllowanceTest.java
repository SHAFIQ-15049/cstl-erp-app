package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class DefaultAllowanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefaultAllowance.class);
        DefaultAllowance defaultAllowance1 = new DefaultAllowance();
        defaultAllowance1.setId(1L);
        DefaultAllowance defaultAllowance2 = new DefaultAllowance();
        defaultAllowance2.setId(defaultAllowance1.getId());
        assertThat(defaultAllowance1).isEqualTo(defaultAllowance2);
        defaultAllowance2.setId(2L);
        assertThat(defaultAllowance1).isNotEqualTo(defaultAllowance2);
        defaultAllowance1.setId(null);
        assertThat(defaultAllowance1).isNotEqualTo(defaultAllowance2);
    }
}
