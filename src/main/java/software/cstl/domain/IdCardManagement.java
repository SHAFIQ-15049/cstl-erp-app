package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A IdCardManagement.
 */
@Entity
@Table(name = "id_card_management")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IdCardManagement extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_no")
    private String cardNo;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "ticket_no")
    private String ticketNo;

    @Column(name = "valid_till")
    private LocalDate validTill;

    @ManyToOne
    @JsonIgnoreProperties(value = "idCardManagements", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public IdCardManagement cardNo(String cardNo) {
        this.cardNo = cardNo;
        return this;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public IdCardManagement issueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public IdCardManagement ticketNo(String ticketNo) {
        this.ticketNo = ticketNo;
        return this;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public LocalDate getValidTill() {
        return validTill;
    }

    public IdCardManagement validTill(LocalDate validTill) {
        this.validTill = validTill;
        return this;
    }

    public void setValidTill(LocalDate validTill) {
        this.validTill = validTill;
    }

    public Employee getEmployee() {
        return employee;
    }

    public IdCardManagement employee(Employee employee) {
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
        if (!(o instanceof IdCardManagement)) {
            return false;
        }
        return id != null && id.equals(((IdCardManagement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IdCardManagement{" +
            "id=" + getId() +
            ", cardNo='" + getCardNo() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", ticketNo='" + getTicketNo() + "'" +
            ", validTill='" + getValidTill() + "'" +
            "}";
    }
}
