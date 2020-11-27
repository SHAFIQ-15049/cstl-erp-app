package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class EducationalInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationalInfo.class);
        EducationalInfo educationalInfo1 = new EducationalInfo();
        educationalInfo1.setId(1L);
        EducationalInfo educationalInfo2 = new EducationalInfo();
        educationalInfo2.setId(educationalInfo1.getId());
        assertThat(educationalInfo1).isEqualTo(educationalInfo2);
        educationalInfo2.setId(2L);
        assertThat(educationalInfo1).isNotEqualTo(educationalInfo2);
        educationalInfo1.setId(null);
        assertThat(educationalInfo1).isNotEqualTo(educationalInfo2);
    }
}
