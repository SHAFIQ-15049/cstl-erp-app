package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import software.cstl.domain.enumeration.EmployeeCategory;

import software.cstl.domain.enumeration.EmployeeType;

import software.cstl.domain.enumeration.EmployeeStatus;

/**
 * A Employee.
 */
@Entity
@Table(name = "mst_employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "emp_id", nullable = false)
    private String empId;

    @NotNull
    @Column(name = "global_id", nullable = false)
    private String globalId;

    @NotNull
    @Column(name = "local_id", nullable = false)
    private String localId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private EmployeeCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EmployeeType type;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmployeeStatus status;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Lob
    @Column(name = "termination_reason")
    private String terminationReason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private PersonalInfo personalInfo;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EducationalInfo> educationalInfos = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Training> trainings = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmployeeAccount> employeeAccounts = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<JobHistory> jobHistories = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ServiceHistory> serviceHistories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private Grade grade;

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private Designation designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpId() {
        return empId;
    }

    public Employee empId(String empId) {
        this.empId = empId;
        return this;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getGlobalId() {
        return globalId;
    }

    public Employee globalId(String globalId) {
        this.globalId = globalId;
        return this;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getLocalId() {
        return localId;
    }

    public Employee localId(String localId) {
        this.localId = localId;
        return this;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public EmployeeCategory getCategory() {
        return category;
    }

    public Employee category(EmployeeCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(EmployeeCategory category) {
        this.category = category;
    }

    public EmployeeType getType() {
        return type;
    }

    public Employee type(EmployeeType type) {
        this.type = type;
        return this;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public Employee joiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
        return this;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public Employee status(EmployeeStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public Employee terminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
        return this;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getTerminationReason() {
        return terminationReason;
    }

    public Employee terminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
        return this;
    }

    public void setTerminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
    }

    public Address getAddress() {
        return address;
    }

    public Employee address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public Employee personalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
        return this;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Set<EducationalInfo> getEducationalInfos() {
        return educationalInfos;
    }

    public Employee educationalInfos(Set<EducationalInfo> educationalInfos) {
        this.educationalInfos = educationalInfos;
        return this;
    }

    public Employee addEducationalInfo(EducationalInfo educationalInfo) {
        this.educationalInfos.add(educationalInfo);
        educationalInfo.setEmployee(this);
        return this;
    }

    public Employee removeEducationalInfo(EducationalInfo educationalInfo) {
        this.educationalInfos.remove(educationalInfo);
        educationalInfo.setEmployee(null);
        return this;
    }

    public void setEducationalInfos(Set<EducationalInfo> educationalInfos) {
        this.educationalInfos = educationalInfos;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public Employee trainings(Set<Training> trainings) {
        this.trainings = trainings;
        return this;
    }

    public Employee addTraining(Training training) {
        this.trainings.add(training);
        training.setEmployee(this);
        return this;
    }

    public Employee removeTraining(Training training) {
        this.trainings.remove(training);
        training.setEmployee(null);
        return this;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    public Set<EmployeeAccount> getEmployeeAccounts() {
        return employeeAccounts;
    }

    public Employee employeeAccounts(Set<EmployeeAccount> employeeAccounts) {
        this.employeeAccounts = employeeAccounts;
        return this;
    }

    public Employee addEmployeeAccount(EmployeeAccount employeeAccount) {
        this.employeeAccounts.add(employeeAccount);
        employeeAccount.setEmployee(this);
        return this;
    }

    public Employee removeEmployeeAccount(EmployeeAccount employeeAccount) {
        this.employeeAccounts.remove(employeeAccount);
        employeeAccount.setEmployee(null);
        return this;
    }

    public void setEmployeeAccounts(Set<EmployeeAccount> employeeAccounts) {
        this.employeeAccounts = employeeAccounts;
    }

    public Set<JobHistory> getJobHistories() {
        return jobHistories;
    }

    public Employee jobHistories(Set<JobHistory> jobHistories) {
        this.jobHistories = jobHistories;
        return this;
    }

    public Employee addJobHistory(JobHistory jobHistory) {
        this.jobHistories.add(jobHistory);
        jobHistory.setEmployee(this);
        return this;
    }

    public Employee removeJobHistory(JobHistory jobHistory) {
        this.jobHistories.remove(jobHistory);
        jobHistory.setEmployee(null);
        return this;
    }

    public void setJobHistories(Set<JobHistory> jobHistories) {
        this.jobHistories = jobHistories;
    }

    public Set<ServiceHistory> getServiceHistories() {
        return serviceHistories;
    }

    public Employee serviceHistories(Set<ServiceHistory> serviceHistories) {
        this.serviceHistories = serviceHistories;
        return this;
    }

    public Employee addServiceHistory(ServiceHistory serviceHistory) {
        this.serviceHistories.add(serviceHistory);
        serviceHistory.setEmployee(this);
        return this;
    }

    public Employee removeServiceHistory(ServiceHistory serviceHistory) {
        this.serviceHistories.remove(serviceHistory);
        serviceHistory.setEmployee(null);
        return this;
    }

    public void setServiceHistories(Set<ServiceHistory> serviceHistories) {
        this.serviceHistories = serviceHistories;
    }

    public Company getCompany() {
        return company;
    }

    public Employee company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Grade getGrade() {
        return grade;
    }

    public Employee grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Designation getDesignation() {
        return designation;
    }

    public Employee designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", empId='" + getEmpId() + "'" +
            ", globalId='" + getGlobalId() + "'" +
            ", localId='" + getLocalId() + "'" +
            ", category='" + getCategory() + "'" +
            ", type='" + getType() + "'" +
            ", joiningDate='" + getJoiningDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", terminationDate='" + getTerminationDate() + "'" +
            ", terminationReason='" + getTerminationReason() + "'" +
            "}";
    }
}
