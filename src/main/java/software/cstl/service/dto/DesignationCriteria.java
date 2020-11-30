package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.EmployeeCategory;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link software.cstl.domain.Designation} entity. This class is used
 * in {@link software.cstl.web.rest.DesignationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /designations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DesignationCriteria implements Serializable, Criteria {
    /**
     * Class for filtering EmployeeCategory
     */
    public static class EmployeeCategoryFilter extends Filter<EmployeeCategory> {

        public EmployeeCategoryFilter() {
        }

        public EmployeeCategoryFilter(EmployeeCategoryFilter filter) {
            super(filter);
        }

        @Override
        public EmployeeCategoryFilter copy() {
            return new EmployeeCategoryFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private EmployeeCategoryFilter category;

    private StringFilter name;

    private StringFilter shortName;

    private StringFilter nameInBangla;

    public DesignationCriteria() {
    }

    public DesignationCriteria(DesignationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.shortName = other.shortName == null ? null : other.shortName.copy();
        this.nameInBangla = other.nameInBangla == null ? null : other.nameInBangla.copy();
    }

    @Override
    public DesignationCriteria copy() {
        return new DesignationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public EmployeeCategoryFilter getCategory() {
        return category;
    }

    public void setCategory(EmployeeCategoryFilter category) {
        this.category = category;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DesignationCriteria that = (DesignationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(category, that.category) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortName, that.shortName) &&
            Objects.equals(nameInBangla, that.nameInBangla);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        category,
        name,
        shortName,
        nameInBangla
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DesignationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (category != null ? "category=" + category + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (shortName != null ? "shortName=" + shortName + ", " : "") +
                (nameInBangla != null ? "nameInBangla=" + nameInBangla + ", " : "") +
            "}";
    }

}
