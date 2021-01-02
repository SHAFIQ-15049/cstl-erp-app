package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import software.cstl.domain.enumeration.ActiveStatus;

/**
 * A DefaultAllowance.
 */
@Entity
@Table(name = "default_allowance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DefaultAllowance extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "basic", precision = 21, scale = 2)
    private BigDecimal basic;

    @Column(name = "basic_percent", precision = 21, scale = 2)
    private BigDecimal basicPercent;

    @NotNull
    @Column(name = "total_allowance", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalAllowance;

    @Column(name = "medical_allowance", precision = 21, scale = 2)
    private BigDecimal medicalAllowance;

    @Column(name = "medical_allowance_percent", precision = 21, scale = 2)
    private BigDecimal medicalAllowancePercent;

    @Column(name = "convince_allowance", precision = 21, scale = 2)
    private BigDecimal convinceAllowance;

    @Column(name = "convince_allowance_percent", precision = 21, scale = 2)
    private BigDecimal convinceAllowancePercent;

    @Column(name = "food_allowance", precision = 21, scale = 2)
    private BigDecimal foodAllowance;

    @Column(name = "food_allowance_percent", precision = 21, scale = 2)
    private BigDecimal foodAllowancePercent;

    @Column(name = "festival_allowance", precision = 21, scale = 2)
    private BigDecimal festivalAllowance;

    @Column(name = "festival_allowance_percent", precision = 21, scale = 2)
    private BigDecimal festivalAllowancePercent;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, unique = true)
    private ActiveStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public DefaultAllowance basic(BigDecimal basic) {
        this.basic = basic;
        return this;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public BigDecimal getBasicPercent() {
        return basicPercent;
    }

    public DefaultAllowance basicPercent(BigDecimal basicPercent) {
        this.basicPercent = basicPercent;
        return this;
    }

    public void setBasicPercent(BigDecimal basicPercent) {
        this.basicPercent = basicPercent;
    }

    public BigDecimal getTotalAllowance() {
        return totalAllowance;
    }

    public DefaultAllowance totalAllowance(BigDecimal totalAllowance) {
        this.totalAllowance = totalAllowance;
        return this;
    }

    public void setTotalAllowance(BigDecimal totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public BigDecimal getMedicalAllowance() {
        return medicalAllowance;
    }

    public DefaultAllowance medicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
        return this;
    }

    public void setMedicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimal getMedicalAllowancePercent() {
        return medicalAllowancePercent;
    }

    public DefaultAllowance medicalAllowancePercent(BigDecimal medicalAllowancePercent) {
        this.medicalAllowancePercent = medicalAllowancePercent;
        return this;
    }

    public void setMedicalAllowancePercent(BigDecimal medicalAllowancePercent) {
        this.medicalAllowancePercent = medicalAllowancePercent;
    }

    public BigDecimal getConvinceAllowance() {
        return convinceAllowance;
    }

    public DefaultAllowance convinceAllowance(BigDecimal convinceAllowance) {
        this.convinceAllowance = convinceAllowance;
        return this;
    }

    public void setConvinceAllowance(BigDecimal convinceAllowance) {
        this.convinceAllowance = convinceAllowance;
    }

    public BigDecimal getConvinceAllowancePercent() {
        return convinceAllowancePercent;
    }

    public DefaultAllowance convinceAllowancePercent(BigDecimal convinceAllowancePercent) {
        this.convinceAllowancePercent = convinceAllowancePercent;
        return this;
    }

    public void setConvinceAllowancePercent(BigDecimal convinceAllowancePercent) {
        this.convinceAllowancePercent = convinceAllowancePercent;
    }

    public BigDecimal getFoodAllowance() {
        return foodAllowance;
    }

    public DefaultAllowance foodAllowance(BigDecimal foodAllowance) {
        this.foodAllowance = foodAllowance;
        return this;
    }

    public void setFoodAllowance(BigDecimal foodAllowance) {
        this.foodAllowance = foodAllowance;
    }

    public BigDecimal getFoodAllowancePercent() {
        return foodAllowancePercent;
    }

    public DefaultAllowance foodAllowancePercent(BigDecimal foodAllowancePercent) {
        this.foodAllowancePercent = foodAllowancePercent;
        return this;
    }

    public void setFoodAllowancePercent(BigDecimal foodAllowancePercent) {
        this.foodAllowancePercent = foodAllowancePercent;
    }

    public BigDecimal getFestivalAllowance() {
        return festivalAllowance;
    }

    public DefaultAllowance festivalAllowance(BigDecimal festivalAllowance) {
        this.festivalAllowance = festivalAllowance;
        return this;
    }

    public void setFestivalAllowance(BigDecimal festivalAllowance) {
        this.festivalAllowance = festivalAllowance;
    }

    public BigDecimal getFestivalAllowancePercent() {
        return festivalAllowancePercent;
    }

    public DefaultAllowance festivalAllowancePercent(BigDecimal festivalAllowancePercent) {
        this.festivalAllowancePercent = festivalAllowancePercent;
        return this;
    }

    public void setFestivalAllowancePercent(BigDecimal festivalAllowancePercent) {
        this.festivalAllowancePercent = festivalAllowancePercent;
    }

    public ActiveStatus getStatus() {
        return status;
    }

    public DefaultAllowance status(ActiveStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ActiveStatus status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultAllowance)) {
            return false;
        }
        return id != null && id.equals(((DefaultAllowance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DefaultAllowance{" +
            "id=" + getId() +
            ", basic=" + getBasic() +
            ", basicPercent=" + getBasicPercent() +
            ", totalAllowance=" + getTotalAllowance() +
            ", medicalAllowance=" + getMedicalAllowance() +
            ", medicalAllowancePercent=" + getMedicalAllowancePercent() +
            ", convinceAllowance=" + getConvinceAllowance() +
            ", convinceAllowancePercent=" + getConvinceAllowancePercent() +
            ", foodAllowance=" + getFoodAllowance() +
            ", foodAllowancePercent=" + getFoodAllowancePercent() +
            ", festivalAllowance=" + getFestivalAllowance() +
            ", festivalAllowancePercent=" + getFestivalAllowancePercent() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
