package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import software.cstl.domain.enumeration.MaritalStatus;

import software.cstl.domain.enumeration.GenderType;

import software.cstl.domain.enumeration.ReligionType;

import software.cstl.domain.enumeration.BloodGroupType;

/**
 * A PersonalInfo.
 */
@Entity
@Table(name = "personal_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonalInfo extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "photo_id")
    private String photoId;

    @NotNull
    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "father_name_bangla")
    private String fatherNameBangla;

    @NotNull
    @Column(name = "mother_name", nullable = false)
    private String motherName;

    @Column(name = "mother_name_bangla")
    private String motherNameBangla;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "spouse_name")
    private String spouseName;

    @Column(name = "spouse_name_bangla")
    private String spouseNameBangla;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "national_id")
    private String nationalId;

    @Lob
    @Column(name = "national_id_attachment")
    private byte[] nationalIdAttachment;

    @Column(name = "national_id_attachment_content_type")
    private String nationalIdAttachmentContentType;

    @Column(name = "national_id_attachment_id")
    private String nationalIdAttachmentId;

    @Column(name = "birth_registration")
    private String birthRegistration;

    @Lob
    @Column(name = "birth_registration_attachment")
    private byte[] birthRegistrationAttachment;

    @Column(name = "birth_registration_attachment_content_type")
    private String birthRegistrationAttachmentContentType;

    @Column(name = "birth_registration_attachment_id")
    private String birthRegistrationAttachmentId;

    @Column(name = "height")
    private Double height;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion")
    private ReligionType religion;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroupType bloodGroup;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

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

    public String getPhotoId() {
        return photoId;
    }

    public PersonalInfo photoId(String photoId) {
        this.photoId = photoId;
        return this;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
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

    public String getFatherNameBangla() {
        return fatherNameBangla;
    }

    public PersonalInfo fatherNameBangla(String fatherNameBangla) {
        this.fatherNameBangla = fatherNameBangla;
        return this;
    }

    public void setFatherNameBangla(String fatherNameBangla) {
        this.fatherNameBangla = fatherNameBangla;
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

    public String getMotherNameBangla() {
        return motherNameBangla;
    }

    public PersonalInfo motherNameBangla(String motherNameBangla) {
        this.motherNameBangla = motherNameBangla;
        return this;
    }

    public void setMotherNameBangla(String motherNameBangla) {
        this.motherNameBangla = motherNameBangla;
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

    public String getSpouseNameBangla() {
        return spouseNameBangla;
    }

    public PersonalInfo spouseNameBangla(String spouseNameBangla) {
        this.spouseNameBangla = spouseNameBangla;
        return this;
    }

    public void setSpouseNameBangla(String spouseNameBangla) {
        this.spouseNameBangla = spouseNameBangla;
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

    public byte[] getNationalIdAttachment() {
        return nationalIdAttachment;
    }

    public PersonalInfo nationalIdAttachment(byte[] nationalIdAttachment) {
        this.nationalIdAttachment = nationalIdAttachment;
        return this;
    }

    public void setNationalIdAttachment(byte[] nationalIdAttachment) {
        this.nationalIdAttachment = nationalIdAttachment;
    }

    public String getNationalIdAttachmentContentType() {
        return nationalIdAttachmentContentType;
    }

    public PersonalInfo nationalIdAttachmentContentType(String nationalIdAttachmentContentType) {
        this.nationalIdAttachmentContentType = nationalIdAttachmentContentType;
        return this;
    }

    public void setNationalIdAttachmentContentType(String nationalIdAttachmentContentType) {
        this.nationalIdAttachmentContentType = nationalIdAttachmentContentType;
    }

    public String getNationalIdAttachmentId() {
        return nationalIdAttachmentId;
    }

    public PersonalInfo nationalIdAttachmentId(String nationalIdAttachmentId) {
        this.nationalIdAttachmentId = nationalIdAttachmentId;
        return this;
    }

    public void setNationalIdAttachmentId(String nationalIdAttachmentId) {
        this.nationalIdAttachmentId = nationalIdAttachmentId;
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

    public byte[] getBirthRegistrationAttachment() {
        return birthRegistrationAttachment;
    }

    public PersonalInfo birthRegistrationAttachment(byte[] birthRegistrationAttachment) {
        this.birthRegistrationAttachment = birthRegistrationAttachment;
        return this;
    }

    public void setBirthRegistrationAttachment(byte[] birthRegistrationAttachment) {
        this.birthRegistrationAttachment = birthRegistrationAttachment;
    }

    public String getBirthRegistrationAttachmentContentType() {
        return birthRegistrationAttachmentContentType;
    }

    public PersonalInfo birthRegistrationAttachmentContentType(String birthRegistrationAttachmentContentType) {
        this.birthRegistrationAttachmentContentType = birthRegistrationAttachmentContentType;
        return this;
    }

    public void setBirthRegistrationAttachmentContentType(String birthRegistrationAttachmentContentType) {
        this.birthRegistrationAttachmentContentType = birthRegistrationAttachmentContentType;
    }

    public String getBirthRegistrationAttachmentId() {
        return birthRegistrationAttachmentId;
    }

    public PersonalInfo birthRegistrationAttachmentId(String birthRegistrationAttachmentId) {
        this.birthRegistrationAttachmentId = birthRegistrationAttachmentId;
        return this;
    }

    public void setBirthRegistrationAttachmentId(String birthRegistrationAttachmentId) {
        this.birthRegistrationAttachmentId = birthRegistrationAttachmentId;
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

    public ReligionType getReligion() {
        return religion;
    }

    public PersonalInfo religion(ReligionType religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(ReligionType religion) {
        this.religion = religion;
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

    public Employee getEmployee() {
        return employee;
    }

    public PersonalInfo employee(Employee employee) {
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
            ", photoId='" + getPhotoId() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", fatherNameBangla='" + getFatherNameBangla() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", motherNameBangla='" + getMotherNameBangla() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", spouseName='" + getSpouseName() + "'" +
            ", spouseNameBangla='" + getSpouseNameBangla() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", nationalId='" + getNationalId() + "'" +
            ", nationalIdAttachment='" + getNationalIdAttachment() + "'" +
            ", nationalIdAttachmentContentType='" + getNationalIdAttachmentContentType() + "'" +
            ", nationalIdAttachmentId='" + getNationalIdAttachmentId() + "'" +
            ", birthRegistration='" + getBirthRegistration() + "'" +
            ", birthRegistrationAttachment='" + getBirthRegistrationAttachment() + "'" +
            ", birthRegistrationAttachmentContentType='" + getBirthRegistrationAttachmentContentType() + "'" +
            ", birthRegistrationAttachmentId='" + getBirthRegistrationAttachmentId() + "'" +
            ", height=" + getHeight() +
            ", gender='" + getGender() + "'" +
            ", religion='" + getReligion() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", emergencyContact='" + getEmergencyContact() + "'" +
            "}";
    }
}
