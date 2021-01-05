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
 * Criteria class for the {@link software.cstl.domain.AdvancePaymentHistory} entity. This class is used
 * in {@link software.cstl.web.rest.AdvancePaymentHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /advance-payment-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdvancePaymentHistoryCriteria implements Serializable, Criteria {
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

    private BigDecimalFilter before;

    private BigDecimalFilter after;

    private LongFilter advanceId;

    public AdvancePaymentHistoryCriteria() {
    }

    public AdvancePaymentHistoryCriteria(AdvancePaymentHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.monthType = other.monthType == null ? null : other.monthType.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.before = other.before == null ? null : other.before.copy();
        this.after = other.after == null ? null : other.after.copy();
        this.advanceId = other.advanceId == null ? null : other.advanceId.copy();
    }

    @Override
    public AdvancePaymentHistoryCriteria copy() {
        return new AdvancePaymentHistoryCriteria(this);
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

    public BigDecimalFilter getBefore() {
        return before;
    }

    public void setBefore(BigDecimalFilter before) {
        this.before = before;
    }

    public BigDecimalFilter getAfter() {
        return after;
    }

    public void setAfter(BigDecimalFilter after) {
        this.after = after;
    }

    public LongFilter getAdvanceId() {
        return advanceId;
    }

    public void setAdvanceId(LongFilter advanceId) {
        this.advanceId = advanceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdvancePaymentHistoryCriteria that = (AdvancePaymentHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(monthType, that.monthType) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(before, that.before) &&
            Objects.equals(after, that.after) &&
            Objects.equals(advanceId, that.advanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        year,
        monthType,
        amount,
        before,
        after,
        advanceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvancePaymentHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (monthType != null ? "monthType=" + monthType + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (before != null ? "before=" + before + ", " : "") +
                (after != null ? "after=" + after + ", " : "") +
                (advanceId != null ? "advanceId=" + advanceId + ", " : "") +
            "}";
    }

}
