package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "present_thana_txt")
    private String presentThanaTxt;

    @Column(name = "present_street")
    private String presentStreet;

    @Column(name = "present_street_bangla")
    private String presentStreetBangla;

    @Column(name = "present_area")
    private String presentArea;

    @Column(name = "present_area_bangla")
    private String presentAreaBangla;

    @Column(name = "present_post_code")
    private Integer presentPostCode;

    @Column(name = "present_post_code_bangla")
    private String presentPostCodeBangla;

    @Column(name = "permanent_thana_txt")
    private String permanentThanaTxt;

    @Column(name = "permanent_street")
    private String permanentStreet;

    @Column(name = "permanent_street_bangla")
    private String permanentStreetBangla;

    @Column(name = "permanent_area")
    private String permanentArea;

    @Column(name = "permanent_area_bangla")
    private String permanentAreaBangla;

    @Column(name = "permanent_post_code")
    private Integer permanentPostCode;

    @Column(name = "permenent_post_code_bangla")
    private String permenentPostCodeBangla;

    @Column(name = "is_same")
    private Boolean isSame;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Division presentDivision;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private District presentDistrict;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Thana presentThana;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Division permanentDivision;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private District permanentDistrict;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Thana permanentThana;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresentThanaTxt() {
        return presentThanaTxt;
    }

    public Address presentThanaTxt(String presentThanaTxt) {
        this.presentThanaTxt = presentThanaTxt;
        return this;
    }

    public void setPresentThanaTxt(String presentThanaTxt) {
        this.presentThanaTxt = presentThanaTxt;
    }

    public String getPresentStreet() {
        return presentStreet;
    }

    public Address presentStreet(String presentStreet) {
        this.presentStreet = presentStreet;
        return this;
    }

    public void setPresentStreet(String presentStreet) {
        this.presentStreet = presentStreet;
    }

    public String getPresentStreetBangla() {
        return presentStreetBangla;
    }

    public Address presentStreetBangla(String presentStreetBangla) {
        this.presentStreetBangla = presentStreetBangla;
        return this;
    }

    public void setPresentStreetBangla(String presentStreetBangla) {
        this.presentStreetBangla = presentStreetBangla;
    }

    public String getPresentArea() {
        return presentArea;
    }

    public Address presentArea(String presentArea) {
        this.presentArea = presentArea;
        return this;
    }

    public void setPresentArea(String presentArea) {
        this.presentArea = presentArea;
    }

    public String getPresentAreaBangla() {
        return presentAreaBangla;
    }

    public Address presentAreaBangla(String presentAreaBangla) {
        this.presentAreaBangla = presentAreaBangla;
        return this;
    }

    public void setPresentAreaBangla(String presentAreaBangla) {
        this.presentAreaBangla = presentAreaBangla;
    }

    public Integer getPresentPostCode() {
        return presentPostCode;
    }

    public Address presentPostCode(Integer presentPostCode) {
        this.presentPostCode = presentPostCode;
        return this;
    }

    public void setPresentPostCode(Integer presentPostCode) {
        this.presentPostCode = presentPostCode;
    }

    public String getPresentPostCodeBangla() {
        return presentPostCodeBangla;
    }

    public Address presentPostCodeBangla(String presentPostCodeBangla) {
        this.presentPostCodeBangla = presentPostCodeBangla;
        return this;
    }

    public void setPresentPostCodeBangla(String presentPostCodeBangla) {
        this.presentPostCodeBangla = presentPostCodeBangla;
    }

    public String getPermanentThanaTxt() {
        return permanentThanaTxt;
    }

    public Address permanentThanaTxt(String permanentThanaTxt) {
        this.permanentThanaTxt = permanentThanaTxt;
        return this;
    }

    public void setPermanentThanaTxt(String permanentThanaTxt) {
        this.permanentThanaTxt = permanentThanaTxt;
    }

    public String getPermanentStreet() {
        return permanentStreet;
    }

    public Address permanentStreet(String permanentStreet) {
        this.permanentStreet = permanentStreet;
        return this;
    }

    public void setPermanentStreet(String permanentStreet) {
        this.permanentStreet = permanentStreet;
    }

    public String getPermanentStreetBangla() {
        return permanentStreetBangla;
    }

    public Address permanentStreetBangla(String permanentStreetBangla) {
        this.permanentStreetBangla = permanentStreetBangla;
        return this;
    }

    public void setPermanentStreetBangla(String permanentStreetBangla) {
        this.permanentStreetBangla = permanentStreetBangla;
    }

    public String getPermanentArea() {
        return permanentArea;
    }

    public Address permanentArea(String permanentArea) {
        this.permanentArea = permanentArea;
        return this;
    }

    public void setPermanentArea(String permanentArea) {
        this.permanentArea = permanentArea;
    }

    public String getPermanentAreaBangla() {
        return permanentAreaBangla;
    }

    public Address permanentAreaBangla(String permanentAreaBangla) {
        this.permanentAreaBangla = permanentAreaBangla;
        return this;
    }

    public void setPermanentAreaBangla(String permanentAreaBangla) {
        this.permanentAreaBangla = permanentAreaBangla;
    }

    public Integer getPermanentPostCode() {
        return permanentPostCode;
    }

    public Address permanentPostCode(Integer permanentPostCode) {
        this.permanentPostCode = permanentPostCode;
        return this;
    }

    public void setPermanentPostCode(Integer permanentPostCode) {
        this.permanentPostCode = permanentPostCode;
    }

    public String getPermenentPostCodeBangla() {
        return permenentPostCodeBangla;
    }

    public Address permenentPostCodeBangla(String permenentPostCodeBangla) {
        this.permenentPostCodeBangla = permenentPostCodeBangla;
        return this;
    }

    public void setPermenentPostCodeBangla(String permenentPostCodeBangla) {
        this.permenentPostCodeBangla = permenentPostCodeBangla;
    }

    public Boolean isIsSame() {
        return isSame;
    }

    public Address isSame(Boolean isSame) {
        this.isSame = isSame;
        return this;
    }

    public void setIsSame(Boolean isSame) {
        this.isSame = isSame;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Address employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Division getPresentDivision() {
        return presentDivision;
    }

    public Address presentDivision(Division division) {
        this.presentDivision = division;
        return this;
    }

    public void setPresentDivision(Division division) {
        this.presentDivision = division;
    }

    public District getPresentDistrict() {
        return presentDistrict;
    }

    public Address presentDistrict(District district) {
        this.presentDistrict = district;
        return this;
    }

    public void setPresentDistrict(District district) {
        this.presentDistrict = district;
    }

    public Thana getPresentThana() {
        return presentThana;
    }

    public Address presentThana(Thana thana) {
        this.presentThana = thana;
        return this;
    }

    public void setPresentThana(Thana thana) {
        this.presentThana = thana;
    }

    public Division getPermanentDivision() {
        return permanentDivision;
    }

    public Address permanentDivision(Division division) {
        this.permanentDivision = division;
        return this;
    }

    public void setPermanentDivision(Division division) {
        this.permanentDivision = division;
    }

    public District getPermanentDistrict() {
        return permanentDistrict;
    }

    public Address permanentDistrict(District district) {
        this.permanentDistrict = district;
        return this;
    }

    public void setPermanentDistrict(District district) {
        this.permanentDistrict = district;
    }

    public Thana getPermanentThana() {
        return permanentThana;
    }

    public Address permanentThana(Thana thana) {
        this.permanentThana = thana;
        return this;
    }

    public void setPermanentThana(Thana thana) {
        this.permanentThana = thana;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", presentThanaTxt='" + getPresentThanaTxt() + "'" +
            ", presentStreet='" + getPresentStreet() + "'" +
            ", presentStreetBangla='" + getPresentStreetBangla() + "'" +
            ", presentArea='" + getPresentArea() + "'" +
            ", presentAreaBangla='" + getPresentAreaBangla() + "'" +
            ", presentPostCode=" + getPresentPostCode() +
            ", presentPostCodeBangla='" + getPresentPostCodeBangla() + "'" +
            ", permanentThanaTxt='" + getPermanentThanaTxt() + "'" +
            ", permanentStreet='" + getPermanentStreet() + "'" +
            ", permanentStreetBangla='" + getPermanentStreetBangla() + "'" +
            ", permanentArea='" + getPermanentArea() + "'" +
            ", permanentAreaBangla='" + getPermanentAreaBangla() + "'" +
            ", permanentPostCode=" + getPermanentPostCode() +
            ", permenentPostCodeBangla='" + getPermenentPostCodeBangla() + "'" +
            ", isSame='" + isIsSame() + "'" +
            "}";
    }
}
