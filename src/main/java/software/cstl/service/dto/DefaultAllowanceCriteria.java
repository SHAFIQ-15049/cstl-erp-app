package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.ActiveStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link software.cstl.domain.DefaultAllowance} entity. This class is used
 * in {@link software.cstl.web.rest.DefaultAllowanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /default-allowances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DefaultAllowanceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ActiveStatus
     */
    public static class ActiveStatusFilter extends Filter<ActiveStatus> {

        public ActiveStatusFilter() {
        }

        public ActiveStatusFilter(ActiveStatusFilter filter) {
            super(filter);
        }

        @Override
        public ActiveStatusFilter copy() {
            return new ActiveStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter basic;

    private BigDecimalFilter basicPercent;

    private BigDecimalFilter totalAllowance;

    private BigDecimalFilter medicalAllowance;

    private BigDecimalFilter medicalAllowancePercent;

    private BigDecimalFilter convinceAllowance;

    private BigDecimalFilter convinceAllowancePercent;

    private BigDecimalFilter foodAllowance;

    private BigDecimalFilter foodAllowancePercent;

    private BigDecimalFilter festivalAllowance;

    private BigDecimalFilter festivalAllowancePercent;

    private ActiveStatusFilter status;

    public DefaultAllowanceCriteria() {
    }

    public DefaultAllowanceCriteria(DefaultAllowanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.basic = other.basic == null ? null : other.basic.copy();
        this.basicPercent = other.basicPercent == null ? null : other.basicPercent.copy();
        this.totalAllowance = other.totalAllowance == null ? null : other.totalAllowance.copy();
        this.medicalAllowance = other.medicalAllowance == null ? null : other.medicalAllowance.copy();
        this.medicalAllowancePercent = other.medicalAllowancePercent == null ? null : other.medicalAllowancePercent.copy();
        this.convinceAllowance = other.convinceAllowance == null ? null : other.convinceAllowance.copy();
        this.convinceAllowancePercent = other.convinceAllowancePercent == null ? null : other.convinceAllowancePercent.copy();
        this.foodAllowance = other.foodAllowance == null ? null : other.foodAllowance.copy();
        this.foodAllowancePercent = other.foodAllowancePercent == null ? null : other.foodAllowancePercent.copy();
        this.festivalAllowance = other.festivalAllowance == null ? null : other.festivalAllowance.copy();
        this.festivalAllowancePercent = other.festivalAllowancePercent == null ? null : other.festivalAllowancePercent.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public DefaultAllowanceCriteria copy() {
        return new DefaultAllowanceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getBasic() {
        return basic;
    }

    public void setBasic(BigDecimalFilter basic) {
        this.basic = basic;
    }

    public BigDecimalFilter getBasicPercent() {
        return basicPercent;
    }

    public void setBasicPercent(BigDecimalFilter basicPercent) {
        this.basicPercent = basicPercent;
    }

    public BigDecimalFilter getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(BigDecimalFilter totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public BigDecimalFilter getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(BigDecimalFilter medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimalFilter getMedicalAllowancePercent() {
        return medicalAllowancePercent;
    }

    public void setMedicalAllowancePercent(BigDecimalFilter medicalAllowancePercent) {
        this.medicalAllowancePercent = medicalAllowancePercent;
    }

    public BigDecimalFilter getConvinceAllowance() {
        return convinceAllowance;
    }

    public void setConvinceAllowance(BigDecimalFilter convinceAllowance) {
        this.convinceAllowance = convinceAllowance;
    }

    public BigDecimalFilter getConvinceAllowancePercent() {
        return convinceAllowancePercent;
    }

    public void setConvinceAllowancePercent(BigDecimalFilter convinceAllowancePercent) {
        this.convinceAllowancePercent = convinceAllowancePercent;
    }

    public BigDecimalFilter getFoodAllowance() {
        return foodAllowance;
    }

    public void setFoodAllowance(BigDecimalFilter foodAllowance) {
        this.foodAllowance = foodAllowance;
    }

    public BigDecimalFilter getFoodAllowancePercent() {
        return foodAllowancePercent;
    }

    public void setFoodAllowancePercent(BigDecimalFilter foodAllowancePercent) {
        this.foodAllowancePercent = foodAllowancePercent;
    }

    public BigDecimalFilter getFestivalAllowance() {
        return festivalAllowance;
    }

    public void setFestivalAllowance(BigDecimalFilter festivalAllowance) {
        this.festivalAllowance = festivalAllowance;
    }

    public BigDecimalFilter getFestivalAllowancePercent() {
        return festivalAllowancePercent;
    }

    public void setFestivalAllowancePercent(BigDecimalFilter festivalAllowancePercent) {
        this.festivalAllowancePercent = festivalAllowancePercent;
    }

    public ActiveStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ActiveStatusFilter status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DefaultAllowanceCriteria that = (DefaultAllowanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(basic, that.basic) &&
            Objects.equals(basicPercent, that.basicPercent) &&
            Objects.equals(totalAllowance, that.totalAllowance) &&
            Objects.equals(medicalAllowance, that.medicalAllowance) &&
            Objects.equals(medicalAllowancePercent, that.medicalAllowancePercent) &&
            Objects.equals(convinceAllowance, that.convinceAllowance) &&
            Objects.equals(convinceAllowancePercent, that.convinceAllowancePercent) &&
            Objects.equals(foodAllowance, that.foodAllowance) &&
            Objects.equals(foodAllowancePercent, that.foodAllowancePercent) &&
            Objects.equals(festivalAllowance, that.festivalAllowance) &&
            Objects.equals(festivalAllowancePercent, that.festivalAllowancePercent) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        basic,
        basicPercent,
        totalAllowance,
        medicalAllowance,
        medicalAllowancePercent,
        convinceAllowance,
        convinceAllowancePercent,
        foodAllowance,
        foodAllowancePercent,
        festivalAllowance,
        festivalAllowancePercent,
        status
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DefaultAllowanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (basic != null ? "basic=" + basic + ", " : "") +
                (basicPercent != null ? "basicPercent=" + basicPercent + ", " : "") +
                (totalAllowance != null ? "totalAllowance=" + totalAllowance + ", " : "") +
                (medicalAllowance != null ? "medicalAllowance=" + medicalAllowance + ", " : "") +
                (medicalAllowancePercent != null ? "medicalAllowancePercent=" + medicalAllowancePercent + ", " : "") +
                (convinceAllowance != null ? "convinceAllowance=" + convinceAllowance + ", " : "") +
                (convinceAllowancePercent != null ? "convinceAllowancePercent=" + convinceAllowancePercent + ", " : "") +
                (foodAllowance != null ? "foodAllowance=" + foodAllowance + ", " : "") +
                (foodAllowancePercent != null ? "foodAllowancePercent=" + foodAllowancePercent + ", " : "") +
                (festivalAllowance != null ? "festivalAllowance=" + festivalAllowance + ", " : "") +
                (festivalAllowancePercent != null ? "festivalAllowancePercent=" + festivalAllowancePercent + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
