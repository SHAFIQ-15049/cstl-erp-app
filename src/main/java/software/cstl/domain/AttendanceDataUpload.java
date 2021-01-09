package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A AttendanceDataUpload.
 */
@Entity
@Table(name = "attendance_data_upload")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttendanceDataUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(name = "file_upload", nullable = false)
    private byte[] fileUpload;

    @Column(name = "file_upload_content_type", nullable = false)
    private String fileUploadContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFileUpload() {
        return fileUpload;
    }

    public AttendanceDataUpload fileUpload(byte[] fileUpload) {
        this.fileUpload = fileUpload;
        return this;
    }

    public void setFileUpload(byte[] fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getFileUploadContentType() {
        return fileUploadContentType;
    }

    public AttendanceDataUpload fileUploadContentType(String fileUploadContentType) {
        this.fileUploadContentType = fileUploadContentType;
        return this;
    }

    public void setFileUploadContentType(String fileUploadContentType) {
        this.fileUploadContentType = fileUploadContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceDataUpload)) {
            return false;
        }
        return id != null && id.equals(((AttendanceDataUpload) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceDataUpload{" +
            "id=" + getId() +
            ", fileUpload='" + getFileUpload() + "'" +
            ", fileUploadContentType='" + getFileUploadContentType() + "'" +
            "}";
    }
}
