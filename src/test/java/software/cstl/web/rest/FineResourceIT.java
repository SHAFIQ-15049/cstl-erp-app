package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Fine;
import software.cstl.domain.Employee;
import software.cstl.repository.FineRepository;
import software.cstl.service.FineService;
import software.cstl.service.dto.FineCriteria;
import software.cstl.service.FineQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.PaymentStatus;
/**
 * Integration tests for the {@link FineResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FineResourceIT {

    private static final LocalDate DEFAULT_FINED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINED_ON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FINED_ON = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FINE_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FINE_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_FINE_PERCENTAGE = new BigDecimal(1 - 1);

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.NOT_PAID;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.IN_PROGRESS;

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private FineService fineService;

    @Autowired
    private FineQueryService fineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFineMockMvc;

    private Fine fine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fine createEntity(EntityManager em) {
        Fine fine = new Fine()
            .finedOn(DEFAULT_FINED_ON)
            .reason(DEFAULT_REASON)
            .amount(DEFAULT_AMOUNT)
            .finePercentage(DEFAULT_FINE_PERCENTAGE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        return fine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fine createUpdatedEntity(EntityManager em) {
        Fine fine = new Fine()
            .finedOn(UPDATED_FINED_ON)
            .reason(UPDATED_REASON)
            .amount(UPDATED_AMOUNT)
            .finePercentage(UPDATED_FINE_PERCENTAGE)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        return fine;
    }

    @BeforeEach
    public void initTest() {
        fine = createEntity(em);
    }

    @Test
    @Transactional
    public void createFine() throws Exception {
        int databaseSizeBeforeCreate = fineRepository.findAll().size();
        // Create the Fine
        restFineMockMvc.perform(post("/api/fines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fine)))
            .andExpect(status().isCreated());

        // Validate the Fine in the database
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeCreate + 1);
        Fine testFine = fineList.get(fineList.size() - 1);
        assertThat(testFine.getFinedOn()).isEqualTo(DEFAULT_FINED_ON);
        assertThat(testFine.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testFine.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testFine.getFinePercentage()).isEqualTo(DEFAULT_FINE_PERCENTAGE);
        assertThat(testFine.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void createFineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fineRepository.findAll().size();

        // Create the Fine with an existing ID
        fine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFineMockMvc.perform(post("/api/fines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fine)))
            .andExpect(status().isBadRequest());

        // Validate the Fine in the database
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFines() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList
        restFineMockMvc.perform(get("/api/fines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fine.getId().intValue())))
            .andExpect(jsonPath("$.[*].finedOn").value(hasItem(DEFAULT_FINED_ON.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].finePercentage").value(hasItem(DEFAULT_FINE_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getFine() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get the fine
        restFineMockMvc.perform(get("/api/fines/{id}", fine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fine.getId().intValue()))
            .andExpect(jsonPath("$.finedOn").value(DEFAULT_FINED_ON.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.finePercentage").value(DEFAULT_FINE_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getFinesByIdFiltering() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        Long id = fine.getId();

        defaultFineShouldBeFound("id.equals=" + id);
        defaultFineShouldNotBeFound("id.notEquals=" + id);

        defaultFineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFineShouldNotBeFound("id.greaterThan=" + id);

        defaultFineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFinesByFinedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finedOn equals to DEFAULT_FINED_ON
        defaultFineShouldBeFound("finedOn.equals=" + DEFAULT_FINED_ON);

        // Get all the fineList where finedOn equals to UPDATED_FINED_ON
        defaultFineShouldNotBeFound("finedOn.equals=" + UPDATED_FINED_ON);
    }

    @Test
    @Transactional
    public void getAllFinesByFinedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finedOn not equals to DEFAULT_FINED_ON
        defaultFineShouldNotBeFound("finedOn.notEquals=" + DEFAULT_FINED_ON);

        // Get all the fineList where finedOn not equals to UPDATED_FINED_ON
        defaultFineShouldBeFound("finedOn.notEquals=" + UPDATED_FINED_ON);
    }

    @Test
    @Transactional
    public void getAllFinesByFinedOnIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finedOn in DEFAULT_FINED_ON or UPDATED_FINED_ON
        defaultFineShouldBeFound("finedOn.in=" + DEFAULT_FINED_ON + "," + UPDATED_FINED_ON);

        // Get all the fineList where finedOn equals to UPDATED_FINED_ON
        defaultFineShouldNotBeFound("finedOn.in=" + UPDATED_FINED_ON);
    }

    @Test
    @Transactional
    public void getAllFinesByFinedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finedOn is not null
        defaultFineShouldBeFound("finedOn.specified=true");

        // Get all the fineList where finedOn is null
        defaultFineShouldNotBeFound("finedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByFinedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finedOn is greater than or equal to DEFAULT_FINED_ON
        defaultFineShouldBeFound("finedOn.greaterThanOrEqual=" + DEFAULT_FINED_ON);

        // Get all the fineList where finedOn is greater than or equal to UPDATED_FINED_ON
        defaultFineShouldNotBeFound("finedOn.greaterThanOrEqual=" + UPDATED_FINED_ON);
    }

    @Test
    @Transactional
    public void getAllFinesByFinedOnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finedOn is less than or equal to DEFAULT_FINED_ON
        defaultFineShouldBeFound("finedOn.lessThanOrEqual=" + DEFAULT_FINED_ON);

        // Get all the fineList where finedOn is less than or equal to SMALLER_FINED_ON
        defaultFineShouldNotBeFound("finedOn.lessThanOrEqual=" + SMALLER_FINED_ON);
    }

    @Test
    @Transactional
    public void getAllFinesByFinedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finedOn is less than DEFAULT_FINED_ON
        defaultFineShouldNotBeFound("finedOn.lessThan=" + DEFAULT_FINED_ON);

        // Get all the fineList where finedOn is less than UPDATED_FINED_ON
        defaultFineShouldBeFound("finedOn.lessThan=" + UPDATED_FINED_ON);
    }

    @Test
    @Transactional
    public void getAllFinesByFinedOnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finedOn is greater than DEFAULT_FINED_ON
        defaultFineShouldNotBeFound("finedOn.greaterThan=" + DEFAULT_FINED_ON);

        // Get all the fineList where finedOn is greater than SMALLER_FINED_ON
        defaultFineShouldBeFound("finedOn.greaterThan=" + SMALLER_FINED_ON);
    }


    @Test
    @Transactional
    public void getAllFinesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount equals to DEFAULT_AMOUNT
        defaultFineShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the fineList where amount equals to UPDATED_AMOUNT
        defaultFineShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount not equals to DEFAULT_AMOUNT
        defaultFineShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the fineList where amount not equals to UPDATED_AMOUNT
        defaultFineShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultFineShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the fineList where amount equals to UPDATED_AMOUNT
        defaultFineShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount is not null
        defaultFineShouldBeFound("amount.specified=true");

        // Get all the fineList where amount is null
        defaultFineShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultFineShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the fineList where amount is greater than or equal to UPDATED_AMOUNT
        defaultFineShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount is less than or equal to DEFAULT_AMOUNT
        defaultFineShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the fineList where amount is less than or equal to SMALLER_AMOUNT
        defaultFineShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount is less than DEFAULT_AMOUNT
        defaultFineShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the fineList where amount is less than UPDATED_AMOUNT
        defaultFineShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount is greater than DEFAULT_AMOUNT
        defaultFineShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the fineList where amount is greater than SMALLER_AMOUNT
        defaultFineShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllFinesByFinePercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finePercentage equals to DEFAULT_FINE_PERCENTAGE
        defaultFineShouldBeFound("finePercentage.equals=" + DEFAULT_FINE_PERCENTAGE);

        // Get all the fineList where finePercentage equals to UPDATED_FINE_PERCENTAGE
        defaultFineShouldNotBeFound("finePercentage.equals=" + UPDATED_FINE_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllFinesByFinePercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finePercentage not equals to DEFAULT_FINE_PERCENTAGE
        defaultFineShouldNotBeFound("finePercentage.notEquals=" + DEFAULT_FINE_PERCENTAGE);

        // Get all the fineList where finePercentage not equals to UPDATED_FINE_PERCENTAGE
        defaultFineShouldBeFound("finePercentage.notEquals=" + UPDATED_FINE_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllFinesByFinePercentageIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finePercentage in DEFAULT_FINE_PERCENTAGE or UPDATED_FINE_PERCENTAGE
        defaultFineShouldBeFound("finePercentage.in=" + DEFAULT_FINE_PERCENTAGE + "," + UPDATED_FINE_PERCENTAGE);

        // Get all the fineList where finePercentage equals to UPDATED_FINE_PERCENTAGE
        defaultFineShouldNotBeFound("finePercentage.in=" + UPDATED_FINE_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllFinesByFinePercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finePercentage is not null
        defaultFineShouldBeFound("finePercentage.specified=true");

        // Get all the fineList where finePercentage is null
        defaultFineShouldNotBeFound("finePercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByFinePercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finePercentage is greater than or equal to DEFAULT_FINE_PERCENTAGE
        defaultFineShouldBeFound("finePercentage.greaterThanOrEqual=" + DEFAULT_FINE_PERCENTAGE);

        // Get all the fineList where finePercentage is greater than or equal to UPDATED_FINE_PERCENTAGE
        defaultFineShouldNotBeFound("finePercentage.greaterThanOrEqual=" + UPDATED_FINE_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllFinesByFinePercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finePercentage is less than or equal to DEFAULT_FINE_PERCENTAGE
        defaultFineShouldBeFound("finePercentage.lessThanOrEqual=" + DEFAULT_FINE_PERCENTAGE);

        // Get all the fineList where finePercentage is less than or equal to SMALLER_FINE_PERCENTAGE
        defaultFineShouldNotBeFound("finePercentage.lessThanOrEqual=" + SMALLER_FINE_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllFinesByFinePercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finePercentage is less than DEFAULT_FINE_PERCENTAGE
        defaultFineShouldNotBeFound("finePercentage.lessThan=" + DEFAULT_FINE_PERCENTAGE);

        // Get all the fineList where finePercentage is less than UPDATED_FINE_PERCENTAGE
        defaultFineShouldBeFound("finePercentage.lessThan=" + UPDATED_FINE_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllFinesByFinePercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where finePercentage is greater than DEFAULT_FINE_PERCENTAGE
        defaultFineShouldNotBeFound("finePercentage.greaterThan=" + DEFAULT_FINE_PERCENTAGE);

        // Get all the fineList where finePercentage is greater than SMALLER_FINE_PERCENTAGE
        defaultFineShouldBeFound("finePercentage.greaterThan=" + SMALLER_FINE_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllFinesByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultFineShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the fineList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultFineShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllFinesByPaymentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where paymentStatus not equals to DEFAULT_PAYMENT_STATUS
        defaultFineShouldNotBeFound("paymentStatus.notEquals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the fineList where paymentStatus not equals to UPDATED_PAYMENT_STATUS
        defaultFineShouldBeFound("paymentStatus.notEquals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllFinesByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultFineShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the fineList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultFineShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllFinesByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where paymentStatus is not null
        defaultFineShouldBeFound("paymentStatus.specified=true");

        // Get all the fineList where paymentStatus is null
        defaultFineShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        fine.setEmployee(employee);
        fineRepository.saveAndFlush(fine);
        Long employeeId = employee.getId();

        // Get all the fineList where employee equals to employeeId
        defaultFineShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the fineList where employee equals to employeeId + 1
        defaultFineShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFineShouldBeFound(String filter) throws Exception {
        restFineMockMvc.perform(get("/api/fines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fine.getId().intValue())))
            .andExpect(jsonPath("$.[*].finedOn").value(hasItem(DEFAULT_FINED_ON.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].finePercentage").value(hasItem(DEFAULT_FINE_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));

        // Check, that the count call also returns 1
        restFineMockMvc.perform(get("/api/fines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFineShouldNotBeFound(String filter) throws Exception {
        restFineMockMvc.perform(get("/api/fines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFineMockMvc.perform(get("/api/fines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFine() throws Exception {
        // Get the fine
        restFineMockMvc.perform(get("/api/fines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFine() throws Exception {
        // Initialize the database
        fineService.save(fine);

        int databaseSizeBeforeUpdate = fineRepository.findAll().size();

        // Update the fine
        Fine updatedFine = fineRepository.findById(fine.getId()).get();
        // Disconnect from session so that the updates on updatedFine are not directly saved in db
        em.detach(updatedFine);
        updatedFine
            .finedOn(UPDATED_FINED_ON)
            .reason(UPDATED_REASON)
            .amount(UPDATED_AMOUNT)
            .finePercentage(UPDATED_FINE_PERCENTAGE)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restFineMockMvc.perform(put("/api/fines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFine)))
            .andExpect(status().isOk());

        // Validate the Fine in the database
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeUpdate);
        Fine testFine = fineList.get(fineList.size() - 1);
        assertThat(testFine.getFinedOn()).isEqualTo(UPDATED_FINED_ON);
        assertThat(testFine.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testFine.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFine.getFinePercentage()).isEqualTo(UPDATED_FINE_PERCENTAGE);
        assertThat(testFine.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFine() throws Exception {
        int databaseSizeBeforeUpdate = fineRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFineMockMvc.perform(put("/api/fines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fine)))
            .andExpect(status().isBadRequest());

        // Validate the Fine in the database
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFine() throws Exception {
        // Initialize the database
        fineService.save(fine);

        int databaseSizeBeforeDelete = fineRepository.findAll().size();

        // Delete the fine
        restFineMockMvc.perform(delete("/api/fines/{id}", fine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
