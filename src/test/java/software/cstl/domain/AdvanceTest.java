package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class AdvanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Advance.class);
        Advance advance1 = new Advance();
        advance1.setId(1L);
        Advance advance2 = new Advance();
        advance2.setId(advance1.getId());
        assertThat(advance1).isEqualTo(advance2);
        advance2.setId(2L);
        assertThat(advance1).isNotEqualTo(advance2);
        advance1.setId(null);
        assertThat(advance1).isNotEqualTo(advance2);
    }
}
