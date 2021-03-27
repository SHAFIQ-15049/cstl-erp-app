package software.cstl.web.rest;

import software.cstl.domain.LeaveApplication;
import software.cstl.domain.LeaveType;
import software.cstl.domain.enumeration.LeaveTypeName;
import software.cstl.service.LeaveApplicationService;
import software.cstl.service.LeaveBalanceService;
import software.cstl.service.dto.LeaveBalanceDTO;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.LeaveApplicationCriteria;
import software.cstl.service.LeaveApplicationQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link software.cstl.domain.LeaveApplication}.
 */
@RestController
@RequestMapping("/api")
public class LeaveApplicationResource {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationResource.class);

    private static final String ENTITY_NAME = "leaveApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveApplicationService leaveApplicationService;

    private final LeaveApplicationQueryService leaveApplicationQueryService;

    private final LeaveBalanceService leaveBalanceService;

    public LeaveApplicationResource(LeaveApplicationService leaveApplicationService, LeaveApplicationQueryService leaveApplicationQueryService, LeaveBalanceService leaveBalanceService) {
        this.leaveApplicationService = leaveApplicationService;
        this.leaveApplicationQueryService = leaveApplicationQueryService;
        this.leaveBalanceService = leaveBalanceService;
    }

    /**
     * {@code POST  /leave-applications} : Create a new leaveApplication.
     *
     * @param leaveApplication the leaveApplication to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveApplication, or with status {@code 400 (Bad Request)} if the leaveApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-applications")
    public ResponseEntity<LeaveApplication> createLeaveApplication(@Valid @RequestBody LeaveApplication leaveApplication) throws URISyntaxException {
        log.debug("REST request to save LeaveApplication : {}", leaveApplication);
        if (leaveApplication.getId() != null) {
            throw new BadRequestAlertException("A new leaveApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(leaveApplication.getFrom().getYear() != leaveApplication.getTo().getYear()) {
            throw new BadRequestAlertException("From and to year should be same", ENTITY_NAME, "idexists");
        }
        if(leaveApplication.getFrom().isAfter(leaveApplication.getTo())) {
            throw new BadRequestAlertException("Please check from and to date again.", ENTITY_NAME, "idexists");
        }
        int year = leaveApplication.getFrom().getYear();

        if(leaveApplication.getLeaveType().getName().equals(LeaveTypeName.EARNED_LEAVE)) {
            LocalDate date = LocalDate.of(leaveApplication.getFrom().getYear(), leaveApplication.getApplicant().getJoiningDate().getMonthValue(),
                leaveApplication.getApplicant().getJoiningDate().getDayOfMonth()).minusDays(1);
            if(date.isAfter(leaveApplication.getFrom())) {
                year = leaveApplication.getFrom().getYear() - 1;
            }
        }

        List<LeaveBalanceDTO> leaveBalanceDTOs = leaveBalanceService.getLeaveBalances(leaveApplication.getApplicant().getId(), year)
            .stream().filter(val -> val.getLeaveTypeId().equals(leaveApplication.getLeaveType().getId())).collect(Collectors.toList());
        long l1 = ChronoUnit.DAYS.between(leaveApplication.getFrom(), leaveApplication.getTo()) + 1;
        BigDecimal diff = BigDecimal.valueOf(l1);
        if(leaveBalanceDTOs.get(0).getRemainingDays().compareTo(diff) < 0) {
            throw new BadRequestAlertException("Balance exceed", ENTITY_NAME, "idexists");
        }

        LeaveApplication result = leaveApplicationService.save(leaveApplication);
        return ResponseEntity.created(new URI("/api/leave-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-applications} : Updates an existing leaveApplication.
     *
     * @param leaveApplication the leaveApplication to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveApplication,
     * or with status {@code 400 (Bad Request)} if the leaveApplication is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveApplication couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-applications")
    public ResponseEntity<LeaveApplication> updateLeaveApplication(@Valid @RequestBody LeaveApplication leaveApplication) throws URISyntaxException {
        log.debug("REST request to update LeaveApplication : {}", leaveApplication);
        if (leaveApplication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(leaveApplication.getFrom().getYear() != leaveApplication.getTo().getYear()) {
            throw new BadRequestAlertException("From and to year should be same", ENTITY_NAME, "idexists");
        }
        if(leaveApplication.getFrom().isAfter(leaveApplication.getTo())) {
            throw new BadRequestAlertException("Please check from and to date again.", ENTITY_NAME, "idexists");
        }
        int year = leaveApplication.getFrom().getYear();

        if(leaveApplication.getLeaveType().getName().equals(LeaveTypeName.EARNED_LEAVE)) {
            LocalDate date = LocalDate.of(leaveApplication.getFrom().getYear(), leaveApplication.getApplicant().getJoiningDate().getMonthValue(),
                leaveApplication.getApplicant().getJoiningDate().getDayOfMonth()).minusDays(1);
            if(date.isAfter(leaveApplication.getFrom())) {
                year = leaveApplication.getFrom().getYear() - 1;
            }
        }

        List<LeaveBalanceDTO> leaveBalanceDTOs = leaveBalanceService.getLeaveBalances(leaveApplication.getApplicant().getId(), year)
            .stream().filter(val -> val.getLeaveTypeId().equals(leaveApplication.getLeaveType().getId())).collect(Collectors.toList());
        long l1 = ChronoUnit.DAYS.between(leaveApplication.getFrom(), leaveApplication.getTo()) + 1;
        BigDecimal diff = BigDecimal.valueOf(l1);
        if(leaveBalanceDTOs.get(0).getRemainingDays().compareTo(diff) < 0) {
            throw new BadRequestAlertException("Balance exceed", ENTITY_NAME, "idexists");
        }
        LeaveApplication result = leaveApplicationService.save(leaveApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveApplication.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /leave-applications} : get all the leaveApplications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveApplications in body.
     */
    @GetMapping("/leave-applications")
    public ResponseEntity<List<LeaveApplication>> getAllLeaveApplications(LeaveApplicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeaveApplications by criteria: {}", criteria);
        Page<LeaveApplication> page = leaveApplicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-applications/count} : count all the leaveApplications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-applications/count")
    public ResponseEntity<Long> countLeaveApplications(LeaveApplicationCriteria criteria) {
        log.debug("REST request to count LeaveApplications by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveApplicationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-applications/:id} : get the "id" leaveApplication.
     *
     * @param id the id of the leaveApplication to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveApplication, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-applications/{id}")
    public ResponseEntity<LeaveApplication> getLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to get LeaveApplication : {}", id);
        Optional<LeaveApplication> leaveApplication = leaveApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveApplication);
    }

    /**
     * {@code DELETE  /leave-applications/:id} : delete the "id" leaveApplication.
     *
     * @param id the id of the leaveApplication to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-applications/{id}")
    public ResponseEntity<Void> deleteLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to delete LeaveApplication : {}", id);
        leaveApplicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
