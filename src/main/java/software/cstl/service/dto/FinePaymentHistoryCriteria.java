package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.MonthType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link software.cstl.domain.FinePaymentHistory} entity. This class is used
 * in {@link software.cstl.web.rest.FinePaymentHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fine-payment-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FinePaymentHistoryCriteria implements Serializable, Criteria {
    /**
     * Class for filtering MonthType
     */
    public static class MonthTypeFilter extends Filter<MonthType> {

        public MonthTypeFilter() {
        }

        public MonthTypeFilter(MonthTypeFilter filter) {
            super(filter);
        }

        @Override
        public MonthTypeFilter copy() {
            return new MonthTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter year;

    private MonthTypeFilter monthType;

    private BigDecimalFilter amount;

    private BigDecimalFilter beforeFine;

    private BigDecimalFilter afterFine;

    private LongFilter fineId;

    public FinePaymentHistoryCriteria() {
    }

    public FinePaymentHistoryCriteria(FinePaymentHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.monthType = other.monthType == null ? null : other.monthType.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.beforeFine = other.beforeFine == null ? null : other.beforeFine.copy();
        this.afterFine = other.afterFine == null ? null : other.afterFine.copy();
        this.fineId = other.fineId == null ? null : other.fineId.copy();
    }

    @Override
    public FinePaymentHistoryCriteria copy() {
        return new FinePaymentHistoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getYear() {
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
    }

    public MonthTypeFilter getMonthType() {
        return monthType;
    }

    public void setMonthType(MonthTypeFilter monthType) {
        this.monthType = monthType;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public BigDecimalFilter getBeforeFine() {
        return beforeFine;
    }

    public void setBeforeFine(BigDecimalFilter beforeFine) {
        this.beforeFine = beforeFine;
    }

    public BigDecimalFilter getAfterFine() {
        return afterFine;
    }

    public void setAfterFine(BigDecimalFilter afterFine) {
        this.afterFine = afterFine;
    }

    public LongFilter getFineId() {
        return fineId;
    }

    public void setFineId(LongFilter fineId) {
        this.fineId = fineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FinePaymentHistoryCriteria that = (FinePaymentHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(monthType, that.monthType) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(beforeFine, that.beforeFine) &&
            Objects.equals(afterFine, that.afterFine) &&
            Objects.equals(fineId, that.fineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        year,
        monthType,
        amount,
        beforeFine,
        afterFine,
        fineId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FinePaymentHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (monthType != null ? "monthType=" + monthType + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (beforeFine != null ? "beforeFine=" + beforeFine + ", " : "") +
                (afterFine != null ? "afterFine=" + afterFine + ", " : "") +
                (fineId != null ? "fineId=" + fineId + ", " : "") +
            "}";
    }

}
