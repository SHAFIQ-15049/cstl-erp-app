package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import software.cstl.domain.enumeration.AccountType;

/**
 * A EmployeeAccount.
 */
@Entity
@Table(name = "emp_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeAccount extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @NotNull
    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @Column(name = "is_salary_account")
    private Boolean isSalaryAccount;

    @ManyToOne
    @JsonIgnoreProperties(value = "employeeAccounts", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public EmployeeAccount accountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public EmployeeAccount accountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Boolean isIsSalaryAccount() {
        return isSalaryAccount;
    }

    public EmployeeAccount isSalaryAccount(Boolean isSalaryAccount) {
        this.isSalaryAccount = isSalaryAccount;
        return this;
    }

    public void setIsSalaryAccount(Boolean isSalaryAccount) {
        this.isSalaryAccount = isSalaryAccount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeAccount employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeAccount)) {
            return false;
        }
        return id != null && id.equals(((EmployeeAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAccount{" +
            "id=" + getId() +
            ", accountType='" + getAccountType() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", isSalaryAccount='" + isIsSalaryAccount() + "'" +
            "}";
    }
}
