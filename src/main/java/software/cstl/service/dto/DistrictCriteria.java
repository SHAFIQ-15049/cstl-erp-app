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
 * Criteria class for the {@link software.cstl.domain.District} entity. This class is used
 * in {@link software.cstl.web.rest.DistrictResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /districts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DistrictCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter bangla;

    private StringFilter web;

    private LongFilter divisionId;

    public DistrictCriteria() {
    }

    public DistrictCriteria(DistrictCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.bangla = other.bangla == null ? null : other.bangla.copy();
        this.web = other.web == null ? null : other.web.copy();
        this.divisionId = other.divisionId == null ? null : other.divisionId.copy();
    }

    @Override
    public DistrictCriteria copy() {
        return new DistrictCriteria(this);
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

    public StringFilter getBangla() {
        return bangla;
    }

    public void setBangla(StringFilter bangla) {
        this.bangla = bangla;
    }

    public StringFilter getWeb() {
        return web;
    }

    public void setWeb(StringFilter web) {
        this.web = web;
    }

    public LongFilter getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(LongFilter divisionId) {
        this.divisionId = divisionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DistrictCriteria that = (DistrictCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(bangla, that.bangla) &&
            Objects.equals(web, that.web) &&
            Objects.equals(divisionId, that.divisionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        bangla,
        web,
        divisionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (bangla != null ? "bangla=" + bangla + ", " : "") +
                (web != null ? "web=" + web + ", " : "") +
                (divisionId != null ? "divisionId=" + divisionId + ", " : "") +
            "}";
    }

}
