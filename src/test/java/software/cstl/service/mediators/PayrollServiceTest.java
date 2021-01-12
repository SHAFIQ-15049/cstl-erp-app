package software.cstl.service.mediators;

import liquibase.pro.packaged.A;
import liquibase.pro.packaged.B;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.CodeNodeErpApp;
import software.cstl.domain.*;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.repository.*;
import software.cstl.repository.extended.EmployeeExtRepository;
import software.cstl.security.SecurityUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PayrollServiceTest {
    @Mock
    private SecurityContext securityContext;
    @Mock
    private MonthlySalaryRepository monthlySalaryRepository;
    @Mock
    private MonthlySalaryDtlRepository monthlySalaryDtlRepository;
    @Mock
    private DesignationRepository designationRepository;
    @Mock
    private EmployeeExtRepository employeeExtRepository;
    @Mock
    private DefaultAllowanceRepository defaultAllowanceRepository;
    @Mock
    private FineRepository fineRepository;
    @Mock
    private FinePaymentHistoryRepository finePaymentHistoryRepository;
    @Mock
    private AdvanceRepository advanceRepository;
    @Mock
    private AdvancePaymentHistoryRepository advancePaymentHistoryRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private EmployeeSalaryRepository employeeSalaryRepository;

    @Spy
    @InjectMocks
    private PayrollService payrollService;

    DefaultAllowance defaultAllowance;

    @Nested
    class PartialSalaryTest{


        @Test
        public void assignPartialSalaryAndAllowancesTestWithEqualWorkingDays(){
            PartialSalary partialSalary = new PartialSalary();
            partialSalary.setEmployee(TestConfig.employee());
            partialSalary.setFromDate(LocalDate.now());
            partialSalary.setToDate(LocalDate.now());
            partialSalary.setTotalMonthDays(4);

            MockedStatic<SecurityUtils> securityUtilsMockedStatic = Mockito.mockStatic(SecurityUtils.class);
            Optional<String> adminUser = Optional.of("admin");
            securityUtilsMockedStatic.when(SecurityUtils::getCurrentUserLogin).thenReturn(adminUser);
            doReturn(TestConfig.defaultAllowance()).when(defaultAllowanceRepository).findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
            doReturn(2).when(attendanceRepository).totalAttendanceDays(partialSalary.getFromDate(), partialSalary.getToDate());
            doReturn(TestConfig.attendances()).when(attendanceRepository).findByEmployeeAndAttendanceDataUploadBetween(partialSalary.getEmployee(), partialSalary.getFromDate(), partialSalary.getToDate());

            partialSalary = payrollService.assignPartialSalaryAndAllowances(partialSalary);

            assertThat(partialSalary.getGross()).isEqualTo(new BigDecimal(4000));
            assertThat(partialSalary.getBasic()).isEqualTo(new BigDecimal(4100/2));
            assertThat(partialSalary.getHouseRent()).isEqualByComparingTo(new BigDecimal(2050.0/2));
            assertThat(partialSalary.getMedicalAllowance()).isEqualByComparingTo(new BigDecimal(600.0/2));
            assertThat(partialSalary.getConvinceAllowance()).isEqualByComparingTo(new BigDecimal(350.0/2));
            assertThat(partialSalary.getFoodAllowance()).isEqualByComparingTo(new BigDecimal(900.0/2));
        }

        @Test
        public void assignPartialSalaryAndAllowancesTestWithEqualWorkingDaysAndEmployeeSalaryAllowances(){
            PartialSalary partialSalary = new PartialSalary();
            partialSalary.setEmployee(TestConfig.employee());
            partialSalary.setFromDate(LocalDate.now());
            partialSalary.setToDate(LocalDate.now());
            partialSalary.setTotalMonthDays(4);

            MockedStatic<SecurityUtils> securityUtilsMockedStatic = Mockito.mockStatic(SecurityUtils.class);
            Optional<String> adminUser = Optional.of("admin");
            securityUtilsMockedStatic.when(SecurityUtils::getCurrentUserLogin).thenReturn(adminUser);
            doReturn(TestConfig.defaultAllowance()).when(defaultAllowanceRepository).findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
            doReturn(2).when(attendanceRepository).totalAttendanceDays(partialSalary.getFromDate(), partialSalary.getToDate());
            doReturn(TestConfig.attendancesWithEmployeeSalaryWithAllowance()).when(attendanceRepository).findByEmployeeAndAttendanceDataUploadBetween(partialSalary.getEmployee(), partialSalary.getFromDate(), partialSalary.getToDate());

            partialSalary = payrollService.assignPartialSalaryAndAllowances(partialSalary);

            assertThat(partialSalary.getGross()).isEqualTo(new BigDecimal(4000));
            assertThat(partialSalary.getMedicalAllowance()).isEqualByComparingTo(new BigDecimal(650.0/2));
        }

        @Test
        public void assignPartialSalaryAndAllowancesTestWithEqualWorkingDaysAndDifferentEmployeeSalaries(){
            PartialSalary partialSalary = new PartialSalary();
            partialSalary.setEmployee(TestConfig.employee());
            partialSalary.setFromDate(LocalDate.now());
            partialSalary.setToDate(LocalDate.now());
            partialSalary.setTotalMonthDays(4);

            MockedStatic<SecurityUtils> securityUtilsMockedStatic = Mockito.mockStatic(SecurityUtils.class);
            Optional<String> adminUser = Optional.of("admin");
            securityUtilsMockedStatic.when(SecurityUtils::getCurrentUserLogin).thenReturn(adminUser);
            doReturn(TestConfig.defaultAllowance()).when(defaultAllowanceRepository).findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
            doReturn(2).when(attendanceRepository).totalAttendanceDays(partialSalary.getFromDate(), partialSalary.getToDate());
            doReturn(TestConfig.attendancesWithDifferentEmployeeSalaries()).when(attendanceRepository).findByEmployeeAndAttendanceDataUploadBetween(partialSalary.getEmployee(), partialSalary.getFromDate(), partialSalary.getToDate());

            partialSalary = payrollService.assignPartialSalaryAndAllowances(partialSalary);

            assertThat(partialSalary.getGross()).isEqualTo(new BigDecimal(4025));

        }

        @Test
        public void assignPartialSalaryAndAllowancesTestWithLessWorkingDays(){
            PartialSalary partialSalary = new PartialSalary();
            partialSalary.setEmployee(TestConfig.employee());
            partialSalary.setFromDate(LocalDate.now());
            partialSalary.setToDate(LocalDate.now());
            partialSalary.setTotalMonthDays(4);

            MockedStatic<SecurityUtils> securityUtilsMockedStatic = Mockito.mockStatic(SecurityUtils.class);
            Optional<String> adminUser = Optional.of("admin");
            securityUtilsMockedStatic.when(SecurityUtils::getCurrentUserLogin).thenReturn(adminUser);
            doReturn(TestConfig.defaultAllowance()).when(defaultAllowanceRepository).findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
            doReturn(2).when(attendanceRepository).totalAttendanceDays(partialSalary.getFromDate(), partialSalary.getToDate());
            doReturn(TestConfig.singleAttendances()).when(attendanceRepository).findByEmployeeAndAttendanceDataUploadBetween(partialSalary.getEmployee(), partialSalary.getFromDate(), partialSalary.getToDate());

            partialSalary = payrollService.assignPartialSalaryAndAllowances(partialSalary);

            assertThat(partialSalary.getGross()).isEqualTo(new BigDecimal(2000));
        }
    }



    static class TestConfig{
        public static DefaultAllowance defaultAllowance(){
            DefaultAllowance defaultAllowance = new DefaultAllowance();
            defaultAllowance.setTotalAllowance(new BigDecimal(1850));
            defaultAllowance.setMedicalAllowance(new BigDecimal(600));
            defaultAllowance.setConvinceAllowance(new BigDecimal(350));
            defaultAllowance.setFoodAllowance(new BigDecimal(900));
            return defaultAllowance;
        }

        public static Employee employee(){
            Employee employee = new Employee();
            employee.setId(1L);
            employee.setLocalId("1001");
            employee.setGlobalId("2001");
            employee.setAttendanceMachineId("3001");
            return employee;
        }

        public static EmployeeSalary employeeSalaryWithoutAllowance(){
            EmployeeSalary employeeSalary = new EmployeeSalary();
            employeeSalary.setEmployee(employee());
            employeeSalary.setGross(new BigDecimal(8000));
            employeeSalary.setBasic(new BigDecimal(4100));
            employeeSalary.setHouseRent(new BigDecimal(2050));
            return employeeSalary;
        }

        public static EmployeeSalary employeeSalaryWithAllowance(){
            EmployeeSalary employeeSalary = new EmployeeSalary();
            employeeSalary.setEmployee(employee());
            employeeSalary.setGross(new BigDecimal(8000));
            employeeSalary.setBasic(new BigDecimal(4100));
            employeeSalary.setHouseRent(new BigDecimal(2050));
            employeeSalary.setMedicalAllowance(new BigDecimal(650));
            return employeeSalary;
        }

        public static EmployeeSalary updatedEmployeeSalaryWithoutAllowance(){
            EmployeeSalary employeeSalary = new EmployeeSalary();
            employeeSalary.setEmployee(employee());
            employeeSalary.setGross(new BigDecimal(8100));
            employeeSalary.setBasic(new BigDecimal(4200));
            employeeSalary.setHouseRent(new BigDecimal(2050));
            return employeeSalary;
        }

        public static List<Attendance> attendances(){
            List<Attendance> attendances = new ArrayList<>();
            Attendance attendance1 = new Attendance();
            attendance1.setEmployee(employee());
            attendance1.setEmployeeSalary(employeeSalaryWithoutAllowance());
            attendance1.setAttendanceDate(LocalDate.now());

            attendances.add(attendance1);

            Attendance attendance2 = new Attendance();
            attendance2.setEmployee(employee());
            attendance2.setEmployeeSalary(employeeSalaryWithoutAllowance());
            attendance2.setAttendanceDate(LocalDate.now());
            attendances.add(attendance2);

            return attendances;
        }

        public static List<Attendance> attendancesWithEmployeeSalaryWithAllowance(){
            List<Attendance> attendances = new ArrayList<>();
            Attendance attendance1 = new Attendance();
            attendance1.setEmployee(employee());
            attendance1.setEmployeeSalary(employeeSalaryWithAllowance());
            attendance1.setAttendanceDate(LocalDate.now());

            attendances.add(attendance1);

            Attendance attendance2 = new Attendance();
            attendance2.setEmployee(employee());
            attendance2.setEmployeeSalary(employeeSalaryWithAllowance());
            attendance2.setAttendanceDate(LocalDate.now());
            attendances.add(attendance2);

            return attendances;
        }

        public static List<Attendance> attendancesWithDifferentEmployeeSalaries(){
            List<Attendance> attendances = new ArrayList<>();
            Attendance attendance1 = new Attendance();
            attendance1.setEmployee(employee());
            attendance1.setEmployeeSalary(employeeSalaryWithoutAllowance());
            attendance1.setAttendanceDate(LocalDate.now());

            attendances.add(attendance1);

            Attendance attendance2 = new Attendance();
            attendance2.setEmployee(employee());
            attendance2.setEmployeeSalary(updatedEmployeeSalaryWithoutAllowance());
            attendance2.setAttendanceDate(LocalDate.now());
            attendances.add(attendance2);

            return attendances;
        }

        public static List<Attendance> singleAttendances(){
            List<Attendance> attendances = new ArrayList<>();
            Attendance attendance1 = new Attendance();
            attendance1.setEmployee(employee());
            attendance1.setEmployeeSalary(employeeSalaryWithoutAllowance());
            attendance1.setAttendanceDate(LocalDate.now());
            attendances.add(attendance1);
            return attendances;
        }

    }

}
