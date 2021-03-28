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
 * Criteria class for the {@link software.cstl.domain.IdCardManagement} entity. This class is used
 * in {@link software.cstl.web.rest.IdCardManagementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /id-card-managements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IdCardManagementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cardNo;

    private LocalDateFilter issueDate;

    private StringFilter ticketNo;

    private LocalDateFilter validTill;

    private LongFilter employeeId;

    public IdCardManagementCriteria() {
    }

    public IdCardManagementCriteria(IdCardManagementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cardNo = other.cardNo == null ? null : other.cardNo.copy();
        this.issueDate = other.issueDate == null ? null : other.issueDate.copy();
        this.ticketNo = other.ticketNo == null ? null : other.ticketNo.copy();
        this.validTill = other.validTill == null ? null : other.validTill.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public IdCardManagementCriteria copy() {
        return new IdCardManagementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCardNo() {
        return cardNo;
    }

    public void setCardNo(StringFilter cardNo) {
        this.cardNo = cardNo;
    }

    public LocalDateFilter getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateFilter issueDate) {
        this.issueDate = issueDate;
    }

    public StringFilter getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(StringFilter ticketNo) {
        this.ticketNo = ticketNo;
    }

    public LocalDateFilter getValidTill() {
        return validTill;
    }

    public void setValidTill(LocalDateFilter validTill) {
        this.validTill = validTill;
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
        final IdCardManagementCriteria that = (IdCardManagementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cardNo, that.cardNo) &&
            Objects.equals(issueDate, that.issueDate) &&
            Objects.equals(ticketNo, that.ticketNo) &&
            Objects.equals(validTill, that.validTill) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cardNo,
        issueDate,
        ticketNo,
        validTill,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IdCardManagementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cardNo != null ? "cardNo=" + cardNo + ", " : "") +
                (issueDate != null ? "issueDate=" + issueDate + ", " : "") +
                (ticketNo != null ? "ticketNo=" + ticketNo + ", " : "") +
                (validTill != null ? "validTill=" + validTill + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
