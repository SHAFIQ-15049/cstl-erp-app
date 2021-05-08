package software.cstl.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.cstl.domain.MonthlySalary;
import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.service.mediators.PayrollService;
import software.cstl.service.reports.PayrollExcelReportGenerator;

import javax.xml.bind.ValidationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    private final PayrollExcelReportGenerator payrollExcelReportGenerator;

    public PayrollManagementResource(PayrollService payrollService, PayrollExcelReportGenerator payrollExcelReportGenerator) {
        this.payrollService = payrollService;
        this.payrollExcelReportGenerator = payrollExcelReportGenerator;
    }

    /**
    * GET generateEmptySalaries
    */
    @PostMapping("/generate-empty-salaries")
    public ResponseEntity<MonthlySalary> generateEmptySalaries(@RequestBody MonthlySalary monthlySalary) throws URISyntaxException, CloneNotSupportedException, ValidationException {
        try{
            monthlySalary = payrollService.createEmptyMonthlySalaries(monthlySalary);
            return ResponseEntity.created(new URI("/api/monthly-salaries/" + monthlySalary.getId()))
                .body(monthlySalary);
        }catch (javax.validation.ValidationException e){
            return ResponseEntity.noContent()
                .headers(HeaderUtil.createFailureAlert("Payroll Management", false, "", "","Monthly Salary Already Generated"))
                .build();
        }catch (Exception e){
            return ResponseEntity.noContent()
                .headers(HeaderUtil.createFailureAlert("Payroll Management", false, "", "","Error in creating empty records."))
                .build();
        }

    }

    @PutMapping("/generate-salaries")
    public ResponseEntity<MonthlySalary> generateSalaries(@RequestBody MonthlySalary monthlySalary) throws URISyntaxException {
        payrollService.createMonthlySalaries(monthlySalary);
        return ResponseEntity.created(new URI("/api/monthly-salaries/" + monthlySalary.getId()))
            .body(monthlySalary);
    }

    @PutMapping("/re-generate-salaries")
    public ResponseEntity<MonthlySalary> regenerateSalaries(@RequestBody MonthlySalary monthlySalary) throws URISyntaxException, CloneNotSupportedException {
        monthlySalary =  payrollService.regenerateMonthlySalaries(monthlySalary);
        return ResponseEntity.created(new URI("/api/monthly-salaries/" + monthlySalary.getId()))
            .body(monthlySalary);
    }

   @GetMapping("/re-generate-employee-salary/monthly-salary-id/{monthly-salary-id}/monthly-salary-dtl-id/{monthly-salary-dtl-id}")
   public ResponseEntity<MonthlySalaryDtl> recreateMonthlySalaryOfEmployee(@PathVariable("monthly-salary-id") Long monthlySalaryId, @PathVariable("monthly-salary-dtl-id") Long monthlySalaryDtlId){
        MonthlySalaryDtl monthlySalaryDtl = payrollService.regenerateMonthlySalaryForAnEmployee(monthlySalaryId, monthlySalaryDtlId);
        return ResponseUtil.wrapOrNotFound(Optional.of(monthlySalaryDtl));
   }

   @GetMapping("/report/{year}/{month}/{departmentId}/{designationId}")
   public ResponseEntity<InputStreamResource> generatePayrollReport(@PathVariable("year") Integer year,
                                                                    @PathVariable("month") MonthType month,
                                                                    @PathVariable("departmentId") Long departmentId,
                                                                    @PathVariable("designationId") Long designationId) throws IOException {
       ByteArrayInputStream bis = payrollExcelReportGenerator.createReport(year, month, departmentId, designationId);
       HttpHeaders headers = new HttpHeaders();
       headers.add("Content-Disposition","inline");
       return ResponseEntity
           .ok()
           .headers(headers)
           .contentType(MediaType.APPLICATION_OCTET_STREAM)
           .body(new InputStreamResource(bis));
   }


    @GetMapping("/report/{year}/{month}/{departmentId}")
    public ResponseEntity<InputStreamResource> generatePayrollReport(@PathVariable("year") Integer year,
                                                                     @PathVariable("month") MonthType month,
                                                                     @PathVariable("departmentId") Long departmentId) throws IOException {
        ByteArrayInputStream bis = payrollExcelReportGenerator.createReport(year, month, departmentId, null);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","inline");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(new InputStreamResource(bis));
    }

}
