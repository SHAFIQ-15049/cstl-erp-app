package software.cstl.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.cstl.domain.MonthlySalary;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.service.mediators.PayrollService;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * PayrollManagementResource controller
 */
@RestController
@RequestMapping("/api/payroll-management")
public class PayrollManagementResource {

    private final Logger log = LoggerFactory.getLogger(PayrollManagementResource.class);

    private final PayrollService payrollService;

    public PayrollManagementResource(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    /**
    * GET generateEmptySalaries
    */
    @PostMapping("/generate-empty-salaries")
    public ResponseEntity<MonthlySalary> generateEmptySalaries(@RequestBody MonthlySalary monthlySalary) throws URISyntaxException {
        monthlySalary = payrollService.createEmptyMonthlySalaries(monthlySalary);
        return ResponseEntity.created(new URI("/api/monthly-salaries/" + monthlySalary.getId()))
            .body(monthlySalary);
    }

    /**
    * GET generateSalaries
    */
    @GetMapping("/generate-salaries")
    public String generateSalaries() {
        return "generateSalaries";
    }

}
