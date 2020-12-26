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
 * Criteria class for the {@link software.cstl.domain.Company} entity. This class is used
 * in {@link software.cstl.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter shortName;

    private StringFilter nameInBangla;

    private StringFilter phone;

    public CompanyCriteria() {
    }

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.shortName = other.shortName == null ? null : other.shortName.copy();
        this.nameInBangla = other.nameInBangla == null ? null : other.nameInBangla.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
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

    public StringFilter getShortName() {
        return shortName;
    }

    public void setShortName(StringFilter shortName) {
        this.shortName = shortName;
    }

    public StringFilter getNameInBangla() {
        return nameInBangla;
    }

    public void setNameInBangla(StringFilter nameInBangla) {
        this.nameInBangla = nameInBangla;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyCriteria that = (CompanyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortName, that.shortName) &&
            Objects.equals(nameInBangla, that.nameInBangla) &&
            Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        shortName,
        nameInBangla,
        phone
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (shortName != null ? "shortName=" + shortName + ", " : "") +
                (nameInBangla != null ? "nameInBangla=" + nameInBangla + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
            "}";
    }

}
