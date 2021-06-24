package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import software.cstl.domain.enumeration.GenderType;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "father_or_husband")
    private String fatherOrHusband;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private GenderType sex;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "guardians_name")
    private String guardiansName;

    @Column(name = "chassis_no")
    private String chassisNo;

    @Column(name = "engine_no")
    private String engineNo;

    @Column(name = "years_of_mfg")
    private Integer yearsOfMfg;

    @Column(name = "pre_regn_no")
    private String preRegnNo;

    @Column(name = "po_or_bank")
    private String poOrBank;

    @Column(name = "voter_id_no")
    private String voterIdNo;

    @Lob
    @Column(name = "voter_id_attachment")
    private byte[] voterIdAttachment;

    @Column(name = "voter_id_attachment_content_type")
    private String voterIdAttachmentContentType;

    @Lob
    @Column(name = "passport_attachment")
    private byte[] passportAttachment;

    @Column(name = "passport_attachment_content_type")
    private String passportAttachmentContentType;

    @Lob
    @Column(name = "birth_certificate_attachment")
    private byte[] birthCertificateAttachment;

    @Column(name = "birth_certificate_attachment_content_type")
    private String birthCertificateAttachmentContentType;

    @Lob
    @Column(name = "gass_or_water_or_electricity_bill")
    private byte[] gassOrWaterOrElectricityBill;

    @Column(name = "gass_or_water_or_electricity_bill_content_type")
    private String gassOrWaterOrElectricityBillContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Vehicle vehicle;

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

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherOrHusband() {
        return fatherOrHusband;
    }

    public Customer fatherOrHusband(String fatherOrHusband) {
        this.fatherOrHusband = fatherOrHusband;
        return this;
    }

    public void setFatherOrHusband(String fatherOrHusband) {
        this.fatherOrHusband = fatherOrHusband;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GenderType getSex() {
        return sex;
    }

    public Customer sex(GenderType sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(GenderType sex) {
        this.sex = sex;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Customer phoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getNationality() {
        return nationality;
    }

    public Customer nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Customer dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGuardiansName() {
        return guardiansName;
    }

    public Customer guardiansName(String guardiansName) {
        this.guardiansName = guardiansName;
        return this;
    }

    public void setGuardiansName(String guardiansName) {
        this.guardiansName = guardiansName;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public Customer chassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
        return this;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public Customer engineNo(String engineNo) {
        this.engineNo = engineNo;
        return this;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public Integer getYearsOfMfg() {
        return yearsOfMfg;
    }

    public Customer yearsOfMfg(Integer yearsOfMfg) {
        this.yearsOfMfg = yearsOfMfg;
        return this;
    }

    public void setYearsOfMfg(Integer yearsOfMfg) {
        this.yearsOfMfg = yearsOfMfg;
    }

    public String getPreRegnNo() {
        return preRegnNo;
    }

    public Customer preRegnNo(String preRegnNo) {
        this.preRegnNo = preRegnNo;
        return this;
    }

    public void setPreRegnNo(String preRegnNo) {
        this.preRegnNo = preRegnNo;
    }

    public String getPoOrBank() {
        return poOrBank;
    }

    public Customer poOrBank(String poOrBank) {
        this.poOrBank = poOrBank;
        return this;
    }

    public void setPoOrBank(String poOrBank) {
        this.poOrBank = poOrBank;
    }

    public String getVoterIdNo() {
        return voterIdNo;
    }

    public Customer voterIdNo(String voterIdNo) {
        this.voterIdNo = voterIdNo;
        return this;
    }

    public void setVoterIdNo(String voterIdNo) {
        this.voterIdNo = voterIdNo;
    }

    public byte[] getVoterIdAttachment() {
        return voterIdAttachment;
    }

    public Customer voterIdAttachment(byte[] voterIdAttachment) {
        this.voterIdAttachment = voterIdAttachment;
        return this;
    }

    public void setVoterIdAttachment(byte[] voterIdAttachment) {
        this.voterIdAttachment = voterIdAttachment;
    }

    public String getVoterIdAttachmentContentType() {
        return voterIdAttachmentContentType;
    }

    public Customer voterIdAttachmentContentType(String voterIdAttachmentContentType) {
        this.voterIdAttachmentContentType = voterIdAttachmentContentType;
        return this;
    }

    public void setVoterIdAttachmentContentType(String voterIdAttachmentContentType) {
        this.voterIdAttachmentContentType = voterIdAttachmentContentType;
    }

    public byte[] getPassportAttachment() {
        return passportAttachment;
    }

    public Customer passportAttachment(byte[] passportAttachment) {
        this.passportAttachment = passportAttachment;
        return this;
    }

    public void setPassportAttachment(byte[] passportAttachment) {
        this.passportAttachment = passportAttachment;
    }

    public String getPassportAttachmentContentType() {
        return passportAttachmentContentType;
    }

    public Customer passportAttachmentContentType(String passportAttachmentContentType) {
        this.passportAttachmentContentType = passportAttachmentContentType;
        return this;
    }

    public void setPassportAttachmentContentType(String passportAttachmentContentType) {
        this.passportAttachmentContentType = passportAttachmentContentType;
    }

    public byte[] getBirthCertificateAttachment() {
        return birthCertificateAttachment;
    }

    public Customer birthCertificateAttachment(byte[] birthCertificateAttachment) {
        this.birthCertificateAttachment = birthCertificateAttachment;
        return this;
    }

    public void setBirthCertificateAttachment(byte[] birthCertificateAttachment) {
        this.birthCertificateAttachment = birthCertificateAttachment;
    }

    public String getBirthCertificateAttachmentContentType() {
        return birthCertificateAttachmentContentType;
    }

    public Customer birthCertificateAttachmentContentType(String birthCertificateAttachmentContentType) {
        this.birthCertificateAttachmentContentType = birthCertificateAttachmentContentType;
        return this;
    }

    public void setBirthCertificateAttachmentContentType(String birthCertificateAttachmentContentType) {
        this.birthCertificateAttachmentContentType = birthCertificateAttachmentContentType;
    }

    public byte[] getGassOrWaterOrElectricityBill() {
        return gassOrWaterOrElectricityBill;
    }

    public Customer gassOrWaterOrElectricityBill(byte[] gassOrWaterOrElectricityBill) {
        this.gassOrWaterOrElectricityBill = gassOrWaterOrElectricityBill;
        return this;
    }

    public void setGassOrWaterOrElectricityBill(byte[] gassOrWaterOrElectricityBill) {
        this.gassOrWaterOrElectricityBill = gassOrWaterOrElectricityBill;
    }

    public String getGassOrWaterOrElectricityBillContentType() {
        return gassOrWaterOrElectricityBillContentType;
    }

    public Customer gassOrWaterOrElectricityBillContentType(String gassOrWaterOrElectricityBillContentType) {
        this.gassOrWaterOrElectricityBillContentType = gassOrWaterOrElectricityBillContentType;
        return this;
    }

    public void setGassOrWaterOrElectricityBillContentType(String gassOrWaterOrElectricityBillContentType) {
        this.gassOrWaterOrElectricityBillContentType = gassOrWaterOrElectricityBillContentType;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Vehicle vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fatherOrHusband='" + getFatherOrHusband() + "'" +
            ", address='" + getAddress() + "'" +
            ", sex='" + getSex() + "'" +
            ", phoneNo='" + getPhoneNo() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", guardiansName='" + getGuardiansName() + "'" +
            ", chassisNo='" + getChassisNo() + "'" +
            ", engineNo='" + getEngineNo() + "'" +
            ", yearsOfMfg=" + getYearsOfMfg() +
            ", preRegnNo='" + getPreRegnNo() + "'" +
            ", poOrBank='" + getPoOrBank() + "'" +
            ", voterIdNo='" + getVoterIdNo() + "'" +
            ", voterIdAttachment='" + getVoterIdAttachment() + "'" +
            ", voterIdAttachmentContentType='" + getVoterIdAttachmentContentType() + "'" +
            ", passportAttachment='" + getPassportAttachment() + "'" +
            ", passportAttachmentContentType='" + getPassportAttachmentContentType() + "'" +
            ", birthCertificateAttachment='" + getBirthCertificateAttachment() + "'" +
            ", birthCertificateAttachmentContentType='" + getBirthCertificateAttachmentContentType() + "'" +
            ", gassOrWaterOrElectricityBill='" + getGassOrWaterOrElectricityBill() + "'" +
            ", gassOrWaterOrElectricityBillContentType='" + getGassOrWaterOrElectricityBillContentType() + "'" +
            "}";
    }
}
