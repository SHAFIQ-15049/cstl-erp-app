package software.cstl.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import software.cstl.CodeNodeErpApp;
import software.cstl.domain.AttendanceDataUpload;
import software.cstl.repository.AttendanceDataUploadRepository;
import software.cstl.service.AttendanceDataUploadService;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AttendanceDataUploadResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttendanceDataUploadResourceIT {

    private static final byte[] DEFAULT_FILE_UPLOAD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_UPLOAD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_UPLOAD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_UPLOAD_CONTENT_TYPE = "image/png";

    @Autowired
    private AttendanceDataUploadRepository attendanceDataUploadRepository;

    @Autowired
    private AttendanceDataUploadService attendanceDataUploadService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendanceDataUploadMockMvc;

    private AttendanceDataUpload attendanceDataUpload;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceDataUpload createEntity(EntityManager em) {
        AttendanceDataUpload attendanceDataUpload = new AttendanceDataUpload()
            .fileUpload(DEFAULT_FILE_UPLOAD)
            .fileUploadContentType(DEFAULT_FILE_UPLOAD_CONTENT_TYPE);
        return attendanceDataUpload;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceDataUpload createUpdatedEntity(EntityManager em) {
        AttendanceDataUpload attendanceDataUpload = new AttendanceDataUpload()
            .fileUpload(UPDATED_FILE_UPLOAD)
            .fileUploadContentType(UPDATED_FILE_UPLOAD_CONTENT_TYPE);
        return attendanceDataUpload;
    }

    @BeforeEach
    public void initTest() {
        attendanceDataUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendanceDataUpload() throws Exception {
        int databaseSizeBeforeCreate = attendanceDataUploadRepository.findAll().size();
        // Create the AttendanceDataUpload
        restAttendanceDataUploadMockMvc.perform(post("/api/attendance-data-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDataUpload)))
            .andExpect(status().isCreated());

        // Validate the AttendanceDataUpload in the database
        List<AttendanceDataUpload> attendanceDataUploadList = attendanceDataUploadRepository.findAll();
        assertThat(attendanceDataUploadList).hasSize(databaseSizeBeforeCreate + 1);
        AttendanceDataUpload testAttendanceDataUpload = attendanceDataUploadList.get(attendanceDataUploadList.size() - 1);
        assertThat(testAttendanceDataUpload.getFileUpload()).isEqualTo(DEFAULT_FILE_UPLOAD);
        assertThat(testAttendanceDataUpload.getFileUploadContentType()).isEqualTo(DEFAULT_FILE_UPLOAD_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAttendanceDataUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceDataUploadRepository.findAll().size();

        // Create the AttendanceDataUpload with an existing ID
        attendanceDataUpload.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceDataUploadMockMvc.perform(post("/api/attendance-data-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDataUpload)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceDataUpload in the database
        List<AttendanceDataUpload> attendanceDataUploadList = attendanceDataUploadRepository.findAll();
        assertThat(attendanceDataUploadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttendanceDataUploads() throws Exception {
        // Initialize the database
        attendanceDataUploadRepository.saveAndFlush(attendanceDataUpload);

        // Get all the attendanceDataUploadList
        restAttendanceDataUploadMockMvc.perform(get("/api/attendance-data-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceDataUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileUploadContentType").value(hasItem(DEFAULT_FILE_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_UPLOAD))));
    }

    @Test
    @Transactional
    public void getAttendanceDataUpload() throws Exception {
        // Initialize the database
        attendanceDataUploadRepository.saveAndFlush(attendanceDataUpload);

        // Get the attendanceDataUpload
        restAttendanceDataUploadMockMvc.perform(get("/api/attendance-data-uploads/{id}", attendanceDataUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendanceDataUpload.getId().intValue()))
            .andExpect(jsonPath("$.fileUploadContentType").value(DEFAULT_FILE_UPLOAD_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileUpload").value(Base64Utils.encodeToString(DEFAULT_FILE_UPLOAD)));
    }
    @Test
    @Transactional
    public void getNonExistingAttendanceDataUpload() throws Exception {
        // Get the attendanceDataUpload
        restAttendanceDataUploadMockMvc.perform(get("/api/attendance-data-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendanceDataUpload() throws Exception {
        // Initialize the database
        attendanceDataUploadService.save(attendanceDataUpload);

        int databaseSizeBeforeUpdate = attendanceDataUploadRepository.findAll().size();

        // Update the attendanceDataUpload
        AttendanceDataUpload updatedAttendanceDataUpload = attendanceDataUploadRepository.findById(attendanceDataUpload.getId()).get();
        // Disconnect from session so that the updates on updatedAttendanceDataUpload are not directly saved in db
        em.detach(updatedAttendanceDataUpload);
        updatedAttendanceDataUpload
            .fileUpload(UPDATED_FILE_UPLOAD)
            .fileUploadContentType(UPDATED_FILE_UPLOAD_CONTENT_TYPE);

        restAttendanceDataUploadMockMvc.perform(put("/api/attendance-data-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttendanceDataUpload)))
            .andExpect(status().isOk());

        // Validate the AttendanceDataUpload in the database
        List<AttendanceDataUpload> attendanceDataUploadList = attendanceDataUploadRepository.findAll();
        assertThat(attendanceDataUploadList).hasSize(databaseSizeBeforeUpdate);
        AttendanceDataUpload testAttendanceDataUpload = attendanceDataUploadList.get(attendanceDataUploadList.size() - 1);
        assertThat(testAttendanceDataUpload.getFileUpload()).isEqualTo(UPDATED_FILE_UPLOAD);
        assertThat(testAttendanceDataUpload.getFileUploadContentType()).isEqualTo(UPDATED_FILE_UPLOAD_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendanceDataUpload() throws Exception {
        int databaseSizeBeforeUpdate = attendanceDataUploadRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceDataUploadMockMvc.perform(put("/api/attendance-data-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDataUpload)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceDataUpload in the database
        List<AttendanceDataUpload> attendanceDataUploadList = attendanceDataUploadRepository.findAll();
        assertThat(attendanceDataUploadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendanceDataUpload() throws Exception {
        // Initialize the database
        attendanceDataUploadService.save(attendanceDataUpload);

        int databaseSizeBeforeDelete = attendanceDataUploadRepository.findAll().size();

        // Delete the attendanceDataUpload
        restAttendanceDataUploadMockMvc.perform(delete("/api/attendance-data-uploads/{id}", attendanceDataUpload.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendanceDataUpload> attendanceDataUploadList = attendanceDataUploadRepository.findAll();
        assertThat(attendanceDataUploadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
