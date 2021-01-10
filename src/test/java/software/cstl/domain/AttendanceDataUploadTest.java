package software.cstl.domain;

import org.junit.jupiter.api.Test;
import software.cstl.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceDataUploadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceDataUpload.class);
        AttendanceDataUpload attendanceDataUpload1 = new AttendanceDataUpload();
        attendanceDataUpload1.setId(1L);
        AttendanceDataUpload attendanceDataUpload2 = new AttendanceDataUpload();
        attendanceDataUpload2.setId(attendanceDataUpload1.getId());
        assertThat(attendanceDataUpload1).isEqualTo(attendanceDataUpload2);
        attendanceDataUpload2.setId(2L);
        assertThat(attendanceDataUpload1).isNotEqualTo(attendanceDataUpload2);
        attendanceDataUpload1.setId(null);
        assertThat(attendanceDataUpload1).isNotEqualTo(attendanceDataUpload2);
    }
}
