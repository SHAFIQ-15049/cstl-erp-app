package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class WeekendTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Weekend.class);
        Weekend weekend1 = new Weekend();
        weekend1.setId(1L);
        Weekend weekend2 = new Weekend();
        weekend2.setId(weekend1.getId());
        assertThat(weekend1).isEqualTo(weekend2);
        weekend2.setId(2L);
        assertThat(weekend1).isNotEqualTo(weekend2);
        weekend1.setId(null);
        assertThat(weekend1).isNotEqualTo(weekend2);
    }
}
