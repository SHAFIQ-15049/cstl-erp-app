package software.cstl.service.dto;

import javax.persistence.Lob;
import java.io.Serializable;

/**
 * A DTO for the {@link software.cstl.domain.AttendanceDataUpload} entity.
 */
public class AttendanceDataUploadDTO implements Serializable {

    private Long id;


    @Lob
    private byte[] fileUpload;

    private String fileUploadContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(byte[] fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getFileUploadContentType() {
        return fileUploadContentType;
    }

    public void setFileUploadContentType(String fileUploadContentType) {
        this.fileUploadContentType = fileUploadContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceDataUploadDTO)) {
            return false;
        }

        return id != null && id.equals(((AttendanceDataUploadDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceDataUploadDTO{" +
            "id=" + getId() +
            ", fileUpload='" + getFileUpload() + "'" +
            "}";
    }
}
