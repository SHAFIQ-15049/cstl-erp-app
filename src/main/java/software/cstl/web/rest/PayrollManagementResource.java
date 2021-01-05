package software.cstl.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.service.mediators.PayrollService;

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
    @GetMapping("/generate-empty-salaries/year/{year}/month/{month}/designation/{designationId}")
    public String generateEmptySalaries(@PathVariable Integer year, @PathVariable MonthType month, @PathVariable Long designationId) {
        payrollService.createEmptyMonthlySalaries(year, month, designationId);
        return "generateEmptySalaries";
    }

    /**
    * GET generateSalaries
    */
    @GetMapping("/generate-salaries")
    public String generateSalaries() {
        return "generateSalaries";
    }

}
