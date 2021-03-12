package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class LeaveApplicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveApplication.class);
        LeaveApplication leaveApplication1 = new LeaveApplication();
        leaveApplication1.setId(1L);
        LeaveApplication leaveApplication2 = new LeaveApplication();
        leaveApplication2.setId(leaveApplication1.getId());
        assertThat(leaveApplication1).isEqualTo(leaveApplication2);
        leaveApplication2.setId(2L);
        assertThat(leaveApplication1).isNotEqualTo(leaveApplication2);
        leaveApplication1.setId(null);
        assertThat(leaveApplication1).isNotEqualTo(leaveApplication2);
    }
}
