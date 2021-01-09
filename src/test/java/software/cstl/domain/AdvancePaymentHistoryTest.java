package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class AdvancePaymentHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdvancePaymentHistory.class);
        AdvancePaymentHistory advancePaymentHistory1 = new AdvancePaymentHistory();
        advancePaymentHistory1.setId(1L);
        AdvancePaymentHistory advancePaymentHistory2 = new AdvancePaymentHistory();
        advancePaymentHistory2.setId(advancePaymentHistory1.getId());
        assertThat(advancePaymentHistory1).isEqualTo(advancePaymentHistory2);
        advancePaymentHistory2.setId(2L);
        assertThat(advancePaymentHistory1).isNotEqualTo(advancePaymentHistory2);
        advancePaymentHistory1.setId(null);
        assertThat(advancePaymentHistory1).isNotEqualTo(advancePaymentHistory2);
    }
}
