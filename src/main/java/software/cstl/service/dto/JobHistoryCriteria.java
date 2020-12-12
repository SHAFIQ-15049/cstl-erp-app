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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link software.cstl.domain.JobHistory} entity. This class is used
 * in {@link software.cstl.web.rest.JobHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /job-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JobHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter serial;

    private StringFilter organization;

    private StringFilter designation;

    private LocalDateFilter from;

    private LocalDateFilter to;

    private BigDecimalFilter payScale;

    private DoubleFilter totalExperience;

    private LongFilter employeeId;

    public JobHistoryCriteria() {
    }

    public JobHistoryCriteria(JobHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.serial = other.serial == null ? null : other.serial.copy();
        this.organization = other.organization == null ? null : other.organization.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.from = other.from == null ? null : other.from.copy();
        this.to = other.to == null ? null : other.to.copy();
        this.payScale = other.payScale == null ? null : other.payScale.copy();
        this.totalExperience = other.totalExperience == null ? null : other.totalExperience.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public JobHistoryCriteria copy() {
        return new JobHistoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSerial() {
        return serial;
    }

    public void setSerial(IntegerFilter serial) {
        this.serial = serial;
    }

    public StringFilter getOrganization() {
        return organization;
    }

    public void setOrganization(StringFilter organization) {
        this.organization = organization;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
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

    public BigDecimalFilter getPayScale() {
        return payScale;
    }

    public void setPayScale(BigDecimalFilter payScale) {
        this.payScale = payScale;
    }

    public DoubleFilter getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(DoubleFilter totalExperience) {
        this.totalExperience = totalExperience;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JobHistoryCriteria that = (JobHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serial, that.serial) &&
            Objects.equals(organization, that.organization) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(from, that.from) &&
            Objects.equals(to, that.to) &&
            Objects.equals(payScale, that.payScale) &&
            Objects.equals(totalExperience, that.totalExperience) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serial,
        organization,
        designation,
        from,
        to,
        payScale,
        totalExperience,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serial != null ? "serial=" + serial + ", " : "") +
                (organization != null ? "organization=" + organization + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (from != null ? "from=" + from + ", " : "") +
                (to != null ? "to=" + to + ", " : "") +
                (payScale != null ? "payScale=" + payScale + ", " : "") +
                (totalExperience != null ? "totalExperience=" + totalExperience + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
