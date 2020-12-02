package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Address;
import software.cstl.domain.Division;
import software.cstl.domain.District;
import software.cstl.domain.Thana;
import software.cstl.domain.Employee;
import software.cstl.repository.AddressRepository;
import software.cstl.service.AddressService;
import software.cstl.service.dto.AddressCriteria;
import software.cstl.service.AddressQueryService;

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

/**
 * Integration tests for the {@link AddressResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AddressResourceIT {

    private static final String DEFAULT_PRESENT_THANA_TXT = "AAAAAAAAAA";
    private static final String UPDATED_PRESENT_THANA_TXT = "BBBBBBBBBB";

    private static final String DEFAULT_PRESENT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_PRESENT_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_PRESENT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_PRESENT_AREA = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRESENT_POST_CODE = 1;
    private static final Integer UPDATED_PRESENT_POST_CODE = 2;
    private static final Integer SMALLER_PRESENT_POST_CODE = 1 - 1;

    private static final String DEFAULT_PERMANENT_THANA_TXT = "AAAAAAAAAA";
    private static final String UPDATED_PERMANENT_THANA_TXT = "BBBBBBBBBB";

    private static final String DEFAULT_PERMANENT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_PERMANENT_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_PERMANENT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_PERMANENT_AREA = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERMANENT_POST_CODE = 1;
    private static final Integer UPDATED_PERMANENT_POST_CODE = 2;
    private static final Integer SMALLER_PERMANENT_POST_CODE = 1 - 1;

    private static final Boolean DEFAULT_IS_SAME = false;
    private static final Boolean UPDATED_IS_SAME = true;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressQueryService addressQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .presentThanaTxt(DEFAULT_PRESENT_THANA_TXT)
            .presentStreet(DEFAULT_PRESENT_STREET)
            .presentArea(DEFAULT_PRESENT_AREA)
            .presentPostCode(DEFAULT_PRESENT_POST_CODE)
            .permanentThanaTxt(DEFAULT_PERMANENT_THANA_TXT)
            .permanentStreet(DEFAULT_PERMANENT_STREET)
            .permanentArea(DEFAULT_PERMANENT_AREA)
            .permanentPostCode(DEFAULT_PERMANENT_POST_CODE)
            .isSame(DEFAULT_IS_SAME);
        return address;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .presentThanaTxt(UPDATED_PRESENT_THANA_TXT)
            .presentStreet(UPDATED_PRESENT_STREET)
            .presentArea(UPDATED_PRESENT_AREA)
            .presentPostCode(UPDATED_PRESENT_POST_CODE)
            .permanentThanaTxt(UPDATED_PERMANENT_THANA_TXT)
            .permanentStreet(UPDATED_PERMANENT_STREET)
            .permanentArea(UPDATED_PERMANENT_AREA)
            .permanentPostCode(UPDATED_PERMANENT_POST_CODE)
            .isSame(UPDATED_IS_SAME);
        return address;
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        // Create the Address
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(address)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getPresentThanaTxt()).isEqualTo(DEFAULT_PRESENT_THANA_TXT);
        assertThat(testAddress.getPresentStreet()).isEqualTo(DEFAULT_PRESENT_STREET);
        assertThat(testAddress.getPresentArea()).isEqualTo(DEFAULT_PRESENT_AREA);
        assertThat(testAddress.getPresentPostCode()).isEqualTo(DEFAULT_PRESENT_POST_CODE);
        assertThat(testAddress.getPermanentThanaTxt()).isEqualTo(DEFAULT_PERMANENT_THANA_TXT);
        assertThat(testAddress.getPermanentStreet()).isEqualTo(DEFAULT_PERMANENT_STREET);
        assertThat(testAddress.getPermanentArea()).isEqualTo(DEFAULT_PERMANENT_AREA);
        assertThat(testAddress.getPermanentPostCode()).isEqualTo(DEFAULT_PERMANENT_POST_CODE);
        assertThat(testAddress.isIsSame()).isEqualTo(DEFAULT_IS_SAME);
    }

    @Test
    @Transactional
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        address.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(address)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].presentThanaTxt").value(hasItem(DEFAULT_PRESENT_THANA_TXT)))
            .andExpect(jsonPath("$.[*].presentStreet").value(hasItem(DEFAULT_PRESENT_STREET)))
            .andExpect(jsonPath("$.[*].presentArea").value(hasItem(DEFAULT_PRESENT_AREA)))
            .andExpect(jsonPath("$.[*].presentPostCode").value(hasItem(DEFAULT_PRESENT_POST_CODE)))
            .andExpect(jsonPath("$.[*].permanentThanaTxt").value(hasItem(DEFAULT_PERMANENT_THANA_TXT)))
            .andExpect(jsonPath("$.[*].permanentStreet").value(hasItem(DEFAULT_PERMANENT_STREET)))
            .andExpect(jsonPath("$.[*].permanentArea").value(hasItem(DEFAULT_PERMANENT_AREA)))
            .andExpect(jsonPath("$.[*].permanentPostCode").value(hasItem(DEFAULT_PERMANENT_POST_CODE)))
            .andExpect(jsonPath("$.[*].isSame").value(hasItem(DEFAULT_IS_SAME.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.presentThanaTxt").value(DEFAULT_PRESENT_THANA_TXT))
            .andExpect(jsonPath("$.presentStreet").value(DEFAULT_PRESENT_STREET))
            .andExpect(jsonPath("$.presentArea").value(DEFAULT_PRESENT_AREA))
            .andExpect(jsonPath("$.presentPostCode").value(DEFAULT_PRESENT_POST_CODE))
            .andExpect(jsonPath("$.permanentThanaTxt").value(DEFAULT_PERMANENT_THANA_TXT))
            .andExpect(jsonPath("$.permanentStreet").value(DEFAULT_PERMANENT_STREET))
            .andExpect(jsonPath("$.permanentArea").value(DEFAULT_PERMANENT_AREA))
            .andExpect(jsonPath("$.permanentPostCode").value(DEFAULT_PERMANENT_POST_CODE))
            .andExpect(jsonPath("$.isSame").value(DEFAULT_IS_SAME.booleanValue()));
    }


    @Test
    @Transactional
    public void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        Long id = address.getId();

        defaultAddressShouldBeFound("id.equals=" + id);
        defaultAddressShouldNotBeFound("id.notEquals=" + id);

        defaultAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAddressesByPresentThanaTxtIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentThanaTxt equals to DEFAULT_PRESENT_THANA_TXT
        defaultAddressShouldBeFound("presentThanaTxt.equals=" + DEFAULT_PRESENT_THANA_TXT);

        // Get all the addressList where presentThanaTxt equals to UPDATED_PRESENT_THANA_TXT
        defaultAddressShouldNotBeFound("presentThanaTxt.equals=" + UPDATED_PRESENT_THANA_TXT);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentThanaTxtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentThanaTxt not equals to DEFAULT_PRESENT_THANA_TXT
        defaultAddressShouldNotBeFound("presentThanaTxt.notEquals=" + DEFAULT_PRESENT_THANA_TXT);

        // Get all the addressList where presentThanaTxt not equals to UPDATED_PRESENT_THANA_TXT
        defaultAddressShouldBeFound("presentThanaTxt.notEquals=" + UPDATED_PRESENT_THANA_TXT);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentThanaTxtIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentThanaTxt in DEFAULT_PRESENT_THANA_TXT or UPDATED_PRESENT_THANA_TXT
        defaultAddressShouldBeFound("presentThanaTxt.in=" + DEFAULT_PRESENT_THANA_TXT + "," + UPDATED_PRESENT_THANA_TXT);

        // Get all the addressList where presentThanaTxt equals to UPDATED_PRESENT_THANA_TXT
        defaultAddressShouldNotBeFound("presentThanaTxt.in=" + UPDATED_PRESENT_THANA_TXT);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentThanaTxtIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentThanaTxt is not null
        defaultAddressShouldBeFound("presentThanaTxt.specified=true");

        // Get all the addressList where presentThanaTxt is null
        defaultAddressShouldNotBeFound("presentThanaTxt.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByPresentThanaTxtContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentThanaTxt contains DEFAULT_PRESENT_THANA_TXT
        defaultAddressShouldBeFound("presentThanaTxt.contains=" + DEFAULT_PRESENT_THANA_TXT);

        // Get all the addressList where presentThanaTxt contains UPDATED_PRESENT_THANA_TXT
        defaultAddressShouldNotBeFound("presentThanaTxt.contains=" + UPDATED_PRESENT_THANA_TXT);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentThanaTxtNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentThanaTxt does not contain DEFAULT_PRESENT_THANA_TXT
        defaultAddressShouldNotBeFound("presentThanaTxt.doesNotContain=" + DEFAULT_PRESENT_THANA_TXT);

        // Get all the addressList where presentThanaTxt does not contain UPDATED_PRESENT_THANA_TXT
        defaultAddressShouldBeFound("presentThanaTxt.doesNotContain=" + UPDATED_PRESENT_THANA_TXT);
    }


    @Test
    @Transactional
    public void getAllAddressesByPresentStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentStreet equals to DEFAULT_PRESENT_STREET
        defaultAddressShouldBeFound("presentStreet.equals=" + DEFAULT_PRESENT_STREET);

        // Get all the addressList where presentStreet equals to UPDATED_PRESENT_STREET
        defaultAddressShouldNotBeFound("presentStreet.equals=" + UPDATED_PRESENT_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentStreetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentStreet not equals to DEFAULT_PRESENT_STREET
        defaultAddressShouldNotBeFound("presentStreet.notEquals=" + DEFAULT_PRESENT_STREET);

        // Get all the addressList where presentStreet not equals to UPDATED_PRESENT_STREET
        defaultAddressShouldBeFound("presentStreet.notEquals=" + UPDATED_PRESENT_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentStreetIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentStreet in DEFAULT_PRESENT_STREET or UPDATED_PRESENT_STREET
        defaultAddressShouldBeFound("presentStreet.in=" + DEFAULT_PRESENT_STREET + "," + UPDATED_PRESENT_STREET);

        // Get all the addressList where presentStreet equals to UPDATED_PRESENT_STREET
        defaultAddressShouldNotBeFound("presentStreet.in=" + UPDATED_PRESENT_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentStreet is not null
        defaultAddressShouldBeFound("presentStreet.specified=true");

        // Get all the addressList where presentStreet is null
        defaultAddressShouldNotBeFound("presentStreet.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByPresentStreetContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentStreet contains DEFAULT_PRESENT_STREET
        defaultAddressShouldBeFound("presentStreet.contains=" + DEFAULT_PRESENT_STREET);

        // Get all the addressList where presentStreet contains UPDATED_PRESENT_STREET
        defaultAddressShouldNotBeFound("presentStreet.contains=" + UPDATED_PRESENT_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentStreetNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentStreet does not contain DEFAULT_PRESENT_STREET
        defaultAddressShouldNotBeFound("presentStreet.doesNotContain=" + DEFAULT_PRESENT_STREET);

        // Get all the addressList where presentStreet does not contain UPDATED_PRESENT_STREET
        defaultAddressShouldBeFound("presentStreet.doesNotContain=" + UPDATED_PRESENT_STREET);
    }


    @Test
    @Transactional
    public void getAllAddressesByPresentAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentArea equals to DEFAULT_PRESENT_AREA
        defaultAddressShouldBeFound("presentArea.equals=" + DEFAULT_PRESENT_AREA);

        // Get all the addressList where presentArea equals to UPDATED_PRESENT_AREA
        defaultAddressShouldNotBeFound("presentArea.equals=" + UPDATED_PRESENT_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentArea not equals to DEFAULT_PRESENT_AREA
        defaultAddressShouldNotBeFound("presentArea.notEquals=" + DEFAULT_PRESENT_AREA);

        // Get all the addressList where presentArea not equals to UPDATED_PRESENT_AREA
        defaultAddressShouldBeFound("presentArea.notEquals=" + UPDATED_PRESENT_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentAreaIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentArea in DEFAULT_PRESENT_AREA or UPDATED_PRESENT_AREA
        defaultAddressShouldBeFound("presentArea.in=" + DEFAULT_PRESENT_AREA + "," + UPDATED_PRESENT_AREA);

        // Get all the addressList where presentArea equals to UPDATED_PRESENT_AREA
        defaultAddressShouldNotBeFound("presentArea.in=" + UPDATED_PRESENT_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentArea is not null
        defaultAddressShouldBeFound("presentArea.specified=true");

        // Get all the addressList where presentArea is null
        defaultAddressShouldNotBeFound("presentArea.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByPresentAreaContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentArea contains DEFAULT_PRESENT_AREA
        defaultAddressShouldBeFound("presentArea.contains=" + DEFAULT_PRESENT_AREA);

        // Get all the addressList where presentArea contains UPDATED_PRESENT_AREA
        defaultAddressShouldNotBeFound("presentArea.contains=" + UPDATED_PRESENT_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentAreaNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentArea does not contain DEFAULT_PRESENT_AREA
        defaultAddressShouldNotBeFound("presentArea.doesNotContain=" + DEFAULT_PRESENT_AREA);

        // Get all the addressList where presentArea does not contain UPDATED_PRESENT_AREA
        defaultAddressShouldBeFound("presentArea.doesNotContain=" + UPDATED_PRESENT_AREA);
    }


    @Test
    @Transactional
    public void getAllAddressesByPresentPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentPostCode equals to DEFAULT_PRESENT_POST_CODE
        defaultAddressShouldBeFound("presentPostCode.equals=" + DEFAULT_PRESENT_POST_CODE);

        // Get all the addressList where presentPostCode equals to UPDATED_PRESENT_POST_CODE
        defaultAddressShouldNotBeFound("presentPostCode.equals=" + UPDATED_PRESENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentPostCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentPostCode not equals to DEFAULT_PRESENT_POST_CODE
        defaultAddressShouldNotBeFound("presentPostCode.notEquals=" + DEFAULT_PRESENT_POST_CODE);

        // Get all the addressList where presentPostCode not equals to UPDATED_PRESENT_POST_CODE
        defaultAddressShouldBeFound("presentPostCode.notEquals=" + UPDATED_PRESENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentPostCode in DEFAULT_PRESENT_POST_CODE or UPDATED_PRESENT_POST_CODE
        defaultAddressShouldBeFound("presentPostCode.in=" + DEFAULT_PRESENT_POST_CODE + "," + UPDATED_PRESENT_POST_CODE);

        // Get all the addressList where presentPostCode equals to UPDATED_PRESENT_POST_CODE
        defaultAddressShouldNotBeFound("presentPostCode.in=" + UPDATED_PRESENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentPostCode is not null
        defaultAddressShouldBeFound("presentPostCode.specified=true");

        // Get all the addressList where presentPostCode is null
        defaultAddressShouldNotBeFound("presentPostCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentPostCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentPostCode is greater than or equal to DEFAULT_PRESENT_POST_CODE
        defaultAddressShouldBeFound("presentPostCode.greaterThanOrEqual=" + DEFAULT_PRESENT_POST_CODE);

        // Get all the addressList where presentPostCode is greater than or equal to UPDATED_PRESENT_POST_CODE
        defaultAddressShouldNotBeFound("presentPostCode.greaterThanOrEqual=" + UPDATED_PRESENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentPostCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentPostCode is less than or equal to DEFAULT_PRESENT_POST_CODE
        defaultAddressShouldBeFound("presentPostCode.lessThanOrEqual=" + DEFAULT_PRESENT_POST_CODE);

        // Get all the addressList where presentPostCode is less than or equal to SMALLER_PRESENT_POST_CODE
        defaultAddressShouldNotBeFound("presentPostCode.lessThanOrEqual=" + SMALLER_PRESENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentPostCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentPostCode is less than DEFAULT_PRESENT_POST_CODE
        defaultAddressShouldNotBeFound("presentPostCode.lessThan=" + DEFAULT_PRESENT_POST_CODE);

        // Get all the addressList where presentPostCode is less than UPDATED_PRESENT_POST_CODE
        defaultAddressShouldBeFound("presentPostCode.lessThan=" + UPDATED_PRESENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentPostCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where presentPostCode is greater than DEFAULT_PRESENT_POST_CODE
        defaultAddressShouldNotBeFound("presentPostCode.greaterThan=" + DEFAULT_PRESENT_POST_CODE);

        // Get all the addressList where presentPostCode is greater than SMALLER_PRESENT_POST_CODE
        defaultAddressShouldBeFound("presentPostCode.greaterThan=" + SMALLER_PRESENT_POST_CODE);
    }


    @Test
    @Transactional
    public void getAllAddressesByPermanentThanaTxtIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentThanaTxt equals to DEFAULT_PERMANENT_THANA_TXT
        defaultAddressShouldBeFound("permanentThanaTxt.equals=" + DEFAULT_PERMANENT_THANA_TXT);

        // Get all the addressList where permanentThanaTxt equals to UPDATED_PERMANENT_THANA_TXT
        defaultAddressShouldNotBeFound("permanentThanaTxt.equals=" + UPDATED_PERMANENT_THANA_TXT);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentThanaTxtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentThanaTxt not equals to DEFAULT_PERMANENT_THANA_TXT
        defaultAddressShouldNotBeFound("permanentThanaTxt.notEquals=" + DEFAULT_PERMANENT_THANA_TXT);

        // Get all the addressList where permanentThanaTxt not equals to UPDATED_PERMANENT_THANA_TXT
        defaultAddressShouldBeFound("permanentThanaTxt.notEquals=" + UPDATED_PERMANENT_THANA_TXT);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentThanaTxtIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentThanaTxt in DEFAULT_PERMANENT_THANA_TXT or UPDATED_PERMANENT_THANA_TXT
        defaultAddressShouldBeFound("permanentThanaTxt.in=" + DEFAULT_PERMANENT_THANA_TXT + "," + UPDATED_PERMANENT_THANA_TXT);

        // Get all the addressList where permanentThanaTxt equals to UPDATED_PERMANENT_THANA_TXT
        defaultAddressShouldNotBeFound("permanentThanaTxt.in=" + UPDATED_PERMANENT_THANA_TXT);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentThanaTxtIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentThanaTxt is not null
        defaultAddressShouldBeFound("permanentThanaTxt.specified=true");

        // Get all the addressList where permanentThanaTxt is null
        defaultAddressShouldNotBeFound("permanentThanaTxt.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByPermanentThanaTxtContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentThanaTxt contains DEFAULT_PERMANENT_THANA_TXT
        defaultAddressShouldBeFound("permanentThanaTxt.contains=" + DEFAULT_PERMANENT_THANA_TXT);

        // Get all the addressList where permanentThanaTxt contains UPDATED_PERMANENT_THANA_TXT
        defaultAddressShouldNotBeFound("permanentThanaTxt.contains=" + UPDATED_PERMANENT_THANA_TXT);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentThanaTxtNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentThanaTxt does not contain DEFAULT_PERMANENT_THANA_TXT
        defaultAddressShouldNotBeFound("permanentThanaTxt.doesNotContain=" + DEFAULT_PERMANENT_THANA_TXT);

        // Get all the addressList where permanentThanaTxt does not contain UPDATED_PERMANENT_THANA_TXT
        defaultAddressShouldBeFound("permanentThanaTxt.doesNotContain=" + UPDATED_PERMANENT_THANA_TXT);
    }


    @Test
    @Transactional
    public void getAllAddressesByPermanentStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentStreet equals to DEFAULT_PERMANENT_STREET
        defaultAddressShouldBeFound("permanentStreet.equals=" + DEFAULT_PERMANENT_STREET);

        // Get all the addressList where permanentStreet equals to UPDATED_PERMANENT_STREET
        defaultAddressShouldNotBeFound("permanentStreet.equals=" + UPDATED_PERMANENT_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentStreetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentStreet not equals to DEFAULT_PERMANENT_STREET
        defaultAddressShouldNotBeFound("permanentStreet.notEquals=" + DEFAULT_PERMANENT_STREET);

        // Get all the addressList where permanentStreet not equals to UPDATED_PERMANENT_STREET
        defaultAddressShouldBeFound("permanentStreet.notEquals=" + UPDATED_PERMANENT_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentStreetIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentStreet in DEFAULT_PERMANENT_STREET or UPDATED_PERMANENT_STREET
        defaultAddressShouldBeFound("permanentStreet.in=" + DEFAULT_PERMANENT_STREET + "," + UPDATED_PERMANENT_STREET);

        // Get all the addressList where permanentStreet equals to UPDATED_PERMANENT_STREET
        defaultAddressShouldNotBeFound("permanentStreet.in=" + UPDATED_PERMANENT_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentStreet is not null
        defaultAddressShouldBeFound("permanentStreet.specified=true");

        // Get all the addressList where permanentStreet is null
        defaultAddressShouldNotBeFound("permanentStreet.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByPermanentStreetContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentStreet contains DEFAULT_PERMANENT_STREET
        defaultAddressShouldBeFound("permanentStreet.contains=" + DEFAULT_PERMANENT_STREET);

        // Get all the addressList where permanentStreet contains UPDATED_PERMANENT_STREET
        defaultAddressShouldNotBeFound("permanentStreet.contains=" + UPDATED_PERMANENT_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentStreetNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentStreet does not contain DEFAULT_PERMANENT_STREET
        defaultAddressShouldNotBeFound("permanentStreet.doesNotContain=" + DEFAULT_PERMANENT_STREET);

        // Get all the addressList where permanentStreet does not contain UPDATED_PERMANENT_STREET
        defaultAddressShouldBeFound("permanentStreet.doesNotContain=" + UPDATED_PERMANENT_STREET);
    }


    @Test
    @Transactional
    public void getAllAddressesByPermanentAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentArea equals to DEFAULT_PERMANENT_AREA
        defaultAddressShouldBeFound("permanentArea.equals=" + DEFAULT_PERMANENT_AREA);

        // Get all the addressList where permanentArea equals to UPDATED_PERMANENT_AREA
        defaultAddressShouldNotBeFound("permanentArea.equals=" + UPDATED_PERMANENT_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentArea not equals to DEFAULT_PERMANENT_AREA
        defaultAddressShouldNotBeFound("permanentArea.notEquals=" + DEFAULT_PERMANENT_AREA);

        // Get all the addressList where permanentArea not equals to UPDATED_PERMANENT_AREA
        defaultAddressShouldBeFound("permanentArea.notEquals=" + UPDATED_PERMANENT_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentAreaIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentArea in DEFAULT_PERMANENT_AREA or UPDATED_PERMANENT_AREA
        defaultAddressShouldBeFound("permanentArea.in=" + DEFAULT_PERMANENT_AREA + "," + UPDATED_PERMANENT_AREA);

        // Get all the addressList where permanentArea equals to UPDATED_PERMANENT_AREA
        defaultAddressShouldNotBeFound("permanentArea.in=" + UPDATED_PERMANENT_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentArea is not null
        defaultAddressShouldBeFound("permanentArea.specified=true");

        // Get all the addressList where permanentArea is null
        defaultAddressShouldNotBeFound("permanentArea.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByPermanentAreaContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentArea contains DEFAULT_PERMANENT_AREA
        defaultAddressShouldBeFound("permanentArea.contains=" + DEFAULT_PERMANENT_AREA);

        // Get all the addressList where permanentArea contains UPDATED_PERMANENT_AREA
        defaultAddressShouldNotBeFound("permanentArea.contains=" + UPDATED_PERMANENT_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentAreaNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentArea does not contain DEFAULT_PERMANENT_AREA
        defaultAddressShouldNotBeFound("permanentArea.doesNotContain=" + DEFAULT_PERMANENT_AREA);

        // Get all the addressList where permanentArea does not contain UPDATED_PERMANENT_AREA
        defaultAddressShouldBeFound("permanentArea.doesNotContain=" + UPDATED_PERMANENT_AREA);
    }


    @Test
    @Transactional
    public void getAllAddressesByPermanentPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentPostCode equals to DEFAULT_PERMANENT_POST_CODE
        defaultAddressShouldBeFound("permanentPostCode.equals=" + DEFAULT_PERMANENT_POST_CODE);

        // Get all the addressList where permanentPostCode equals to UPDATED_PERMANENT_POST_CODE
        defaultAddressShouldNotBeFound("permanentPostCode.equals=" + UPDATED_PERMANENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentPostCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentPostCode not equals to DEFAULT_PERMANENT_POST_CODE
        defaultAddressShouldNotBeFound("permanentPostCode.notEquals=" + DEFAULT_PERMANENT_POST_CODE);

        // Get all the addressList where permanentPostCode not equals to UPDATED_PERMANENT_POST_CODE
        defaultAddressShouldBeFound("permanentPostCode.notEquals=" + UPDATED_PERMANENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentPostCode in DEFAULT_PERMANENT_POST_CODE or UPDATED_PERMANENT_POST_CODE
        defaultAddressShouldBeFound("permanentPostCode.in=" + DEFAULT_PERMANENT_POST_CODE + "," + UPDATED_PERMANENT_POST_CODE);

        // Get all the addressList where permanentPostCode equals to UPDATED_PERMANENT_POST_CODE
        defaultAddressShouldNotBeFound("permanentPostCode.in=" + UPDATED_PERMANENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentPostCode is not null
        defaultAddressShouldBeFound("permanentPostCode.specified=true");

        // Get all the addressList where permanentPostCode is null
        defaultAddressShouldNotBeFound("permanentPostCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentPostCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentPostCode is greater than or equal to DEFAULT_PERMANENT_POST_CODE
        defaultAddressShouldBeFound("permanentPostCode.greaterThanOrEqual=" + DEFAULT_PERMANENT_POST_CODE);

        // Get all the addressList where permanentPostCode is greater than or equal to UPDATED_PERMANENT_POST_CODE
        defaultAddressShouldNotBeFound("permanentPostCode.greaterThanOrEqual=" + UPDATED_PERMANENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentPostCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentPostCode is less than or equal to DEFAULT_PERMANENT_POST_CODE
        defaultAddressShouldBeFound("permanentPostCode.lessThanOrEqual=" + DEFAULT_PERMANENT_POST_CODE);

        // Get all the addressList where permanentPostCode is less than or equal to SMALLER_PERMANENT_POST_CODE
        defaultAddressShouldNotBeFound("permanentPostCode.lessThanOrEqual=" + SMALLER_PERMANENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentPostCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentPostCode is less than DEFAULT_PERMANENT_POST_CODE
        defaultAddressShouldNotBeFound("permanentPostCode.lessThan=" + DEFAULT_PERMANENT_POST_CODE);

        // Get all the addressList where permanentPostCode is less than UPDATED_PERMANENT_POST_CODE
        defaultAddressShouldBeFound("permanentPostCode.lessThan=" + UPDATED_PERMANENT_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPermanentPostCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where permanentPostCode is greater than DEFAULT_PERMANENT_POST_CODE
        defaultAddressShouldNotBeFound("permanentPostCode.greaterThan=" + DEFAULT_PERMANENT_POST_CODE);

        // Get all the addressList where permanentPostCode is greater than SMALLER_PERMANENT_POST_CODE
        defaultAddressShouldBeFound("permanentPostCode.greaterThan=" + SMALLER_PERMANENT_POST_CODE);
    }


    @Test
    @Transactional
    public void getAllAddressesByIsSameIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isSame equals to DEFAULT_IS_SAME
        defaultAddressShouldBeFound("isSame.equals=" + DEFAULT_IS_SAME);

        // Get all the addressList where isSame equals to UPDATED_IS_SAME
        defaultAddressShouldNotBeFound("isSame.equals=" + UPDATED_IS_SAME);
    }

    @Test
    @Transactional
    public void getAllAddressesByIsSameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isSame not equals to DEFAULT_IS_SAME
        defaultAddressShouldNotBeFound("isSame.notEquals=" + DEFAULT_IS_SAME);

        // Get all the addressList where isSame not equals to UPDATED_IS_SAME
        defaultAddressShouldBeFound("isSame.notEquals=" + UPDATED_IS_SAME);
    }

    @Test
    @Transactional
    public void getAllAddressesByIsSameIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isSame in DEFAULT_IS_SAME or UPDATED_IS_SAME
        defaultAddressShouldBeFound("isSame.in=" + DEFAULT_IS_SAME + "," + UPDATED_IS_SAME);

        // Get all the addressList where isSame equals to UPDATED_IS_SAME
        defaultAddressShouldNotBeFound("isSame.in=" + UPDATED_IS_SAME);
    }

    @Test
    @Transactional
    public void getAllAddressesByIsSameIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isSame is not null
        defaultAddressShouldBeFound("isSame.specified=true");

        // Get all the addressList where isSame is null
        defaultAddressShouldNotBeFound("isSame.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByPresentDivisionIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        Division presentDivision = DivisionResourceIT.createEntity(em);
        em.persist(presentDivision);
        em.flush();
        address.setPresentDivision(presentDivision);
        addressRepository.saveAndFlush(address);
        Long presentDivisionId = presentDivision.getId();

        // Get all the addressList where presentDivision equals to presentDivisionId
        defaultAddressShouldBeFound("presentDivisionId.equals=" + presentDivisionId);

        // Get all the addressList where presentDivision equals to presentDivisionId + 1
        defaultAddressShouldNotBeFound("presentDivisionId.equals=" + (presentDivisionId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByPresentDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        District presentDistrict = DistrictResourceIT.createEntity(em);
        em.persist(presentDistrict);
        em.flush();
        address.setPresentDistrict(presentDistrict);
        addressRepository.saveAndFlush(address);
        Long presentDistrictId = presentDistrict.getId();

        // Get all the addressList where presentDistrict equals to presentDistrictId
        defaultAddressShouldBeFound("presentDistrictId.equals=" + presentDistrictId);

        // Get all the addressList where presentDistrict equals to presentDistrictId + 1
        defaultAddressShouldNotBeFound("presentDistrictId.equals=" + (presentDistrictId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByPresentThanaIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        Thana presentThana = ThanaResourceIT.createEntity(em);
        em.persist(presentThana);
        em.flush();
        address.setPresentThana(presentThana);
        addressRepository.saveAndFlush(address);
        Long presentThanaId = presentThana.getId();

        // Get all the addressList where presentThana equals to presentThanaId
        defaultAddressShouldBeFound("presentThanaId.equals=" + presentThanaId);

        // Get all the addressList where presentThana equals to presentThanaId + 1
        defaultAddressShouldNotBeFound("presentThanaId.equals=" + (presentThanaId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByPermanentDivisionIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        Division permanentDivision = DivisionResourceIT.createEntity(em);
        em.persist(permanentDivision);
        em.flush();
        address.setPermanentDivision(permanentDivision);
        addressRepository.saveAndFlush(address);
        Long permanentDivisionId = permanentDivision.getId();

        // Get all the addressList where permanentDivision equals to permanentDivisionId
        defaultAddressShouldBeFound("permanentDivisionId.equals=" + permanentDivisionId);

        // Get all the addressList where permanentDivision equals to permanentDivisionId + 1
        defaultAddressShouldNotBeFound("permanentDivisionId.equals=" + (permanentDivisionId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByPermanentDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        District permanentDistrict = DistrictResourceIT.createEntity(em);
        em.persist(permanentDistrict);
        em.flush();
        address.setPermanentDistrict(permanentDistrict);
        addressRepository.saveAndFlush(address);
        Long permanentDistrictId = permanentDistrict.getId();

        // Get all the addressList where permanentDistrict equals to permanentDistrictId
        defaultAddressShouldBeFound("permanentDistrictId.equals=" + permanentDistrictId);

        // Get all the addressList where permanentDistrict equals to permanentDistrictId + 1
        defaultAddressShouldNotBeFound("permanentDistrictId.equals=" + (permanentDistrictId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByPermanentThanaIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        Thana permanentThana = ThanaResourceIT.createEntity(em);
        em.persist(permanentThana);
        em.flush();
        address.setPermanentThana(permanentThana);
        addressRepository.saveAndFlush(address);
        Long permanentThanaId = permanentThana.getId();

        // Get all the addressList where permanentThana equals to permanentThanaId
        defaultAddressShouldBeFound("permanentThanaId.equals=" + permanentThanaId);

        // Get all the addressList where permanentThana equals to permanentThanaId + 1
        defaultAddressShouldNotBeFound("permanentThanaId.equals=" + (permanentThanaId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        address.setEmployee(employee);
        employee.setAddress(address);
        addressRepository.saveAndFlush(address);
        Long employeeId = employee.getId();

        // Get all the addressList where employee equals to employeeId
        defaultAddressShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the addressList where employee equals to employeeId + 1
        defaultAddressShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressShouldBeFound(String filter) throws Exception {
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].presentThanaTxt").value(hasItem(DEFAULT_PRESENT_THANA_TXT)))
            .andExpect(jsonPath("$.[*].presentStreet").value(hasItem(DEFAULT_PRESENT_STREET)))
            .andExpect(jsonPath("$.[*].presentArea").value(hasItem(DEFAULT_PRESENT_AREA)))
            .andExpect(jsonPath("$.[*].presentPostCode").value(hasItem(DEFAULT_PRESENT_POST_CODE)))
            .andExpect(jsonPath("$.[*].permanentThanaTxt").value(hasItem(DEFAULT_PERMANENT_THANA_TXT)))
            .andExpect(jsonPath("$.[*].permanentStreet").value(hasItem(DEFAULT_PERMANENT_STREET)))
            .andExpect(jsonPath("$.[*].permanentArea").value(hasItem(DEFAULT_PERMANENT_AREA)))
            .andExpect(jsonPath("$.[*].permanentPostCode").value(hasItem(DEFAULT_PERMANENT_POST_CODE)))
            .andExpect(jsonPath("$.[*].isSame").value(hasItem(DEFAULT_IS_SAME.booleanValue())));

        // Check, that the count call also returns 1
        restAddressMockMvc.perform(get("/api/addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressShouldNotBeFound(String filter) throws Exception {
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressMockMvc.perform(get("/api/addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressService.save(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .presentThanaTxt(UPDATED_PRESENT_THANA_TXT)
            .presentStreet(UPDATED_PRESENT_STREET)
            .presentArea(UPDATED_PRESENT_AREA)
            .presentPostCode(UPDATED_PRESENT_POST_CODE)
            .permanentThanaTxt(UPDATED_PERMANENT_THANA_TXT)
            .permanentStreet(UPDATED_PERMANENT_STREET)
            .permanentArea(UPDATED_PERMANENT_AREA)
            .permanentPostCode(UPDATED_PERMANENT_POST_CODE)
            .isSame(UPDATED_IS_SAME);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAddress)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getPresentThanaTxt()).isEqualTo(UPDATED_PRESENT_THANA_TXT);
        assertThat(testAddress.getPresentStreet()).isEqualTo(UPDATED_PRESENT_STREET);
        assertThat(testAddress.getPresentArea()).isEqualTo(UPDATED_PRESENT_AREA);
        assertThat(testAddress.getPresentPostCode()).isEqualTo(UPDATED_PRESENT_POST_CODE);
        assertThat(testAddress.getPermanentThanaTxt()).isEqualTo(UPDATED_PERMANENT_THANA_TXT);
        assertThat(testAddress.getPermanentStreet()).isEqualTo(UPDATED_PERMANENT_STREET);
        assertThat(testAddress.getPermanentArea()).isEqualTo(UPDATED_PERMANENT_AREA);
        assertThat(testAddress.getPermanentPostCode()).isEqualTo(UPDATED_PERMANENT_POST_CODE);
        assertThat(testAddress.isIsSame()).isEqualTo(UPDATED_IS_SAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(address)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressService.save(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
