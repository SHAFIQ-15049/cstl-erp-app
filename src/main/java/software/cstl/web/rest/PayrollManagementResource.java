package software.cstl.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.cstl.domain.MonthlySalary;
import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.service.mediators.PayrollService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * PayrollManagementResource controller
 */
@RestController
@RequestMapping("/api/payroll-management")
public class PayrollManagementResource {


    private final PayrollService payrollService;

    public PayrollManagementResource( PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    /**
    * GET generateEmptySalaries
    */
    @PostMapping("/generate-empty-salaries")
    public ResponseEntity<MonthlySalary> generateEmptySalaries(@RequestBody MonthlySalary monthlySalary) throws URISyntaxException, CloneNotSupportedException {
        monthlySalary = payrollService.createEmptyMonthlySalaries(monthlySalary);
        return ResponseEntity.created(new URI("/api/monthly-salaries/" + monthlySalary.getId()))
            .body(monthlySalary);
    }

    @PutMapping("/generate-salaries")
    public ResponseEntity<MonthlySalary> generateSalaries(@RequestBody MonthlySalary monthlySalary) throws URISyntaxException {
        payrollService.createMonthlySalaries(monthlySalary);
        return ResponseEntity.created(new URI("/api/monthly-salaries/" + monthlySalary.getId()))
            .body(monthlySalary);
    }

    @PutMapping("/re-generate-salaries")
    public ResponseEntity<MonthlySalary> regenerateSalaries(@RequestBody MonthlySalary monthlySalary) throws URISyntaxException {
        payrollService.regenerateMonthlySalaries(monthlySalary);
        return ResponseEntity.created(new URI("/api/monthly-salaries/" + monthlySalary.getId()))
            .body(monthlySalary);
    }

   @GetMapping("/re-generate-employee-salary/monthly-salary-id/{monthly-salary-id}/monthly-salary-dtl-id/{monthly-salary-dtl-id}")
   public ResponseEntity<MonthlySalaryDtl> recreateMonthlySalaryOfEmployee(@PathVariable("monthly-salary-id") Long monthlySalaryId, @PathVariable("monthly-salary-dtl-id") Long monthlySalaryDtlId){
        MonthlySalaryDtl monthlySalaryDtl = payrollService.regenerateMonthlySalaryForAnEmployee(monthlySalaryId, monthlySalaryDtlId);
        return ResponseUtil.wrapOrNotFound(Optional.of(monthlySalaryDtl));
   }

}
