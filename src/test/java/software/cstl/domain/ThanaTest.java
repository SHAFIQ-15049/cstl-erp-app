package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class ThanaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thana.class);
        Thana thana1 = new Thana();
        thana1.setId(1L);
        Thana thana2 = new Thana();
        thana2.setId(thana1.getId());
        assertThat(thana1).isEqualTo(thana2);
        thana2.setId(2L);
        assertThat(thana1).isNotEqualTo(thana2);
        thana1.setId(null);
        assertThat(thana1).isNotEqualTo(thana2);
    }
}
