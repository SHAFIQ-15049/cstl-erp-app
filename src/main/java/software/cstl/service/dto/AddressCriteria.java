package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.AddressType;
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
    /**
     * Class for filtering AddressType
     */
    public static class AddressTypeFilter extends Filter<AddressType> {

        public AddressTypeFilter() {
        }

        public AddressTypeFilter(AddressTypeFilter filter) {
            super(filter);
        }

        @Override
        public AddressTypeFilter copy() {
            return new AddressTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter street;

    private StringFilter area;

    private IntegerFilter postCode;

    private AddressTypeFilter addressType;

    private LongFilter divisionId;

    private LongFilter districtId;

    private LongFilter thanaId;

    private LongFilter employeeId;

    public AddressCriteria() {
    }

    public AddressCriteria(AddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.street = other.street == null ? null : other.street.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.postCode = other.postCode == null ? null : other.postCode.copy();
        this.addressType = other.addressType == null ? null : other.addressType.copy();
        this.divisionId = other.divisionId == null ? null : other.divisionId.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.thanaId = other.thanaId == null ? null : other.thanaId.copy();
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

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getArea() {
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
    }

    public IntegerFilter getPostCode() {
        return postCode;
    }

    public void setPostCode(IntegerFilter postCode) {
        this.postCode = postCode;
    }

    public AddressTypeFilter getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressTypeFilter addressType) {
        this.addressType = addressType;
    }

    public LongFilter getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(LongFilter divisionId) {
        this.divisionId = divisionId;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }

    public LongFilter getThanaId() {
        return thanaId;
    }

    public void setThanaId(LongFilter thanaId) {
        this.thanaId = thanaId;
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
            Objects.equals(street, that.street) &&
            Objects.equals(area, that.area) &&
            Objects.equals(postCode, that.postCode) &&
            Objects.equals(addressType, that.addressType) &&
            Objects.equals(divisionId, that.divisionId) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(thanaId, that.thanaId) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        street,
        area,
        postCode,
        addressType,
        divisionId,
        districtId,
        thanaId,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (street != null ? "street=" + street + ", " : "") +
                (area != null ? "area=" + area + ", " : "") +
                (postCode != null ? "postCode=" + postCode + ", " : "") +
                (addressType != null ? "addressType=" + addressType + ", " : "") +
                (divisionId != null ? "divisionId=" + divisionId + ", " : "") +
                (districtId != null ? "districtId=" + districtId + ", " : "") +
                (thanaId != null ? "thanaId=" + thanaId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
