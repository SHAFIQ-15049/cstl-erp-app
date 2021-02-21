package software.cstl.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import software.cstl.domain.enumeration.LeaveTypeName;

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
    /**
     * Class for filtering LeaveTypeName
     */
    public static class LeaveTypeNameFilter extends Filter<LeaveTypeName> {

        public LeaveTypeNameFilter() {
        }

        public LeaveTypeNameFilter(LeaveTypeNameFilter filter) {
            super(filter);
        }

        @Override
        public LeaveTypeNameFilter copy() {
            return new LeaveTypeNameFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LeaveTypeNameFilter name;

    private IntegerFilter totalDays;

    public LeaveTypeCriteria() {
    }

    public LeaveTypeCriteria(LeaveTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.totalDays = other.totalDays == null ? null : other.totalDays.copy();
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

    public LeaveTypeNameFilter getName() {
        return name;
    }

    public void setName(LeaveTypeNameFilter name) {
        this.name = name;
    }

    public IntegerFilter getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(IntegerFilter totalDays) {
        this.totalDays = totalDays;
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
            Objects.equals(totalDays, that.totalDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        totalDays
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (totalDays != null ? "totalDays=" + totalDays + ", " : "") +
            "}";
    }

}
