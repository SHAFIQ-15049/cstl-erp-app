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

import software.cstl.domain.enumeration.AddressType;
/**
 * Integration tests for the {@link AddressResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AddressResourceIT {

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final Integer DEFAULT_POST_CODE = 1;
    private static final Integer UPDATED_POST_CODE = 2;
    private static final Integer SMALLER_POST_CODE = 1 - 1;

    private static final AddressType DEFAULT_ADDRESS_TYPE = AddressType.PRESENT;
    private static final AddressType UPDATED_ADDRESS_TYPE = AddressType.PERMANENT;

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
            .street(DEFAULT_STREET)
            .area(DEFAULT_AREA)
            .postCode(DEFAULT_POST_CODE)
            .addressType(DEFAULT_ADDRESS_TYPE);
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
            .street(UPDATED_STREET)
            .area(UPDATED_AREA)
            .postCode(UPDATED_POST_CODE)
            .addressType(UPDATED_ADDRESS_TYPE);
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
        assertThat(testAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAddress.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testAddress.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testAddress.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
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
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())));
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
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()));
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
    public void getAllAddressesByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street equals to DEFAULT_STREET
        defaultAddressShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the addressList where street equals to UPDATED_STREET
        defaultAddressShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street not equals to DEFAULT_STREET
        defaultAddressShouldNotBeFound("street.notEquals=" + DEFAULT_STREET);

        // Get all the addressList where street not equals to UPDATED_STREET
        defaultAddressShouldBeFound("street.notEquals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street in DEFAULT_STREET or UPDATED_STREET
        defaultAddressShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the addressList where street equals to UPDATED_STREET
        defaultAddressShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street is not null
        defaultAddressShouldBeFound("street.specified=true");

        // Get all the addressList where street is null
        defaultAddressShouldNotBeFound("street.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByStreetContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street contains DEFAULT_STREET
        defaultAddressShouldBeFound("street.contains=" + DEFAULT_STREET);

        // Get all the addressList where street contains UPDATED_STREET
        defaultAddressShouldNotBeFound("street.contains=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street does not contain DEFAULT_STREET
        defaultAddressShouldNotBeFound("street.doesNotContain=" + DEFAULT_STREET);

        // Get all the addressList where street does not contain UPDATED_STREET
        defaultAddressShouldBeFound("street.doesNotContain=" + UPDATED_STREET);
    }


    @Test
    @Transactional
    public void getAllAddressesByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area equals to DEFAULT_AREA
        defaultAddressShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the addressList where area equals to UPDATED_AREA
        defaultAddressShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area not equals to DEFAULT_AREA
        defaultAddressShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the addressList where area not equals to UPDATED_AREA
        defaultAddressShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area in DEFAULT_AREA or UPDATED_AREA
        defaultAddressShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the addressList where area equals to UPDATED_AREA
        defaultAddressShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area is not null
        defaultAddressShouldBeFound("area.specified=true");

        // Get all the addressList where area is null
        defaultAddressShouldNotBeFound("area.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByAreaContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area contains DEFAULT_AREA
        defaultAddressShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the addressList where area contains UPDATED_AREA
        defaultAddressShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    public void getAllAddressesByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area does not contain DEFAULT_AREA
        defaultAddressShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the addressList where area does not contain UPDATED_AREA
        defaultAddressShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }


    @Test
    @Transactional
    public void getAllAddressesByPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postCode equals to DEFAULT_POST_CODE
        defaultAddressShouldBeFound("postCode.equals=" + DEFAULT_POST_CODE);

        // Get all the addressList where postCode equals to UPDATED_POST_CODE
        defaultAddressShouldNotBeFound("postCode.equals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postCode not equals to DEFAULT_POST_CODE
        defaultAddressShouldNotBeFound("postCode.notEquals=" + DEFAULT_POST_CODE);

        // Get all the addressList where postCode not equals to UPDATED_POST_CODE
        defaultAddressShouldBeFound("postCode.notEquals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postCode in DEFAULT_POST_CODE or UPDATED_POST_CODE
        defaultAddressShouldBeFound("postCode.in=" + DEFAULT_POST_CODE + "," + UPDATED_POST_CODE);

        // Get all the addressList where postCode equals to UPDATED_POST_CODE
        defaultAddressShouldNotBeFound("postCode.in=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postCode is not null
        defaultAddressShouldBeFound("postCode.specified=true");

        // Get all the addressList where postCode is null
        defaultAddressShouldNotBeFound("postCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByPostCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postCode is greater than or equal to DEFAULT_POST_CODE
        defaultAddressShouldBeFound("postCode.greaterThanOrEqual=" + DEFAULT_POST_CODE);

        // Get all the addressList where postCode is greater than or equal to UPDATED_POST_CODE
        defaultAddressShouldNotBeFound("postCode.greaterThanOrEqual=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postCode is less than or equal to DEFAULT_POST_CODE
        defaultAddressShouldBeFound("postCode.lessThanOrEqual=" + DEFAULT_POST_CODE);

        // Get all the addressList where postCode is less than or equal to SMALLER_POST_CODE
        defaultAddressShouldNotBeFound("postCode.lessThanOrEqual=" + SMALLER_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postCode is less than DEFAULT_POST_CODE
        defaultAddressShouldNotBeFound("postCode.lessThan=" + DEFAULT_POST_CODE);

        // Get all the addressList where postCode is less than UPDATED_POST_CODE
        defaultAddressShouldBeFound("postCode.lessThan=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postCode is greater than DEFAULT_POST_CODE
        defaultAddressShouldNotBeFound("postCode.greaterThan=" + DEFAULT_POST_CODE);

        // Get all the addressList where postCode is greater than SMALLER_POST_CODE
        defaultAddressShouldBeFound("postCode.greaterThan=" + SMALLER_POST_CODE);
    }


    @Test
    @Transactional
    public void getAllAddressesByAddressTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressType equals to DEFAULT_ADDRESS_TYPE
        defaultAddressShouldBeFound("addressType.equals=" + DEFAULT_ADDRESS_TYPE);

        // Get all the addressList where addressType equals to UPDATED_ADDRESS_TYPE
        defaultAddressShouldNotBeFound("addressType.equals=" + UPDATED_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressType not equals to DEFAULT_ADDRESS_TYPE
        defaultAddressShouldNotBeFound("addressType.notEquals=" + DEFAULT_ADDRESS_TYPE);

        // Get all the addressList where addressType not equals to UPDATED_ADDRESS_TYPE
        defaultAddressShouldBeFound("addressType.notEquals=" + UPDATED_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressTypeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressType in DEFAULT_ADDRESS_TYPE or UPDATED_ADDRESS_TYPE
        defaultAddressShouldBeFound("addressType.in=" + DEFAULT_ADDRESS_TYPE + "," + UPDATED_ADDRESS_TYPE);

        // Get all the addressList where addressType equals to UPDATED_ADDRESS_TYPE
        defaultAddressShouldNotBeFound("addressType.in=" + UPDATED_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressType is not null
        defaultAddressShouldBeFound("addressType.specified=true");

        // Get all the addressList where addressType is null
        defaultAddressShouldNotBeFound("addressType.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByDivisionIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        Division division = DivisionResourceIT.createEntity(em);
        em.persist(division);
        em.flush();
        address.setDivision(division);
        addressRepository.saveAndFlush(address);
        Long divisionId = division.getId();

        // Get all the addressList where division equals to divisionId
        defaultAddressShouldBeFound("divisionId.equals=" + divisionId);

        // Get all the addressList where division equals to divisionId + 1
        defaultAddressShouldNotBeFound("divisionId.equals=" + (divisionId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        District district = DistrictResourceIT.createEntity(em);
        em.persist(district);
        em.flush();
        address.setDistrict(district);
        addressRepository.saveAndFlush(address);
        Long districtId = district.getId();

        // Get all the addressList where district equals to districtId
        defaultAddressShouldBeFound("districtId.equals=" + districtId);

        // Get all the addressList where district equals to districtId + 1
        defaultAddressShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByThanaIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        Thana thana = ThanaResourceIT.createEntity(em);
        em.persist(thana);
        em.flush();
        address.setThana(thana);
        addressRepository.saveAndFlush(address);
        Long thanaId = thana.getId();

        // Get all the addressList where thana equals to thanaId
        defaultAddressShouldBeFound("thanaId.equals=" + thanaId);

        // Get all the addressList where thana equals to thanaId + 1
        defaultAddressShouldNotBeFound("thanaId.equals=" + (thanaId + 1));
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
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE)))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())));

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
            .street(UPDATED_STREET)
            .area(UPDATED_AREA)
            .postCode(UPDATED_POST_CODE)
            .addressType(UPDATED_ADDRESS_TYPE);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAddress)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddress.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testAddress.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testAddress.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
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
