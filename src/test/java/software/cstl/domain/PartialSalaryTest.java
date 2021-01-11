package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class PartialSalaryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartialSalary.class);
        PartialSalary partialSalary1 = new PartialSalary();
        partialSalary1.setId(1L);
        PartialSalary partialSalary2 = new PartialSalary();
        partialSalary2.setId(partialSalary1.getId());
        assertThat(partialSalary1).isEqualTo(partialSalary2);
        partialSalary2.setId(2L);
        assertThat(partialSalary1).isNotEqualTo(partialSalary2);
        partialSalary1.setId(null);
        assertThat(partialSalary1).isNotEqualTo(partialSalary2);
    }
}
