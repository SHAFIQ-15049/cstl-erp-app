package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link software.cstl.domain.Holiday} entity. This class is used
 * in {@link software.cstl.web.rest.HolidayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /holidays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HolidayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter from;

    private LocalDateFilter to;

    private IntegerFilter totalDays;

    private LongFilter holidayTypeId;

    public HolidayCriteria() {
    }

    public HolidayCriteria(HolidayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.from = other.from == null ? null : other.from.copy();
        this.to = other.to == null ? null : other.to.copy();
        this.totalDays = other.totalDays == null ? null : other.totalDays.copy();
        this.holidayTypeId = other.holidayTypeId == null ? null : other.holidayTypeId.copy();
    }

    @Override
    public HolidayCriteria copy() {
        return new HolidayCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFrom() {
        return from;
    }

    public void setFrom(LocalDateFilter from) {
        this.from = from;
    }

    public LocalDateFilter getTo() {
        return to;
    }

    public void setTo(LocalDateFilter to) {
        this.to = to;
    }

    public IntegerFilter getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(IntegerFilter totalDays) {
        this.totalDays = totalDays;
    }

    public LongFilter getHolidayTypeId() {
        return holidayTypeId;
    }

    public void setHolidayTypeId(LongFilter holidayTypeId) {
        this.holidayTypeId = holidayTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HolidayCriteria that = (HolidayCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(from, that.from) &&
            Objects.equals(to, that.to) &&
            Objects.equals(totalDays, that.totalDays) &&
            Objects.equals(holidayTypeId, that.holidayTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        from,
        to,
        totalDays,
        holidayTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HolidayCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (from != null ? "from=" + from + ", " : "") +
                (to != null ? "to=" + to + ", " : "") +
                (totalDays != null ? "totalDays=" + totalDays + ", " : "") +
                (holidayTypeId != null ? "holidayTypeId=" + holidayTypeId + ", " : "") +
            "}";
    }

}
