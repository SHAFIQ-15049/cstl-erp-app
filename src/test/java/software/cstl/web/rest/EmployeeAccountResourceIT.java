package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.EmployeeAccount;
import software.cstl.domain.Employee;
import software.cstl.repository.EmployeeAccountRepository;
import software.cstl.service.EmployeeAccountService;
import software.cstl.service.dto.EmployeeAccountCriteria;
import software.cstl.service.EmployeeAccountQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.AccountType;
/**
 * Integration tests for the {@link EmployeeAccountResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeAccountResourceIT {

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.BANK;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.BKASH;

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SALARY_ACCOUNT = false;
    private static final Boolean UPDATED_IS_SALARY_ACCOUNT = true;

    @Autowired
    private EmployeeAccountRepository employeeAccountRepository;

    @Autowired
    private EmployeeAccountService employeeAccountService;

    @Autowired
    private EmployeeAccountQueryService employeeAccountQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeAccountMockMvc;

    private EmployeeAccount employeeAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeAccount createEntity(EntityManager em) {
        EmployeeAccount employeeAccount = new EmployeeAccount()
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .accountNo(DEFAULT_ACCOUNT_NO)
            .isSalaryAccount(DEFAULT_IS_SALARY_ACCOUNT);
        return employeeAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeAccount createUpdatedEntity(EntityManager em) {
        EmployeeAccount employeeAccount = new EmployeeAccount()
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .isSalaryAccount(UPDATED_IS_SALARY_ACCOUNT);
        return employeeAccount;
    }

    @BeforeEach
    public void initTest() {
        employeeAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeAccount() throws Exception {
        int databaseSizeBeforeCreate = employeeAccountRepository.findAll().size();
        // Create the EmployeeAccount
        restEmployeeAccountMockMvc.perform(post("/api/employee-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAccount)))
            .andExpect(status().isCreated());

        // Validate the EmployeeAccount in the database
        List<EmployeeAccount> employeeAccountList = employeeAccountRepository.findAll();
        assertThat(employeeAccountList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeAccount testEmployeeAccount = employeeAccountList.get(employeeAccountList.size() - 1);
        assertThat(testEmployeeAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testEmployeeAccount.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testEmployeeAccount.isIsSalaryAccount()).isEqualTo(DEFAULT_IS_SALARY_ACCOUNT);
    }

    @Test
    @Transactional
    public void createEmployeeAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeAccountRepository.findAll().size();

        // Create the EmployeeAccount with an existing ID
        employeeAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeAccountMockMvc.perform(post("/api/employee-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAccount)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAccount in the database
        List<EmployeeAccount> employeeAccountList = employeeAccountRepository.findAll();
        assertThat(employeeAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeAccountRepository.findAll().size();
        // set the field null
        employeeAccount.setAccountType(null);

        // Create the EmployeeAccount, which fails.


        restEmployeeAccountMockMvc.perform(post("/api/employee-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAccount)))
            .andExpect(status().isBadRequest());

        List<EmployeeAccount> employeeAccountList = employeeAccountRepository.findAll();
        assertThat(employeeAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeAccountRepository.findAll().size();
        // set the field null
        employeeAccount.setAccountNo(null);

        // Create the EmployeeAccount, which fails.


        restEmployeeAccountMockMvc.perform(post("/api/employee-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAccount)))
            .andExpect(status().isBadRequest());

        List<EmployeeAccount> employeeAccountList = employeeAccountRepository.findAll();
        assertThat(employeeAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccounts() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList
        restEmployeeAccountMockMvc.perform(get("/api/employee-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].isSalaryAccount").value(hasItem(DEFAULT_IS_SALARY_ACCOUNT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeAccount() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get the employeeAccount
        restEmployeeAccountMockMvc.perform(get("/api/employee-accounts/{id}", employeeAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO))
            .andExpect(jsonPath("$.isSalaryAccount").value(DEFAULT_IS_SALARY_ACCOUNT.booleanValue()));
    }


    @Test
    @Transactional
    public void getEmployeeAccountsByIdFiltering() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        Long id = employeeAccount.getId();

        defaultEmployeeAccountShouldBeFound("id.equals=" + id);
        defaultEmployeeAccountShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeAccountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultEmployeeAccountShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the employeeAccountList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultEmployeeAccountShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountType not equals to DEFAULT_ACCOUNT_TYPE
        defaultEmployeeAccountShouldNotBeFound("accountType.notEquals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the employeeAccountList where accountType not equals to UPDATED_ACCOUNT_TYPE
        defaultEmployeeAccountShouldBeFound("accountType.notEquals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultEmployeeAccountShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the employeeAccountList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultEmployeeAccountShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountType is not null
        defaultEmployeeAccountShouldBeFound("accountType.specified=true");

        // Get all the employeeAccountList where accountType is null
        defaultEmployeeAccountShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultEmployeeAccountShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the employeeAccountList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultEmployeeAccountShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountNo not equals to DEFAULT_ACCOUNT_NO
        defaultEmployeeAccountShouldNotBeFound("accountNo.notEquals=" + DEFAULT_ACCOUNT_NO);

        // Get all the employeeAccountList where accountNo not equals to UPDATED_ACCOUNT_NO
        defaultEmployeeAccountShouldBeFound("accountNo.notEquals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultEmployeeAccountShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the employeeAccountList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultEmployeeAccountShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountNo is not null
        defaultEmployeeAccountShouldBeFound("accountNo.specified=true");

        // Get all the employeeAccountList where accountNo is null
        defaultEmployeeAccountShouldNotBeFound("accountNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountNoContainsSomething() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountNo contains DEFAULT_ACCOUNT_NO
        defaultEmployeeAccountShouldBeFound("accountNo.contains=" + DEFAULT_ACCOUNT_NO);

        // Get all the employeeAccountList where accountNo contains UPDATED_ACCOUNT_NO
        defaultEmployeeAccountShouldNotBeFound("accountNo.contains=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByAccountNoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where accountNo does not contain DEFAULT_ACCOUNT_NO
        defaultEmployeeAccountShouldNotBeFound("accountNo.doesNotContain=" + DEFAULT_ACCOUNT_NO);

        // Get all the employeeAccountList where accountNo does not contain UPDATED_ACCOUNT_NO
        defaultEmployeeAccountShouldBeFound("accountNo.doesNotContain=" + UPDATED_ACCOUNT_NO);
    }


    @Test
    @Transactional
    public void getAllEmployeeAccountsByIsSalaryAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where isSalaryAccount equals to DEFAULT_IS_SALARY_ACCOUNT
        defaultEmployeeAccountShouldBeFound("isSalaryAccount.equals=" + DEFAULT_IS_SALARY_ACCOUNT);

        // Get all the employeeAccountList where isSalaryAccount equals to UPDATED_IS_SALARY_ACCOUNT
        defaultEmployeeAccountShouldNotBeFound("isSalaryAccount.equals=" + UPDATED_IS_SALARY_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByIsSalaryAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where isSalaryAccount not equals to DEFAULT_IS_SALARY_ACCOUNT
        defaultEmployeeAccountShouldNotBeFound("isSalaryAccount.notEquals=" + DEFAULT_IS_SALARY_ACCOUNT);

        // Get all the employeeAccountList where isSalaryAccount not equals to UPDATED_IS_SALARY_ACCOUNT
        defaultEmployeeAccountShouldBeFound("isSalaryAccount.notEquals=" + UPDATED_IS_SALARY_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByIsSalaryAccountIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where isSalaryAccount in DEFAULT_IS_SALARY_ACCOUNT or UPDATED_IS_SALARY_ACCOUNT
        defaultEmployeeAccountShouldBeFound("isSalaryAccount.in=" + DEFAULT_IS_SALARY_ACCOUNT + "," + UPDATED_IS_SALARY_ACCOUNT);

        // Get all the employeeAccountList where isSalaryAccount equals to UPDATED_IS_SALARY_ACCOUNT
        defaultEmployeeAccountShouldNotBeFound("isSalaryAccount.in=" + UPDATED_IS_SALARY_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByIsSalaryAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);

        // Get all the employeeAccountList where isSalaryAccount is not null
        defaultEmployeeAccountShouldBeFound("isSalaryAccount.specified=true");

        // Get all the employeeAccountList where isSalaryAccount is null
        defaultEmployeeAccountShouldNotBeFound("isSalaryAccount.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeAccountsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAccountRepository.saveAndFlush(employeeAccount);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeAccount.setEmployee(employee);
        employeeAccountRepository.saveAndFlush(employeeAccount);
        Long employeeId = employee.getId();

        // Get all the employeeAccountList where employee equals to employeeId
        defaultEmployeeAccountShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeAccountList where employee equals to employeeId + 1
        defaultEmployeeAccountShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeAccountShouldBeFound(String filter) throws Exception {
        restEmployeeAccountMockMvc.perform(get("/api/employee-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].isSalaryAccount").value(hasItem(DEFAULT_IS_SALARY_ACCOUNT.booleanValue())));

        // Check, that the count call also returns 1
        restEmployeeAccountMockMvc.perform(get("/api/employee-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeAccountShouldNotBeFound(String filter) throws Exception {
        restEmployeeAccountMockMvc.perform(get("/api/employee-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeAccountMockMvc.perform(get("/api/employee-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeAccount() throws Exception {
        // Get the employeeAccount
        restEmployeeAccountMockMvc.perform(get("/api/employee-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeAccount() throws Exception {
        // Initialize the database
        employeeAccountService.save(employeeAccount);

        int databaseSizeBeforeUpdate = employeeAccountRepository.findAll().size();

        // Update the employeeAccount
        EmployeeAccount updatedEmployeeAccount = employeeAccountRepository.findById(employeeAccount.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeAccount are not directly saved in db
        em.detach(updatedEmployeeAccount);
        updatedEmployeeAccount
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .isSalaryAccount(UPDATED_IS_SALARY_ACCOUNT);

        restEmployeeAccountMockMvc.perform(put("/api/employee-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeAccount)))
            .andExpect(status().isOk());

        // Validate the EmployeeAccount in the database
        List<EmployeeAccount> employeeAccountList = employeeAccountRepository.findAll();
        assertThat(employeeAccountList).hasSize(databaseSizeBeforeUpdate);
        EmployeeAccount testEmployeeAccount = employeeAccountList.get(employeeAccountList.size() - 1);
        assertThat(testEmployeeAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testEmployeeAccount.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testEmployeeAccount.isIsSalaryAccount()).isEqualTo(UPDATED_IS_SALARY_ACCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeAccount() throws Exception {
        int databaseSizeBeforeUpdate = employeeAccountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeAccountMockMvc.perform(put("/api/employee-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeAccount)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAccount in the database
        List<EmployeeAccount> employeeAccountList = employeeAccountRepository.findAll();
        assertThat(employeeAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeAccount() throws Exception {
        // Initialize the database
        employeeAccountService.save(employeeAccount);

        int databaseSizeBeforeDelete = employeeAccountRepository.findAll().size();

        // Delete the employeeAccount
        restEmployeeAccountMockMvc.perform(delete("/api/employee-accounts/{id}", employeeAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeAccount> employeeAccountList = employeeAccountRepository.findAll();
        assertThat(employeeAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
