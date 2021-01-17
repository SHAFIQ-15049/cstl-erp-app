package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import software.cstl.domain.enumeration.MonthType;

/**
 * A OverTime.
 */
@Entity
@Table(
    name = "over_time",
    uniqueConstraints = @UniqueConstraint(columnNames = {"year","month","employee_id"})
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OverTime extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private MonthType month;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "to_date")
    private Instant toDate;

    @Column(name = "total_over_time")
    private Double totalOverTime;

    @Column(name = "official_over_time")
    private Double officialOverTime;

    @Column(name = "extra_over_time")
    private Double extraOverTime;

    @Column(name = "total_amount", precision = 21, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "official_amount", precision = 21, scale = 2)
    private BigDecimal officialAmount;

    @Column(name = "extra_amount", precision = 21, scale = 2)
    private BigDecimal extraAmount;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "executed_on")
    private Instant executedOn;

    @Column(name = "executed_by")
    private String executedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "overTimes", allowSetters = true)
    private Designation designation;

    @ManyToOne
    @JsonIgnoreProperties(value = "overTimes", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public OverTime year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthType getMonth() {
        return month;
    }

    public OverTime month(MonthType month) {
        this.month = month;
        return this;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public OverTime fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public OverTime toDate(Instant toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Double getTotalOverTime() {
        return totalOverTime;
    }

    public OverTime totalOverTime(Double totalOverTime) {
        this.totalOverTime = totalOverTime;
        return this;
    }

    public void setTotalOverTime(Double totalOverTime) {
        this.totalOverTime = totalOverTime;
    }

    public Double getOfficialOverTime() {
        return officialOverTime;
    }

    public OverTime officialOverTime(Double officialOverTime) {
        this.officialOverTime = officialOverTime;
        return this;
    }

    public void setOfficialOverTime(Double officialOverTime) {
        this.officialOverTime = officialOverTime;
    }

    public Double getExtraOverTime() {
        return extraOverTime;
    }

    public OverTime extraOverTime(Double extraOverTime) {
        this.extraOverTime = extraOverTime;
        return this;
    }

    public void setExtraOverTime(Double extraOverTime) {
        this.extraOverTime = extraOverTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OverTime totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getOfficialAmount() {
        return officialAmount;
    }

    public OverTime officialAmount(BigDecimal officialAmount) {
        this.officialAmount = officialAmount;
        return this;
    }

    public void setOfficialAmount(BigDecimal officialAmount) {
        this.officialAmount = officialAmount;
    }

    public BigDecimal getExtraAmount() {
        return extraAmount;
    }

    public OverTime extraAmount(BigDecimal extraAmount) {
        this.extraAmount = extraAmount;
        return this;
    }

    public void setExtraAmount(BigDecimal extraAmount) {
        this.extraAmount = extraAmount;
    }

    public String getNote() {
        return note;
    }

    public OverTime note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getExecutedOn() {
        return executedOn;
    }

    public OverTime executedOn(Instant executedOn) {
        this.executedOn = executedOn;
        return this;
    }

    public void setExecutedOn(Instant executedOn) {
        this.executedOn = executedOn;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public OverTime executedBy(String executedBy) {
        this.executedBy = executedBy;
        return this;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public Designation getDesignation() {
        return designation;
    }

    public OverTime designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Employee getEmployee() {
        return employee;
    }

    public OverTime employee(Employee employee) {
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
        if (!(o instanceof OverTime)) {
            return false;
        }
        return id != null && id.equals(((OverTime) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OverTime{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", month='" + getMonth() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", totalOverTime=" + getTotalOverTime() +
            ", officialOverTime=" + getOfficialOverTime() +
            ", extraOverTime=" + getExtraOverTime() +
            ", totalAmount=" + getTotalAmount() +
            ", officialAmount=" + getOfficialAmount() +
            ", extraAmount=" + getExtraAmount() +
            ", note='" + getNote() + "'" +
            ", executedOn='" + getExecutedOn() + "'" +
            ", executedBy='" + getExecutedBy() + "'" +
            "}";
    }
}
