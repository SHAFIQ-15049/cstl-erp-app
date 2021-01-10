package software.cstl.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link software.cstl.domain.LeaveType} entity. This class is used
 * in {@link software.cstl.web.rest.LeaveTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeaveTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter totalDays;

    private IntegerFilter maxValidity;

    public LeaveTypeCriteria() {
    }

    public LeaveTypeCriteria(LeaveTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.totalDays = other.totalDays == null ? null : other.totalDays.copy();
        this.maxValidity = other.maxValidity == null ? null : other.maxValidity.copy();
    }

    @Override
    public LeaveTypeCriteria copy() {
        return new LeaveTypeCriteria(this);
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

    public IntegerFilter getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(IntegerFilter totalDays) {
        this.totalDays = totalDays;
    }

    public IntegerFilter getMaxValidity() {
        return maxValidity;
    }

    public void setMaxValidity(IntegerFilter maxValidity) {
        this.maxValidity = maxValidity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeaveTypeCriteria that = (LeaveTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(totalDays, that.totalDays) &&
            Objects.equals(maxValidity, that.maxValidity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        totalDays,
        maxValidity
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (totalDays != null ? "totalDays=" + totalDays + ", " : "") +
                (maxValidity != null ? "maxValidity=" + maxValidity + ", " : "") +
            "}";
    }

}
