package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.AccountType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link software.cstl.domain.EmployeeAccount} entity. This class is used
 * in {@link software.cstl.web.rest.EmployeeAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeAccountCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AccountType
     */
    public static class AccountTypeFilter extends Filter<AccountType> {

        public AccountTypeFilter() {
        }

        public AccountTypeFilter(AccountTypeFilter filter) {
            super(filter);
        }

        @Override
        public AccountTypeFilter copy() {
            return new AccountTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AccountTypeFilter accountType;

    private StringFilter accountNo;

    private BooleanFilter isSalaryAccount;

    private LongFilter employeeId;

    public EmployeeAccountCriteria() {
    }

    public EmployeeAccountCriteria(EmployeeAccountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accountType = other.accountType == null ? null : other.accountType.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.isSalaryAccount = other.isSalaryAccount == null ? null : other.isSalaryAccount.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public EmployeeAccountCriteria copy() {
        return new EmployeeAccountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AccountTypeFilter getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeFilter accountType) {
        this.accountType = accountType;
    }

    public StringFilter getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(StringFilter accountNo) {
        this.accountNo = accountNo;
    }

    public BooleanFilter getIsSalaryAccount() {
        return isSalaryAccount;
    }

    public void setIsSalaryAccount(BooleanFilter isSalaryAccount) {
        this.isSalaryAccount = isSalaryAccount;
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
        final EmployeeAccountCriteria that = (EmployeeAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(accountType, that.accountType) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(isSalaryAccount, that.isSalaryAccount) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        accountType,
        accountNo,
        isSalaryAccount,
        employeeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (accountType != null ? "accountType=" + accountType + ", " : "") +
                (accountNo != null ? "accountNo=" + accountNo + ", " : "") +
                (isSalaryAccount != null ? "isSalaryAccount=" + isSalaryAccount + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
