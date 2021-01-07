package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class FestivalAllowancePaymentDtlTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FestivalAllowancePaymentDtl.class);
        FestivalAllowancePaymentDtl festivalAllowancePaymentDtl1 = new FestivalAllowancePaymentDtl();
        festivalAllowancePaymentDtl1.setId(1L);
        FestivalAllowancePaymentDtl festivalAllowancePaymentDtl2 = new FestivalAllowancePaymentDtl();
        festivalAllowancePaymentDtl2.setId(festivalAllowancePaymentDtl1.getId());
        assertThat(festivalAllowancePaymentDtl1).isEqualTo(festivalAllowancePaymentDtl2);
        festivalAllowancePaymentDtl2.setId(2L);
        assertThat(festivalAllowancePaymentDtl1).isNotEqualTo(festivalAllowancePaymentDtl2);
        festivalAllowancePaymentDtl1.setId(null);
        assertThat(festivalAllowancePaymentDtl1).isNotEqualTo(festivalAllowancePaymentDtl2);
    }
}
