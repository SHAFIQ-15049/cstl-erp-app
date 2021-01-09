package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class FineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fine.class);
        Fine fine1 = new Fine();
        fine1.setId(1L);
        Fine fine2 = new Fine();
        fine2.setId(fine1.getId());
        assertThat(fine1).isEqualTo(fine2);
        fine2.setId(2L);
        assertThat(fine1).isNotEqualTo(fine2);
        fine1.setId(null);
        assertThat(fine1).isNotEqualTo(fine2);
    }
}
