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

/**
 * Criteria class for the {@link software.cstl.domain.EducationalInfo} entity. This class is used
 * in {@link software.cstl.web.rest.EducationalInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /educational-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EducationalInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter serial;

    private StringFilter degree;

    private StringFilter institution;

    private IntegerFilter passingYear;

    private IntegerFilter courseDuration;

    private LongFilter employeeId;

    public EducationalInfoCriteria() {
    }

    public EducationalInfoCriteria(EducationalInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.serial = other.serial == null ? null : other.serial.copy();
        this.degree = other.degree == null ? null : other.degree.copy();
        this.institution = other.institution == null ? null : other.institution.copy();
        this.passingYear = other.passingYear == null ? null : other.passingYear.copy();
        this.courseDuration = other.courseDuration == null ? null : other.courseDuration.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public EducationalInfoCriteria copy() {
        return new EducationalInfoCriteria(this);
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

    public StringFilter getDegree() {
        return degree;
    }

    public void setDegree(StringFilter degree) {
        this.degree = degree;
    }

    public StringFilter getInstitution() {
        return institution;
    }

    public void setInstitution(StringFilter institution) {
        this.institution = institution;
    }

    public IntegerFilter getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(IntegerFilter passingYear) {
        this.passingYear = passingYear;
    }

    public IntegerFilter getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(IntegerFilter courseDuration) {
        this.courseDuration = courseDuration;
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
        final EducationalInfoCriteria that = (EducationalInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serial, that.serial) &&
            Objects.equals(degree, that.degree) &&
            Objects.equals(institution, that.institution) &&
            Objects.equals(passingYear, that.passingYear) &&
            Objects.equals(courseDuration, that.courseDuration) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serial,
        degree,
        institution,
        passingYear,
        courseDuration,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationalInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serial != null ? "serial=" + serial + ", " : "") +
                (degree != null ? "degree=" + degree + ", " : "") +
                (institution != null ? "institution=" + institution + ", " : "") +
                (passingYear != null ? "passingYear=" + passingYear + ", " : "") +
                (courseDuration != null ? "courseDuration=" + courseDuration + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
