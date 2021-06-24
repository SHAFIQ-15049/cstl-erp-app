package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.GenderType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link software.cstl.domain.Customer} entity. This class is used
 * in {@link software.cstl.web.rest.CustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCriteria implements Serializable, Criteria {
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter fatherOrHusband;

    private StringFilter address;

    private GenderTypeFilter sex;

    private StringFilter phoneNo;

    private StringFilter nationality;

    private LocalDateFilter dateOfBirth;

    private StringFilter guardiansName;

    private StringFilter chassisNo;

    private StringFilter engineNo;

    private IntegerFilter yearsOfMfg;

    private StringFilter preRegnNo;

    private StringFilter poOrBank;

    private StringFilter voterIdNo;

    private LongFilter vehicleId;

    public CustomerCriteria() {
    }

    public CustomerCriteria(CustomerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.fatherOrHusband = other.fatherOrHusband == null ? null : other.fatherOrHusband.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.sex = other.sex == null ? null : other.sex.copy();
        this.phoneNo = other.phoneNo == null ? null : other.phoneNo.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.guardiansName = other.guardiansName == null ? null : other.guardiansName.copy();
        this.chassisNo = other.chassisNo == null ? null : other.chassisNo.copy();
        this.engineNo = other.engineNo == null ? null : other.engineNo.copy();
        this.yearsOfMfg = other.yearsOfMfg == null ? null : other.yearsOfMfg.copy();
        this.preRegnNo = other.preRegnNo == null ? null : other.preRegnNo.copy();
        this.poOrBank = other.poOrBank == null ? null : other.poOrBank.copy();
        this.voterIdNo = other.voterIdNo == null ? null : other.voterIdNo.copy();
        this.vehicleId = other.vehicleId == null ? null : other.vehicleId.copy();
    }

    @Override
    public CustomerCriteria copy() {
        return new CustomerCriteria(this);
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

    public StringFilter getFatherOrHusband() {
        return fatherOrHusband;
    }

    public void setFatherOrHusband(StringFilter fatherOrHusband) {
        this.fatherOrHusband = fatherOrHusband;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public GenderTypeFilter getSex() {
        return sex;
    }

    public void setSex(GenderTypeFilter sex) {
        this.sex = sex;
    }

    public StringFilter getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(StringFilter phoneNo) {
        this.phoneNo = phoneNo;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getGuardiansName() {
        return guardiansName;
    }

    public void setGuardiansName(StringFilter guardiansName) {
        this.guardiansName = guardiansName;
    }

    public StringFilter getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(StringFilter chassisNo) {
        this.chassisNo = chassisNo;
    }

    public StringFilter getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(StringFilter engineNo) {
        this.engineNo = engineNo;
    }

    public IntegerFilter getYearsOfMfg() {
        return yearsOfMfg;
    }

    public void setYearsOfMfg(IntegerFilter yearsOfMfg) {
        this.yearsOfMfg = yearsOfMfg;
    }

    public StringFilter getPreRegnNo() {
        return preRegnNo;
    }

    public void setPreRegnNo(StringFilter preRegnNo) {
        this.preRegnNo = preRegnNo;
    }

    public StringFilter getPoOrBank() {
        return poOrBank;
    }

    public void setPoOrBank(StringFilter poOrBank) {
        this.poOrBank = poOrBank;
    }

    public StringFilter getVoterIdNo() {
        return voterIdNo;
    }

    public void setVoterIdNo(StringFilter voterIdNo) {
        this.voterIdNo = voterIdNo;
    }

    public LongFilter getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(LongFilter vehicleId) {
        this.vehicleId = vehicleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerCriteria that = (CustomerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(fatherOrHusband, that.fatherOrHusband) &&
            Objects.equals(address, that.address) &&
            Objects.equals(sex, that.sex) &&
            Objects.equals(phoneNo, that.phoneNo) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(guardiansName, that.guardiansName) &&
            Objects.equals(chassisNo, that.chassisNo) &&
            Objects.equals(engineNo, that.engineNo) &&
            Objects.equals(yearsOfMfg, that.yearsOfMfg) &&
            Objects.equals(preRegnNo, that.preRegnNo) &&
            Objects.equals(poOrBank, that.poOrBank) &&
            Objects.equals(voterIdNo, that.voterIdNo)&&
            Objects.equals(vehicleId, that.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        fatherOrHusband,
        address,
        sex,
        phoneNo,
        nationality,
        dateOfBirth,
        guardiansName,
        chassisNo,
        engineNo,
        yearsOfMfg,
        preRegnNo,
        poOrBank,
        voterIdNo,
            vehicleId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (fatherOrHusband != null ? "fatherOrHusband=" + fatherOrHusband + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (sex != null ? "sex=" + sex + ", " : "") +
                (phoneNo != null ? "phoneNo=" + phoneNo + ", " : "") +
                (nationality != null ? "nationality=" + nationality + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
                (guardiansName != null ? "guardiansName=" + guardiansName + ", " : "") +
                (chassisNo != null ? "chassisNo=" + chassisNo + ", " : "") +
                (engineNo != null ? "engineNo=" + engineNo + ", " : "") +
                (yearsOfMfg != null ? "yearsOfMfg=" + yearsOfMfg + ", " : "") +
                (preRegnNo != null ? "preRegnNo=" + preRegnNo + ", " : "") +
                (poOrBank != null ? "poOrBank=" + poOrBank + ", " : "") +
                (voterIdNo != null ? "voterIdNo=" + voterIdNo + ", " : "") +
            (vehicleId != null ? "vehicleId=" + vehicleId + ", " : "") +
            "}";
    }

}
