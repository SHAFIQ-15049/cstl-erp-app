package software.cstl.service.mediators;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.MonthlySalary;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.repository.DesignationRepository;
import software.cstl.repository.MonthlySalaryDtlRepository;
import software.cstl.repository.MonthlySalaryRepository;
import software.cstl.security.SecurityUtils;
import software.cstl.service.MonthlySalaryService;

import java.time.Instant;
import java.time.LocalDate;

@Service
@Transactional
public class PayrollService {
    private final MonthlySalaryRepository monthlySalaryRepository;
    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;
    private final DesignationRepository designationRepository;

    public PayrollService(MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryDtlRepository monthlySalaryDtlRepository, DesignationRepository designationRepository) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.monthlySalaryDtlRepository = monthlySalaryDtlRepository;
        this.designationRepository = designationRepository;
    }

    public void createEmptyMonthlySalaries(Integer year, MonthType monthType, Long designationId){
        MonthlySalary monthlySalary = new MonthlySalary();
        monthlySalary.month(monthType)
            .year(year)
            .designation(designationRepository.getOne(designationId))
            .executedOn(Instant.now());
    }

}
