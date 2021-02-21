package software.cstl.service.reports;

import liquibase.pro.packaged.S;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.MonthlySalary;
import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.repository.*;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.dto.salary.EmployeeInfoDto;
import software.cstl.service.dto.salary.SalaryDetailsDto;
import software.cstl.service.dto.salary.SalaryReportDto;
import software.cstl.utils.CodeNodeErpUtils;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PayrollExcelReportGenerator {
    private final MonthlySalaryRepository monthlySalaryRepository;
    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;
    private final CompanyRepository companyRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceSummaryService attendanceSummaryService;
    private final OverTimeRepository overTimeRepository;
    private final FineRepository fineRepository;
    private final FinePaymentHistoryRepository finePaymentHistoryRepository;
    private final AdvanceRepository advanceRepository;
    private final AdvancePaymentHistoryRepository advancePaymentHistoryRepository;
    private final DepartmentRepository departmentRepository;

    public ByteArrayInputStream createReport(Integer year, MonthType month, Long departmentId, Long designationId){

        YearMonth yearMonth = YearMonth.of(year, month.ordinal()+1);
        LocalDate initialDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate lastDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());

        String departmentName = departmentId!=null? departmentRepository.getOne(departmentId).getName(): "";

        List<MonthlySalaryDtl> monthlySalaryDtls = new ArrayList<>();
        if(departmentId!=null && designationId!=null){
            monthlySalaryDtls = monthlySalaryDtlRepository.findAllByMonthlySalary_YearAndMonthlySalary_MonthAndMonthlySalary_Designation_IdAndEmployee_Department_Id(year, month, departmentId, designationId);
        }else if(departmentId!=null && designationId==null){
            monthlySalaryDtls = monthlySalaryDtlRepository.findAllByMonthlySalary_YearAndMonthlySalary_MonthAndEmployee_Department_Id(year, month, departmentId);
        }else if(departmentId==null && designationId!=null){
            monthlySalaryDtls = monthlySalaryDtlRepository.findAllByMonthlySalary_YearAndMonthlySalary_MonthAndMonthlySalary_Designation_Id(year, month, designationId);
        }

        List<SalaryReportDto> salaryReportDtoList = new ArrayList<>();

        for(int i=0; i<monthlySalaryDtls.size(); i++){
            MonthlySalaryDtl monthlySalaryDtl = monthlySalaryDtls.get(i);


            SalaryReportDto salaryReportDto = new SalaryReportDto();
            salaryReportDto.setSerial(i+1);

            EmployeeInfoDto employeeInfoDto = new EmployeeInfoDto();
            employeeInfoDto.setName(ObjectUtils.defaultIfNull(monthlySalaryDtl.getEmployee().getPersonalInfo().getBanglaName(),monthlySalaryDtl.getEmployee().getName()));
            employeeInfoDto.setDesignation(monthlySalaryDtl.getEmployee().getDesignation().getNameInBangla());
            employeeInfoDto.setEmployeeId(monthlySalaryDtl.getEmployee().getLocalId());
            employeeInfoDto.setJoiningDate(monthlySalaryDtl.getEmployee().getJoiningDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
            salaryReportDto.setEmployeeInfoDto(employeeInfoDto);

            salaryReportDto.setGrade(ObjectUtils.defaultIfNull(monthlySalaryDtl.getEmployee().getGrade().getName(),""));

            SalaryDetailsDto salaryDetailsDto = new SalaryDetailsDto();
            salaryDetailsDto.setTotalSalary( CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getGross()));
            salaryDetailsDto.setMainSalary( CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getBasic()));
            salaryDetailsDto.setHouseRent( CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getHouseRent()));
            salaryReportDto.setSalaryDetailsDto(salaryDetailsDto);

            salaryReportDto.setMainSalary(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getBasic()));
            salaryReportDto.setHouseRent(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getHouseRent()));
            salaryReportDto.setMedicalAllowance(CodeNodeErpUtils.currencyWithChosenLocalisationInBangla(monthlySalaryDtl.getMedicalAllowance()));



            salaryReportDtoList.add(salaryReportDto);
        }

        return null;
    }
}
