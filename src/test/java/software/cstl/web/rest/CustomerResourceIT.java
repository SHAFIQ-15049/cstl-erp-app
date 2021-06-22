package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Customer;
import software.cstl.repository.CustomerRepository;
import software.cstl.service.CustomerService;
import software.cstl.service.dto.CustomerCriteria;
import software.cstl.service.CustomerQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.GenderType;
/**
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_OR_HUSBAND = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_OR_HUSBAND = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final GenderType DEFAULT_SEX = GenderType.MALE;
    private static final GenderType UPDATED_SEX = GenderType.FEMALE;

    private static final String DEFAULT_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_GUARDIANS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GUARDIANS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHASSIS_NO = "AAAAAAAAAA";
    private static final String UPDATED_CHASSIS_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ENGINE_NO = "AAAAAAAAAA";
    private static final String UPDATED_ENGINE_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEARS_OF_MFG = 1;
    private static final Integer UPDATED_YEARS_OF_MFG = 2;
    private static final Integer SMALLER_YEARS_OF_MFG = 1 - 1;

    private static final String DEFAULT_PRE_REGN_NO = "AAAAAAAAAA";
    private static final String UPDATED_PRE_REGN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PO_OR_BANK = "AAAAAAAAAA";
    private static final String UPDATED_PO_OR_BANK = "BBBBBBBBBB";

    private static final String DEFAULT_VOTER_ID_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOTER_ID_NO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_VOTER_ID_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VOTER_ID_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VOTER_ID_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VOTER_ID_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PASSPORT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PASSPORT_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PASSPORT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PASSPORT_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BIRTH_CERTIFICATE_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GASS_OR_WATER_OR_ELECTRICITY_BILL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE = "image/png";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerQueryService customerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .name(DEFAULT_NAME)
            .fatherOrHusband(DEFAULT_FATHER_OR_HUSBAND)
            .address(DEFAULT_ADDRESS)
            .sex(DEFAULT_SEX)
            .phoneNo(DEFAULT_PHONE_NO)
            .nationality(DEFAULT_NATIONALITY)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .guardiansName(DEFAULT_GUARDIANS_NAME)
            .chassisNo(DEFAULT_CHASSIS_NO)
            .engineNo(DEFAULT_ENGINE_NO)
            .yearsOfMfg(DEFAULT_YEARS_OF_MFG)
            .preRegnNo(DEFAULT_PRE_REGN_NO)
            .poOrBank(DEFAULT_PO_OR_BANK)
            .voterIdNo(DEFAULT_VOTER_ID_NO)
            .voterIdAttachment(DEFAULT_VOTER_ID_ATTACHMENT)
            .voterIdAttachmentContentType(DEFAULT_VOTER_ID_ATTACHMENT_CONTENT_TYPE)
            .passportAttachment(DEFAULT_PASSPORT_ATTACHMENT)
            .passportAttachmentContentType(DEFAULT_PASSPORT_ATTACHMENT_CONTENT_TYPE)
            .birthCertificateAttachment(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT)
            .birthCertificateAttachmentContentType(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE)
            .gassOrWaterOrElectricityBill(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL)
            .gassOrWaterOrElectricityBillContentType(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .name(UPDATED_NAME)
            .fatherOrHusband(UPDATED_FATHER_OR_HUSBAND)
            .address(UPDATED_ADDRESS)
            .sex(UPDATED_SEX)
            .phoneNo(UPDATED_PHONE_NO)
            .nationality(UPDATED_NATIONALITY)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .guardiansName(UPDATED_GUARDIANS_NAME)
            .chassisNo(UPDATED_CHASSIS_NO)
            .engineNo(UPDATED_ENGINE_NO)
            .yearsOfMfg(UPDATED_YEARS_OF_MFG)
            .preRegnNo(UPDATED_PRE_REGN_NO)
            .poOrBank(UPDATED_PO_OR_BANK)
            .voterIdNo(UPDATED_VOTER_ID_NO)
            .voterIdAttachment(UPDATED_VOTER_ID_ATTACHMENT)
            .voterIdAttachmentContentType(UPDATED_VOTER_ID_ATTACHMENT_CONTENT_TYPE)
            .passportAttachment(UPDATED_PASSPORT_ATTACHMENT)
            .passportAttachmentContentType(UPDATED_PASSPORT_ATTACHMENT_CONTENT_TYPE)
            .birthCertificateAttachment(UPDATED_BIRTH_CERTIFICATE_ATTACHMENT)
            .birthCertificateAttachmentContentType(UPDATED_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE)
            .gassOrWaterOrElectricityBill(UPDATED_GASS_OR_WATER_OR_ELECTRICITY_BILL)
            .gassOrWaterOrElectricityBillContentType(UPDATED_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();
        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getFatherOrHusband()).isEqualTo(DEFAULT_FATHER_OR_HUSBAND);
        assertThat(testCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCustomer.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testCustomer.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testCustomer.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testCustomer.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testCustomer.getGuardiansName()).isEqualTo(DEFAULT_GUARDIANS_NAME);
        assertThat(testCustomer.getChassisNo()).isEqualTo(DEFAULT_CHASSIS_NO);
        assertThat(testCustomer.getEngineNo()).isEqualTo(DEFAULT_ENGINE_NO);
        assertThat(testCustomer.getYearsOfMfg()).isEqualTo(DEFAULT_YEARS_OF_MFG);
        assertThat(testCustomer.getPreRegnNo()).isEqualTo(DEFAULT_PRE_REGN_NO);
        assertThat(testCustomer.getPoOrBank()).isEqualTo(DEFAULT_PO_OR_BANK);
        assertThat(testCustomer.getVoterIdNo()).isEqualTo(DEFAULT_VOTER_ID_NO);
        assertThat(testCustomer.getVoterIdAttachment()).isEqualTo(DEFAULT_VOTER_ID_ATTACHMENT);
        assertThat(testCustomer.getVoterIdAttachmentContentType()).isEqualTo(DEFAULT_VOTER_ID_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCustomer.getPassportAttachment()).isEqualTo(DEFAULT_PASSPORT_ATTACHMENT);
        assertThat(testCustomer.getPassportAttachmentContentType()).isEqualTo(DEFAULT_PASSPORT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCustomer.getBirthCertificateAttachment()).isEqualTo(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT);
        assertThat(testCustomer.getBirthCertificateAttachmentContentType()).isEqualTo(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCustomer.getGassOrWaterOrElectricityBill()).isEqualTo(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL);
        assertThat(testCustomer.getGassOrWaterOrElectricityBillContentType()).isEqualTo(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fatherOrHusband").value(hasItem(DEFAULT_FATHER_OR_HUSBAND)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].guardiansName").value(hasItem(DEFAULT_GUARDIANS_NAME)))
            .andExpect(jsonPath("$.[*].chassisNo").value(hasItem(DEFAULT_CHASSIS_NO)))
            .andExpect(jsonPath("$.[*].engineNo").value(hasItem(DEFAULT_ENGINE_NO)))
            .andExpect(jsonPath("$.[*].yearsOfMfg").value(hasItem(DEFAULT_YEARS_OF_MFG)))
            .andExpect(jsonPath("$.[*].preRegnNo").value(hasItem(DEFAULT_PRE_REGN_NO)))
            .andExpect(jsonPath("$.[*].poOrBank").value(hasItem(DEFAULT_PO_OR_BANK)))
            .andExpect(jsonPath("$.[*].voterIdNo").value(hasItem(DEFAULT_VOTER_ID_NO)))
            .andExpect(jsonPath("$.[*].voterIdAttachmentContentType").value(hasItem(DEFAULT_VOTER_ID_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].voterIdAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_VOTER_ID_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].passportAttachmentContentType").value(hasItem(DEFAULT_PASSPORT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].passportAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_PASSPORT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].birthCertificateAttachmentContentType").value(hasItem(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].birthCertificateAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].gassOrWaterOrElectricityBillContentType").value(hasItem(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].gassOrWaterOrElectricityBill").value(hasItem(Base64Utils.encodeToString(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL))));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fatherOrHusband").value(DEFAULT_FATHER_OR_HUSBAND))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.guardiansName").value(DEFAULT_GUARDIANS_NAME))
            .andExpect(jsonPath("$.chassisNo").value(DEFAULT_CHASSIS_NO))
            .andExpect(jsonPath("$.engineNo").value(DEFAULT_ENGINE_NO))
            .andExpect(jsonPath("$.yearsOfMfg").value(DEFAULT_YEARS_OF_MFG))
            .andExpect(jsonPath("$.preRegnNo").value(DEFAULT_PRE_REGN_NO))
            .andExpect(jsonPath("$.poOrBank").value(DEFAULT_PO_OR_BANK))
            .andExpect(jsonPath("$.voterIdNo").value(DEFAULT_VOTER_ID_NO))
            .andExpect(jsonPath("$.voterIdAttachmentContentType").value(DEFAULT_VOTER_ID_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.voterIdAttachment").value(Base64Utils.encodeToString(DEFAULT_VOTER_ID_ATTACHMENT)))
            .andExpect(jsonPath("$.passportAttachmentContentType").value(DEFAULT_PASSPORT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.passportAttachment").value(Base64Utils.encodeToString(DEFAULT_PASSPORT_ATTACHMENT)))
            .andExpect(jsonPath("$.birthCertificateAttachmentContentType").value(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.birthCertificateAttachment").value(Base64Utils.encodeToString(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT)))
            .andExpect(jsonPath("$.gassOrWaterOrElectricityBillContentType").value(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE))
            .andExpect(jsonPath("$.gassOrWaterOrElectricityBill").value(Base64Utils.encodeToString(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL)));
    }


    @Test
    @Transactional
    public void getCustomersByIdFiltering() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        Long id = customer.getId();

        defaultCustomerShouldBeFound("id.equals=" + id);
        defaultCustomerShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name equals to DEFAULT_NAME
        defaultCustomerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerList where name equals to UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name not equals to DEFAULT_NAME
        defaultCustomerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the customerList where name not equals to UPDATED_NAME
        defaultCustomerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerList where name equals to UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name is not null
        defaultCustomerShouldBeFound("name.specified=true");

        // Get all the customerList where name is null
        defaultCustomerShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name contains DEFAULT_NAME
        defaultCustomerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the customerList where name contains UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name does not contain DEFAULT_NAME
        defaultCustomerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the customerList where name does not contain UPDATED_NAME
        defaultCustomerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCustomersByFatherOrHusbandIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fatherOrHusband equals to DEFAULT_FATHER_OR_HUSBAND
        defaultCustomerShouldBeFound("fatherOrHusband.equals=" + DEFAULT_FATHER_OR_HUSBAND);

        // Get all the customerList where fatherOrHusband equals to UPDATED_FATHER_OR_HUSBAND
        defaultCustomerShouldNotBeFound("fatherOrHusband.equals=" + UPDATED_FATHER_OR_HUSBAND);
    }

    @Test
    @Transactional
    public void getAllCustomersByFatherOrHusbandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fatherOrHusband not equals to DEFAULT_FATHER_OR_HUSBAND
        defaultCustomerShouldNotBeFound("fatherOrHusband.notEquals=" + DEFAULT_FATHER_OR_HUSBAND);

        // Get all the customerList where fatherOrHusband not equals to UPDATED_FATHER_OR_HUSBAND
        defaultCustomerShouldBeFound("fatherOrHusband.notEquals=" + UPDATED_FATHER_OR_HUSBAND);
    }

    @Test
    @Transactional
    public void getAllCustomersByFatherOrHusbandIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fatherOrHusband in DEFAULT_FATHER_OR_HUSBAND or UPDATED_FATHER_OR_HUSBAND
        defaultCustomerShouldBeFound("fatherOrHusband.in=" + DEFAULT_FATHER_OR_HUSBAND + "," + UPDATED_FATHER_OR_HUSBAND);

        // Get all the customerList where fatherOrHusband equals to UPDATED_FATHER_OR_HUSBAND
        defaultCustomerShouldNotBeFound("fatherOrHusband.in=" + UPDATED_FATHER_OR_HUSBAND);
    }

    @Test
    @Transactional
    public void getAllCustomersByFatherOrHusbandIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fatherOrHusband is not null
        defaultCustomerShouldBeFound("fatherOrHusband.specified=true");

        // Get all the customerList where fatherOrHusband is null
        defaultCustomerShouldNotBeFound("fatherOrHusband.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByFatherOrHusbandContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fatherOrHusband contains DEFAULT_FATHER_OR_HUSBAND
        defaultCustomerShouldBeFound("fatherOrHusband.contains=" + DEFAULT_FATHER_OR_HUSBAND);

        // Get all the customerList where fatherOrHusband contains UPDATED_FATHER_OR_HUSBAND
        defaultCustomerShouldNotBeFound("fatherOrHusband.contains=" + UPDATED_FATHER_OR_HUSBAND);
    }

    @Test
    @Transactional
    public void getAllCustomersByFatherOrHusbandNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where fatherOrHusband does not contain DEFAULT_FATHER_OR_HUSBAND
        defaultCustomerShouldNotBeFound("fatherOrHusband.doesNotContain=" + DEFAULT_FATHER_OR_HUSBAND);

        // Get all the customerList where fatherOrHusband does not contain UPDATED_FATHER_OR_HUSBAND
        defaultCustomerShouldBeFound("fatherOrHusband.doesNotContain=" + UPDATED_FATHER_OR_HUSBAND);
    }


    @Test
    @Transactional
    public void getAllCustomersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address equals to DEFAULT_ADDRESS
        defaultCustomerShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the customerList where address equals to UPDATED_ADDRESS
        defaultCustomerShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address not equals to DEFAULT_ADDRESS
        defaultCustomerShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the customerList where address not equals to UPDATED_ADDRESS
        defaultCustomerShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCustomerShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the customerList where address equals to UPDATED_ADDRESS
        defaultCustomerShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address is not null
        defaultCustomerShouldBeFound("address.specified=true");

        // Get all the customerList where address is null
        defaultCustomerShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByAddressContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address contains DEFAULT_ADDRESS
        defaultCustomerShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the customerList where address contains UPDATED_ADDRESS
        defaultCustomerShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCustomersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where address does not contain DEFAULT_ADDRESS
        defaultCustomerShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the customerList where address does not contain UPDATED_ADDRESS
        defaultCustomerShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllCustomersBySexIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where sex equals to DEFAULT_SEX
        defaultCustomerShouldBeFound("sex.equals=" + DEFAULT_SEX);

        // Get all the customerList where sex equals to UPDATED_SEX
        defaultCustomerShouldNotBeFound("sex.equals=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllCustomersBySexIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where sex not equals to DEFAULT_SEX
        defaultCustomerShouldNotBeFound("sex.notEquals=" + DEFAULT_SEX);

        // Get all the customerList where sex not equals to UPDATED_SEX
        defaultCustomerShouldBeFound("sex.notEquals=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllCustomersBySexIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where sex in DEFAULT_SEX or UPDATED_SEX
        defaultCustomerShouldBeFound("sex.in=" + DEFAULT_SEX + "," + UPDATED_SEX);

        // Get all the customerList where sex equals to UPDATED_SEX
        defaultCustomerShouldNotBeFound("sex.in=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllCustomersBySexIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where sex is not null
        defaultCustomerShouldBeFound("sex.specified=true");

        // Get all the customerList where sex is null
        defaultCustomerShouldNotBeFound("sex.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneNoIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phoneNo equals to DEFAULT_PHONE_NO
        defaultCustomerShouldBeFound("phoneNo.equals=" + DEFAULT_PHONE_NO);

        // Get all the customerList where phoneNo equals to UPDATED_PHONE_NO
        defaultCustomerShouldNotBeFound("phoneNo.equals=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phoneNo not equals to DEFAULT_PHONE_NO
        defaultCustomerShouldNotBeFound("phoneNo.notEquals=" + DEFAULT_PHONE_NO);

        // Get all the customerList where phoneNo not equals to UPDATED_PHONE_NO
        defaultCustomerShouldBeFound("phoneNo.notEquals=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneNoIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phoneNo in DEFAULT_PHONE_NO or UPDATED_PHONE_NO
        defaultCustomerShouldBeFound("phoneNo.in=" + DEFAULT_PHONE_NO + "," + UPDATED_PHONE_NO);

        // Get all the customerList where phoneNo equals to UPDATED_PHONE_NO
        defaultCustomerShouldNotBeFound("phoneNo.in=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phoneNo is not null
        defaultCustomerShouldBeFound("phoneNo.specified=true");

        // Get all the customerList where phoneNo is null
        defaultCustomerShouldNotBeFound("phoneNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByPhoneNoContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phoneNo contains DEFAULT_PHONE_NO
        defaultCustomerShouldBeFound("phoneNo.contains=" + DEFAULT_PHONE_NO);

        // Get all the customerList where phoneNo contains UPDATED_PHONE_NO
        defaultCustomerShouldNotBeFound("phoneNo.contains=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByPhoneNoNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where phoneNo does not contain DEFAULT_PHONE_NO
        defaultCustomerShouldNotBeFound("phoneNo.doesNotContain=" + DEFAULT_PHONE_NO);

        // Get all the customerList where phoneNo does not contain UPDATED_PHONE_NO
        defaultCustomerShouldBeFound("phoneNo.doesNotContain=" + UPDATED_PHONE_NO);
    }


    @Test
    @Transactional
    public void getAllCustomersByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality equals to DEFAULT_NATIONALITY
        defaultCustomerShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the customerList where nationality equals to UPDATED_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllCustomersByNationalityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality not equals to DEFAULT_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.notEquals=" + DEFAULT_NATIONALITY);

        // Get all the customerList where nationality not equals to UPDATED_NATIONALITY
        defaultCustomerShouldBeFound("nationality.notEquals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllCustomersByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultCustomerShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the customerList where nationality equals to UPDATED_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllCustomersByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality is not null
        defaultCustomerShouldBeFound("nationality.specified=true");

        // Get all the customerList where nationality is null
        defaultCustomerShouldNotBeFound("nationality.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByNationalityContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality contains DEFAULT_NATIONALITY
        defaultCustomerShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

        // Get all the customerList where nationality contains UPDATED_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllCustomersByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality does not contain DEFAULT_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.doesNotContain=" + DEFAULT_NATIONALITY);

        // Get all the customerList where nationality does not contain UPDATED_NATIONALITY
        defaultCustomerShouldBeFound("nationality.doesNotContain=" + UPDATED_NATIONALITY);
    }


    @Test
    @Transactional
    public void getAllCustomersByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCustomersByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCustomersByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCustomersByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is not null
        defaultCustomerShouldBeFound("dateOfBirth.specified=true");

        // Get all the customerList where dateOfBirth is null
        defaultCustomerShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCustomersByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCustomersByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllCustomersByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllCustomersByGuardiansNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where guardiansName equals to DEFAULT_GUARDIANS_NAME
        defaultCustomerShouldBeFound("guardiansName.equals=" + DEFAULT_GUARDIANS_NAME);

        // Get all the customerList where guardiansName equals to UPDATED_GUARDIANS_NAME
        defaultCustomerShouldNotBeFound("guardiansName.equals=" + UPDATED_GUARDIANS_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByGuardiansNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where guardiansName not equals to DEFAULT_GUARDIANS_NAME
        defaultCustomerShouldNotBeFound("guardiansName.notEquals=" + DEFAULT_GUARDIANS_NAME);

        // Get all the customerList where guardiansName not equals to UPDATED_GUARDIANS_NAME
        defaultCustomerShouldBeFound("guardiansName.notEquals=" + UPDATED_GUARDIANS_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByGuardiansNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where guardiansName in DEFAULT_GUARDIANS_NAME or UPDATED_GUARDIANS_NAME
        defaultCustomerShouldBeFound("guardiansName.in=" + DEFAULT_GUARDIANS_NAME + "," + UPDATED_GUARDIANS_NAME);

        // Get all the customerList where guardiansName equals to UPDATED_GUARDIANS_NAME
        defaultCustomerShouldNotBeFound("guardiansName.in=" + UPDATED_GUARDIANS_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByGuardiansNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where guardiansName is not null
        defaultCustomerShouldBeFound("guardiansName.specified=true");

        // Get all the customerList where guardiansName is null
        defaultCustomerShouldNotBeFound("guardiansName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByGuardiansNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where guardiansName contains DEFAULT_GUARDIANS_NAME
        defaultCustomerShouldBeFound("guardiansName.contains=" + DEFAULT_GUARDIANS_NAME);

        // Get all the customerList where guardiansName contains UPDATED_GUARDIANS_NAME
        defaultCustomerShouldNotBeFound("guardiansName.contains=" + UPDATED_GUARDIANS_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByGuardiansNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where guardiansName does not contain DEFAULT_GUARDIANS_NAME
        defaultCustomerShouldNotBeFound("guardiansName.doesNotContain=" + DEFAULT_GUARDIANS_NAME);

        // Get all the customerList where guardiansName does not contain UPDATED_GUARDIANS_NAME
        defaultCustomerShouldBeFound("guardiansName.doesNotContain=" + UPDATED_GUARDIANS_NAME);
    }


    @Test
    @Transactional
    public void getAllCustomersByChassisNoIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where chassisNo equals to DEFAULT_CHASSIS_NO
        defaultCustomerShouldBeFound("chassisNo.equals=" + DEFAULT_CHASSIS_NO);

        // Get all the customerList where chassisNo equals to UPDATED_CHASSIS_NO
        defaultCustomerShouldNotBeFound("chassisNo.equals=" + UPDATED_CHASSIS_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByChassisNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where chassisNo not equals to DEFAULT_CHASSIS_NO
        defaultCustomerShouldNotBeFound("chassisNo.notEquals=" + DEFAULT_CHASSIS_NO);

        // Get all the customerList where chassisNo not equals to UPDATED_CHASSIS_NO
        defaultCustomerShouldBeFound("chassisNo.notEquals=" + UPDATED_CHASSIS_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByChassisNoIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where chassisNo in DEFAULT_CHASSIS_NO or UPDATED_CHASSIS_NO
        defaultCustomerShouldBeFound("chassisNo.in=" + DEFAULT_CHASSIS_NO + "," + UPDATED_CHASSIS_NO);

        // Get all the customerList where chassisNo equals to UPDATED_CHASSIS_NO
        defaultCustomerShouldNotBeFound("chassisNo.in=" + UPDATED_CHASSIS_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByChassisNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where chassisNo is not null
        defaultCustomerShouldBeFound("chassisNo.specified=true");

        // Get all the customerList where chassisNo is null
        defaultCustomerShouldNotBeFound("chassisNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByChassisNoContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where chassisNo contains DEFAULT_CHASSIS_NO
        defaultCustomerShouldBeFound("chassisNo.contains=" + DEFAULT_CHASSIS_NO);

        // Get all the customerList where chassisNo contains UPDATED_CHASSIS_NO
        defaultCustomerShouldNotBeFound("chassisNo.contains=" + UPDATED_CHASSIS_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByChassisNoNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where chassisNo does not contain DEFAULT_CHASSIS_NO
        defaultCustomerShouldNotBeFound("chassisNo.doesNotContain=" + DEFAULT_CHASSIS_NO);

        // Get all the customerList where chassisNo does not contain UPDATED_CHASSIS_NO
        defaultCustomerShouldBeFound("chassisNo.doesNotContain=" + UPDATED_CHASSIS_NO);
    }


    @Test
    @Transactional
    public void getAllCustomersByEngineNoIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where engineNo equals to DEFAULT_ENGINE_NO
        defaultCustomerShouldBeFound("engineNo.equals=" + DEFAULT_ENGINE_NO);

        // Get all the customerList where engineNo equals to UPDATED_ENGINE_NO
        defaultCustomerShouldNotBeFound("engineNo.equals=" + UPDATED_ENGINE_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByEngineNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where engineNo not equals to DEFAULT_ENGINE_NO
        defaultCustomerShouldNotBeFound("engineNo.notEquals=" + DEFAULT_ENGINE_NO);

        // Get all the customerList where engineNo not equals to UPDATED_ENGINE_NO
        defaultCustomerShouldBeFound("engineNo.notEquals=" + UPDATED_ENGINE_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByEngineNoIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where engineNo in DEFAULT_ENGINE_NO or UPDATED_ENGINE_NO
        defaultCustomerShouldBeFound("engineNo.in=" + DEFAULT_ENGINE_NO + "," + UPDATED_ENGINE_NO);

        // Get all the customerList where engineNo equals to UPDATED_ENGINE_NO
        defaultCustomerShouldNotBeFound("engineNo.in=" + UPDATED_ENGINE_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByEngineNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where engineNo is not null
        defaultCustomerShouldBeFound("engineNo.specified=true");

        // Get all the customerList where engineNo is null
        defaultCustomerShouldNotBeFound("engineNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByEngineNoContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where engineNo contains DEFAULT_ENGINE_NO
        defaultCustomerShouldBeFound("engineNo.contains=" + DEFAULT_ENGINE_NO);

        // Get all the customerList where engineNo contains UPDATED_ENGINE_NO
        defaultCustomerShouldNotBeFound("engineNo.contains=" + UPDATED_ENGINE_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByEngineNoNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where engineNo does not contain DEFAULT_ENGINE_NO
        defaultCustomerShouldNotBeFound("engineNo.doesNotContain=" + DEFAULT_ENGINE_NO);

        // Get all the customerList where engineNo does not contain UPDATED_ENGINE_NO
        defaultCustomerShouldBeFound("engineNo.doesNotContain=" + UPDATED_ENGINE_NO);
    }


    @Test
    @Transactional
    public void getAllCustomersByYearsOfMfgIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where yearsOfMfg equals to DEFAULT_YEARS_OF_MFG
        defaultCustomerShouldBeFound("yearsOfMfg.equals=" + DEFAULT_YEARS_OF_MFG);

        // Get all the customerList where yearsOfMfg equals to UPDATED_YEARS_OF_MFG
        defaultCustomerShouldNotBeFound("yearsOfMfg.equals=" + UPDATED_YEARS_OF_MFG);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsOfMfgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where yearsOfMfg not equals to DEFAULT_YEARS_OF_MFG
        defaultCustomerShouldNotBeFound("yearsOfMfg.notEquals=" + DEFAULT_YEARS_OF_MFG);

        // Get all the customerList where yearsOfMfg not equals to UPDATED_YEARS_OF_MFG
        defaultCustomerShouldBeFound("yearsOfMfg.notEquals=" + UPDATED_YEARS_OF_MFG);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsOfMfgIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where yearsOfMfg in DEFAULT_YEARS_OF_MFG or UPDATED_YEARS_OF_MFG
        defaultCustomerShouldBeFound("yearsOfMfg.in=" + DEFAULT_YEARS_OF_MFG + "," + UPDATED_YEARS_OF_MFG);

        // Get all the customerList where yearsOfMfg equals to UPDATED_YEARS_OF_MFG
        defaultCustomerShouldNotBeFound("yearsOfMfg.in=" + UPDATED_YEARS_OF_MFG);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsOfMfgIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where yearsOfMfg is not null
        defaultCustomerShouldBeFound("yearsOfMfg.specified=true");

        // Get all the customerList where yearsOfMfg is null
        defaultCustomerShouldNotBeFound("yearsOfMfg.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsOfMfgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where yearsOfMfg is greater than or equal to DEFAULT_YEARS_OF_MFG
        defaultCustomerShouldBeFound("yearsOfMfg.greaterThanOrEqual=" + DEFAULT_YEARS_OF_MFG);

        // Get all the customerList where yearsOfMfg is greater than or equal to UPDATED_YEARS_OF_MFG
        defaultCustomerShouldNotBeFound("yearsOfMfg.greaterThanOrEqual=" + UPDATED_YEARS_OF_MFG);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsOfMfgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where yearsOfMfg is less than or equal to DEFAULT_YEARS_OF_MFG
        defaultCustomerShouldBeFound("yearsOfMfg.lessThanOrEqual=" + DEFAULT_YEARS_OF_MFG);

        // Get all the customerList where yearsOfMfg is less than or equal to SMALLER_YEARS_OF_MFG
        defaultCustomerShouldNotBeFound("yearsOfMfg.lessThanOrEqual=" + SMALLER_YEARS_OF_MFG);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsOfMfgIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where yearsOfMfg is less than DEFAULT_YEARS_OF_MFG
        defaultCustomerShouldNotBeFound("yearsOfMfg.lessThan=" + DEFAULT_YEARS_OF_MFG);

        // Get all the customerList where yearsOfMfg is less than UPDATED_YEARS_OF_MFG
        defaultCustomerShouldBeFound("yearsOfMfg.lessThan=" + UPDATED_YEARS_OF_MFG);
    }

    @Test
    @Transactional
    public void getAllCustomersByYearsOfMfgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where yearsOfMfg is greater than DEFAULT_YEARS_OF_MFG
        defaultCustomerShouldNotBeFound("yearsOfMfg.greaterThan=" + DEFAULT_YEARS_OF_MFG);

        // Get all the customerList where yearsOfMfg is greater than SMALLER_YEARS_OF_MFG
        defaultCustomerShouldBeFound("yearsOfMfg.greaterThan=" + SMALLER_YEARS_OF_MFG);
    }


    @Test
    @Transactional
    public void getAllCustomersByPreRegnNoIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where preRegnNo equals to DEFAULT_PRE_REGN_NO
        defaultCustomerShouldBeFound("preRegnNo.equals=" + DEFAULT_PRE_REGN_NO);

        // Get all the customerList where preRegnNo equals to UPDATED_PRE_REGN_NO
        defaultCustomerShouldNotBeFound("preRegnNo.equals=" + UPDATED_PRE_REGN_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByPreRegnNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where preRegnNo not equals to DEFAULT_PRE_REGN_NO
        defaultCustomerShouldNotBeFound("preRegnNo.notEquals=" + DEFAULT_PRE_REGN_NO);

        // Get all the customerList where preRegnNo not equals to UPDATED_PRE_REGN_NO
        defaultCustomerShouldBeFound("preRegnNo.notEquals=" + UPDATED_PRE_REGN_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByPreRegnNoIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where preRegnNo in DEFAULT_PRE_REGN_NO or UPDATED_PRE_REGN_NO
        defaultCustomerShouldBeFound("preRegnNo.in=" + DEFAULT_PRE_REGN_NO + "," + UPDATED_PRE_REGN_NO);

        // Get all the customerList where preRegnNo equals to UPDATED_PRE_REGN_NO
        defaultCustomerShouldNotBeFound("preRegnNo.in=" + UPDATED_PRE_REGN_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByPreRegnNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where preRegnNo is not null
        defaultCustomerShouldBeFound("preRegnNo.specified=true");

        // Get all the customerList where preRegnNo is null
        defaultCustomerShouldNotBeFound("preRegnNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByPreRegnNoContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where preRegnNo contains DEFAULT_PRE_REGN_NO
        defaultCustomerShouldBeFound("preRegnNo.contains=" + DEFAULT_PRE_REGN_NO);

        // Get all the customerList where preRegnNo contains UPDATED_PRE_REGN_NO
        defaultCustomerShouldNotBeFound("preRegnNo.contains=" + UPDATED_PRE_REGN_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByPreRegnNoNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where preRegnNo does not contain DEFAULT_PRE_REGN_NO
        defaultCustomerShouldNotBeFound("preRegnNo.doesNotContain=" + DEFAULT_PRE_REGN_NO);

        // Get all the customerList where preRegnNo does not contain UPDATED_PRE_REGN_NO
        defaultCustomerShouldBeFound("preRegnNo.doesNotContain=" + UPDATED_PRE_REGN_NO);
    }


    @Test
    @Transactional
    public void getAllCustomersByPoOrBankIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where poOrBank equals to DEFAULT_PO_OR_BANK
        defaultCustomerShouldBeFound("poOrBank.equals=" + DEFAULT_PO_OR_BANK);

        // Get all the customerList where poOrBank equals to UPDATED_PO_OR_BANK
        defaultCustomerShouldNotBeFound("poOrBank.equals=" + UPDATED_PO_OR_BANK);
    }

    @Test
    @Transactional
    public void getAllCustomersByPoOrBankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where poOrBank not equals to DEFAULT_PO_OR_BANK
        defaultCustomerShouldNotBeFound("poOrBank.notEquals=" + DEFAULT_PO_OR_BANK);

        // Get all the customerList where poOrBank not equals to UPDATED_PO_OR_BANK
        defaultCustomerShouldBeFound("poOrBank.notEquals=" + UPDATED_PO_OR_BANK);
    }

    @Test
    @Transactional
    public void getAllCustomersByPoOrBankIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where poOrBank in DEFAULT_PO_OR_BANK or UPDATED_PO_OR_BANK
        defaultCustomerShouldBeFound("poOrBank.in=" + DEFAULT_PO_OR_BANK + "," + UPDATED_PO_OR_BANK);

        // Get all the customerList where poOrBank equals to UPDATED_PO_OR_BANK
        defaultCustomerShouldNotBeFound("poOrBank.in=" + UPDATED_PO_OR_BANK);
    }

    @Test
    @Transactional
    public void getAllCustomersByPoOrBankIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where poOrBank is not null
        defaultCustomerShouldBeFound("poOrBank.specified=true");

        // Get all the customerList where poOrBank is null
        defaultCustomerShouldNotBeFound("poOrBank.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByPoOrBankContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where poOrBank contains DEFAULT_PO_OR_BANK
        defaultCustomerShouldBeFound("poOrBank.contains=" + DEFAULT_PO_OR_BANK);

        // Get all the customerList where poOrBank contains UPDATED_PO_OR_BANK
        defaultCustomerShouldNotBeFound("poOrBank.contains=" + UPDATED_PO_OR_BANK);
    }

    @Test
    @Transactional
    public void getAllCustomersByPoOrBankNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where poOrBank does not contain DEFAULT_PO_OR_BANK
        defaultCustomerShouldNotBeFound("poOrBank.doesNotContain=" + DEFAULT_PO_OR_BANK);

        // Get all the customerList where poOrBank does not contain UPDATED_PO_OR_BANK
        defaultCustomerShouldBeFound("poOrBank.doesNotContain=" + UPDATED_PO_OR_BANK);
    }


    @Test
    @Transactional
    public void getAllCustomersByVoterIdNoIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where voterIdNo equals to DEFAULT_VOTER_ID_NO
        defaultCustomerShouldBeFound("voterIdNo.equals=" + DEFAULT_VOTER_ID_NO);

        // Get all the customerList where voterIdNo equals to UPDATED_VOTER_ID_NO
        defaultCustomerShouldNotBeFound("voterIdNo.equals=" + UPDATED_VOTER_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByVoterIdNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where voterIdNo not equals to DEFAULT_VOTER_ID_NO
        defaultCustomerShouldNotBeFound("voterIdNo.notEquals=" + DEFAULT_VOTER_ID_NO);

        // Get all the customerList where voterIdNo not equals to UPDATED_VOTER_ID_NO
        defaultCustomerShouldBeFound("voterIdNo.notEquals=" + UPDATED_VOTER_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByVoterIdNoIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where voterIdNo in DEFAULT_VOTER_ID_NO or UPDATED_VOTER_ID_NO
        defaultCustomerShouldBeFound("voterIdNo.in=" + DEFAULT_VOTER_ID_NO + "," + UPDATED_VOTER_ID_NO);

        // Get all the customerList where voterIdNo equals to UPDATED_VOTER_ID_NO
        defaultCustomerShouldNotBeFound("voterIdNo.in=" + UPDATED_VOTER_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByVoterIdNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where voterIdNo is not null
        defaultCustomerShouldBeFound("voterIdNo.specified=true");

        // Get all the customerList where voterIdNo is null
        defaultCustomerShouldNotBeFound("voterIdNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByVoterIdNoContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where voterIdNo contains DEFAULT_VOTER_ID_NO
        defaultCustomerShouldBeFound("voterIdNo.contains=" + DEFAULT_VOTER_ID_NO);

        // Get all the customerList where voterIdNo contains UPDATED_VOTER_ID_NO
        defaultCustomerShouldNotBeFound("voterIdNo.contains=" + UPDATED_VOTER_ID_NO);
    }

    @Test
    @Transactional
    public void getAllCustomersByVoterIdNoNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where voterIdNo does not contain DEFAULT_VOTER_ID_NO
        defaultCustomerShouldNotBeFound("voterIdNo.doesNotContain=" + DEFAULT_VOTER_ID_NO);

        // Get all the customerList where voterIdNo does not contain UPDATED_VOTER_ID_NO
        defaultCustomerShouldBeFound("voterIdNo.doesNotContain=" + UPDATED_VOTER_ID_NO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerShouldBeFound(String filter) throws Exception {
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fatherOrHusband").value(hasItem(DEFAULT_FATHER_OR_HUSBAND)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].guardiansName").value(hasItem(DEFAULT_GUARDIANS_NAME)))
            .andExpect(jsonPath("$.[*].chassisNo").value(hasItem(DEFAULT_CHASSIS_NO)))
            .andExpect(jsonPath("$.[*].engineNo").value(hasItem(DEFAULT_ENGINE_NO)))
            .andExpect(jsonPath("$.[*].yearsOfMfg").value(hasItem(DEFAULT_YEARS_OF_MFG)))
            .andExpect(jsonPath("$.[*].preRegnNo").value(hasItem(DEFAULT_PRE_REGN_NO)))
            .andExpect(jsonPath("$.[*].poOrBank").value(hasItem(DEFAULT_PO_OR_BANK)))
            .andExpect(jsonPath("$.[*].voterIdNo").value(hasItem(DEFAULT_VOTER_ID_NO)))
            .andExpect(jsonPath("$.[*].voterIdAttachmentContentType").value(hasItem(DEFAULT_VOTER_ID_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].voterIdAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_VOTER_ID_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].passportAttachmentContentType").value(hasItem(DEFAULT_PASSPORT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].passportAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_PASSPORT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].birthCertificateAttachmentContentType").value(hasItem(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].birthCertificateAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_BIRTH_CERTIFICATE_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].gassOrWaterOrElectricityBillContentType").value(hasItem(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].gassOrWaterOrElectricityBill").value(hasItem(Base64Utils.encodeToString(DEFAULT_GASS_OR_WATER_OR_ELECTRICITY_BILL))));

        // Check, that the count call also returns 1
        restCustomerMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerShouldNotBeFound(String filter) throws Exception {
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .name(UPDATED_NAME)
            .fatherOrHusband(UPDATED_FATHER_OR_HUSBAND)
            .address(UPDATED_ADDRESS)
            .sex(UPDATED_SEX)
            .phoneNo(UPDATED_PHONE_NO)
            .nationality(UPDATED_NATIONALITY)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .guardiansName(UPDATED_GUARDIANS_NAME)
            .chassisNo(UPDATED_CHASSIS_NO)
            .engineNo(UPDATED_ENGINE_NO)
            .yearsOfMfg(UPDATED_YEARS_OF_MFG)
            .preRegnNo(UPDATED_PRE_REGN_NO)
            .poOrBank(UPDATED_PO_OR_BANK)
            .voterIdNo(UPDATED_VOTER_ID_NO)
            .voterIdAttachment(UPDATED_VOTER_ID_ATTACHMENT)
            .voterIdAttachmentContentType(UPDATED_VOTER_ID_ATTACHMENT_CONTENT_TYPE)
            .passportAttachment(UPDATED_PASSPORT_ATTACHMENT)
            .passportAttachmentContentType(UPDATED_PASSPORT_ATTACHMENT_CONTENT_TYPE)
            .birthCertificateAttachment(UPDATED_BIRTH_CERTIFICATE_ATTACHMENT)
            .birthCertificateAttachmentContentType(UPDATED_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE)
            .gassOrWaterOrElectricityBill(UPDATED_GASS_OR_WATER_OR_ELECTRICITY_BILL)
            .gassOrWaterOrElectricityBillContentType(UPDATED_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getFatherOrHusband()).isEqualTo(UPDATED_FATHER_OR_HUSBAND);
        assertThat(testCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCustomer.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testCustomer.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testCustomer.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testCustomer.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCustomer.getGuardiansName()).isEqualTo(UPDATED_GUARDIANS_NAME);
        assertThat(testCustomer.getChassisNo()).isEqualTo(UPDATED_CHASSIS_NO);
        assertThat(testCustomer.getEngineNo()).isEqualTo(UPDATED_ENGINE_NO);
        assertThat(testCustomer.getYearsOfMfg()).isEqualTo(UPDATED_YEARS_OF_MFG);
        assertThat(testCustomer.getPreRegnNo()).isEqualTo(UPDATED_PRE_REGN_NO);
        assertThat(testCustomer.getPoOrBank()).isEqualTo(UPDATED_PO_OR_BANK);
        assertThat(testCustomer.getVoterIdNo()).isEqualTo(UPDATED_VOTER_ID_NO);
        assertThat(testCustomer.getVoterIdAttachment()).isEqualTo(UPDATED_VOTER_ID_ATTACHMENT);
        assertThat(testCustomer.getVoterIdAttachmentContentType()).isEqualTo(UPDATED_VOTER_ID_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCustomer.getPassportAttachment()).isEqualTo(UPDATED_PASSPORT_ATTACHMENT);
        assertThat(testCustomer.getPassportAttachmentContentType()).isEqualTo(UPDATED_PASSPORT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCustomer.getBirthCertificateAttachment()).isEqualTo(UPDATED_BIRTH_CERTIFICATE_ATTACHMENT);
        assertThat(testCustomer.getBirthCertificateAttachmentContentType()).isEqualTo(UPDATED_BIRTH_CERTIFICATE_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCustomer.getGassOrWaterOrElectricityBill()).isEqualTo(UPDATED_GASS_OR_WATER_OR_ELECTRICITY_BILL);
        assertThat(testCustomer.getGassOrWaterOrElectricityBillContentType()).isEqualTo(UPDATED_GASS_OR_WATER_OR_ELECTRICITY_BILL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
