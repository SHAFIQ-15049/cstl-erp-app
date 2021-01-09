package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.MaritalStatus;
import software.cstl.domain.enumeration.GenderType;
import software.cstl.domain.enumeration.ReligionType;
import software.cstl.domain.enumeration.BloodGroupType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link software.cstl.domain.PersonalInfo} entity. This class is used
 * in {@link software.cstl.web.rest.PersonalInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /personal-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonalInfoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering MaritalStatus
     */
    public static class MaritalStatusFilter extends Filter<MaritalStatus> {

        public MaritalStatusFilter() {
        }

        public MaritalStatusFilter(MaritalStatusFilter filter) {
            super(filter);
        }

        @Override
        public MaritalStatusFilter copy() {
            return new MaritalStatusFilter(this);
        }

    }
    /**
     * Class for filtering GenderType
     */
    public static class GenderTypeFilter extends Filter<GenderType> {

        public GenderTypeFilter() {
        }

        public GenderTypeFilter(GenderTypeFilter filter) {
            super(filter);
        }

        @Override
        public GenderTypeFilter copy() {
            return new GenderTypeFilter(this);
        }

    }
    /**
     * Class for filtering ReligionType
     */
    public static class ReligionTypeFilter extends Filter<ReligionType> {

        public ReligionTypeFilter() {
        }

        public ReligionTypeFilter(ReligionTypeFilter filter) {
            super(filter);
        }

        @Override
        public ReligionTypeFilter copy() {
            return new ReligionTypeFilter(this);
        }

    }
    /**
     * Class for filtering BloodGroupType
     */
    public static class BloodGroupTypeFilter extends Filter<BloodGroupType> {

        public BloodGroupTypeFilter() {
        }

        public BloodGroupTypeFilter(BloodGroupTypeFilter filter) {
            super(filter);
        }

        @Override
        public BloodGroupTypeFilter copy() {
            return new BloodGroupTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter banglaName;

    private StringFilter photoId;

    private StringFilter fatherName;

    private StringFilter fatherNameBangla;

    private StringFilter motherName;

    private StringFilter motherNameBangla;

    private MaritalStatusFilter maritalStatus;

    private StringFilter spouseName;

    private StringFilter spouseNameBangla;

    private LocalDateFilter dateOfBirth;

    private StringFilter nationalId;

    private StringFilter nationalIdAttachmentId;

    private StringFilter birthRegistration;

    private StringFilter birthRegistrationAttachmentId;

    private DoubleFilter height;

    private GenderTypeFilter gender;

    private ReligionTypeFilter religion;

    private BloodGroupTypeFilter bloodGroup;

    private StringFilter emergencyContact;

    private LongFilter employeeId;

    public PersonalInfoCriteria() {
    }

    public PersonalInfoCriteria(PersonalInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.banglaName = other.banglaName == null ? null : other.banglaName.copy();
        this.photoId = other.photoId == null ? null : other.photoId.copy();
        this.fatherName = other.fatherName == null ? null : other.fatherName.copy();
        this.fatherNameBangla = other.fatherNameBangla == null ? null : other.fatherNameBangla.copy();
        this.motherName = other.motherName == null ? null : other.motherName.copy();
        this.motherNameBangla = other.motherNameBangla == null ? null : other.motherNameBangla.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.spouseName = other.spouseName == null ? null : other.spouseName.copy();
        this.spouseNameBangla = other.spouseNameBangla == null ? null : other.spouseNameBangla.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.nationalId = other.nationalId == null ? null : other.nationalId.copy();
        this.nationalIdAttachmentId = other.nationalIdAttachmentId == null ? null : other.nationalIdAttachmentId.copy();
        this.birthRegistration = other.birthRegistration == null ? null : other.birthRegistration.copy();
        this.birthRegistrationAttachmentId = other.birthRegistrationAttachmentId == null ? null : other.birthRegistrationAttachmentId.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.religion = other.religion == null ? null : other.religion.copy();
        this.bloodGroup = other.bloodGroup == null ? null : other.bloodGroup.copy();
        this.emergencyContact = other.emergencyContact == null ? null : other.emergencyContact.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public PersonalInfoCriteria copy() {
        return new PersonalInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getBanglaName() {
        return banglaName;
    }

    public void setBanglaName(StringFilter banglaName) {
        this.banglaName = banglaName;
    }

    public StringFilter getPhotoId() {
        return photoId;
    }

    public void setPhotoId(StringFilter photoId) {
        this.photoId = photoId;
    }

    public StringFilter getFatherName() {
        return fatherName;
    }

    public void setFatherName(StringFilter fatherName) {
        this.fatherName = fatherName;
    }

    public StringFilter getFatherNameBangla() {
        return fatherNameBangla;
    }

    public void setFatherNameBangla(StringFilter fatherNameBangla) {
        this.fatherNameBangla = fatherNameBangla;
    }

    public StringFilter getMotherName() {
        return motherName;
    }

    public void setMotherName(StringFilter motherName) {
        this.motherName = motherName;
    }

    public StringFilter getMotherNameBangla() {
        return motherNameBangla;
    }

    public void setMotherNameBangla(StringFilter motherNameBangla) {
        this.motherNameBangla = motherNameBangla;
    }

    public MaritalStatusFilter getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public StringFilter getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(StringFilter spouseName) {
        this.spouseName = spouseName;
    }

    public StringFilter getSpouseNameBangla() {
        return spouseNameBangla;
    }

    public void setSpouseNameBangla(StringFilter spouseNameBangla) {
        this.spouseNameBangla = spouseNameBangla;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getNationalId() {
        return nationalId;
    }

    public void setNationalId(StringFilter nationalId) {
        this.nationalId = nationalId;
    }

    public StringFilter getNationalIdAttachmentId() {
        return nationalIdAttachmentId;
    }

    public void setNationalIdAttachmentId(StringFilter nationalIdAttachmentId) {
        this.nationalIdAttachmentId = nationalIdAttachmentId;
    }

    public StringFilter getBirthRegistration() {
        return birthRegistration;
    }

    public void setBirthRegistration(StringFilter birthRegistration) {
        this.birthRegistration = birthRegistration;
    }

    public StringFilter getBirthRegistrationAttachmentId() {
        return birthRegistrationAttachmentId;
    }

    public void setBirthRegistrationAttachmentId(StringFilter birthRegistrationAttachmentId) {
        this.birthRegistrationAttachmentId = birthRegistrationAttachmentId;
    }

    public DoubleFilter getHeight() {
        return height;
    }

    public void setHeight(DoubleFilter height) {
        this.height = height;
    }

    public GenderTypeFilter getGender() {
        return gender;
    }

    public void setGender(GenderTypeFilter gender) {
        this.gender = gender;
    }

    public ReligionTypeFilter getReligion() {
        return religion;
    }

    public void setReligion(ReligionTypeFilter religion) {
        this.religion = religion;
    }

    public BloodGroupTypeFilter getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroupTypeFilter bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public StringFilter getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(StringFilter emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonalInfoCriteria that = (PersonalInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(banglaName, that.banglaName) &&
            Objects.equals(photoId, that.photoId) &&
            Objects.equals(fatherName, that.fatherName) &&
            Objects.equals(fatherNameBangla, that.fatherNameBangla) &&
            Objects.equals(motherName, that.motherName) &&
            Objects.equals(motherNameBangla, that.motherNameBangla) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(spouseName, that.spouseName) &&
            Objects.equals(spouseNameBangla, that.spouseNameBangla) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(nationalId, that.nationalId) &&
            Objects.equals(nationalIdAttachmentId, that.nationalIdAttachmentId) &&
            Objects.equals(birthRegistration, that.birthRegistration) &&
            Objects.equals(birthRegistrationAttachmentId, that.birthRegistrationAttachmentId) &&
            Objects.equals(height, that.height) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(bloodGroup, that.bloodGroup) &&
            Objects.equals(emergencyContact, that.emergencyContact) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        banglaName,
        photoId,
        fatherName,
        fatherNameBangla,
        motherName,
        motherNameBangla,
        maritalStatus,
        spouseName,
        spouseNameBangla,
        dateOfBirth,
        nationalId,
        nationalIdAttachmentId,
        birthRegistration,
        birthRegistrationAttachmentId,
        height,
        gender,
        religion,
        bloodGroup,
        emergencyContact,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (banglaName != null ? "banglaName=" + banglaName + ", " : "") +
                (photoId != null ? "photoId=" + photoId + ", " : "") +
                (fatherName != null ? "fatherName=" + fatherName + ", " : "") +
                (fatherNameBangla != null ? "fatherNameBangla=" + fatherNameBangla + ", " : "") +
                (motherName != null ? "motherName=" + motherName + ", " : "") +
                (motherNameBangla != null ? "motherNameBangla=" + motherNameBangla + ", " : "") +
                (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
                (spouseName != null ? "spouseName=" + spouseName + ", " : "") +
                (spouseNameBangla != null ? "spouseNameBangla=" + spouseNameBangla + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
                (nationalId != null ? "nationalId=" + nationalId + ", " : "") +
                (nationalIdAttachmentId != null ? "nationalIdAttachmentId=" + nationalIdAttachmentId + ", " : "") +
                (birthRegistration != null ? "birthRegistration=" + birthRegistration + ", " : "") +
                (birthRegistrationAttachmentId != null ? "birthRegistrationAttachmentId=" + birthRegistrationAttachmentId + ", " : "") +
                (height != null ? "height=" + height + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (religion != null ? "religion=" + religion + ", " : "") +
                (bloodGroup != null ? "bloodGroup=" + bloodGroup + ", " : "") +
                (emergencyContact != null ? "emergencyContact=" + emergencyContact + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
