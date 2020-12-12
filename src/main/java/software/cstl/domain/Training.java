package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Training.
 */
@Entity
@Table(name = "training")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Training extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "serial", nullable = false)
    private Integer serial;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "training_institute", nullable = false)
    private String trainingInstitute;

    @Column(name = "received_on")
    private LocalDate receivedOn;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "trainings", allowSetters = true)
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

    public Training serial(Integer serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public Training name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainingInstitute() {
        return trainingInstitute;
    }

    public Training trainingInstitute(String trainingInstitute) {
        this.trainingInstitute = trainingInstitute;
        return this;
    }

    public void setTrainingInstitute(String trainingInstitute) {
        this.trainingInstitute = trainingInstitute;
    }

    public LocalDate getReceivedOn() {
        return receivedOn;
    }

    public Training receivedOn(LocalDate receivedOn) {
        this.receivedOn = receivedOn;
        return this;
    }

    public void setReceivedOn(LocalDate receivedOn) {
        this.receivedOn = receivedOn;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public Training attachment(byte[] attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public Training attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Training employee(Employee employee) {
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
        if (!(o instanceof Training)) {
            return false;
        }
        return id != null && id.equals(((Training) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Training{" +
            "id=" + getId() +
            ", serial=" + getSerial() +
            ", name='" + getName() + "'" +
            ", trainingInstitute='" + getTrainingInstitute() + "'" +
            ", receivedOn='" + getReceivedOn() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            "}";
    }
}
