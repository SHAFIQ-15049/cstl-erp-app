package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class FestivalAllowancePaymentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FestivalAllowancePayment.class);
        FestivalAllowancePayment festivalAllowancePayment1 = new FestivalAllowancePayment();
        festivalAllowancePayment1.setId(1L);
        FestivalAllowancePayment festivalAllowancePayment2 = new FestivalAllowancePayment();
        festivalAllowancePayment2.setId(festivalAllowancePayment1.getId());
        assertThat(festivalAllowancePayment1).isEqualTo(festivalAllowancePayment2);
        festivalAllowancePayment2.setId(2L);
        assertThat(festivalAllowancePayment1).isNotEqualTo(festivalAllowancePayment2);
        festivalAllowancePayment1.setId(null);
        assertThat(festivalAllowancePayment1).isNotEqualTo(festivalAllowancePayment2);
    }
}
