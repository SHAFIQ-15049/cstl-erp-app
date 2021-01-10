package software.cstl.domain;

import org.junit.jupiter.api.Test;
import software.cstl.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attendance.class);
        Attendance attendance1 = new Attendance();
        attendance1.setId(1L);
        Attendance attendance2 = new Attendance();
        attendance2.setId(attendance1.getId());
        assertThat(attendance1).isEqualTo(attendance2);
        attendance2.setId(2L);
        assertThat(attendance1).isNotEqualTo(attendance2);
        attendance1.setId(null);
        assertThat(attendance1).isNotEqualTo(attendance2);
    }
}
