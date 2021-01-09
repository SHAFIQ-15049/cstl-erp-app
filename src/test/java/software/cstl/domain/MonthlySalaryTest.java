package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class MonthlySalaryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlySalary.class);
        MonthlySalary monthlySalary1 = new MonthlySalary();
        monthlySalary1.setId(1L);
        MonthlySalary monthlySalary2 = new MonthlySalary();
        monthlySalary2.setId(monthlySalary1.getId());
        assertThat(monthlySalary1).isEqualTo(monthlySalary2);
        monthlySalary2.setId(2L);
        assertThat(monthlySalary1).isNotEqualTo(monthlySalary2);
        monthlySalary1.setId(null);
        assertThat(monthlySalary1).isNotEqualTo(monthlySalary2);
    }
}
