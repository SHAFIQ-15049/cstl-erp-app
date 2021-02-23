package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.Employee;
import software.cstl.domain.MonthlySalary;
import software.cstl.repository.MonthlySalaryDtlRepository;
import software.cstl.service.MonthlySalaryDtlService;
import software.cstl.service.dto.MonthlySalaryDtlCriteria;
import software.cstl.service.MonthlySalaryDtlQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.SalaryExecutionStatus;
import software.cstl.domain.enumeration.PayrollGenerationType;
/**
 * Integration tests for the {@link MonthlySalaryDtlResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MonthlySalaryDtlResourceIT {

    private static final BigDecimal DEFAULT_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_GROSS = new BigDecimal(2);
    private static final BigDecimal SMALLER_GROSS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_BASIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC = new BigDecimal(2);
    private static final BigDecimal SMALLER_BASIC = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_BASIC_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_BASIC_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HOUSE_RENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOUSE_RENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_HOUSE_RENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HOUSE_RENT_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOUSE_RENT_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_HOUSE_RENT_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MEDICAL_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MEDICAL_ALLOWANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_MEDICAL_ALLOWANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MEDICAL_ALLOWANCE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MEDICAL_ALLOWANCE_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_MEDICAL_ALLOWANCE_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CONVINCE_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONVINCE_ALLOWANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CONVINCE_ALLOWANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CONVINCE_ALLOWANCE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONVINCE_ALLOWANCE_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_CONVINCE_ALLOWANCE_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOOD_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOOD_ALLOWANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOOD_ALLOWANCE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOOD_ALLOWANCE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOOD_ALLOWANCE_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOOD_ALLOWANCE_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FINE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FINE = new BigDecimal(2);
    private static final BigDecimal SMALLER_FINE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ADVANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADVANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_ADVANCE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_TOTAL_WORKING_DAYS = 1;
    private static final Integer UPDATED_TOTAL_WORKING_DAYS = 2;
    private static final Integer SMALLER_TOTAL_WORKING_DAYS = 1 - 1;

    private static final Integer DEFAULT_REGULAR_LEAVE = 1;
    private static final Integer UPDATED_REGULAR_LEAVE = 2;
    private static final Integer SMALLER_REGULAR_LEAVE = 1 - 1;

    private static final Integer DEFAULT_SICK_LEAVE = 1;
    private static final Integer UPDATED_SICK_LEAVE = 2;
    private static final Integer SMALLER_SICK_LEAVE = 1 - 1;

    private static final Integer DEFAULT_COMPENSATION_LEAVE = 1;
    private static final Integer UPDATED_COMPENSATION_LEAVE = 2;
    private static final Integer SMALLER_COMPENSATION_LEAVE = 1 - 1;

    private static final Integer DEFAULT_FESTIVAL_LEAVE = 1;
    private static final Integer UPDATED_FESTIVAL_LEAVE = 2;
    private static final Integer SMALLER_FESTIVAL_LEAVE = 1 - 1;

    private static final Integer DEFAULT_WEEKLY_LEAVE = 1;
    private static final Integer UPDATED_WEEKLY_LEAVE = 2;
    private static final Integer SMALLER_WEEKLY_LEAVE = 1 - 1;

    private static final Integer DEFAULT_PRESENT = 1;
    private static final Integer UPDATED_PRESENT = 2;
    private static final Integer SMALLER_PRESENT = 1 - 1;

    private static final Integer DEFAULT_ABSENT = 1;
    private static final Integer UPDATED_ABSENT = 2;
    private static final Integer SMALLER_ABSENT = 1 - 1;

    private static final Integer DEFAULT_TOTAL_MONTH_DAYS = 1;
    private static final Integer UPDATED_TOTAL_MONTH_DAYS = 2;
    private static final Integer SMALLER_TOTAL_MONTH_DAYS = 1 - 1;

    private static final Double DEFAULT_OVER_TIME_HOUR = 1D;
    private static final Double UPDATED_OVER_TIME_HOUR = 2D;
    private static final Double SMALLER_OVER_TIME_HOUR = 1D - 1D;

    private static final BigDecimal DEFAULT_OVER_TIME_SALARY_HOURLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_OVER_TIME_SALARY_HOURLY = new BigDecimal(2);
    private static final BigDecimal SMALLER_OVER_TIME_SALARY_HOURLY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OVER_TIME_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_OVER_TIME_SALARY = new BigDecimal(2);
    private static final BigDecimal SMALLER_OVER_TIME_SALARY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PRESENT_BONUS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRESENT_BONUS = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRESENT_BONUS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ABSENT_FINE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ABSENT_FINE = new BigDecimal(2);
    private static final BigDecimal SMALLER_ABSENT_FINE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_STAMP_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_STAMP_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_STAMP_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OTHERS = new BigDecimal(1);
    private static final BigDecimal UPDATED_OTHERS = new BigDecimal(2);
    private static final BigDecimal SMALLER_OTHERS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PAYABLE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PAYABLE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PAYABLE = new BigDecimal(1 - 1);

    private static final SalaryExecutionStatus DEFAULT_STATUS = SalaryExecutionStatus.DONE;
    private static final SalaryExecutionStatus UPDATED_STATUS = SalaryExecutionStatus.NOT_DONE;

    private static final PayrollGenerationType DEFAULT_TYPE = PayrollGenerationType.FULL;
    private static final PayrollGenerationType UPDATED_TYPE = PayrollGenerationType.PARTIAL;

    private static final Instant DEFAULT_EXECUTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXECUTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private MonthlySalaryDtlRepository monthlySalaryDtlRepository;

    @Autowired
    private MonthlySalaryDtlService monthlySalaryDtlService;

    @Autowired
    private MonthlySalaryDtlQueryService monthlySalaryDtlQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonthlySalaryDtlMockMvc;

    private MonthlySalaryDtl monthlySalaryDtl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlySalaryDtl createEntity(EntityManager em) {
        MonthlySalaryDtl monthlySalaryDtl = new MonthlySalaryDtl()
            .gross(DEFAULT_GROSS)
            .basic(DEFAULT_BASIC)
            .basicPercent(DEFAULT_BASIC_PERCENT)
            .houseRent(DEFAULT_HOUSE_RENT)
            .houseRentPercent(DEFAULT_HOUSE_RENT_PERCENT)
            .medicalAllowance(DEFAULT_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(DEFAULT_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(DEFAULT_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(DEFAULT_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(DEFAULT_FOOD_ALLOWANCE)
            .foodAllowancePercent(DEFAULT_FOOD_ALLOWANCE_PERCENT)
            .fine(DEFAULT_FINE)
            .advance(DEFAULT_ADVANCE)
            .totalWorkingDays(DEFAULT_TOTAL_WORKING_DAYS)
            .regularLeave(DEFAULT_REGULAR_LEAVE)
            .sickLeave(DEFAULT_SICK_LEAVE)
            .compensationLeave(DEFAULT_COMPENSATION_LEAVE)
            .festivalLeave(DEFAULT_FESTIVAL_LEAVE)
            .weeklyLeave(DEFAULT_WEEKLY_LEAVE)
            .present(DEFAULT_PRESENT)
            .absent(DEFAULT_ABSENT)
            .totalMonthDays(DEFAULT_TOTAL_MONTH_DAYS)
            .overTimeHour(DEFAULT_OVER_TIME_HOUR)
            .overTimeSalaryHourly(DEFAULT_OVER_TIME_SALARY_HOURLY)
            .overTimeSalary(DEFAULT_OVER_TIME_SALARY)
            .presentBonus(DEFAULT_PRESENT_BONUS)
            .absentFine(DEFAULT_ABSENT_FINE)
            .stampPrice(DEFAULT_STAMP_PRICE)
            .tax(DEFAULT_TAX)
            .others(DEFAULT_OTHERS)
            .totalPayable(DEFAULT_TOTAL_PAYABLE)
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE)
            .executedOn(DEFAULT_EXECUTED_ON)
            .executedBy(DEFAULT_EXECUTED_BY)
            .note(DEFAULT_NOTE);
        return monthlySalaryDtl;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlySalaryDtl createUpdatedEntity(EntityManager em) {
        MonthlySalaryDtl monthlySalaryDtl = new MonthlySalaryDtl()
            .gross(UPDATED_GROSS)
            .basic(UPDATED_BASIC)
            .basicPercent(UPDATED_BASIC_PERCENT)
            .houseRent(UPDATED_HOUSE_RENT)
            .houseRentPercent(UPDATED_HOUSE_RENT_PERCENT)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(UPDATED_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(UPDATED_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(UPDATED_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(UPDATED_FOOD_ALLOWANCE)
            .foodAllowancePercent(UPDATED_FOOD_ALLOWANCE_PERCENT)
            .fine(UPDATED_FINE)
            .advance(UPDATED_ADVANCE)
            .totalWorkingDays(UPDATED_TOTAL_WORKING_DAYS)
            .regularLeave(UPDATED_REGULAR_LEAVE)
            .sickLeave(UPDATED_SICK_LEAVE)
            .compensationLeave(UPDATED_COMPENSATION_LEAVE)
            .festivalLeave(UPDATED_FESTIVAL_LEAVE)
            .weeklyLeave(UPDATED_WEEKLY_LEAVE)
            .present(UPDATED_PRESENT)
            .absent(UPDATED_ABSENT)
            .totalMonthDays(UPDATED_TOTAL_MONTH_DAYS)
            .overTimeHour(UPDATED_OVER_TIME_HOUR)
            .overTimeSalaryHourly(UPDATED_OVER_TIME_SALARY_HOURLY)
            .overTimeSalary(UPDATED_OVER_TIME_SALARY)
            .presentBonus(UPDATED_PRESENT_BONUS)
            .absentFine(UPDATED_ABSENT_FINE)
            .stampPrice(UPDATED_STAMP_PRICE)
            .tax(UPDATED_TAX)
            .others(UPDATED_OTHERS)
            .totalPayable(UPDATED_TOTAL_PAYABLE)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY)
            .note(UPDATED_NOTE);
        return monthlySalaryDtl;
    }

    @BeforeEach
    public void initTest() {
        monthlySalaryDtl = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonthlySalaryDtl() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryDtlRepository.findAll().size();
        // Create the MonthlySalaryDtl
        restMonthlySalaryDtlMockMvc.perform(post("/api/monthly-salary-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDtl)))
            .andExpect(status().isCreated());

        // Validate the MonthlySalaryDtl in the database
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlySalaryDtl testMonthlySalaryDtl = monthlySalaryDtlList.get(monthlySalaryDtlList.size() - 1);
        assertThat(testMonthlySalaryDtl.getGross()).isEqualTo(DEFAULT_GROSS);
        assertThat(testMonthlySalaryDtl.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testMonthlySalaryDtl.getBasicPercent()).isEqualTo(DEFAULT_BASIC_PERCENT);
        assertThat(testMonthlySalaryDtl.getHouseRent()).isEqualTo(DEFAULT_HOUSE_RENT);
        assertThat(testMonthlySalaryDtl.getHouseRentPercent()).isEqualTo(DEFAULT_HOUSE_RENT_PERCENT);
        assertThat(testMonthlySalaryDtl.getMedicalAllowance()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getMedicalAllowancePercent()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getConvinceAllowance()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getConvinceAllowancePercent()).isEqualTo(DEFAULT_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getFoodAllowance()).isEqualTo(DEFAULT_FOOD_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getFoodAllowancePercent()).isEqualTo(DEFAULT_FOOD_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getFine()).isEqualTo(DEFAULT_FINE);
        assertThat(testMonthlySalaryDtl.getAdvance()).isEqualTo(DEFAULT_ADVANCE);
        assertThat(testMonthlySalaryDtl.getTotalWorkingDays()).isEqualTo(DEFAULT_TOTAL_WORKING_DAYS);
        assertThat(testMonthlySalaryDtl.getRegularLeave()).isEqualTo(DEFAULT_REGULAR_LEAVE);
        assertThat(testMonthlySalaryDtl.getSickLeave()).isEqualTo(DEFAULT_SICK_LEAVE);
        assertThat(testMonthlySalaryDtl.getCompensationLeave()).isEqualTo(DEFAULT_COMPENSATION_LEAVE);
        assertThat(testMonthlySalaryDtl.getFestivalLeave()).isEqualTo(DEFAULT_FESTIVAL_LEAVE);
        assertThat(testMonthlySalaryDtl.getWeeklyLeave()).isEqualTo(DEFAULT_WEEKLY_LEAVE);
        assertThat(testMonthlySalaryDtl.getPresent()).isEqualTo(DEFAULT_PRESENT);
        assertThat(testMonthlySalaryDtl.getAbsent()).isEqualTo(DEFAULT_ABSENT);
        assertThat(testMonthlySalaryDtl.getTotalMonthDays()).isEqualTo(DEFAULT_TOTAL_MONTH_DAYS);
        assertThat(testMonthlySalaryDtl.getOverTimeHour()).isEqualTo(DEFAULT_OVER_TIME_HOUR);
        assertThat(testMonthlySalaryDtl.getOverTimeSalaryHourly()).isEqualTo(DEFAULT_OVER_TIME_SALARY_HOURLY);
        assertThat(testMonthlySalaryDtl.getOverTimeSalary()).isEqualTo(DEFAULT_OVER_TIME_SALARY);
        assertThat(testMonthlySalaryDtl.getPresentBonus()).isEqualTo(DEFAULT_PRESENT_BONUS);
        assertThat(testMonthlySalaryDtl.getAbsentFine()).isEqualTo(DEFAULT_ABSENT_FINE);
        assertThat(testMonthlySalaryDtl.getStampPrice()).isEqualTo(DEFAULT_STAMP_PRICE);
        assertThat(testMonthlySalaryDtl.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testMonthlySalaryDtl.getOthers()).isEqualTo(DEFAULT_OTHERS);
        assertThat(testMonthlySalaryDtl.getTotalPayable()).isEqualTo(DEFAULT_TOTAL_PAYABLE);
        assertThat(testMonthlySalaryDtl.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMonthlySalaryDtl.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMonthlySalaryDtl.getExecutedOn()).isEqualTo(DEFAULT_EXECUTED_ON);
        assertThat(testMonthlySalaryDtl.getExecutedBy()).isEqualTo(DEFAULT_EXECUTED_BY);
        assertThat(testMonthlySalaryDtl.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createMonthlySalaryDtlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryDtlRepository.findAll().size();

        // Create the MonthlySalaryDtl with an existing ID
        monthlySalaryDtl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlySalaryDtlMockMvc.perform(post("/api/monthly-salary-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDtl)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalaryDtl in the database
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtls() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalaryDtl.getId().intValue())))
            .andExpect(jsonPath("$.[*].gross").value(hasItem(DEFAULT_GROSS.intValue())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].basicPercent").value(hasItem(DEFAULT_BASIC_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRentPercent").value(hasItem(DEFAULT_HOUSE_RENT_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowancePercent").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowance").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowancePercent").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowancePercent").value(hasItem(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.intValue())))
            .andExpect(jsonPath("$.[*].advance").value(hasItem(DEFAULT_ADVANCE.intValue())))
            .andExpect(jsonPath("$.[*].totalWorkingDays").value(hasItem(DEFAULT_TOTAL_WORKING_DAYS)))
            .andExpect(jsonPath("$.[*].regularLeave").value(hasItem(DEFAULT_REGULAR_LEAVE)))
            .andExpect(jsonPath("$.[*].sickLeave").value(hasItem(DEFAULT_SICK_LEAVE)))
            .andExpect(jsonPath("$.[*].compensationLeave").value(hasItem(DEFAULT_COMPENSATION_LEAVE)))
            .andExpect(jsonPath("$.[*].festivalLeave").value(hasItem(DEFAULT_FESTIVAL_LEAVE)))
            .andExpect(jsonPath("$.[*].weeklyLeave").value(hasItem(DEFAULT_WEEKLY_LEAVE)))
            .andExpect(jsonPath("$.[*].present").value(hasItem(DEFAULT_PRESENT)))
            .andExpect(jsonPath("$.[*].absent").value(hasItem(DEFAULT_ABSENT)))
            .andExpect(jsonPath("$.[*].totalMonthDays").value(hasItem(DEFAULT_TOTAL_MONTH_DAYS)))
            .andExpect(jsonPath("$.[*].overTimeHour").value(hasItem(DEFAULT_OVER_TIME_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].overTimeSalaryHourly").value(hasItem(DEFAULT_OVER_TIME_SALARY_HOURLY.intValue())))
            .andExpect(jsonPath("$.[*].overTimeSalary").value(hasItem(DEFAULT_OVER_TIME_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].presentBonus").value(hasItem(DEFAULT_PRESENT_BONUS.intValue())))
            .andExpect(jsonPath("$.[*].absentFine").value(hasItem(DEFAULT_ABSENT_FINE.intValue())))
            .andExpect(jsonPath("$.[*].stampPrice").value(hasItem(DEFAULT_STAMP_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.intValue())))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS.intValue())))
            .andExpect(jsonPath("$.[*].totalPayable").value(hasItem(DEFAULT_TOTAL_PAYABLE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getMonthlySalaryDtl() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get the monthlySalaryDtl
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls/{id}", monthlySalaryDtl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monthlySalaryDtl.getId().intValue()))
            .andExpect(jsonPath("$.gross").value(DEFAULT_GROSS.intValue()))
            .andExpect(jsonPath("$.basic").value(DEFAULT_BASIC.intValue()))
            .andExpect(jsonPath("$.basicPercent").value(DEFAULT_BASIC_PERCENT.intValue()))
            .andExpect(jsonPath("$.houseRent").value(DEFAULT_HOUSE_RENT.intValue()))
            .andExpect(jsonPath("$.houseRentPercent").value(DEFAULT_HOUSE_RENT_PERCENT.intValue()))
            .andExpect(jsonPath("$.medicalAllowance").value(DEFAULT_MEDICAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.medicalAllowancePercent").value(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.convinceAllowance").value(DEFAULT_CONVINCE_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.convinceAllowancePercent").value(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.foodAllowance").value(DEFAULT_FOOD_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.foodAllowancePercent").value(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue()))
            .andExpect(jsonPath("$.fine").value(DEFAULT_FINE.intValue()))
            .andExpect(jsonPath("$.advance").value(DEFAULT_ADVANCE.intValue()))
            .andExpect(jsonPath("$.totalWorkingDays").value(DEFAULT_TOTAL_WORKING_DAYS))
            .andExpect(jsonPath("$.regularLeave").value(DEFAULT_REGULAR_LEAVE))
            .andExpect(jsonPath("$.sickLeave").value(DEFAULT_SICK_LEAVE))
            .andExpect(jsonPath("$.compensationLeave").value(DEFAULT_COMPENSATION_LEAVE))
            .andExpect(jsonPath("$.festivalLeave").value(DEFAULT_FESTIVAL_LEAVE))
            .andExpect(jsonPath("$.weeklyLeave").value(DEFAULT_WEEKLY_LEAVE))
            .andExpect(jsonPath("$.present").value(DEFAULT_PRESENT))
            .andExpect(jsonPath("$.absent").value(DEFAULT_ABSENT))
            .andExpect(jsonPath("$.totalMonthDays").value(DEFAULT_TOTAL_MONTH_DAYS))
            .andExpect(jsonPath("$.overTimeHour").value(DEFAULT_OVER_TIME_HOUR.doubleValue()))
            .andExpect(jsonPath("$.overTimeSalaryHourly").value(DEFAULT_OVER_TIME_SALARY_HOURLY.intValue()))
            .andExpect(jsonPath("$.overTimeSalary").value(DEFAULT_OVER_TIME_SALARY.intValue()))
            .andExpect(jsonPath("$.presentBonus").value(DEFAULT_PRESENT_BONUS.intValue()))
            .andExpect(jsonPath("$.absentFine").value(DEFAULT_ABSENT_FINE.intValue()))
            .andExpect(jsonPath("$.stampPrice").value(DEFAULT_STAMP_PRICE.intValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.intValue()))
            .andExpect(jsonPath("$.others").value(DEFAULT_OTHERS.intValue()))
            .andExpect(jsonPath("$.totalPayable").value(DEFAULT_TOTAL_PAYABLE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.executedOn").value(DEFAULT_EXECUTED_ON.toString()))
            .andExpect(jsonPath("$.executedBy").value(DEFAULT_EXECUTED_BY))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }


    @Test
    @Transactional
    public void getMonthlySalaryDtlsByIdFiltering() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        Long id = monthlySalaryDtl.getId();

        defaultMonthlySalaryDtlShouldBeFound("id.equals=" + id);
        defaultMonthlySalaryDtlShouldNotBeFound("id.notEquals=" + id);

        defaultMonthlySalaryDtlShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMonthlySalaryDtlShouldNotBeFound("id.greaterThan=" + id);

        defaultMonthlySalaryDtlShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMonthlySalaryDtlShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross equals to DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.equals=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross equals to UPDATED_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.equals=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross not equals to DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.notEquals=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross not equals to UPDATED_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.notEquals=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross in DEFAULT_GROSS or UPDATED_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.in=" + DEFAULT_GROSS + "," + UPDATED_GROSS);

        // Get all the monthlySalaryDtlList where gross equals to UPDATED_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.in=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is not null
        defaultMonthlySalaryDtlShouldBeFound("gross.specified=true");

        // Get all the monthlySalaryDtlList where gross is null
        defaultMonthlySalaryDtlShouldNotBeFound("gross.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is greater than or equal to DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.greaterThanOrEqual=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross is greater than or equal to UPDATED_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.greaterThanOrEqual=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is less than or equal to DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.lessThanOrEqual=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross is less than or equal to SMALLER_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.lessThanOrEqual=" + SMALLER_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is less than DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.lessThan=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross is less than UPDATED_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.lessThan=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByGrossIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where gross is greater than DEFAULT_GROSS
        defaultMonthlySalaryDtlShouldNotBeFound("gross.greaterThan=" + DEFAULT_GROSS);

        // Get all the monthlySalaryDtlList where gross is greater than SMALLER_GROSS
        defaultMonthlySalaryDtlShouldBeFound("gross.greaterThan=" + SMALLER_GROSS);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic equals to DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.equals=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic equals to UPDATED_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.equals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic not equals to DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.notEquals=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic not equals to UPDATED_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.notEquals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic in DEFAULT_BASIC or UPDATED_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.in=" + DEFAULT_BASIC + "," + UPDATED_BASIC);

        // Get all the monthlySalaryDtlList where basic equals to UPDATED_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.in=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is not null
        defaultMonthlySalaryDtlShouldBeFound("basic.specified=true");

        // Get all the monthlySalaryDtlList where basic is null
        defaultMonthlySalaryDtlShouldNotBeFound("basic.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is greater than or equal to DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.greaterThanOrEqual=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic is greater than or equal to UPDATED_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.greaterThanOrEqual=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is less than or equal to DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.lessThanOrEqual=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic is less than or equal to SMALLER_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.lessThanOrEqual=" + SMALLER_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is less than DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.lessThan=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic is less than UPDATED_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.lessThan=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basic is greater than DEFAULT_BASIC
        defaultMonthlySalaryDtlShouldNotBeFound("basic.greaterThan=" + DEFAULT_BASIC);

        // Get all the monthlySalaryDtlList where basic is greater than SMALLER_BASIC
        defaultMonthlySalaryDtlShouldBeFound("basic.greaterThan=" + SMALLER_BASIC);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent equals to DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.equals=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.equals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent not equals to DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.notEquals=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent not equals to UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.notEquals=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent in DEFAULT_BASIC_PERCENT or UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.in=" + DEFAULT_BASIC_PERCENT + "," + UPDATED_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent equals to UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.in=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is not null
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.specified=true");

        // Get all the monthlySalaryDtlList where basicPercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is greater than or equal to DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.greaterThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent is greater than or equal to UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.greaterThanOrEqual=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is less than or equal to DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.lessThanOrEqual=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent is less than or equal to SMALLER_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.lessThanOrEqual=" + SMALLER_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is less than DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.lessThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent is less than UPDATED_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.lessThan=" + UPDATED_BASIC_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByBasicPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where basicPercent is greater than DEFAULT_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("basicPercent.greaterThan=" + DEFAULT_BASIC_PERCENT);

        // Get all the monthlySalaryDtlList where basicPercent is greater than SMALLER_BASIC_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("basicPercent.greaterThan=" + SMALLER_BASIC_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent equals to DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.equals=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent equals to UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.equals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent not equals to DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.notEquals=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent not equals to UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.notEquals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent in DEFAULT_HOUSE_RENT or UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.in=" + DEFAULT_HOUSE_RENT + "," + UPDATED_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent equals to UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.in=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is not null
        defaultMonthlySalaryDtlShouldBeFound("houseRent.specified=true");

        // Get all the monthlySalaryDtlList where houseRent is null
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is greater than or equal to DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.greaterThanOrEqual=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent is greater than or equal to UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.greaterThanOrEqual=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is less than or equal to DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.lessThanOrEqual=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent is less than or equal to SMALLER_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.lessThanOrEqual=" + SMALLER_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is less than DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.lessThan=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent is less than UPDATED_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.lessThan=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRent is greater than DEFAULT_HOUSE_RENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRent.greaterThan=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryDtlList where houseRent is greater than SMALLER_HOUSE_RENT
        defaultMonthlySalaryDtlShouldBeFound("houseRent.greaterThan=" + SMALLER_HOUSE_RENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent equals to DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.equals=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent equals to UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.equals=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent not equals to DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.notEquals=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent not equals to UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.notEquals=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent in DEFAULT_HOUSE_RENT_PERCENT or UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.in=" + DEFAULT_HOUSE_RENT_PERCENT + "," + UPDATED_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent equals to UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.in=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is not null
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.specified=true");

        // Get all the monthlySalaryDtlList where houseRentPercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is greater than or equal to DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.greaterThanOrEqual=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent is greater than or equal to UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.greaterThanOrEqual=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is less than or equal to DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.lessThanOrEqual=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent is less than or equal to SMALLER_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.lessThanOrEqual=" + SMALLER_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is less than DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.lessThan=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent is less than UPDATED_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.lessThan=" + UPDATED_HOUSE_RENT_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByHouseRentPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where houseRentPercent is greater than DEFAULT_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("houseRentPercent.greaterThan=" + DEFAULT_HOUSE_RENT_PERCENT);

        // Get all the monthlySalaryDtlList where houseRentPercent is greater than SMALLER_HOUSE_RENT_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("houseRentPercent.greaterThan=" + SMALLER_HOUSE_RENT_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.equals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.equals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance not equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance not equals to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.notEquals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance in DEFAULT_MEDICAL_ALLOWANCE or UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.in=" + DEFAULT_MEDICAL_ALLOWANCE + "," + UPDATED_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.in=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is not null
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.specified=true");

        // Get all the monthlySalaryDtlList where medicalAllowance is null
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance is greater than or equal to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is less than or equal to DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance is less than or equal to SMALLER_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is less than DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance is less than UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.lessThan=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowance is greater than DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowance.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryDtlList where medicalAllowance is greater than SMALLER_MEDICAL_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowance.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.equals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.equals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent not equals to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.notEquals=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent not equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.notEquals=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent in DEFAULT_MEDICAL_ALLOWANCE_PERCENT or UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.in=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT + "," + UPDATED_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent equals to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.in=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is not null
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.specified=true");

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is greater than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.greaterThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is greater than or equal to UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.greaterThanOrEqual=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is less than or equal to DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.lessThanOrEqual=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is less than or equal to SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.lessThanOrEqual=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is less than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.lessThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is less than UPDATED_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.lessThan=" + UPDATED_MEDICAL_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMedicalAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is greater than DEFAULT_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("medicalAllowancePercent.greaterThan=" + DEFAULT_MEDICAL_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where medicalAllowancePercent is greater than SMALLER_MEDICAL_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("medicalAllowancePercent.greaterThan=" + SMALLER_MEDICAL_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.equals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.equals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance not equals to DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance not equals to UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.notEquals=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance in DEFAULT_CONVINCE_ALLOWANCE or UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.in=" + DEFAULT_CONVINCE_ALLOWANCE + "," + UPDATED_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance equals to UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.in=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is not null
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.specified=true");

        // Get all the monthlySalaryDtlList where convinceAllowance is null
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance is greater than or equal to UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is less than or equal to DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance is less than or equal to SMALLER_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is less than DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance is less than UPDATED_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.lessThan=" + UPDATED_CONVINCE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowance is greater than DEFAULT_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowance.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE);

        // Get all the monthlySalaryDtlList where convinceAllowance is greater than SMALLER_CONVINCE_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowance.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.equals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.equals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent not equals to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.notEquals=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent not equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.notEquals=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent in DEFAULT_CONVINCE_ALLOWANCE_PERCENT or UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.in=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT + "," + UPDATED_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent equals to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.in=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is not null
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.specified=true");

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is greater than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.greaterThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is greater than or equal to UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.greaterThanOrEqual=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is less than or equal to DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.lessThanOrEqual=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is less than or equal to SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.lessThanOrEqual=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is less than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.lessThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is less than UPDATED_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.lessThan=" + UPDATED_CONVINCE_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByConvinceAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is greater than DEFAULT_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("convinceAllowancePercent.greaterThan=" + DEFAULT_CONVINCE_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where convinceAllowancePercent is greater than SMALLER_CONVINCE_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("convinceAllowancePercent.greaterThan=" + SMALLER_CONVINCE_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance equals to DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.equals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.equals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance not equals to DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.notEquals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance not equals to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.notEquals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance in DEFAULT_FOOD_ALLOWANCE or UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.in=" + DEFAULT_FOOD_ALLOWANCE + "," + UPDATED_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.in=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is not null
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.specified=true");

        // Get all the monthlySalaryDtlList where foodAllowance is null
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is greater than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance is greater than or equal to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is less than or equal to DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance is less than or equal to SMALLER_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is less than DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.lessThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance is less than UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.lessThan=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowance is greater than DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowance.greaterThan=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryDtlList where foodAllowance is greater than SMALLER_FOOD_ALLOWANCE
        defaultMonthlySalaryDtlShouldBeFound("foodAllowance.greaterThan=" + SMALLER_FOOD_ALLOWANCE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.equals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.equals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent not equals to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.notEquals=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent not equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.notEquals=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent in DEFAULT_FOOD_ALLOWANCE_PERCENT or UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.in=" + DEFAULT_FOOD_ALLOWANCE_PERCENT + "," + UPDATED_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent equals to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.in=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is not null
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.specified=true");

        // Get all the monthlySalaryDtlList where foodAllowancePercent is null
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is greater than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.greaterThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is greater than or equal to UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.greaterThanOrEqual=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is less than or equal to DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.lessThanOrEqual=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is less than or equal to SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.lessThanOrEqual=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is less than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.lessThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is less than UPDATED_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.lessThan=" + UPDATED_FOOD_ALLOWANCE_PERCENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFoodAllowancePercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is greater than DEFAULT_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldNotBeFound("foodAllowancePercent.greaterThan=" + DEFAULT_FOOD_ALLOWANCE_PERCENT);

        // Get all the monthlySalaryDtlList where foodAllowancePercent is greater than SMALLER_FOOD_ALLOWANCE_PERCENT
        defaultMonthlySalaryDtlShouldBeFound("foodAllowancePercent.greaterThan=" + SMALLER_FOOD_ALLOWANCE_PERCENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine equals to DEFAULT_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.equals=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine equals to UPDATED_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.equals=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine not equals to DEFAULT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.notEquals=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine not equals to UPDATED_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.notEquals=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine in DEFAULT_FINE or UPDATED_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.in=" + DEFAULT_FINE + "," + UPDATED_FINE);

        // Get all the monthlySalaryDtlList where fine equals to UPDATED_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.in=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is not null
        defaultMonthlySalaryDtlShouldBeFound("fine.specified=true");

        // Get all the monthlySalaryDtlList where fine is null
        defaultMonthlySalaryDtlShouldNotBeFound("fine.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is greater than or equal to DEFAULT_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.greaterThanOrEqual=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine is greater than or equal to UPDATED_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.greaterThanOrEqual=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is less than or equal to DEFAULT_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.lessThanOrEqual=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine is less than or equal to SMALLER_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.lessThanOrEqual=" + SMALLER_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is less than DEFAULT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.lessThan=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine is less than UPDATED_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.lessThan=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where fine is greater than DEFAULT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("fine.greaterThan=" + DEFAULT_FINE);

        // Get all the monthlySalaryDtlList where fine is greater than SMALLER_FINE
        defaultMonthlySalaryDtlShouldBeFound("fine.greaterThan=" + SMALLER_FINE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance equals to DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.equals=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance equals to UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.equals=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance not equals to DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.notEquals=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance not equals to UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.notEquals=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance in DEFAULT_ADVANCE or UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.in=" + DEFAULT_ADVANCE + "," + UPDATED_ADVANCE);

        // Get all the monthlySalaryDtlList where advance equals to UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.in=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is not null
        defaultMonthlySalaryDtlShouldBeFound("advance.specified=true");

        // Get all the monthlySalaryDtlList where advance is null
        defaultMonthlySalaryDtlShouldNotBeFound("advance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is greater than or equal to DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.greaterThanOrEqual=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance is greater than or equal to UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.greaterThanOrEqual=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is less than or equal to DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.lessThanOrEqual=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance is less than or equal to SMALLER_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.lessThanOrEqual=" + SMALLER_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is less than DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.lessThan=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance is less than UPDATED_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.lessThan=" + UPDATED_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAdvanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where advance is greater than DEFAULT_ADVANCE
        defaultMonthlySalaryDtlShouldNotBeFound("advance.greaterThan=" + DEFAULT_ADVANCE);

        // Get all the monthlySalaryDtlList where advance is greater than SMALLER_ADVANCE
        defaultMonthlySalaryDtlShouldBeFound("advance.greaterThan=" + SMALLER_ADVANCE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalWorkingDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalWorkingDays equals to DEFAULT_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalWorkingDays.equals=" + DEFAULT_TOTAL_WORKING_DAYS);

        // Get all the monthlySalaryDtlList where totalWorkingDays equals to UPDATED_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalWorkingDays.equals=" + UPDATED_TOTAL_WORKING_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalWorkingDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalWorkingDays not equals to DEFAULT_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalWorkingDays.notEquals=" + DEFAULT_TOTAL_WORKING_DAYS);

        // Get all the monthlySalaryDtlList where totalWorkingDays not equals to UPDATED_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalWorkingDays.notEquals=" + UPDATED_TOTAL_WORKING_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalWorkingDaysIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalWorkingDays in DEFAULT_TOTAL_WORKING_DAYS or UPDATED_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalWorkingDays.in=" + DEFAULT_TOTAL_WORKING_DAYS + "," + UPDATED_TOTAL_WORKING_DAYS);

        // Get all the monthlySalaryDtlList where totalWorkingDays equals to UPDATED_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalWorkingDays.in=" + UPDATED_TOTAL_WORKING_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalWorkingDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalWorkingDays is not null
        defaultMonthlySalaryDtlShouldBeFound("totalWorkingDays.specified=true");

        // Get all the monthlySalaryDtlList where totalWorkingDays is null
        defaultMonthlySalaryDtlShouldNotBeFound("totalWorkingDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalWorkingDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalWorkingDays is greater than or equal to DEFAULT_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalWorkingDays.greaterThanOrEqual=" + DEFAULT_TOTAL_WORKING_DAYS);

        // Get all the monthlySalaryDtlList where totalWorkingDays is greater than or equal to UPDATED_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalWorkingDays.greaterThanOrEqual=" + UPDATED_TOTAL_WORKING_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalWorkingDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalWorkingDays is less than or equal to DEFAULT_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalWorkingDays.lessThanOrEqual=" + DEFAULT_TOTAL_WORKING_DAYS);

        // Get all the monthlySalaryDtlList where totalWorkingDays is less than or equal to SMALLER_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalWorkingDays.lessThanOrEqual=" + SMALLER_TOTAL_WORKING_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalWorkingDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalWorkingDays is less than DEFAULT_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalWorkingDays.lessThan=" + DEFAULT_TOTAL_WORKING_DAYS);

        // Get all the monthlySalaryDtlList where totalWorkingDays is less than UPDATED_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalWorkingDays.lessThan=" + UPDATED_TOTAL_WORKING_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalWorkingDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalWorkingDays is greater than DEFAULT_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalWorkingDays.greaterThan=" + DEFAULT_TOTAL_WORKING_DAYS);

        // Get all the monthlySalaryDtlList where totalWorkingDays is greater than SMALLER_TOTAL_WORKING_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalWorkingDays.greaterThan=" + SMALLER_TOTAL_WORKING_DAYS);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByRegularLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where regularLeave equals to DEFAULT_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("regularLeave.equals=" + DEFAULT_REGULAR_LEAVE);

        // Get all the monthlySalaryDtlList where regularLeave equals to UPDATED_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("regularLeave.equals=" + UPDATED_REGULAR_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByRegularLeaveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where regularLeave not equals to DEFAULT_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("regularLeave.notEquals=" + DEFAULT_REGULAR_LEAVE);

        // Get all the monthlySalaryDtlList where regularLeave not equals to UPDATED_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("regularLeave.notEquals=" + UPDATED_REGULAR_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByRegularLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where regularLeave in DEFAULT_REGULAR_LEAVE or UPDATED_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("regularLeave.in=" + DEFAULT_REGULAR_LEAVE + "," + UPDATED_REGULAR_LEAVE);

        // Get all the monthlySalaryDtlList where regularLeave equals to UPDATED_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("regularLeave.in=" + UPDATED_REGULAR_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByRegularLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where regularLeave is not null
        defaultMonthlySalaryDtlShouldBeFound("regularLeave.specified=true");

        // Get all the monthlySalaryDtlList where regularLeave is null
        defaultMonthlySalaryDtlShouldNotBeFound("regularLeave.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByRegularLeaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where regularLeave is greater than or equal to DEFAULT_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("regularLeave.greaterThanOrEqual=" + DEFAULT_REGULAR_LEAVE);

        // Get all the monthlySalaryDtlList where regularLeave is greater than or equal to UPDATED_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("regularLeave.greaterThanOrEqual=" + UPDATED_REGULAR_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByRegularLeaveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where regularLeave is less than or equal to DEFAULT_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("regularLeave.lessThanOrEqual=" + DEFAULT_REGULAR_LEAVE);

        // Get all the monthlySalaryDtlList where regularLeave is less than or equal to SMALLER_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("regularLeave.lessThanOrEqual=" + SMALLER_REGULAR_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByRegularLeaveIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where regularLeave is less than DEFAULT_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("regularLeave.lessThan=" + DEFAULT_REGULAR_LEAVE);

        // Get all the monthlySalaryDtlList where regularLeave is less than UPDATED_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("regularLeave.lessThan=" + UPDATED_REGULAR_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByRegularLeaveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where regularLeave is greater than DEFAULT_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("regularLeave.greaterThan=" + DEFAULT_REGULAR_LEAVE);

        // Get all the monthlySalaryDtlList where regularLeave is greater than SMALLER_REGULAR_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("regularLeave.greaterThan=" + SMALLER_REGULAR_LEAVE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsBySickLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where sickLeave equals to DEFAULT_SICK_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("sickLeave.equals=" + DEFAULT_SICK_LEAVE);

        // Get all the monthlySalaryDtlList where sickLeave equals to UPDATED_SICK_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("sickLeave.equals=" + UPDATED_SICK_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsBySickLeaveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where sickLeave not equals to DEFAULT_SICK_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("sickLeave.notEquals=" + DEFAULT_SICK_LEAVE);

        // Get all the monthlySalaryDtlList where sickLeave not equals to UPDATED_SICK_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("sickLeave.notEquals=" + UPDATED_SICK_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsBySickLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where sickLeave in DEFAULT_SICK_LEAVE or UPDATED_SICK_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("sickLeave.in=" + DEFAULT_SICK_LEAVE + "," + UPDATED_SICK_LEAVE);

        // Get all the monthlySalaryDtlList where sickLeave equals to UPDATED_SICK_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("sickLeave.in=" + UPDATED_SICK_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsBySickLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where sickLeave is not null
        defaultMonthlySalaryDtlShouldBeFound("sickLeave.specified=true");

        // Get all the monthlySalaryDtlList where sickLeave is null
        defaultMonthlySalaryDtlShouldNotBeFound("sickLeave.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsBySickLeaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where sickLeave is greater than or equal to DEFAULT_SICK_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("sickLeave.greaterThanOrEqual=" + DEFAULT_SICK_LEAVE);

        // Get all the monthlySalaryDtlList where sickLeave is greater than or equal to UPDATED_SICK_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("sickLeave.greaterThanOrEqual=" + UPDATED_SICK_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsBySickLeaveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where sickLeave is less than or equal to DEFAULT_SICK_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("sickLeave.lessThanOrEqual=" + DEFAULT_SICK_LEAVE);

        // Get all the monthlySalaryDtlList where sickLeave is less than or equal to SMALLER_SICK_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("sickLeave.lessThanOrEqual=" + SMALLER_SICK_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsBySickLeaveIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where sickLeave is less than DEFAULT_SICK_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("sickLeave.lessThan=" + DEFAULT_SICK_LEAVE);

        // Get all the monthlySalaryDtlList where sickLeave is less than UPDATED_SICK_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("sickLeave.lessThan=" + UPDATED_SICK_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsBySickLeaveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where sickLeave is greater than DEFAULT_SICK_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("sickLeave.greaterThan=" + DEFAULT_SICK_LEAVE);

        // Get all the monthlySalaryDtlList where sickLeave is greater than SMALLER_SICK_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("sickLeave.greaterThan=" + SMALLER_SICK_LEAVE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByCompensationLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where compensationLeave equals to DEFAULT_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("compensationLeave.equals=" + DEFAULT_COMPENSATION_LEAVE);

        // Get all the monthlySalaryDtlList where compensationLeave equals to UPDATED_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("compensationLeave.equals=" + UPDATED_COMPENSATION_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByCompensationLeaveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where compensationLeave not equals to DEFAULT_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("compensationLeave.notEquals=" + DEFAULT_COMPENSATION_LEAVE);

        // Get all the monthlySalaryDtlList where compensationLeave not equals to UPDATED_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("compensationLeave.notEquals=" + UPDATED_COMPENSATION_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByCompensationLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where compensationLeave in DEFAULT_COMPENSATION_LEAVE or UPDATED_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("compensationLeave.in=" + DEFAULT_COMPENSATION_LEAVE + "," + UPDATED_COMPENSATION_LEAVE);

        // Get all the monthlySalaryDtlList where compensationLeave equals to UPDATED_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("compensationLeave.in=" + UPDATED_COMPENSATION_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByCompensationLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where compensationLeave is not null
        defaultMonthlySalaryDtlShouldBeFound("compensationLeave.specified=true");

        // Get all the monthlySalaryDtlList where compensationLeave is null
        defaultMonthlySalaryDtlShouldNotBeFound("compensationLeave.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByCompensationLeaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where compensationLeave is greater than or equal to DEFAULT_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("compensationLeave.greaterThanOrEqual=" + DEFAULT_COMPENSATION_LEAVE);

        // Get all the monthlySalaryDtlList where compensationLeave is greater than or equal to UPDATED_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("compensationLeave.greaterThanOrEqual=" + UPDATED_COMPENSATION_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByCompensationLeaveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where compensationLeave is less than or equal to DEFAULT_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("compensationLeave.lessThanOrEqual=" + DEFAULT_COMPENSATION_LEAVE);

        // Get all the monthlySalaryDtlList where compensationLeave is less than or equal to SMALLER_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("compensationLeave.lessThanOrEqual=" + SMALLER_COMPENSATION_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByCompensationLeaveIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where compensationLeave is less than DEFAULT_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("compensationLeave.lessThan=" + DEFAULT_COMPENSATION_LEAVE);

        // Get all the monthlySalaryDtlList where compensationLeave is less than UPDATED_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("compensationLeave.lessThan=" + UPDATED_COMPENSATION_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByCompensationLeaveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where compensationLeave is greater than DEFAULT_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("compensationLeave.greaterThan=" + DEFAULT_COMPENSATION_LEAVE);

        // Get all the monthlySalaryDtlList where compensationLeave is greater than SMALLER_COMPENSATION_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("compensationLeave.greaterThan=" + SMALLER_COMPENSATION_LEAVE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFestivalLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where festivalLeave equals to DEFAULT_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("festivalLeave.equals=" + DEFAULT_FESTIVAL_LEAVE);

        // Get all the monthlySalaryDtlList where festivalLeave equals to UPDATED_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("festivalLeave.equals=" + UPDATED_FESTIVAL_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFestivalLeaveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where festivalLeave not equals to DEFAULT_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("festivalLeave.notEquals=" + DEFAULT_FESTIVAL_LEAVE);

        // Get all the monthlySalaryDtlList where festivalLeave not equals to UPDATED_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("festivalLeave.notEquals=" + UPDATED_FESTIVAL_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFestivalLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where festivalLeave in DEFAULT_FESTIVAL_LEAVE or UPDATED_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("festivalLeave.in=" + DEFAULT_FESTIVAL_LEAVE + "," + UPDATED_FESTIVAL_LEAVE);

        // Get all the monthlySalaryDtlList where festivalLeave equals to UPDATED_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("festivalLeave.in=" + UPDATED_FESTIVAL_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFestivalLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where festivalLeave is not null
        defaultMonthlySalaryDtlShouldBeFound("festivalLeave.specified=true");

        // Get all the monthlySalaryDtlList where festivalLeave is null
        defaultMonthlySalaryDtlShouldNotBeFound("festivalLeave.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFestivalLeaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where festivalLeave is greater than or equal to DEFAULT_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("festivalLeave.greaterThanOrEqual=" + DEFAULT_FESTIVAL_LEAVE);

        // Get all the monthlySalaryDtlList where festivalLeave is greater than or equal to UPDATED_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("festivalLeave.greaterThanOrEqual=" + UPDATED_FESTIVAL_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFestivalLeaveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where festivalLeave is less than or equal to DEFAULT_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("festivalLeave.lessThanOrEqual=" + DEFAULT_FESTIVAL_LEAVE);

        // Get all the monthlySalaryDtlList where festivalLeave is less than or equal to SMALLER_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("festivalLeave.lessThanOrEqual=" + SMALLER_FESTIVAL_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFestivalLeaveIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where festivalLeave is less than DEFAULT_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("festivalLeave.lessThan=" + DEFAULT_FESTIVAL_LEAVE);

        // Get all the monthlySalaryDtlList where festivalLeave is less than UPDATED_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("festivalLeave.lessThan=" + UPDATED_FESTIVAL_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByFestivalLeaveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where festivalLeave is greater than DEFAULT_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("festivalLeave.greaterThan=" + DEFAULT_FESTIVAL_LEAVE);

        // Get all the monthlySalaryDtlList where festivalLeave is greater than SMALLER_FESTIVAL_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("festivalLeave.greaterThan=" + SMALLER_FESTIVAL_LEAVE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByWeeklyLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where weeklyLeave equals to DEFAULT_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("weeklyLeave.equals=" + DEFAULT_WEEKLY_LEAVE);

        // Get all the monthlySalaryDtlList where weeklyLeave equals to UPDATED_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("weeklyLeave.equals=" + UPDATED_WEEKLY_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByWeeklyLeaveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where weeklyLeave not equals to DEFAULT_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("weeklyLeave.notEquals=" + DEFAULT_WEEKLY_LEAVE);

        // Get all the monthlySalaryDtlList where weeklyLeave not equals to UPDATED_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("weeklyLeave.notEquals=" + UPDATED_WEEKLY_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByWeeklyLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where weeklyLeave in DEFAULT_WEEKLY_LEAVE or UPDATED_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("weeklyLeave.in=" + DEFAULT_WEEKLY_LEAVE + "," + UPDATED_WEEKLY_LEAVE);

        // Get all the monthlySalaryDtlList where weeklyLeave equals to UPDATED_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("weeklyLeave.in=" + UPDATED_WEEKLY_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByWeeklyLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where weeklyLeave is not null
        defaultMonthlySalaryDtlShouldBeFound("weeklyLeave.specified=true");

        // Get all the monthlySalaryDtlList where weeklyLeave is null
        defaultMonthlySalaryDtlShouldNotBeFound("weeklyLeave.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByWeeklyLeaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where weeklyLeave is greater than or equal to DEFAULT_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("weeklyLeave.greaterThanOrEqual=" + DEFAULT_WEEKLY_LEAVE);

        // Get all the monthlySalaryDtlList where weeklyLeave is greater than or equal to UPDATED_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("weeklyLeave.greaterThanOrEqual=" + UPDATED_WEEKLY_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByWeeklyLeaveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where weeklyLeave is less than or equal to DEFAULT_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("weeklyLeave.lessThanOrEqual=" + DEFAULT_WEEKLY_LEAVE);

        // Get all the monthlySalaryDtlList where weeklyLeave is less than or equal to SMALLER_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("weeklyLeave.lessThanOrEqual=" + SMALLER_WEEKLY_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByWeeklyLeaveIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where weeklyLeave is less than DEFAULT_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("weeklyLeave.lessThan=" + DEFAULT_WEEKLY_LEAVE);

        // Get all the monthlySalaryDtlList where weeklyLeave is less than UPDATED_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("weeklyLeave.lessThan=" + UPDATED_WEEKLY_LEAVE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByWeeklyLeaveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where weeklyLeave is greater than DEFAULT_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldNotBeFound("weeklyLeave.greaterThan=" + DEFAULT_WEEKLY_LEAVE);

        // Get all the monthlySalaryDtlList where weeklyLeave is greater than SMALLER_WEEKLY_LEAVE
        defaultMonthlySalaryDtlShouldBeFound("weeklyLeave.greaterThan=" + SMALLER_WEEKLY_LEAVE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where present equals to DEFAULT_PRESENT
        defaultMonthlySalaryDtlShouldBeFound("present.equals=" + DEFAULT_PRESENT);

        // Get all the monthlySalaryDtlList where present equals to UPDATED_PRESENT
        defaultMonthlySalaryDtlShouldNotBeFound("present.equals=" + UPDATED_PRESENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where present not equals to DEFAULT_PRESENT
        defaultMonthlySalaryDtlShouldNotBeFound("present.notEquals=" + DEFAULT_PRESENT);

        // Get all the monthlySalaryDtlList where present not equals to UPDATED_PRESENT
        defaultMonthlySalaryDtlShouldBeFound("present.notEquals=" + UPDATED_PRESENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where present in DEFAULT_PRESENT or UPDATED_PRESENT
        defaultMonthlySalaryDtlShouldBeFound("present.in=" + DEFAULT_PRESENT + "," + UPDATED_PRESENT);

        // Get all the monthlySalaryDtlList where present equals to UPDATED_PRESENT
        defaultMonthlySalaryDtlShouldNotBeFound("present.in=" + UPDATED_PRESENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where present is not null
        defaultMonthlySalaryDtlShouldBeFound("present.specified=true");

        // Get all the monthlySalaryDtlList where present is null
        defaultMonthlySalaryDtlShouldNotBeFound("present.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where present is greater than or equal to DEFAULT_PRESENT
        defaultMonthlySalaryDtlShouldBeFound("present.greaterThanOrEqual=" + DEFAULT_PRESENT);

        // Get all the monthlySalaryDtlList where present is greater than or equal to UPDATED_PRESENT
        defaultMonthlySalaryDtlShouldNotBeFound("present.greaterThanOrEqual=" + UPDATED_PRESENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where present is less than or equal to DEFAULT_PRESENT
        defaultMonthlySalaryDtlShouldBeFound("present.lessThanOrEqual=" + DEFAULT_PRESENT);

        // Get all the monthlySalaryDtlList where present is less than or equal to SMALLER_PRESENT
        defaultMonthlySalaryDtlShouldNotBeFound("present.lessThanOrEqual=" + SMALLER_PRESENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where present is less than DEFAULT_PRESENT
        defaultMonthlySalaryDtlShouldNotBeFound("present.lessThan=" + DEFAULT_PRESENT);

        // Get all the monthlySalaryDtlList where present is less than UPDATED_PRESENT
        defaultMonthlySalaryDtlShouldBeFound("present.lessThan=" + UPDATED_PRESENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where present is greater than DEFAULT_PRESENT
        defaultMonthlySalaryDtlShouldNotBeFound("present.greaterThan=" + DEFAULT_PRESENT);

        // Get all the monthlySalaryDtlList where present is greater than SMALLER_PRESENT
        defaultMonthlySalaryDtlShouldBeFound("present.greaterThan=" + SMALLER_PRESENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absent equals to DEFAULT_ABSENT
        defaultMonthlySalaryDtlShouldBeFound("absent.equals=" + DEFAULT_ABSENT);

        // Get all the monthlySalaryDtlList where absent equals to UPDATED_ABSENT
        defaultMonthlySalaryDtlShouldNotBeFound("absent.equals=" + UPDATED_ABSENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absent not equals to DEFAULT_ABSENT
        defaultMonthlySalaryDtlShouldNotBeFound("absent.notEquals=" + DEFAULT_ABSENT);

        // Get all the monthlySalaryDtlList where absent not equals to UPDATED_ABSENT
        defaultMonthlySalaryDtlShouldBeFound("absent.notEquals=" + UPDATED_ABSENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absent in DEFAULT_ABSENT or UPDATED_ABSENT
        defaultMonthlySalaryDtlShouldBeFound("absent.in=" + DEFAULT_ABSENT + "," + UPDATED_ABSENT);

        // Get all the monthlySalaryDtlList where absent equals to UPDATED_ABSENT
        defaultMonthlySalaryDtlShouldNotBeFound("absent.in=" + UPDATED_ABSENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absent is not null
        defaultMonthlySalaryDtlShouldBeFound("absent.specified=true");

        // Get all the monthlySalaryDtlList where absent is null
        defaultMonthlySalaryDtlShouldNotBeFound("absent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absent is greater than or equal to DEFAULT_ABSENT
        defaultMonthlySalaryDtlShouldBeFound("absent.greaterThanOrEqual=" + DEFAULT_ABSENT);

        // Get all the monthlySalaryDtlList where absent is greater than or equal to UPDATED_ABSENT
        defaultMonthlySalaryDtlShouldNotBeFound("absent.greaterThanOrEqual=" + UPDATED_ABSENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absent is less than or equal to DEFAULT_ABSENT
        defaultMonthlySalaryDtlShouldBeFound("absent.lessThanOrEqual=" + DEFAULT_ABSENT);

        // Get all the monthlySalaryDtlList where absent is less than or equal to SMALLER_ABSENT
        defaultMonthlySalaryDtlShouldNotBeFound("absent.lessThanOrEqual=" + SMALLER_ABSENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absent is less than DEFAULT_ABSENT
        defaultMonthlySalaryDtlShouldNotBeFound("absent.lessThan=" + DEFAULT_ABSENT);

        // Get all the monthlySalaryDtlList where absent is less than UPDATED_ABSENT
        defaultMonthlySalaryDtlShouldBeFound("absent.lessThan=" + UPDATED_ABSENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absent is greater than DEFAULT_ABSENT
        defaultMonthlySalaryDtlShouldNotBeFound("absent.greaterThan=" + DEFAULT_ABSENT);

        // Get all the monthlySalaryDtlList where absent is greater than SMALLER_ABSENT
        defaultMonthlySalaryDtlShouldBeFound("absent.greaterThan=" + SMALLER_ABSENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalMonthDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalMonthDays equals to DEFAULT_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalMonthDays.equals=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the monthlySalaryDtlList where totalMonthDays equals to UPDATED_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalMonthDays.equals=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalMonthDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalMonthDays not equals to DEFAULT_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalMonthDays.notEquals=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the monthlySalaryDtlList where totalMonthDays not equals to UPDATED_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalMonthDays.notEquals=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalMonthDaysIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalMonthDays in DEFAULT_TOTAL_MONTH_DAYS or UPDATED_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalMonthDays.in=" + DEFAULT_TOTAL_MONTH_DAYS + "," + UPDATED_TOTAL_MONTH_DAYS);

        // Get all the monthlySalaryDtlList where totalMonthDays equals to UPDATED_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalMonthDays.in=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalMonthDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalMonthDays is not null
        defaultMonthlySalaryDtlShouldBeFound("totalMonthDays.specified=true");

        // Get all the monthlySalaryDtlList where totalMonthDays is null
        defaultMonthlySalaryDtlShouldNotBeFound("totalMonthDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalMonthDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalMonthDays is greater than or equal to DEFAULT_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalMonthDays.greaterThanOrEqual=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the monthlySalaryDtlList where totalMonthDays is greater than or equal to UPDATED_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalMonthDays.greaterThanOrEqual=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalMonthDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalMonthDays is less than or equal to DEFAULT_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalMonthDays.lessThanOrEqual=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the monthlySalaryDtlList where totalMonthDays is less than or equal to SMALLER_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalMonthDays.lessThanOrEqual=" + SMALLER_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalMonthDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalMonthDays is less than DEFAULT_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalMonthDays.lessThan=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the monthlySalaryDtlList where totalMonthDays is less than UPDATED_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalMonthDays.lessThan=" + UPDATED_TOTAL_MONTH_DAYS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalMonthDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalMonthDays is greater than DEFAULT_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldNotBeFound("totalMonthDays.greaterThan=" + DEFAULT_TOTAL_MONTH_DAYS);

        // Get all the monthlySalaryDtlList where totalMonthDays is greater than SMALLER_TOTAL_MONTH_DAYS
        defaultMonthlySalaryDtlShouldBeFound("totalMonthDays.greaterThan=" + SMALLER_TOTAL_MONTH_DAYS);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeHourIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeHour equals to DEFAULT_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldBeFound("overTimeHour.equals=" + DEFAULT_OVER_TIME_HOUR);

        // Get all the monthlySalaryDtlList where overTimeHour equals to UPDATED_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeHour.equals=" + UPDATED_OVER_TIME_HOUR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeHour not equals to DEFAULT_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeHour.notEquals=" + DEFAULT_OVER_TIME_HOUR);

        // Get all the monthlySalaryDtlList where overTimeHour not equals to UPDATED_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldBeFound("overTimeHour.notEquals=" + UPDATED_OVER_TIME_HOUR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeHourIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeHour in DEFAULT_OVER_TIME_HOUR or UPDATED_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldBeFound("overTimeHour.in=" + DEFAULT_OVER_TIME_HOUR + "," + UPDATED_OVER_TIME_HOUR);

        // Get all the monthlySalaryDtlList where overTimeHour equals to UPDATED_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeHour.in=" + UPDATED_OVER_TIME_HOUR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeHour is not null
        defaultMonthlySalaryDtlShouldBeFound("overTimeHour.specified=true");

        // Get all the monthlySalaryDtlList where overTimeHour is null
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeHour.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeHourIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeHour is greater than or equal to DEFAULT_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldBeFound("overTimeHour.greaterThanOrEqual=" + DEFAULT_OVER_TIME_HOUR);

        // Get all the monthlySalaryDtlList where overTimeHour is greater than or equal to UPDATED_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeHour.greaterThanOrEqual=" + UPDATED_OVER_TIME_HOUR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeHourIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeHour is less than or equal to DEFAULT_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldBeFound("overTimeHour.lessThanOrEqual=" + DEFAULT_OVER_TIME_HOUR);

        // Get all the monthlySalaryDtlList where overTimeHour is less than or equal to SMALLER_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeHour.lessThanOrEqual=" + SMALLER_OVER_TIME_HOUR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeHourIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeHour is less than DEFAULT_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeHour.lessThan=" + DEFAULT_OVER_TIME_HOUR);

        // Get all the monthlySalaryDtlList where overTimeHour is less than UPDATED_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldBeFound("overTimeHour.lessThan=" + UPDATED_OVER_TIME_HOUR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeHourIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeHour is greater than DEFAULT_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeHour.greaterThan=" + DEFAULT_OVER_TIME_HOUR);

        // Get all the monthlySalaryDtlList where overTimeHour is greater than SMALLER_OVER_TIME_HOUR
        defaultMonthlySalaryDtlShouldBeFound("overTimeHour.greaterThan=" + SMALLER_OVER_TIME_HOUR);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryHourlyIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly equals to DEFAULT_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalaryHourly.equals=" + DEFAULT_OVER_TIME_SALARY_HOURLY);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly equals to UPDATED_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalaryHourly.equals=" + UPDATED_OVER_TIME_SALARY_HOURLY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryHourlyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly not equals to DEFAULT_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalaryHourly.notEquals=" + DEFAULT_OVER_TIME_SALARY_HOURLY);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly not equals to UPDATED_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalaryHourly.notEquals=" + UPDATED_OVER_TIME_SALARY_HOURLY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryHourlyIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly in DEFAULT_OVER_TIME_SALARY_HOURLY or UPDATED_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalaryHourly.in=" + DEFAULT_OVER_TIME_SALARY_HOURLY + "," + UPDATED_OVER_TIME_SALARY_HOURLY);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly equals to UPDATED_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalaryHourly.in=" + UPDATED_OVER_TIME_SALARY_HOURLY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryHourlyIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is not null
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalaryHourly.specified=true");

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is null
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalaryHourly.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryHourlyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is greater than or equal to DEFAULT_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalaryHourly.greaterThanOrEqual=" + DEFAULT_OVER_TIME_SALARY_HOURLY);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is greater than or equal to UPDATED_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalaryHourly.greaterThanOrEqual=" + UPDATED_OVER_TIME_SALARY_HOURLY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryHourlyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is less than or equal to DEFAULT_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalaryHourly.lessThanOrEqual=" + DEFAULT_OVER_TIME_SALARY_HOURLY);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is less than or equal to SMALLER_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalaryHourly.lessThanOrEqual=" + SMALLER_OVER_TIME_SALARY_HOURLY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryHourlyIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is less than DEFAULT_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalaryHourly.lessThan=" + DEFAULT_OVER_TIME_SALARY_HOURLY);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is less than UPDATED_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalaryHourly.lessThan=" + UPDATED_OVER_TIME_SALARY_HOURLY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryHourlyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is greater than DEFAULT_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalaryHourly.greaterThan=" + DEFAULT_OVER_TIME_SALARY_HOURLY);

        // Get all the monthlySalaryDtlList where overTimeSalaryHourly is greater than SMALLER_OVER_TIME_SALARY_HOURLY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalaryHourly.greaterThan=" + SMALLER_OVER_TIME_SALARY_HOURLY);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalary equals to DEFAULT_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalary.equals=" + DEFAULT_OVER_TIME_SALARY);

        // Get all the monthlySalaryDtlList where overTimeSalary equals to UPDATED_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalary.equals=" + UPDATED_OVER_TIME_SALARY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalary not equals to DEFAULT_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalary.notEquals=" + DEFAULT_OVER_TIME_SALARY);

        // Get all the monthlySalaryDtlList where overTimeSalary not equals to UPDATED_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalary.notEquals=" + UPDATED_OVER_TIME_SALARY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalary in DEFAULT_OVER_TIME_SALARY or UPDATED_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalary.in=" + DEFAULT_OVER_TIME_SALARY + "," + UPDATED_OVER_TIME_SALARY);

        // Get all the monthlySalaryDtlList where overTimeSalary equals to UPDATED_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalary.in=" + UPDATED_OVER_TIME_SALARY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalary is not null
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalary.specified=true");

        // Get all the monthlySalaryDtlList where overTimeSalary is null
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalary.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalary is greater than or equal to DEFAULT_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalary.greaterThanOrEqual=" + DEFAULT_OVER_TIME_SALARY);

        // Get all the monthlySalaryDtlList where overTimeSalary is greater than or equal to UPDATED_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalary.greaterThanOrEqual=" + UPDATED_OVER_TIME_SALARY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalary is less than or equal to DEFAULT_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalary.lessThanOrEqual=" + DEFAULT_OVER_TIME_SALARY);

        // Get all the monthlySalaryDtlList where overTimeSalary is less than or equal to SMALLER_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalary.lessThanOrEqual=" + SMALLER_OVER_TIME_SALARY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalary is less than DEFAULT_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalary.lessThan=" + DEFAULT_OVER_TIME_SALARY);

        // Get all the monthlySalaryDtlList where overTimeSalary is less than UPDATED_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalary.lessThan=" + UPDATED_OVER_TIME_SALARY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOverTimeSalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where overTimeSalary is greater than DEFAULT_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldNotBeFound("overTimeSalary.greaterThan=" + DEFAULT_OVER_TIME_SALARY);

        // Get all the monthlySalaryDtlList where overTimeSalary is greater than SMALLER_OVER_TIME_SALARY
        defaultMonthlySalaryDtlShouldBeFound("overTimeSalary.greaterThan=" + SMALLER_OVER_TIME_SALARY);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentBonusIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where presentBonus equals to DEFAULT_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldBeFound("presentBonus.equals=" + DEFAULT_PRESENT_BONUS);

        // Get all the monthlySalaryDtlList where presentBonus equals to UPDATED_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldNotBeFound("presentBonus.equals=" + UPDATED_PRESENT_BONUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentBonusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where presentBonus not equals to DEFAULT_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldNotBeFound("presentBonus.notEquals=" + DEFAULT_PRESENT_BONUS);

        // Get all the monthlySalaryDtlList where presentBonus not equals to UPDATED_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldBeFound("presentBonus.notEquals=" + UPDATED_PRESENT_BONUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentBonusIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where presentBonus in DEFAULT_PRESENT_BONUS or UPDATED_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldBeFound("presentBonus.in=" + DEFAULT_PRESENT_BONUS + "," + UPDATED_PRESENT_BONUS);

        // Get all the monthlySalaryDtlList where presentBonus equals to UPDATED_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldNotBeFound("presentBonus.in=" + UPDATED_PRESENT_BONUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentBonusIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where presentBonus is not null
        defaultMonthlySalaryDtlShouldBeFound("presentBonus.specified=true");

        // Get all the monthlySalaryDtlList where presentBonus is null
        defaultMonthlySalaryDtlShouldNotBeFound("presentBonus.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentBonusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where presentBonus is greater than or equal to DEFAULT_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldBeFound("presentBonus.greaterThanOrEqual=" + DEFAULT_PRESENT_BONUS);

        // Get all the monthlySalaryDtlList where presentBonus is greater than or equal to UPDATED_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldNotBeFound("presentBonus.greaterThanOrEqual=" + UPDATED_PRESENT_BONUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentBonusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where presentBonus is less than or equal to DEFAULT_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldBeFound("presentBonus.lessThanOrEqual=" + DEFAULT_PRESENT_BONUS);

        // Get all the monthlySalaryDtlList where presentBonus is less than or equal to SMALLER_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldNotBeFound("presentBonus.lessThanOrEqual=" + SMALLER_PRESENT_BONUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentBonusIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where presentBonus is less than DEFAULT_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldNotBeFound("presentBonus.lessThan=" + DEFAULT_PRESENT_BONUS);

        // Get all the monthlySalaryDtlList where presentBonus is less than UPDATED_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldBeFound("presentBonus.lessThan=" + UPDATED_PRESENT_BONUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByPresentBonusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where presentBonus is greater than DEFAULT_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldNotBeFound("presentBonus.greaterThan=" + DEFAULT_PRESENT_BONUS);

        // Get all the monthlySalaryDtlList where presentBonus is greater than SMALLER_PRESENT_BONUS
        defaultMonthlySalaryDtlShouldBeFound("presentBonus.greaterThan=" + SMALLER_PRESENT_BONUS);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentFineIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absentFine equals to DEFAULT_ABSENT_FINE
        defaultMonthlySalaryDtlShouldBeFound("absentFine.equals=" + DEFAULT_ABSENT_FINE);

        // Get all the monthlySalaryDtlList where absentFine equals to UPDATED_ABSENT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("absentFine.equals=" + UPDATED_ABSENT_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentFineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absentFine not equals to DEFAULT_ABSENT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("absentFine.notEquals=" + DEFAULT_ABSENT_FINE);

        // Get all the monthlySalaryDtlList where absentFine not equals to UPDATED_ABSENT_FINE
        defaultMonthlySalaryDtlShouldBeFound("absentFine.notEquals=" + UPDATED_ABSENT_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentFineIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absentFine in DEFAULT_ABSENT_FINE or UPDATED_ABSENT_FINE
        defaultMonthlySalaryDtlShouldBeFound("absentFine.in=" + DEFAULT_ABSENT_FINE + "," + UPDATED_ABSENT_FINE);

        // Get all the monthlySalaryDtlList where absentFine equals to UPDATED_ABSENT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("absentFine.in=" + UPDATED_ABSENT_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentFineIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absentFine is not null
        defaultMonthlySalaryDtlShouldBeFound("absentFine.specified=true");

        // Get all the monthlySalaryDtlList where absentFine is null
        defaultMonthlySalaryDtlShouldNotBeFound("absentFine.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentFineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absentFine is greater than or equal to DEFAULT_ABSENT_FINE
        defaultMonthlySalaryDtlShouldBeFound("absentFine.greaterThanOrEqual=" + DEFAULT_ABSENT_FINE);

        // Get all the monthlySalaryDtlList where absentFine is greater than or equal to UPDATED_ABSENT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("absentFine.greaterThanOrEqual=" + UPDATED_ABSENT_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentFineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absentFine is less than or equal to DEFAULT_ABSENT_FINE
        defaultMonthlySalaryDtlShouldBeFound("absentFine.lessThanOrEqual=" + DEFAULT_ABSENT_FINE);

        // Get all the monthlySalaryDtlList where absentFine is less than or equal to SMALLER_ABSENT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("absentFine.lessThanOrEqual=" + SMALLER_ABSENT_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentFineIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absentFine is less than DEFAULT_ABSENT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("absentFine.lessThan=" + DEFAULT_ABSENT_FINE);

        // Get all the monthlySalaryDtlList where absentFine is less than UPDATED_ABSENT_FINE
        defaultMonthlySalaryDtlShouldBeFound("absentFine.lessThan=" + UPDATED_ABSENT_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByAbsentFineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where absentFine is greater than DEFAULT_ABSENT_FINE
        defaultMonthlySalaryDtlShouldNotBeFound("absentFine.greaterThan=" + DEFAULT_ABSENT_FINE);

        // Get all the monthlySalaryDtlList where absentFine is greater than SMALLER_ABSENT_FINE
        defaultMonthlySalaryDtlShouldBeFound("absentFine.greaterThan=" + SMALLER_ABSENT_FINE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStampPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where stampPrice equals to DEFAULT_STAMP_PRICE
        defaultMonthlySalaryDtlShouldBeFound("stampPrice.equals=" + DEFAULT_STAMP_PRICE);

        // Get all the monthlySalaryDtlList where stampPrice equals to UPDATED_STAMP_PRICE
        defaultMonthlySalaryDtlShouldNotBeFound("stampPrice.equals=" + UPDATED_STAMP_PRICE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStampPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where stampPrice not equals to DEFAULT_STAMP_PRICE
        defaultMonthlySalaryDtlShouldNotBeFound("stampPrice.notEquals=" + DEFAULT_STAMP_PRICE);

        // Get all the monthlySalaryDtlList where stampPrice not equals to UPDATED_STAMP_PRICE
        defaultMonthlySalaryDtlShouldBeFound("stampPrice.notEquals=" + UPDATED_STAMP_PRICE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStampPriceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where stampPrice in DEFAULT_STAMP_PRICE or UPDATED_STAMP_PRICE
        defaultMonthlySalaryDtlShouldBeFound("stampPrice.in=" + DEFAULT_STAMP_PRICE + "," + UPDATED_STAMP_PRICE);

        // Get all the monthlySalaryDtlList where stampPrice equals to UPDATED_STAMP_PRICE
        defaultMonthlySalaryDtlShouldNotBeFound("stampPrice.in=" + UPDATED_STAMP_PRICE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStampPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where stampPrice is not null
        defaultMonthlySalaryDtlShouldBeFound("stampPrice.specified=true");

        // Get all the monthlySalaryDtlList where stampPrice is null
        defaultMonthlySalaryDtlShouldNotBeFound("stampPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStampPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where stampPrice is greater than or equal to DEFAULT_STAMP_PRICE
        defaultMonthlySalaryDtlShouldBeFound("stampPrice.greaterThanOrEqual=" + DEFAULT_STAMP_PRICE);

        // Get all the monthlySalaryDtlList where stampPrice is greater than or equal to UPDATED_STAMP_PRICE
        defaultMonthlySalaryDtlShouldNotBeFound("stampPrice.greaterThanOrEqual=" + UPDATED_STAMP_PRICE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStampPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where stampPrice is less than or equal to DEFAULT_STAMP_PRICE
        defaultMonthlySalaryDtlShouldBeFound("stampPrice.lessThanOrEqual=" + DEFAULT_STAMP_PRICE);

        // Get all the monthlySalaryDtlList where stampPrice is less than or equal to SMALLER_STAMP_PRICE
        defaultMonthlySalaryDtlShouldNotBeFound("stampPrice.lessThanOrEqual=" + SMALLER_STAMP_PRICE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStampPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where stampPrice is less than DEFAULT_STAMP_PRICE
        defaultMonthlySalaryDtlShouldNotBeFound("stampPrice.lessThan=" + DEFAULT_STAMP_PRICE);

        // Get all the monthlySalaryDtlList where stampPrice is less than UPDATED_STAMP_PRICE
        defaultMonthlySalaryDtlShouldBeFound("stampPrice.lessThan=" + UPDATED_STAMP_PRICE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStampPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where stampPrice is greater than DEFAULT_STAMP_PRICE
        defaultMonthlySalaryDtlShouldNotBeFound("stampPrice.greaterThan=" + DEFAULT_STAMP_PRICE);

        // Get all the monthlySalaryDtlList where stampPrice is greater than SMALLER_STAMP_PRICE
        defaultMonthlySalaryDtlShouldBeFound("stampPrice.greaterThan=" + SMALLER_STAMP_PRICE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where tax equals to DEFAULT_TAX
        defaultMonthlySalaryDtlShouldBeFound("tax.equals=" + DEFAULT_TAX);

        // Get all the monthlySalaryDtlList where tax equals to UPDATED_TAX
        defaultMonthlySalaryDtlShouldNotBeFound("tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where tax not equals to DEFAULT_TAX
        defaultMonthlySalaryDtlShouldNotBeFound("tax.notEquals=" + DEFAULT_TAX);

        // Get all the monthlySalaryDtlList where tax not equals to UPDATED_TAX
        defaultMonthlySalaryDtlShouldBeFound("tax.notEquals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where tax in DEFAULT_TAX or UPDATED_TAX
        defaultMonthlySalaryDtlShouldBeFound("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX);

        // Get all the monthlySalaryDtlList where tax equals to UPDATED_TAX
        defaultMonthlySalaryDtlShouldNotBeFound("tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where tax is not null
        defaultMonthlySalaryDtlShouldBeFound("tax.specified=true");

        // Get all the monthlySalaryDtlList where tax is null
        defaultMonthlySalaryDtlShouldNotBeFound("tax.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where tax is greater than or equal to DEFAULT_TAX
        defaultMonthlySalaryDtlShouldBeFound("tax.greaterThanOrEqual=" + DEFAULT_TAX);

        // Get all the monthlySalaryDtlList where tax is greater than or equal to UPDATED_TAX
        defaultMonthlySalaryDtlShouldNotBeFound("tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where tax is less than or equal to DEFAULT_TAX
        defaultMonthlySalaryDtlShouldBeFound("tax.lessThanOrEqual=" + DEFAULT_TAX);

        // Get all the monthlySalaryDtlList where tax is less than or equal to SMALLER_TAX
        defaultMonthlySalaryDtlShouldNotBeFound("tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where tax is less than DEFAULT_TAX
        defaultMonthlySalaryDtlShouldNotBeFound("tax.lessThan=" + DEFAULT_TAX);

        // Get all the monthlySalaryDtlList where tax is less than UPDATED_TAX
        defaultMonthlySalaryDtlShouldBeFound("tax.lessThan=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where tax is greater than DEFAULT_TAX
        defaultMonthlySalaryDtlShouldNotBeFound("tax.greaterThan=" + DEFAULT_TAX);

        // Get all the monthlySalaryDtlList where tax is greater than SMALLER_TAX
        defaultMonthlySalaryDtlShouldBeFound("tax.greaterThan=" + SMALLER_TAX);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOthersIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where others equals to DEFAULT_OTHERS
        defaultMonthlySalaryDtlShouldBeFound("others.equals=" + DEFAULT_OTHERS);

        // Get all the monthlySalaryDtlList where others equals to UPDATED_OTHERS
        defaultMonthlySalaryDtlShouldNotBeFound("others.equals=" + UPDATED_OTHERS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOthersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where others not equals to DEFAULT_OTHERS
        defaultMonthlySalaryDtlShouldNotBeFound("others.notEquals=" + DEFAULT_OTHERS);

        // Get all the monthlySalaryDtlList where others not equals to UPDATED_OTHERS
        defaultMonthlySalaryDtlShouldBeFound("others.notEquals=" + UPDATED_OTHERS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOthersIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where others in DEFAULT_OTHERS or UPDATED_OTHERS
        defaultMonthlySalaryDtlShouldBeFound("others.in=" + DEFAULT_OTHERS + "," + UPDATED_OTHERS);

        // Get all the monthlySalaryDtlList where others equals to UPDATED_OTHERS
        defaultMonthlySalaryDtlShouldNotBeFound("others.in=" + UPDATED_OTHERS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOthersIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where others is not null
        defaultMonthlySalaryDtlShouldBeFound("others.specified=true");

        // Get all the monthlySalaryDtlList where others is null
        defaultMonthlySalaryDtlShouldNotBeFound("others.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOthersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where others is greater than or equal to DEFAULT_OTHERS
        defaultMonthlySalaryDtlShouldBeFound("others.greaterThanOrEqual=" + DEFAULT_OTHERS);

        // Get all the monthlySalaryDtlList where others is greater than or equal to UPDATED_OTHERS
        defaultMonthlySalaryDtlShouldNotBeFound("others.greaterThanOrEqual=" + UPDATED_OTHERS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOthersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where others is less than or equal to DEFAULT_OTHERS
        defaultMonthlySalaryDtlShouldBeFound("others.lessThanOrEqual=" + DEFAULT_OTHERS);

        // Get all the monthlySalaryDtlList where others is less than or equal to SMALLER_OTHERS
        defaultMonthlySalaryDtlShouldNotBeFound("others.lessThanOrEqual=" + SMALLER_OTHERS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOthersIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where others is less than DEFAULT_OTHERS
        defaultMonthlySalaryDtlShouldNotBeFound("others.lessThan=" + DEFAULT_OTHERS);

        // Get all the monthlySalaryDtlList where others is less than UPDATED_OTHERS
        defaultMonthlySalaryDtlShouldBeFound("others.lessThan=" + UPDATED_OTHERS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByOthersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where others is greater than DEFAULT_OTHERS
        defaultMonthlySalaryDtlShouldNotBeFound("others.greaterThan=" + DEFAULT_OTHERS);

        // Get all the monthlySalaryDtlList where others is greater than SMALLER_OTHERS
        defaultMonthlySalaryDtlShouldBeFound("others.greaterThan=" + SMALLER_OTHERS);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalPayableIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalPayable equals to DEFAULT_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldBeFound("totalPayable.equals=" + DEFAULT_TOTAL_PAYABLE);

        // Get all the monthlySalaryDtlList where totalPayable equals to UPDATED_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldNotBeFound("totalPayable.equals=" + UPDATED_TOTAL_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalPayableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalPayable not equals to DEFAULT_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldNotBeFound("totalPayable.notEquals=" + DEFAULT_TOTAL_PAYABLE);

        // Get all the monthlySalaryDtlList where totalPayable not equals to UPDATED_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldBeFound("totalPayable.notEquals=" + UPDATED_TOTAL_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalPayableIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalPayable in DEFAULT_TOTAL_PAYABLE or UPDATED_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldBeFound("totalPayable.in=" + DEFAULT_TOTAL_PAYABLE + "," + UPDATED_TOTAL_PAYABLE);

        // Get all the monthlySalaryDtlList where totalPayable equals to UPDATED_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldNotBeFound("totalPayable.in=" + UPDATED_TOTAL_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalPayableIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalPayable is not null
        defaultMonthlySalaryDtlShouldBeFound("totalPayable.specified=true");

        // Get all the monthlySalaryDtlList where totalPayable is null
        defaultMonthlySalaryDtlShouldNotBeFound("totalPayable.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalPayableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalPayable is greater than or equal to DEFAULT_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldBeFound("totalPayable.greaterThanOrEqual=" + DEFAULT_TOTAL_PAYABLE);

        // Get all the monthlySalaryDtlList where totalPayable is greater than or equal to UPDATED_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldNotBeFound("totalPayable.greaterThanOrEqual=" + UPDATED_TOTAL_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalPayableIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalPayable is less than or equal to DEFAULT_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldBeFound("totalPayable.lessThanOrEqual=" + DEFAULT_TOTAL_PAYABLE);

        // Get all the monthlySalaryDtlList where totalPayable is less than or equal to SMALLER_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldNotBeFound("totalPayable.lessThanOrEqual=" + SMALLER_TOTAL_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalPayableIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalPayable is less than DEFAULT_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldNotBeFound("totalPayable.lessThan=" + DEFAULT_TOTAL_PAYABLE);

        // Get all the monthlySalaryDtlList where totalPayable is less than UPDATED_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldBeFound("totalPayable.lessThan=" + UPDATED_TOTAL_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTotalPayableIsGreaterThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where totalPayable is greater than DEFAULT_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldNotBeFound("totalPayable.greaterThan=" + DEFAULT_TOTAL_PAYABLE);

        // Get all the monthlySalaryDtlList where totalPayable is greater than SMALLER_TOTAL_PAYABLE
        defaultMonthlySalaryDtlShouldBeFound("totalPayable.greaterThan=" + SMALLER_TOTAL_PAYABLE);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where status equals to DEFAULT_STATUS
        defaultMonthlySalaryDtlShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the monthlySalaryDtlList where status equals to UPDATED_STATUS
        defaultMonthlySalaryDtlShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where status not equals to DEFAULT_STATUS
        defaultMonthlySalaryDtlShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the monthlySalaryDtlList where status not equals to UPDATED_STATUS
        defaultMonthlySalaryDtlShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMonthlySalaryDtlShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the monthlySalaryDtlList where status equals to UPDATED_STATUS
        defaultMonthlySalaryDtlShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where status is not null
        defaultMonthlySalaryDtlShouldBeFound("status.specified=true");

        // Get all the monthlySalaryDtlList where status is null
        defaultMonthlySalaryDtlShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where type equals to DEFAULT_TYPE
        defaultMonthlySalaryDtlShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the monthlySalaryDtlList where type equals to UPDATED_TYPE
        defaultMonthlySalaryDtlShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where type not equals to DEFAULT_TYPE
        defaultMonthlySalaryDtlShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the monthlySalaryDtlList where type not equals to UPDATED_TYPE
        defaultMonthlySalaryDtlShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMonthlySalaryDtlShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the monthlySalaryDtlList where type equals to UPDATED_TYPE
        defaultMonthlySalaryDtlShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where type is not null
        defaultMonthlySalaryDtlShouldBeFound("type.specified=true");

        // Get all the monthlySalaryDtlList where type is null
        defaultMonthlySalaryDtlShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedOn equals to DEFAULT_EXECUTED_ON
        defaultMonthlySalaryDtlShouldBeFound("executedOn.equals=" + DEFAULT_EXECUTED_ON);

        // Get all the monthlySalaryDtlList where executedOn equals to UPDATED_EXECUTED_ON
        defaultMonthlySalaryDtlShouldNotBeFound("executedOn.equals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedOn not equals to DEFAULT_EXECUTED_ON
        defaultMonthlySalaryDtlShouldNotBeFound("executedOn.notEquals=" + DEFAULT_EXECUTED_ON);

        // Get all the monthlySalaryDtlList where executedOn not equals to UPDATED_EXECUTED_ON
        defaultMonthlySalaryDtlShouldBeFound("executedOn.notEquals=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedOnIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedOn in DEFAULT_EXECUTED_ON or UPDATED_EXECUTED_ON
        defaultMonthlySalaryDtlShouldBeFound("executedOn.in=" + DEFAULT_EXECUTED_ON + "," + UPDATED_EXECUTED_ON);

        // Get all the monthlySalaryDtlList where executedOn equals to UPDATED_EXECUTED_ON
        defaultMonthlySalaryDtlShouldNotBeFound("executedOn.in=" + UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedOn is not null
        defaultMonthlySalaryDtlShouldBeFound("executedOn.specified=true");

        // Get all the monthlySalaryDtlList where executedOn is null
        defaultMonthlySalaryDtlShouldNotBeFound("executedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy equals to DEFAULT_EXECUTED_BY
        defaultMonthlySalaryDtlShouldBeFound("executedBy.equals=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryDtlList where executedBy equals to UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.equals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy not equals to DEFAULT_EXECUTED_BY
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.notEquals=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryDtlList where executedBy not equals to UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldBeFound("executedBy.notEquals=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy in DEFAULT_EXECUTED_BY or UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldBeFound("executedBy.in=" + DEFAULT_EXECUTED_BY + "," + UPDATED_EXECUTED_BY);

        // Get all the monthlySalaryDtlList where executedBy equals to UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.in=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy is not null
        defaultMonthlySalaryDtlShouldBeFound("executedBy.specified=true");

        // Get all the monthlySalaryDtlList where executedBy is null
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByContainsSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy contains DEFAULT_EXECUTED_BY
        defaultMonthlySalaryDtlShouldBeFound("executedBy.contains=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryDtlList where executedBy contains UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.contains=" + UPDATED_EXECUTED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByExecutedByNotContainsSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);

        // Get all the monthlySalaryDtlList where executedBy does not contain DEFAULT_EXECUTED_BY
        defaultMonthlySalaryDtlShouldNotBeFound("executedBy.doesNotContain=" + DEFAULT_EXECUTED_BY);

        // Get all the monthlySalaryDtlList where executedBy does not contain UPDATED_EXECUTED_BY
        defaultMonthlySalaryDtlShouldBeFound("executedBy.doesNotContain=" + UPDATED_EXECUTED_BY);
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        monthlySalaryDtl.setEmployee(employee);
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);
        Long employeeId = employee.getId();

        // Get all the monthlySalaryDtlList where employee equals to employeeId
        defaultMonthlySalaryDtlShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the monthlySalaryDtlList where employee equals to employeeId + 1
        defaultMonthlySalaryDtlShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllMonthlySalaryDtlsByMonthlySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);
        MonthlySalary monthlySalary = MonthlySalaryResourceIT.createEntity(em);
        em.persist(monthlySalary);
        em.flush();
        monthlySalaryDtl.setMonthlySalary(monthlySalary);
        monthlySalaryDtlRepository.saveAndFlush(monthlySalaryDtl);
        Long monthlySalaryId = monthlySalary.getId();

        // Get all the monthlySalaryDtlList where monthlySalary equals to monthlySalaryId
        defaultMonthlySalaryDtlShouldBeFound("monthlySalaryId.equals=" + monthlySalaryId);

        // Get all the monthlySalaryDtlList where monthlySalary equals to monthlySalaryId + 1
        defaultMonthlySalaryDtlShouldNotBeFound("monthlySalaryId.equals=" + (monthlySalaryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMonthlySalaryDtlShouldBeFound(String filter) throws Exception {
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalaryDtl.getId().intValue())))
            .andExpect(jsonPath("$.[*].gross").value(hasItem(DEFAULT_GROSS.intValue())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].basicPercent").value(hasItem(DEFAULT_BASIC_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
            .andExpect(jsonPath("$.[*].houseRentPercent").value(hasItem(DEFAULT_HOUSE_RENT_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowancePercent").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowance").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].convinceAllowancePercent").value(hasItem(DEFAULT_CONVINCE_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowancePercent").value(hasItem(DEFAULT_FOOD_ALLOWANCE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.intValue())))
            .andExpect(jsonPath("$.[*].advance").value(hasItem(DEFAULT_ADVANCE.intValue())))
            .andExpect(jsonPath("$.[*].totalWorkingDays").value(hasItem(DEFAULT_TOTAL_WORKING_DAYS)))
            .andExpect(jsonPath("$.[*].regularLeave").value(hasItem(DEFAULT_REGULAR_LEAVE)))
            .andExpect(jsonPath("$.[*].sickLeave").value(hasItem(DEFAULT_SICK_LEAVE)))
            .andExpect(jsonPath("$.[*].compensationLeave").value(hasItem(DEFAULT_COMPENSATION_LEAVE)))
            .andExpect(jsonPath("$.[*].festivalLeave").value(hasItem(DEFAULT_FESTIVAL_LEAVE)))
            .andExpect(jsonPath("$.[*].weeklyLeave").value(hasItem(DEFAULT_WEEKLY_LEAVE)))
            .andExpect(jsonPath("$.[*].present").value(hasItem(DEFAULT_PRESENT)))
            .andExpect(jsonPath("$.[*].absent").value(hasItem(DEFAULT_ABSENT)))
            .andExpect(jsonPath("$.[*].totalMonthDays").value(hasItem(DEFAULT_TOTAL_MONTH_DAYS)))
            .andExpect(jsonPath("$.[*].overTimeHour").value(hasItem(DEFAULT_OVER_TIME_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].overTimeSalaryHourly").value(hasItem(DEFAULT_OVER_TIME_SALARY_HOURLY.intValue())))
            .andExpect(jsonPath("$.[*].overTimeSalary").value(hasItem(DEFAULT_OVER_TIME_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].presentBonus").value(hasItem(DEFAULT_PRESENT_BONUS.intValue())))
            .andExpect(jsonPath("$.[*].absentFine").value(hasItem(DEFAULT_ABSENT_FINE.intValue())))
            .andExpect(jsonPath("$.[*].stampPrice").value(hasItem(DEFAULT_STAMP_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.intValue())))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS.intValue())))
            .andExpect(jsonPath("$.[*].totalPayable").value(hasItem(DEFAULT_TOTAL_PAYABLE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));

        // Check, that the count call also returns 1
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMonthlySalaryDtlShouldNotBeFound(String filter) throws Exception {
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMonthlySalaryDtl() throws Exception {
        // Get the monthlySalaryDtl
        restMonthlySalaryDtlMockMvc.perform(get("/api/monthly-salary-dtls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonthlySalaryDtl() throws Exception {
        // Initialize the database
        monthlySalaryDtlService.save(monthlySalaryDtl);

        int databaseSizeBeforeUpdate = monthlySalaryDtlRepository.findAll().size();

        // Update the monthlySalaryDtl
        MonthlySalaryDtl updatedMonthlySalaryDtl = monthlySalaryDtlRepository.findById(monthlySalaryDtl.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlySalaryDtl are not directly saved in db
        em.detach(updatedMonthlySalaryDtl);
        updatedMonthlySalaryDtl
            .gross(UPDATED_GROSS)
            .basic(UPDATED_BASIC)
            .basicPercent(UPDATED_BASIC_PERCENT)
            .houseRent(UPDATED_HOUSE_RENT)
            .houseRentPercent(UPDATED_HOUSE_RENT_PERCENT)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .medicalAllowancePercent(UPDATED_MEDICAL_ALLOWANCE_PERCENT)
            .convinceAllowance(UPDATED_CONVINCE_ALLOWANCE)
            .convinceAllowancePercent(UPDATED_CONVINCE_ALLOWANCE_PERCENT)
            .foodAllowance(UPDATED_FOOD_ALLOWANCE)
            .foodAllowancePercent(UPDATED_FOOD_ALLOWANCE_PERCENT)
            .fine(UPDATED_FINE)
            .advance(UPDATED_ADVANCE)
            .totalWorkingDays(UPDATED_TOTAL_WORKING_DAYS)
            .regularLeave(UPDATED_REGULAR_LEAVE)
            .sickLeave(UPDATED_SICK_LEAVE)
            .compensationLeave(UPDATED_COMPENSATION_LEAVE)
            .festivalLeave(UPDATED_FESTIVAL_LEAVE)
            .weeklyLeave(UPDATED_WEEKLY_LEAVE)
            .present(UPDATED_PRESENT)
            .absent(UPDATED_ABSENT)
            .totalMonthDays(UPDATED_TOTAL_MONTH_DAYS)
            .overTimeHour(UPDATED_OVER_TIME_HOUR)
            .overTimeSalaryHourly(UPDATED_OVER_TIME_SALARY_HOURLY)
            .overTimeSalary(UPDATED_OVER_TIME_SALARY)
            .presentBonus(UPDATED_PRESENT_BONUS)
            .absentFine(UPDATED_ABSENT_FINE)
            .stampPrice(UPDATED_STAMP_PRICE)
            .tax(UPDATED_TAX)
            .others(UPDATED_OTHERS)
            .totalPayable(UPDATED_TOTAL_PAYABLE)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .executedOn(UPDATED_EXECUTED_ON)
            .executedBy(UPDATED_EXECUTED_BY)
            .note(UPDATED_NOTE);

        restMonthlySalaryDtlMockMvc.perform(put("/api/monthly-salary-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonthlySalaryDtl)))
            .andExpect(status().isOk());

        // Validate the MonthlySalaryDtl in the database
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeUpdate);
        MonthlySalaryDtl testMonthlySalaryDtl = monthlySalaryDtlList.get(monthlySalaryDtlList.size() - 1);
        assertThat(testMonthlySalaryDtl.getGross()).isEqualTo(UPDATED_GROSS);
        assertThat(testMonthlySalaryDtl.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testMonthlySalaryDtl.getBasicPercent()).isEqualTo(UPDATED_BASIC_PERCENT);
        assertThat(testMonthlySalaryDtl.getHouseRent()).isEqualTo(UPDATED_HOUSE_RENT);
        assertThat(testMonthlySalaryDtl.getHouseRentPercent()).isEqualTo(UPDATED_HOUSE_RENT_PERCENT);
        assertThat(testMonthlySalaryDtl.getMedicalAllowance()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getMedicalAllowancePercent()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getConvinceAllowance()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getConvinceAllowancePercent()).isEqualTo(UPDATED_CONVINCE_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getFoodAllowance()).isEqualTo(UPDATED_FOOD_ALLOWANCE);
        assertThat(testMonthlySalaryDtl.getFoodAllowancePercent()).isEqualTo(UPDATED_FOOD_ALLOWANCE_PERCENT);
        assertThat(testMonthlySalaryDtl.getFine()).isEqualTo(UPDATED_FINE);
        assertThat(testMonthlySalaryDtl.getAdvance()).isEqualTo(UPDATED_ADVANCE);
        assertThat(testMonthlySalaryDtl.getTotalWorkingDays()).isEqualTo(UPDATED_TOTAL_WORKING_DAYS);
        assertThat(testMonthlySalaryDtl.getRegularLeave()).isEqualTo(UPDATED_REGULAR_LEAVE);
        assertThat(testMonthlySalaryDtl.getSickLeave()).isEqualTo(UPDATED_SICK_LEAVE);
        assertThat(testMonthlySalaryDtl.getCompensationLeave()).isEqualTo(UPDATED_COMPENSATION_LEAVE);
        assertThat(testMonthlySalaryDtl.getFestivalLeave()).isEqualTo(UPDATED_FESTIVAL_LEAVE);
        assertThat(testMonthlySalaryDtl.getWeeklyLeave()).isEqualTo(UPDATED_WEEKLY_LEAVE);
        assertThat(testMonthlySalaryDtl.getPresent()).isEqualTo(UPDATED_PRESENT);
        assertThat(testMonthlySalaryDtl.getAbsent()).isEqualTo(UPDATED_ABSENT);
        assertThat(testMonthlySalaryDtl.getTotalMonthDays()).isEqualTo(UPDATED_TOTAL_MONTH_DAYS);
        assertThat(testMonthlySalaryDtl.getOverTimeHour()).isEqualTo(UPDATED_OVER_TIME_HOUR);
        assertThat(testMonthlySalaryDtl.getOverTimeSalaryHourly()).isEqualTo(UPDATED_OVER_TIME_SALARY_HOURLY);
        assertThat(testMonthlySalaryDtl.getOverTimeSalary()).isEqualTo(UPDATED_OVER_TIME_SALARY);
        assertThat(testMonthlySalaryDtl.getPresentBonus()).isEqualTo(UPDATED_PRESENT_BONUS);
        assertThat(testMonthlySalaryDtl.getAbsentFine()).isEqualTo(UPDATED_ABSENT_FINE);
        assertThat(testMonthlySalaryDtl.getStampPrice()).isEqualTo(UPDATED_STAMP_PRICE);
        assertThat(testMonthlySalaryDtl.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testMonthlySalaryDtl.getOthers()).isEqualTo(UPDATED_OTHERS);
        assertThat(testMonthlySalaryDtl.getTotalPayable()).isEqualTo(UPDATED_TOTAL_PAYABLE);
        assertThat(testMonthlySalaryDtl.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMonthlySalaryDtl.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMonthlySalaryDtl.getExecutedOn()).isEqualTo(UPDATED_EXECUTED_ON);
        assertThat(testMonthlySalaryDtl.getExecutedBy()).isEqualTo(UPDATED_EXECUTED_BY);
        assertThat(testMonthlySalaryDtl.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingMonthlySalaryDtl() throws Exception {
        int databaseSizeBeforeUpdate = monthlySalaryDtlRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlySalaryDtlMockMvc.perform(put("/api/monthly-salary-dtls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDtl)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalaryDtl in the database
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonthlySalaryDtl() throws Exception {
        // Initialize the database
        monthlySalaryDtlService.save(monthlySalaryDtl);

        int databaseSizeBeforeDelete = monthlySalaryDtlRepository.findAll().size();

        // Delete the monthlySalaryDtl
        restMonthlySalaryDtlMockMvc.perform(delete("/api/monthly-salary-dtls/{id}", monthlySalaryDtl.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MonthlySalaryDtl> monthlySalaryDtlList = monthlySalaryDtlRepository.findAll();
        assertThat(monthlySalaryDtlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
