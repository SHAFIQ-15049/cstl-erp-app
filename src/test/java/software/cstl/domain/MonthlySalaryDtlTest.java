package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class MonthlySalaryDtlTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlySalaryDtl.class);
        MonthlySalaryDtl monthlySalaryDtl1 = new MonthlySalaryDtl();
        monthlySalaryDtl1.setId(1L);
        MonthlySalaryDtl monthlySalaryDtl2 = new MonthlySalaryDtl();
        monthlySalaryDtl2.setId(monthlySalaryDtl1.getId());
        assertThat(monthlySalaryDtl1).isEqualTo(monthlySalaryDtl2);
        monthlySalaryDtl2.setId(2L);
        assertThat(monthlySalaryDtl1).isNotEqualTo(monthlySalaryDtl2);
        monthlySalaryDtl1.setId(null);
        assertThat(monthlySalaryDtl1).isNotEqualTo(monthlySalaryDtl2);
    }
}
