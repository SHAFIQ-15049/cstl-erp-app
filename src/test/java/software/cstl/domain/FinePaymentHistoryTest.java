package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class FinePaymentHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinePaymentHistory.class);
        FinePaymentHistory finePaymentHistory1 = new FinePaymentHistory();
        finePaymentHistory1.setId(1L);
        FinePaymentHistory finePaymentHistory2 = new FinePaymentHistory();
        finePaymentHistory2.setId(finePaymentHistory1.getId());
        assertThat(finePaymentHistory1).isEqualTo(finePaymentHistory2);
        finePaymentHistory2.setId(2L);
        assertThat(finePaymentHistory1).isNotEqualTo(finePaymentHistory2);
        finePaymentHistory1.setId(null);
        assertThat(finePaymentHistory1).isNotEqualTo(finePaymentHistory2);
    }
}
