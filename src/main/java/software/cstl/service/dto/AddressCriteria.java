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
 * Criteria class for the {@link software.cstl.domain.Address} entity. This class is used
 * in {@link software.cstl.web.rest.AddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter presentThanaTxt;

    private StringFilter presentStreet;

    private StringFilter presentArea;

    private IntegerFilter presentPostCode;

    private StringFilter permanentThanaTxt;

    private StringFilter permanentStreet;

    private StringFilter permanentArea;

    private IntegerFilter permanentPostCode;

    private BooleanFilter isSame;

    private LongFilter presentDivisionId;

    private LongFilter presentDistrictId;

    private LongFilter presentThanaId;

    private LongFilter permanentDivisionId;

    private LongFilter permanentDistrictId;

    private LongFilter permanentThanaId;

    private LongFilter employeeId;

    public AddressCriteria() {
    }

    public AddressCriteria(AddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.presentThanaTxt = other.presentThanaTxt == null ? null : other.presentThanaTxt.copy();
        this.presentStreet = other.presentStreet == null ? null : other.presentStreet.copy();
        this.presentArea = other.presentArea == null ? null : other.presentArea.copy();
        this.presentPostCode = other.presentPostCode == null ? null : other.presentPostCode.copy();
        this.permanentThanaTxt = other.permanentThanaTxt == null ? null : other.permanentThanaTxt.copy();
        this.permanentStreet = other.permanentStreet == null ? null : other.permanentStreet.copy();
        this.permanentArea = other.permanentArea == null ? null : other.permanentArea.copy();
        this.permanentPostCode = other.permanentPostCode == null ? null : other.permanentPostCode.copy();
        this.isSame = other.isSame == null ? null : other.isSame.copy();
        this.presentDivisionId = other.presentDivisionId == null ? null : other.presentDivisionId.copy();
        this.presentDistrictId = other.presentDistrictId == null ? null : other.presentDistrictId.copy();
        this.presentThanaId = other.presentThanaId == null ? null : other.presentThanaId.copy();
        this.permanentDivisionId = other.permanentDivisionId == null ? null : other.permanentDivisionId.copy();
        this.permanentDistrictId = other.permanentDistrictId == null ? null : other.permanentDistrictId.copy();
        this.permanentThanaId = other.permanentThanaId == null ? null : other.permanentThanaId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public AddressCriteria copy() {
        return new AddressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPresentThanaTxt() {
        return presentThanaTxt;
    }

    public void setPresentThanaTxt(StringFilter presentThanaTxt) {
        this.presentThanaTxt = presentThanaTxt;
    }

    public StringFilter getPresentStreet() {
        return presentStreet;
    }

    public void setPresentStreet(StringFilter presentStreet) {
        this.presentStreet = presentStreet;
    }

    public StringFilter getPresentArea() {
        return presentArea;
    }

    public void setPresentArea(StringFilter presentArea) {
        this.presentArea = presentArea;
    }

    public IntegerFilter getPresentPostCode() {
        return presentPostCode;
    }

    public void setPresentPostCode(IntegerFilter presentPostCode) {
        this.presentPostCode = presentPostCode;
    }

    public StringFilter getPermanentThanaTxt() {
        return permanentThanaTxt;
    }

    public void setPermanentThanaTxt(StringFilter permanentThanaTxt) {
        this.permanentThanaTxt = permanentThanaTxt;
    }

    public StringFilter getPermanentStreet() {
        return permanentStreet;
    }

    public void setPermanentStreet(StringFilter permanentStreet) {
        this.permanentStreet = permanentStreet;
    }

    public StringFilter getPermanentArea() {
        return permanentArea;
    }

    public void setPermanentArea(StringFilter permanentArea) {
        this.permanentArea = permanentArea;
    }

    public IntegerFilter getPermanentPostCode() {
        return permanentPostCode;
    }

    public void setPermanentPostCode(IntegerFilter permanentPostCode) {
        this.permanentPostCode = permanentPostCode;
    }

    public BooleanFilter getIsSame() {
        return isSame;
    }

    public void setIsSame(BooleanFilter isSame) {
        this.isSame = isSame;
    }

    public LongFilter getPresentDivisionId() {
        return presentDivisionId;
    }

    public void setPresentDivisionId(LongFilter presentDivisionId) {
        this.presentDivisionId = presentDivisionId;
    }

    public LongFilter getPresentDistrictId() {
        return presentDistrictId;
    }

    public void setPresentDistrictId(LongFilter presentDistrictId) {
        this.presentDistrictId = presentDistrictId;
    }

    public LongFilter getPresentThanaId() {
        return presentThanaId;
    }

    public void setPresentThanaId(LongFilter presentThanaId) {
        this.presentThanaId = presentThanaId;
    }

    public LongFilter getPermanentDivisionId() {
        return permanentDivisionId;
    }

    public void setPermanentDivisionId(LongFilter permanentDivisionId) {
        this.permanentDivisionId = permanentDivisionId;
    }

    public LongFilter getPermanentDistrictId() {
        return permanentDistrictId;
    }

    public void setPermanentDistrictId(LongFilter permanentDistrictId) {
        this.permanentDistrictId = permanentDistrictId;
    }

    public LongFilter getPermanentThanaId() {
        return permanentThanaId;
    }

    public void setPermanentThanaId(LongFilter permanentThanaId) {
        this.permanentThanaId = permanentThanaId;
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
        final AddressCriteria that = (AddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(presentThanaTxt, that.presentThanaTxt) &&
            Objects.equals(presentStreet, that.presentStreet) &&
            Objects.equals(presentArea, that.presentArea) &&
            Objects.equals(presentPostCode, that.presentPostCode) &&
            Objects.equals(permanentThanaTxt, that.permanentThanaTxt) &&
            Objects.equals(permanentStreet, that.permanentStreet) &&
            Objects.equals(permanentArea, that.permanentArea) &&
            Objects.equals(permanentPostCode, that.permanentPostCode) &&
            Objects.equals(isSame, that.isSame) &&
            Objects.equals(presentDivisionId, that.presentDivisionId) &&
            Objects.equals(presentDistrictId, that.presentDistrictId) &&
            Objects.equals(presentThanaId, that.presentThanaId) &&
            Objects.equals(permanentDivisionId, that.permanentDivisionId) &&
            Objects.equals(permanentDistrictId, that.permanentDistrictId) &&
            Objects.equals(permanentThanaId, that.permanentThanaId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        presentThanaTxt,
        presentStreet,
        presentArea,
        presentPostCode,
        permanentThanaTxt,
        permanentStreet,
        permanentArea,
        permanentPostCode,
        isSame,
        presentDivisionId,
        presentDistrictId,
        presentThanaId,
        permanentDivisionId,
        permanentDistrictId,
        permanentThanaId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (presentThanaTxt != null ? "presentThanaTxt=" + presentThanaTxt + ", " : "") +
                (presentStreet != null ? "presentStreet=" + presentStreet + ", " : "") +
                (presentArea != null ? "presentArea=" + presentArea + ", " : "") +
                (presentPostCode != null ? "presentPostCode=" + presentPostCode + ", " : "") +
                (permanentThanaTxt != null ? "permanentThanaTxt=" + permanentThanaTxt + ", " : "") +
                (permanentStreet != null ? "permanentStreet=" + permanentStreet + ", " : "") +
                (permanentArea != null ? "permanentArea=" + permanentArea + ", " : "") +
                (permanentPostCode != null ? "permanentPostCode=" + permanentPostCode + ", " : "") +
                (isSame != null ? "isSame=" + isSame + ", " : "") +
                (presentDivisionId != null ? "presentDivisionId=" + presentDivisionId + ", " : "") +
                (presentDistrictId != null ? "presentDistrictId=" + presentDistrictId + ", " : "") +
                (presentThanaId != null ? "presentThanaId=" + presentThanaId + ", " : "") +
                (permanentDivisionId != null ? "permanentDivisionId=" + permanentDivisionId + ", " : "") +
                (permanentDistrictId != null ? "permanentDistrictId=" + permanentDistrictId + ", " : "") +
                (permanentThanaId != null ? "permanentThanaId=" + permanentThanaId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
