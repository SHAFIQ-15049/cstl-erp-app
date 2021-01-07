package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class FestivalAllowanceTimeLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FestivalAllowanceTimeLine.class);
        FestivalAllowanceTimeLine festivalAllowanceTimeLine1 = new FestivalAllowanceTimeLine();
        festivalAllowanceTimeLine1.setId(1L);
        FestivalAllowanceTimeLine festivalAllowanceTimeLine2 = new FestivalAllowanceTimeLine();
        festivalAllowanceTimeLine2.setId(festivalAllowanceTimeLine1.getId());
        assertThat(festivalAllowanceTimeLine1).isEqualTo(festivalAllowanceTimeLine2);
        festivalAllowanceTimeLine2.setId(2L);
        assertThat(festivalAllowanceTimeLine1).isNotEqualTo(festivalAllowanceTimeLine2);
        festivalAllowanceTimeLine1.setId(null);
        assertThat(festivalAllowanceTimeLine1).isNotEqualTo(festivalAllowanceTimeLine2);
    }
}
