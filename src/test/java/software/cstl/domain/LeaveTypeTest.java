package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class LeaveTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveType.class);
        LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(1L);
        LeaveType leaveType2 = new LeaveType();
        leaveType2.setId(leaveType1.getId());
        assertThat(leaveType1).isEqualTo(leaveType2);
        leaveType2.setId(2L);
        assertThat(leaveType1).isNotEqualTo(leaveType2);
        leaveType1.setId(null);
        assertThat(leaveType1).isNotEqualTo(leaveType2);
    }
}
