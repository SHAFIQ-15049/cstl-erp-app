package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import software.cstl.domain.enumeration.MaritalStatus;

import software.cstl.domain.enumeration.GenderType;

import software.cstl.domain.enumeration.BloodGroupType;

/**
 * A PersonalInfo.
 */
@Entity
@Table(name = "personal_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonalInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "bangla_name", nullable = false)
    private String banglaName;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @NotNull
    @Column(name = "mother_name", nullable = false)
    private String motherName;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "spouse_name")
    private String spouseName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "birth_registration")
    private String birthRegistration;

    @Column(name = "height")
    private Double height;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroupType bloodGroup;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PersonalInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanglaName() {
        return banglaName;
    }

    public PersonalInfo banglaName(String banglaName) {
        this.banglaName = banglaName;
        return this;
    }

    public void setBanglaName(String banglaName) {
        this.banglaName = banglaName;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public PersonalInfo photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public PersonalInfo photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getFatherName() {
        return fatherName;
    }

    public PersonalInfo fatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public PersonalInfo motherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public PersonalInfo maritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public PersonalInfo spouseName(String spouseName) {
        this.spouseName = spouseName;
        return this;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public PersonalInfo dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalId() {
        return nationalId;
    }

    public PersonalInfo nationalId(String nationalId) {
        this.nationalId = nationalId;
        return this;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getBirthRegistration() {
        return birthRegistration;
    }

    public PersonalInfo birthRegistration(String birthRegistration) {
        this.birthRegistration = birthRegistration;
        return this;
    }

    public void setBirthRegistration(String birthRegistration) {
        this.birthRegistration = birthRegistration;
    }

    public Double getHeight() {
        return height;
    }

    public PersonalInfo height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public GenderType getGender() {
        return gender;
    }

    public PersonalInfo gender(GenderType gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public BloodGroupType getBloodGroup() {
        return bloodGroup;
    }

    public PersonalInfo bloodGroup(BloodGroupType bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public void setBloodGroup(BloodGroupType bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public PersonalInfo emergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
        return this;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalInfo)) {
            return false;
        }
        return id != null && id.equals(((PersonalInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", banglaName='" + getBanglaName() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", spouseName='" + getSpouseName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", nationalId='" + getNationalId() + "'" +
            ", birthRegistration='" + getBirthRegistration() + "'" +
            ", height=" + getHeight() +
            ", gender='" + getGender() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", emergencyContact='" + getEmergencyContact() + "'" +
            "}";
    }
}
