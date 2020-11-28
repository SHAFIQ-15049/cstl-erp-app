package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A EducationalInfo.
 */
@Entity
@Table(name = "educational_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EducationalInfo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "serial", nullable = false)
    private Integer serial;

    @NotNull
    @Column(name = "degree", nullable = false)
    private String degree;

    @NotNull
    @Column(name = "institution", nullable = false)
    private String institution;

    @NotNull
    @Column(name = "passing_year", nullable = false)
    private Integer passingYear;

    @Column(name = "course_duration")
    private Integer courseDuration;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "educationalInfos", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerial() {
        return serial;
    }

    public EducationalInfo serial(Integer serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getDegree() {
        return degree;
    }

    public EducationalInfo degree(String degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public EducationalInfo institution(String institution) {
        this.institution = institution;
        return this;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Integer getPassingYear() {
        return passingYear;
    }

    public EducationalInfo passingYear(Integer passingYear) {
        this.passingYear = passingYear;
        return this;
    }

    public void setPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
    }

    public Integer getCourseDuration() {
        return courseDuration;
    }

    public EducationalInfo courseDuration(Integer courseDuration) {
        this.courseDuration = courseDuration;
        return this;
    }

    public void setCourseDuration(Integer courseDuration) {
        this.courseDuration = courseDuration;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public EducationalInfo attachment(byte[] attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public EducationalInfo attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EducationalInfo employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EducationalInfo)) {
            return false;
        }
        return id != null && id.equals(((EducationalInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationalInfo{" +
            "id=" + getId() +
            ", serial=" + getSerial() +
            ", degree='" + getDegree() + "'" +
            ", institution='" + getInstitution() + "'" +
            ", passingYear=" + getPassingYear() +
            ", courseDuration=" + getCourseDuration() +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            "}";
    }
}
