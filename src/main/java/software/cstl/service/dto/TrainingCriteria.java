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
 * Criteria class for the {@link software.cstl.domain.Training} entity. This class is used
 * in {@link software.cstl.web.rest.TrainingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trainings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TrainingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter serial;

    private StringFilter name;

    private StringFilter trainingInstitute;

    private LocalDateFilter receivedOn;

    private LongFilter employeeId;

    public TrainingCriteria() {
    }

    public TrainingCriteria(TrainingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.serial = other.serial == null ? null : other.serial.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.trainingInstitute = other.trainingInstitute == null ? null : other.trainingInstitute.copy();
        this.receivedOn = other.receivedOn == null ? null : other.receivedOn.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public TrainingCriteria copy() {
        return new TrainingCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getTrainingInstitute() {
        return trainingInstitute;
    }

    public void setTrainingInstitute(StringFilter trainingInstitute) {
        this.trainingInstitute = trainingInstitute;
    }

    public LocalDateFilter getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(LocalDateFilter receivedOn) {
        this.receivedOn = receivedOn;
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
        final TrainingCriteria that = (TrainingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serial, that.serial) &&
            Objects.equals(name, that.name) &&
            Objects.equals(trainingInstitute, that.trainingInstitute) &&
            Objects.equals(receivedOn, that.receivedOn) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serial,
        name,
        trainingInstitute,
        receivedOn,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serial != null ? "serial=" + serial + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (trainingInstitute != null ? "trainingInstitute=" + trainingInstitute + ", " : "") +
                (receivedOn != null ? "receivedOn=" + receivedOn + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
