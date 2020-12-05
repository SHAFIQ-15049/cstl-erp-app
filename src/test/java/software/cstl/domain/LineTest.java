package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class LineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Line.class);
        Line line1 = new Line();
        line1.setId(1L);
        Line line2 = new Line();
        line2.setId(line1.getId());
        assertThat(line1).isEqualTo(line2);
        line2.setId(2L);
        assertThat(line1).isNotEqualTo(line2);
        line1.setId(null);
        assertThat(line1).isNotEqualTo(line2);
    }
}
