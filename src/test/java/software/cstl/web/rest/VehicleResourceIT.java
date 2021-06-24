package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Vehicle;
import software.cstl.domain.Customer;
import software.cstl.repository.VehicleRepository;
import software.cstl.service.VehicleService;
import software.cstl.service.dto.VehicleCriteria;
import software.cstl.service.VehicleQueryService;

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

import software.cstl.domain.enumeration.VehicleType;
import software.cstl.domain.enumeration.ColourType;
import software.cstl.domain.enumeration.CylinderNumber;
import software.cstl.domain.enumeration.FuelType;
/**
 * Integration tests for the {@link VehicleResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VehicleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_ID = "BBBBBBBBBB";

    private static final VehicleType DEFAULT_TYPE = VehicleType.VEHICLE;
    private static final VehicleType UPDATED_TYPE = VehicleType.TRAILER;

    private static final String DEFAULT_CLASS_OF_VEHICLE = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_OF_VEHICLE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_OF_BODY = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_OF_BODY = "BBBBBBBBBB";

    private static final ColourType DEFAULT_COLOUR = ColourType.BLACK;
    private static final ColourType UPDATED_COLOUR = ColourType.SILVER;

    private static final CylinderNumber DEFAULT_NUMBER_OF_CYLINDERS = CylinderNumber.ONE;
    private static final CylinderNumber UPDATED_NUMBER_OF_CYLINDERS = CylinderNumber.TWO;

    private static final String DEFAULT_ENGINE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ENGINE_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_HORSE_POWER = 1;
    private static final Integer UPDATED_HORSE_POWER = 2;
    private static final Integer SMALLER_HORSE_POWER = 1 - 1;

    private static final Integer DEFAULT_CUBIC_CAPACITY = 1;
    private static final Integer UPDATED_CUBIC_CAPACITY = 2;
    private static final Integer SMALLER_CUBIC_CAPACITY = 1 - 1;

    private static final String DEFAULT_NO_OF_STANDEE = "AAAAAAAAAA";
    private static final String UPDATED_NO_OF_STANDEE = "BBBBBBBBBB";

    private static final Integer DEFAULT_UNLADEN_WEIGHT = 1;
    private static final Integer UPDATED_UNLADEN_WEIGHT = 2;
    private static final Integer SMALLER_UNLADEN_WEIGHT = 1 - 1;

    private static final String DEFAULT_PREV_REGN_NO = "AAAAAAAAAA";
    private static final String UPDATED_PREV_REGN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MAKERS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MAKERS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAKERS_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_MAKERS_COUNTRY = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEARS_OF_MANUFACTURE = 1;
    private static final Integer UPDATED_YEARS_OF_MANUFACTURE = 2;
    private static final Integer SMALLER_YEARS_OF_MANUFACTURE = 1 - 1;

    private static final String DEFAULT_CHASSIS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHASSIS_NUMBER = "BBBBBBBBBB";

    private static final FuelType DEFAULT_FUEL_USED = FuelType.OCTANE;
    private static final FuelType UPDATED_FUEL_USED = FuelType.PETROL;

    private static final Integer DEFAULT_RPM = 1;
    private static final Integer UPDATED_RPM = 2;
    private static final Integer SMALLER_RPM = 1 - 1;

    private static final Integer DEFAULT_SEATS = 1;
    private static final Integer UPDATED_SEATS = 2;
    private static final Integer SMALLER_SEATS = 1 - 1;

    private static final Integer DEFAULT_WHEEL_BASE = 1;
    private static final Integer UPDATED_WHEEL_BASE = 2;
    private static final Integer SMALLER_WHEEL_BASE = 1 - 1;

    private static final Integer DEFAULT_MAX_LADEN = 1;
    private static final Integer UPDATED_MAX_LADEN = 2;
    private static final Integer SMALLER_MAX_LADEN = 1 - 1;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleQueryService vehicleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .name(DEFAULT_NAME)
            .vehicleId(DEFAULT_VEHICLE_ID)
            .type(DEFAULT_TYPE)
            .classOfVehicle(DEFAULT_CLASS_OF_VEHICLE)
            .typeOfBody(DEFAULT_TYPE_OF_BODY)
            .colour(DEFAULT_COLOUR)
            .numberOfCylinders(DEFAULT_NUMBER_OF_CYLINDERS)
            .engineNumber(DEFAULT_ENGINE_NUMBER)
            .horsePower(DEFAULT_HORSE_POWER)
            .cubicCapacity(DEFAULT_CUBIC_CAPACITY)
            .noOfStandee(DEFAULT_NO_OF_STANDEE)
            .unladenWeight(DEFAULT_UNLADEN_WEIGHT)
            .prevRegnNo(DEFAULT_PREV_REGN_NO)
            .makersName(DEFAULT_MAKERS_NAME)
            .makersCountry(DEFAULT_MAKERS_COUNTRY)
            .yearsOfManufacture(DEFAULT_YEARS_OF_MANUFACTURE)
            .chassisNumber(DEFAULT_CHASSIS_NUMBER)
            .fuelUsed(DEFAULT_FUEL_USED)
            .rpm(DEFAULT_RPM)
            .seats(DEFAULT_SEATS)
            .wheelBase(DEFAULT_WHEEL_BASE)
            .maxLaden(DEFAULT_MAX_LADEN);
        return vehicle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createUpdatedEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .name(UPDATED_NAME)
            .vehicleId(UPDATED_VEHICLE_ID)
            .type(UPDATED_TYPE)
            .classOfVehicle(UPDATED_CLASS_OF_VEHICLE)
            .typeOfBody(UPDATED_TYPE_OF_BODY)
            .colour(UPDATED_COLOUR)
            .numberOfCylinders(UPDATED_NUMBER_OF_CYLINDERS)
            .engineNumber(UPDATED_ENGINE_NUMBER)
            .horsePower(UPDATED_HORSE_POWER)
            .cubicCapacity(UPDATED_CUBIC_CAPACITY)
            .noOfStandee(UPDATED_NO_OF_STANDEE)
            .unladenWeight(UPDATED_UNLADEN_WEIGHT)
            .prevRegnNo(UPDATED_PREV_REGN_NO)
            .makersName(UPDATED_MAKERS_NAME)
            .makersCountry(UPDATED_MAKERS_COUNTRY)
            .yearsOfManufacture(UPDATED_YEARS_OF_MANUFACTURE)
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .fuelUsed(UPDATED_FUEL_USED)
            .rpm(UPDATED_RPM)
            .seats(UPDATED_SEATS)
            .wheelBase(UPDATED_WHEEL_BASE)
            .maxLaden(UPDATED_MAX_LADEN);
        return vehicle;
    }

    @BeforeEach
    public void initTest() {
        vehicle = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();
        // Create the Vehicle
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicle.getVehicleId()).isEqualTo(DEFAULT_VEHICLE_ID);
        assertThat(testVehicle.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVehicle.getClassOfVehicle()).isEqualTo(DEFAULT_CLASS_OF_VEHICLE);
        assertThat(testVehicle.getTypeOfBody()).isEqualTo(DEFAULT_TYPE_OF_BODY);
        assertThat(testVehicle.getColour()).isEqualTo(DEFAULT_COLOUR);
        assertThat(testVehicle.getNumberOfCylinders()).isEqualTo(DEFAULT_NUMBER_OF_CYLINDERS);
        assertThat(testVehicle.getEngineNumber()).isEqualTo(DEFAULT_ENGINE_NUMBER);
        assertThat(testVehicle.getHorsePower()).isEqualTo(DEFAULT_HORSE_POWER);
        assertThat(testVehicle.getCubicCapacity()).isEqualTo(DEFAULT_CUBIC_CAPACITY);
        assertThat(testVehicle.getNoOfStandee()).isEqualTo(DEFAULT_NO_OF_STANDEE);
        assertThat(testVehicle.getUnladenWeight()).isEqualTo(DEFAULT_UNLADEN_WEIGHT);
        assertThat(testVehicle.getPrevRegnNo()).isEqualTo(DEFAULT_PREV_REGN_NO);
        assertThat(testVehicle.getMakersName()).isEqualTo(DEFAULT_MAKERS_NAME);
        assertThat(testVehicle.getMakersCountry()).isEqualTo(DEFAULT_MAKERS_COUNTRY);
        assertThat(testVehicle.getYearsOfManufacture()).isEqualTo(DEFAULT_YEARS_OF_MANUFACTURE);
        assertThat(testVehicle.getChassisNumber()).isEqualTo(DEFAULT_CHASSIS_NUMBER);
        assertThat(testVehicle.getFuelUsed()).isEqualTo(DEFAULT_FUEL_USED);
        assertThat(testVehicle.getRpm()).isEqualTo(DEFAULT_RPM);
        assertThat(testVehicle.getSeats()).isEqualTo(DEFAULT_SEATS);
        assertThat(testVehicle.getWheelBase()).isEqualTo(DEFAULT_WHEEL_BASE);
        assertThat(testVehicle.getMaxLaden()).isEqualTo(DEFAULT_MAX_LADEN);
    }

    @Test
    @Transactional
    public void createVehicleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle with an existing ID
        vehicle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vehicleId").value(hasItem(DEFAULT_VEHICLE_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].classOfVehicle").value(hasItem(DEFAULT_CLASS_OF_VEHICLE)))
            .andExpect(jsonPath("$.[*].typeOfBody").value(hasItem(DEFAULT_TYPE_OF_BODY)))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR.toString())))
            .andExpect(jsonPath("$.[*].numberOfCylinders").value(hasItem(DEFAULT_NUMBER_OF_CYLINDERS.toString())))
            .andExpect(jsonPath("$.[*].engineNumber").value(hasItem(DEFAULT_ENGINE_NUMBER)))
            .andExpect(jsonPath("$.[*].horsePower").value(hasItem(DEFAULT_HORSE_POWER)))
            .andExpect(jsonPath("$.[*].cubicCapacity").value(hasItem(DEFAULT_CUBIC_CAPACITY)))
            .andExpect(jsonPath("$.[*].noOfStandee").value(hasItem(DEFAULT_NO_OF_STANDEE)))
            .andExpect(jsonPath("$.[*].unladenWeight").value(hasItem(DEFAULT_UNLADEN_WEIGHT)))
            .andExpect(jsonPath("$.[*].prevRegnNo").value(hasItem(DEFAULT_PREV_REGN_NO)))
            .andExpect(jsonPath("$.[*].makersName").value(hasItem(DEFAULT_MAKERS_NAME)))
            .andExpect(jsonPath("$.[*].makersCountry").value(hasItem(DEFAULT_MAKERS_COUNTRY)))
            .andExpect(jsonPath("$.[*].yearsOfManufacture").value(hasItem(DEFAULT_YEARS_OF_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].chassisNumber").value(hasItem(DEFAULT_CHASSIS_NUMBER)))
            .andExpect(jsonPath("$.[*].fuelUsed").value(hasItem(DEFAULT_FUEL_USED.toString())))
            .andExpect(jsonPath("$.[*].rpm").value(hasItem(DEFAULT_RPM)))
            .andExpect(jsonPath("$.[*].seats").value(hasItem(DEFAULT_SEATS)))
            .andExpect(jsonPath("$.[*].wheelBase").value(hasItem(DEFAULT_WHEEL_BASE)))
            .andExpect(jsonPath("$.[*].maxLaden").value(hasItem(DEFAULT_MAX_LADEN)));
    }
    
    @Test
    @Transactional
    public void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.vehicleId").value(DEFAULT_VEHICLE_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.classOfVehicle").value(DEFAULT_CLASS_OF_VEHICLE))
            .andExpect(jsonPath("$.typeOfBody").value(DEFAULT_TYPE_OF_BODY))
            .andExpect(jsonPath("$.colour").value(DEFAULT_COLOUR.toString()))
            .andExpect(jsonPath("$.numberOfCylinders").value(DEFAULT_NUMBER_OF_CYLINDERS.toString()))
            .andExpect(jsonPath("$.engineNumber").value(DEFAULT_ENGINE_NUMBER))
            .andExpect(jsonPath("$.horsePower").value(DEFAULT_HORSE_POWER))
            .andExpect(jsonPath("$.cubicCapacity").value(DEFAULT_CUBIC_CAPACITY))
            .andExpect(jsonPath("$.noOfStandee").value(DEFAULT_NO_OF_STANDEE))
            .andExpect(jsonPath("$.unladenWeight").value(DEFAULT_UNLADEN_WEIGHT))
            .andExpect(jsonPath("$.prevRegnNo").value(DEFAULT_PREV_REGN_NO))
            .andExpect(jsonPath("$.makersName").value(DEFAULT_MAKERS_NAME))
            .andExpect(jsonPath("$.makersCountry").value(DEFAULT_MAKERS_COUNTRY))
            .andExpect(jsonPath("$.yearsOfManufacture").value(DEFAULT_YEARS_OF_MANUFACTURE))
            .andExpect(jsonPath("$.chassisNumber").value(DEFAULT_CHASSIS_NUMBER))
            .andExpect(jsonPath("$.fuelUsed").value(DEFAULT_FUEL_USED.toString()))
            .andExpect(jsonPath("$.rpm").value(DEFAULT_RPM))
            .andExpect(jsonPath("$.seats").value(DEFAULT_SEATS))
            .andExpect(jsonPath("$.wheelBase").value(DEFAULT_WHEEL_BASE))
            .andExpect(jsonPath("$.maxLaden").value(DEFAULT_MAX_LADEN));
    }


    @Test
    @Transactional
    public void getVehiclesByIdFiltering() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        Long id = vehicle.getId();

        defaultVehicleShouldBeFound("id.equals=" + id);
        defaultVehicleShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVehiclesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where name equals to DEFAULT_NAME
        defaultVehicleShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the vehicleList where name equals to UPDATED_NAME
        defaultVehicleShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where name not equals to DEFAULT_NAME
        defaultVehicleShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the vehicleList where name not equals to UPDATED_NAME
        defaultVehicleShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVehicleShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the vehicleList where name equals to UPDATED_NAME
        defaultVehicleShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where name is not null
        defaultVehicleShouldBeFound("name.specified=true");

        // Get all the vehicleList where name is null
        defaultVehicleShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByNameContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where name contains DEFAULT_NAME
        defaultVehicleShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the vehicleList where name contains UPDATED_NAME
        defaultVehicleShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where name does not contain DEFAULT_NAME
        defaultVehicleShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the vehicleList where name does not contain UPDATED_NAME
        defaultVehicleShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllVehiclesByVehicleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleId equals to DEFAULT_VEHICLE_ID
        defaultVehicleShouldBeFound("vehicleId.equals=" + DEFAULT_VEHICLE_ID);

        // Get all the vehicleList where vehicleId equals to UPDATED_VEHICLE_ID
        defaultVehicleShouldNotBeFound("vehicleId.equals=" + UPDATED_VEHICLE_ID);
    }

    @Test
    @Transactional
    public void getAllVehiclesByVehicleIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleId not equals to DEFAULT_VEHICLE_ID
        defaultVehicleShouldNotBeFound("vehicleId.notEquals=" + DEFAULT_VEHICLE_ID);

        // Get all the vehicleList where vehicleId not equals to UPDATED_VEHICLE_ID
        defaultVehicleShouldBeFound("vehicleId.notEquals=" + UPDATED_VEHICLE_ID);
    }

    @Test
    @Transactional
    public void getAllVehiclesByVehicleIdIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleId in DEFAULT_VEHICLE_ID or UPDATED_VEHICLE_ID
        defaultVehicleShouldBeFound("vehicleId.in=" + DEFAULT_VEHICLE_ID + "," + UPDATED_VEHICLE_ID);

        // Get all the vehicleList where vehicleId equals to UPDATED_VEHICLE_ID
        defaultVehicleShouldNotBeFound("vehicleId.in=" + UPDATED_VEHICLE_ID);
    }

    @Test
    @Transactional
    public void getAllVehiclesByVehicleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleId is not null
        defaultVehicleShouldBeFound("vehicleId.specified=true");

        // Get all the vehicleList where vehicleId is null
        defaultVehicleShouldNotBeFound("vehicleId.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByVehicleIdContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleId contains DEFAULT_VEHICLE_ID
        defaultVehicleShouldBeFound("vehicleId.contains=" + DEFAULT_VEHICLE_ID);

        // Get all the vehicleList where vehicleId contains UPDATED_VEHICLE_ID
        defaultVehicleShouldNotBeFound("vehicleId.contains=" + UPDATED_VEHICLE_ID);
    }

    @Test
    @Transactional
    public void getAllVehiclesByVehicleIdNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where vehicleId does not contain DEFAULT_VEHICLE_ID
        defaultVehicleShouldNotBeFound("vehicleId.doesNotContain=" + DEFAULT_VEHICLE_ID);

        // Get all the vehicleList where vehicleId does not contain UPDATED_VEHICLE_ID
        defaultVehicleShouldBeFound("vehicleId.doesNotContain=" + UPDATED_VEHICLE_ID);
    }


    @Test
    @Transactional
    public void getAllVehiclesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where type equals to DEFAULT_TYPE
        defaultVehicleShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the vehicleList where type equals to UPDATED_TYPE
        defaultVehicleShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where type not equals to DEFAULT_TYPE
        defaultVehicleShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the vehicleList where type not equals to UPDATED_TYPE
        defaultVehicleShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultVehicleShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the vehicleList where type equals to UPDATED_TYPE
        defaultVehicleShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where type is not null
        defaultVehicleShouldBeFound("type.specified=true");

        // Get all the vehicleList where type is null
        defaultVehicleShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByClassOfVehicleIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where classOfVehicle equals to DEFAULT_CLASS_OF_VEHICLE
        defaultVehicleShouldBeFound("classOfVehicle.equals=" + DEFAULT_CLASS_OF_VEHICLE);

        // Get all the vehicleList where classOfVehicle equals to UPDATED_CLASS_OF_VEHICLE
        defaultVehicleShouldNotBeFound("classOfVehicle.equals=" + UPDATED_CLASS_OF_VEHICLE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByClassOfVehicleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where classOfVehicle not equals to DEFAULT_CLASS_OF_VEHICLE
        defaultVehicleShouldNotBeFound("classOfVehicle.notEquals=" + DEFAULT_CLASS_OF_VEHICLE);

        // Get all the vehicleList where classOfVehicle not equals to UPDATED_CLASS_OF_VEHICLE
        defaultVehicleShouldBeFound("classOfVehicle.notEquals=" + UPDATED_CLASS_OF_VEHICLE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByClassOfVehicleIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where classOfVehicle in DEFAULT_CLASS_OF_VEHICLE or UPDATED_CLASS_OF_VEHICLE
        defaultVehicleShouldBeFound("classOfVehicle.in=" + DEFAULT_CLASS_OF_VEHICLE + "," + UPDATED_CLASS_OF_VEHICLE);

        // Get all the vehicleList where classOfVehicle equals to UPDATED_CLASS_OF_VEHICLE
        defaultVehicleShouldNotBeFound("classOfVehicle.in=" + UPDATED_CLASS_OF_VEHICLE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByClassOfVehicleIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where classOfVehicle is not null
        defaultVehicleShouldBeFound("classOfVehicle.specified=true");

        // Get all the vehicleList where classOfVehicle is null
        defaultVehicleShouldNotBeFound("classOfVehicle.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByClassOfVehicleContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where classOfVehicle contains DEFAULT_CLASS_OF_VEHICLE
        defaultVehicleShouldBeFound("classOfVehicle.contains=" + DEFAULT_CLASS_OF_VEHICLE);

        // Get all the vehicleList where classOfVehicle contains UPDATED_CLASS_OF_VEHICLE
        defaultVehicleShouldNotBeFound("classOfVehicle.contains=" + UPDATED_CLASS_OF_VEHICLE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByClassOfVehicleNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where classOfVehicle does not contain DEFAULT_CLASS_OF_VEHICLE
        defaultVehicleShouldNotBeFound("classOfVehicle.doesNotContain=" + DEFAULT_CLASS_OF_VEHICLE);

        // Get all the vehicleList where classOfVehicle does not contain UPDATED_CLASS_OF_VEHICLE
        defaultVehicleShouldBeFound("classOfVehicle.doesNotContain=" + UPDATED_CLASS_OF_VEHICLE);
    }


    @Test
    @Transactional
    public void getAllVehiclesByTypeOfBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where typeOfBody equals to DEFAULT_TYPE_OF_BODY
        defaultVehicleShouldBeFound("typeOfBody.equals=" + DEFAULT_TYPE_OF_BODY);

        // Get all the vehicleList where typeOfBody equals to UPDATED_TYPE_OF_BODY
        defaultVehicleShouldNotBeFound("typeOfBody.equals=" + UPDATED_TYPE_OF_BODY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByTypeOfBodyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where typeOfBody not equals to DEFAULT_TYPE_OF_BODY
        defaultVehicleShouldNotBeFound("typeOfBody.notEquals=" + DEFAULT_TYPE_OF_BODY);

        // Get all the vehicleList where typeOfBody not equals to UPDATED_TYPE_OF_BODY
        defaultVehicleShouldBeFound("typeOfBody.notEquals=" + UPDATED_TYPE_OF_BODY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByTypeOfBodyIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where typeOfBody in DEFAULT_TYPE_OF_BODY or UPDATED_TYPE_OF_BODY
        defaultVehicleShouldBeFound("typeOfBody.in=" + DEFAULT_TYPE_OF_BODY + "," + UPDATED_TYPE_OF_BODY);

        // Get all the vehicleList where typeOfBody equals to UPDATED_TYPE_OF_BODY
        defaultVehicleShouldNotBeFound("typeOfBody.in=" + UPDATED_TYPE_OF_BODY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByTypeOfBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where typeOfBody is not null
        defaultVehicleShouldBeFound("typeOfBody.specified=true");

        // Get all the vehicleList where typeOfBody is null
        defaultVehicleShouldNotBeFound("typeOfBody.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByTypeOfBodyContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where typeOfBody contains DEFAULT_TYPE_OF_BODY
        defaultVehicleShouldBeFound("typeOfBody.contains=" + DEFAULT_TYPE_OF_BODY);

        // Get all the vehicleList where typeOfBody contains UPDATED_TYPE_OF_BODY
        defaultVehicleShouldNotBeFound("typeOfBody.contains=" + UPDATED_TYPE_OF_BODY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByTypeOfBodyNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where typeOfBody does not contain DEFAULT_TYPE_OF_BODY
        defaultVehicleShouldNotBeFound("typeOfBody.doesNotContain=" + DEFAULT_TYPE_OF_BODY);

        // Get all the vehicleList where typeOfBody does not contain UPDATED_TYPE_OF_BODY
        defaultVehicleShouldBeFound("typeOfBody.doesNotContain=" + UPDATED_TYPE_OF_BODY);
    }


    @Test
    @Transactional
    public void getAllVehiclesByColourIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where colour equals to DEFAULT_COLOUR
        defaultVehicleShouldBeFound("colour.equals=" + DEFAULT_COLOUR);

        // Get all the vehicleList where colour equals to UPDATED_COLOUR
        defaultVehicleShouldNotBeFound("colour.equals=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllVehiclesByColourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where colour not equals to DEFAULT_COLOUR
        defaultVehicleShouldNotBeFound("colour.notEquals=" + DEFAULT_COLOUR);

        // Get all the vehicleList where colour not equals to UPDATED_COLOUR
        defaultVehicleShouldBeFound("colour.notEquals=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllVehiclesByColourIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where colour in DEFAULT_COLOUR or UPDATED_COLOUR
        defaultVehicleShouldBeFound("colour.in=" + DEFAULT_COLOUR + "," + UPDATED_COLOUR);

        // Get all the vehicleList where colour equals to UPDATED_COLOUR
        defaultVehicleShouldNotBeFound("colour.in=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllVehiclesByColourIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where colour is not null
        defaultVehicleShouldBeFound("colour.specified=true");

        // Get all the vehicleList where colour is null
        defaultVehicleShouldNotBeFound("colour.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByNumberOfCylindersIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where numberOfCylinders equals to DEFAULT_NUMBER_OF_CYLINDERS
        defaultVehicleShouldBeFound("numberOfCylinders.equals=" + DEFAULT_NUMBER_OF_CYLINDERS);

        // Get all the vehicleList where numberOfCylinders equals to UPDATED_NUMBER_OF_CYLINDERS
        defaultVehicleShouldNotBeFound("numberOfCylinders.equals=" + UPDATED_NUMBER_OF_CYLINDERS);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNumberOfCylindersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where numberOfCylinders not equals to DEFAULT_NUMBER_OF_CYLINDERS
        defaultVehicleShouldNotBeFound("numberOfCylinders.notEquals=" + DEFAULT_NUMBER_OF_CYLINDERS);

        // Get all the vehicleList where numberOfCylinders not equals to UPDATED_NUMBER_OF_CYLINDERS
        defaultVehicleShouldBeFound("numberOfCylinders.notEquals=" + UPDATED_NUMBER_OF_CYLINDERS);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNumberOfCylindersIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where numberOfCylinders in DEFAULT_NUMBER_OF_CYLINDERS or UPDATED_NUMBER_OF_CYLINDERS
        defaultVehicleShouldBeFound("numberOfCylinders.in=" + DEFAULT_NUMBER_OF_CYLINDERS + "," + UPDATED_NUMBER_OF_CYLINDERS);

        // Get all the vehicleList where numberOfCylinders equals to UPDATED_NUMBER_OF_CYLINDERS
        defaultVehicleShouldNotBeFound("numberOfCylinders.in=" + UPDATED_NUMBER_OF_CYLINDERS);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNumberOfCylindersIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where numberOfCylinders is not null
        defaultVehicleShouldBeFound("numberOfCylinders.specified=true");

        // Get all the vehicleList where numberOfCylinders is null
        defaultVehicleShouldNotBeFound("numberOfCylinders.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByEngineNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where engineNumber equals to DEFAULT_ENGINE_NUMBER
        defaultVehicleShouldBeFound("engineNumber.equals=" + DEFAULT_ENGINE_NUMBER);

        // Get all the vehicleList where engineNumber equals to UPDATED_ENGINE_NUMBER
        defaultVehicleShouldNotBeFound("engineNumber.equals=" + UPDATED_ENGINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByEngineNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where engineNumber not equals to DEFAULT_ENGINE_NUMBER
        defaultVehicleShouldNotBeFound("engineNumber.notEquals=" + DEFAULT_ENGINE_NUMBER);

        // Get all the vehicleList where engineNumber not equals to UPDATED_ENGINE_NUMBER
        defaultVehicleShouldBeFound("engineNumber.notEquals=" + UPDATED_ENGINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByEngineNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where engineNumber in DEFAULT_ENGINE_NUMBER or UPDATED_ENGINE_NUMBER
        defaultVehicleShouldBeFound("engineNumber.in=" + DEFAULT_ENGINE_NUMBER + "," + UPDATED_ENGINE_NUMBER);

        // Get all the vehicleList where engineNumber equals to UPDATED_ENGINE_NUMBER
        defaultVehicleShouldNotBeFound("engineNumber.in=" + UPDATED_ENGINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByEngineNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where engineNumber is not null
        defaultVehicleShouldBeFound("engineNumber.specified=true");

        // Get all the vehicleList where engineNumber is null
        defaultVehicleShouldNotBeFound("engineNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByEngineNumberContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where engineNumber contains DEFAULT_ENGINE_NUMBER
        defaultVehicleShouldBeFound("engineNumber.contains=" + DEFAULT_ENGINE_NUMBER);

        // Get all the vehicleList where engineNumber contains UPDATED_ENGINE_NUMBER
        defaultVehicleShouldNotBeFound("engineNumber.contains=" + UPDATED_ENGINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByEngineNumberNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where engineNumber does not contain DEFAULT_ENGINE_NUMBER
        defaultVehicleShouldNotBeFound("engineNumber.doesNotContain=" + DEFAULT_ENGINE_NUMBER);

        // Get all the vehicleList where engineNumber does not contain UPDATED_ENGINE_NUMBER
        defaultVehicleShouldBeFound("engineNumber.doesNotContain=" + UPDATED_ENGINE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllVehiclesByHorsePowerIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where horsePower equals to DEFAULT_HORSE_POWER
        defaultVehicleShouldBeFound("horsePower.equals=" + DEFAULT_HORSE_POWER);

        // Get all the vehicleList where horsePower equals to UPDATED_HORSE_POWER
        defaultVehicleShouldNotBeFound("horsePower.equals=" + UPDATED_HORSE_POWER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByHorsePowerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where horsePower not equals to DEFAULT_HORSE_POWER
        defaultVehicleShouldNotBeFound("horsePower.notEquals=" + DEFAULT_HORSE_POWER);

        // Get all the vehicleList where horsePower not equals to UPDATED_HORSE_POWER
        defaultVehicleShouldBeFound("horsePower.notEquals=" + UPDATED_HORSE_POWER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByHorsePowerIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where horsePower in DEFAULT_HORSE_POWER or UPDATED_HORSE_POWER
        defaultVehicleShouldBeFound("horsePower.in=" + DEFAULT_HORSE_POWER + "," + UPDATED_HORSE_POWER);

        // Get all the vehicleList where horsePower equals to UPDATED_HORSE_POWER
        defaultVehicleShouldNotBeFound("horsePower.in=" + UPDATED_HORSE_POWER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByHorsePowerIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where horsePower is not null
        defaultVehicleShouldBeFound("horsePower.specified=true");

        // Get all the vehicleList where horsePower is null
        defaultVehicleShouldNotBeFound("horsePower.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByHorsePowerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where horsePower is greater than or equal to DEFAULT_HORSE_POWER
        defaultVehicleShouldBeFound("horsePower.greaterThanOrEqual=" + DEFAULT_HORSE_POWER);

        // Get all the vehicleList where horsePower is greater than or equal to UPDATED_HORSE_POWER
        defaultVehicleShouldNotBeFound("horsePower.greaterThanOrEqual=" + UPDATED_HORSE_POWER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByHorsePowerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where horsePower is less than or equal to DEFAULT_HORSE_POWER
        defaultVehicleShouldBeFound("horsePower.lessThanOrEqual=" + DEFAULT_HORSE_POWER);

        // Get all the vehicleList where horsePower is less than or equal to SMALLER_HORSE_POWER
        defaultVehicleShouldNotBeFound("horsePower.lessThanOrEqual=" + SMALLER_HORSE_POWER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByHorsePowerIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where horsePower is less than DEFAULT_HORSE_POWER
        defaultVehicleShouldNotBeFound("horsePower.lessThan=" + DEFAULT_HORSE_POWER);

        // Get all the vehicleList where horsePower is less than UPDATED_HORSE_POWER
        defaultVehicleShouldBeFound("horsePower.lessThan=" + UPDATED_HORSE_POWER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByHorsePowerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where horsePower is greater than DEFAULT_HORSE_POWER
        defaultVehicleShouldNotBeFound("horsePower.greaterThan=" + DEFAULT_HORSE_POWER);

        // Get all the vehicleList where horsePower is greater than SMALLER_HORSE_POWER
        defaultVehicleShouldBeFound("horsePower.greaterThan=" + SMALLER_HORSE_POWER);
    }


    @Test
    @Transactional
    public void getAllVehiclesByCubicCapacityIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where cubicCapacity equals to DEFAULT_CUBIC_CAPACITY
        defaultVehicleShouldBeFound("cubicCapacity.equals=" + DEFAULT_CUBIC_CAPACITY);

        // Get all the vehicleList where cubicCapacity equals to UPDATED_CUBIC_CAPACITY
        defaultVehicleShouldNotBeFound("cubicCapacity.equals=" + UPDATED_CUBIC_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByCubicCapacityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where cubicCapacity not equals to DEFAULT_CUBIC_CAPACITY
        defaultVehicleShouldNotBeFound("cubicCapacity.notEquals=" + DEFAULT_CUBIC_CAPACITY);

        // Get all the vehicleList where cubicCapacity not equals to UPDATED_CUBIC_CAPACITY
        defaultVehicleShouldBeFound("cubicCapacity.notEquals=" + UPDATED_CUBIC_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByCubicCapacityIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where cubicCapacity in DEFAULT_CUBIC_CAPACITY or UPDATED_CUBIC_CAPACITY
        defaultVehicleShouldBeFound("cubicCapacity.in=" + DEFAULT_CUBIC_CAPACITY + "," + UPDATED_CUBIC_CAPACITY);

        // Get all the vehicleList where cubicCapacity equals to UPDATED_CUBIC_CAPACITY
        defaultVehicleShouldNotBeFound("cubicCapacity.in=" + UPDATED_CUBIC_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByCubicCapacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where cubicCapacity is not null
        defaultVehicleShouldBeFound("cubicCapacity.specified=true");

        // Get all the vehicleList where cubicCapacity is null
        defaultVehicleShouldNotBeFound("cubicCapacity.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByCubicCapacityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where cubicCapacity is greater than or equal to DEFAULT_CUBIC_CAPACITY
        defaultVehicleShouldBeFound("cubicCapacity.greaterThanOrEqual=" + DEFAULT_CUBIC_CAPACITY);

        // Get all the vehicleList where cubicCapacity is greater than or equal to UPDATED_CUBIC_CAPACITY
        defaultVehicleShouldNotBeFound("cubicCapacity.greaterThanOrEqual=" + UPDATED_CUBIC_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByCubicCapacityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where cubicCapacity is less than or equal to DEFAULT_CUBIC_CAPACITY
        defaultVehicleShouldBeFound("cubicCapacity.lessThanOrEqual=" + DEFAULT_CUBIC_CAPACITY);

        // Get all the vehicleList where cubicCapacity is less than or equal to SMALLER_CUBIC_CAPACITY
        defaultVehicleShouldNotBeFound("cubicCapacity.lessThanOrEqual=" + SMALLER_CUBIC_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByCubicCapacityIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where cubicCapacity is less than DEFAULT_CUBIC_CAPACITY
        defaultVehicleShouldNotBeFound("cubicCapacity.lessThan=" + DEFAULT_CUBIC_CAPACITY);

        // Get all the vehicleList where cubicCapacity is less than UPDATED_CUBIC_CAPACITY
        defaultVehicleShouldBeFound("cubicCapacity.lessThan=" + UPDATED_CUBIC_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByCubicCapacityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where cubicCapacity is greater than DEFAULT_CUBIC_CAPACITY
        defaultVehicleShouldNotBeFound("cubicCapacity.greaterThan=" + DEFAULT_CUBIC_CAPACITY);

        // Get all the vehicleList where cubicCapacity is greater than SMALLER_CUBIC_CAPACITY
        defaultVehicleShouldBeFound("cubicCapacity.greaterThan=" + SMALLER_CUBIC_CAPACITY);
    }


    @Test
    @Transactional
    public void getAllVehiclesByNoOfStandeeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where noOfStandee equals to DEFAULT_NO_OF_STANDEE
        defaultVehicleShouldBeFound("noOfStandee.equals=" + DEFAULT_NO_OF_STANDEE);

        // Get all the vehicleList where noOfStandee equals to UPDATED_NO_OF_STANDEE
        defaultVehicleShouldNotBeFound("noOfStandee.equals=" + UPDATED_NO_OF_STANDEE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNoOfStandeeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where noOfStandee not equals to DEFAULT_NO_OF_STANDEE
        defaultVehicleShouldNotBeFound("noOfStandee.notEquals=" + DEFAULT_NO_OF_STANDEE);

        // Get all the vehicleList where noOfStandee not equals to UPDATED_NO_OF_STANDEE
        defaultVehicleShouldBeFound("noOfStandee.notEquals=" + UPDATED_NO_OF_STANDEE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNoOfStandeeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where noOfStandee in DEFAULT_NO_OF_STANDEE or UPDATED_NO_OF_STANDEE
        defaultVehicleShouldBeFound("noOfStandee.in=" + DEFAULT_NO_OF_STANDEE + "," + UPDATED_NO_OF_STANDEE);

        // Get all the vehicleList where noOfStandee equals to UPDATED_NO_OF_STANDEE
        defaultVehicleShouldNotBeFound("noOfStandee.in=" + UPDATED_NO_OF_STANDEE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNoOfStandeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where noOfStandee is not null
        defaultVehicleShouldBeFound("noOfStandee.specified=true");

        // Get all the vehicleList where noOfStandee is null
        defaultVehicleShouldNotBeFound("noOfStandee.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByNoOfStandeeContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where noOfStandee contains DEFAULT_NO_OF_STANDEE
        defaultVehicleShouldBeFound("noOfStandee.contains=" + DEFAULT_NO_OF_STANDEE);

        // Get all the vehicleList where noOfStandee contains UPDATED_NO_OF_STANDEE
        defaultVehicleShouldNotBeFound("noOfStandee.contains=" + UPDATED_NO_OF_STANDEE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByNoOfStandeeNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where noOfStandee does not contain DEFAULT_NO_OF_STANDEE
        defaultVehicleShouldNotBeFound("noOfStandee.doesNotContain=" + DEFAULT_NO_OF_STANDEE);

        // Get all the vehicleList where noOfStandee does not contain UPDATED_NO_OF_STANDEE
        defaultVehicleShouldBeFound("noOfStandee.doesNotContain=" + UPDATED_NO_OF_STANDEE);
    }


    @Test
    @Transactional
    public void getAllVehiclesByUnladenWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where unladenWeight equals to DEFAULT_UNLADEN_WEIGHT
        defaultVehicleShouldBeFound("unladenWeight.equals=" + DEFAULT_UNLADEN_WEIGHT);

        // Get all the vehicleList where unladenWeight equals to UPDATED_UNLADEN_WEIGHT
        defaultVehicleShouldNotBeFound("unladenWeight.equals=" + UPDATED_UNLADEN_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllVehiclesByUnladenWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where unladenWeight not equals to DEFAULT_UNLADEN_WEIGHT
        defaultVehicleShouldNotBeFound("unladenWeight.notEquals=" + DEFAULT_UNLADEN_WEIGHT);

        // Get all the vehicleList where unladenWeight not equals to UPDATED_UNLADEN_WEIGHT
        defaultVehicleShouldBeFound("unladenWeight.notEquals=" + UPDATED_UNLADEN_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllVehiclesByUnladenWeightIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where unladenWeight in DEFAULT_UNLADEN_WEIGHT or UPDATED_UNLADEN_WEIGHT
        defaultVehicleShouldBeFound("unladenWeight.in=" + DEFAULT_UNLADEN_WEIGHT + "," + UPDATED_UNLADEN_WEIGHT);

        // Get all the vehicleList where unladenWeight equals to UPDATED_UNLADEN_WEIGHT
        defaultVehicleShouldNotBeFound("unladenWeight.in=" + UPDATED_UNLADEN_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllVehiclesByUnladenWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where unladenWeight is not null
        defaultVehicleShouldBeFound("unladenWeight.specified=true");

        // Get all the vehicleList where unladenWeight is null
        defaultVehicleShouldNotBeFound("unladenWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByUnladenWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where unladenWeight is greater than or equal to DEFAULT_UNLADEN_WEIGHT
        defaultVehicleShouldBeFound("unladenWeight.greaterThanOrEqual=" + DEFAULT_UNLADEN_WEIGHT);

        // Get all the vehicleList where unladenWeight is greater than or equal to UPDATED_UNLADEN_WEIGHT
        defaultVehicleShouldNotBeFound("unladenWeight.greaterThanOrEqual=" + UPDATED_UNLADEN_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllVehiclesByUnladenWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where unladenWeight is less than or equal to DEFAULT_UNLADEN_WEIGHT
        defaultVehicleShouldBeFound("unladenWeight.lessThanOrEqual=" + DEFAULT_UNLADEN_WEIGHT);

        // Get all the vehicleList where unladenWeight is less than or equal to SMALLER_UNLADEN_WEIGHT
        defaultVehicleShouldNotBeFound("unladenWeight.lessThanOrEqual=" + SMALLER_UNLADEN_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllVehiclesByUnladenWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where unladenWeight is less than DEFAULT_UNLADEN_WEIGHT
        defaultVehicleShouldNotBeFound("unladenWeight.lessThan=" + DEFAULT_UNLADEN_WEIGHT);

        // Get all the vehicleList where unladenWeight is less than UPDATED_UNLADEN_WEIGHT
        defaultVehicleShouldBeFound("unladenWeight.lessThan=" + UPDATED_UNLADEN_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllVehiclesByUnladenWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where unladenWeight is greater than DEFAULT_UNLADEN_WEIGHT
        defaultVehicleShouldNotBeFound("unladenWeight.greaterThan=" + DEFAULT_UNLADEN_WEIGHT);

        // Get all the vehicleList where unladenWeight is greater than SMALLER_UNLADEN_WEIGHT
        defaultVehicleShouldBeFound("unladenWeight.greaterThan=" + SMALLER_UNLADEN_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllVehiclesByPrevRegnNoIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where prevRegnNo equals to DEFAULT_PREV_REGN_NO
        defaultVehicleShouldBeFound("prevRegnNo.equals=" + DEFAULT_PREV_REGN_NO);

        // Get all the vehicleList where prevRegnNo equals to UPDATED_PREV_REGN_NO
        defaultVehicleShouldNotBeFound("prevRegnNo.equals=" + UPDATED_PREV_REGN_NO);
    }

    @Test
    @Transactional
    public void getAllVehiclesByPrevRegnNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where prevRegnNo not equals to DEFAULT_PREV_REGN_NO
        defaultVehicleShouldNotBeFound("prevRegnNo.notEquals=" + DEFAULT_PREV_REGN_NO);

        // Get all the vehicleList where prevRegnNo not equals to UPDATED_PREV_REGN_NO
        defaultVehicleShouldBeFound("prevRegnNo.notEquals=" + UPDATED_PREV_REGN_NO);
    }

    @Test
    @Transactional
    public void getAllVehiclesByPrevRegnNoIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where prevRegnNo in DEFAULT_PREV_REGN_NO or UPDATED_PREV_REGN_NO
        defaultVehicleShouldBeFound("prevRegnNo.in=" + DEFAULT_PREV_REGN_NO + "," + UPDATED_PREV_REGN_NO);

        // Get all the vehicleList where prevRegnNo equals to UPDATED_PREV_REGN_NO
        defaultVehicleShouldNotBeFound("prevRegnNo.in=" + UPDATED_PREV_REGN_NO);
    }

    @Test
    @Transactional
    public void getAllVehiclesByPrevRegnNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where prevRegnNo is not null
        defaultVehicleShouldBeFound("prevRegnNo.specified=true");

        // Get all the vehicleList where prevRegnNo is null
        defaultVehicleShouldNotBeFound("prevRegnNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByPrevRegnNoContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where prevRegnNo contains DEFAULT_PREV_REGN_NO
        defaultVehicleShouldBeFound("prevRegnNo.contains=" + DEFAULT_PREV_REGN_NO);

        // Get all the vehicleList where prevRegnNo contains UPDATED_PREV_REGN_NO
        defaultVehicleShouldNotBeFound("prevRegnNo.contains=" + UPDATED_PREV_REGN_NO);
    }

    @Test
    @Transactional
    public void getAllVehiclesByPrevRegnNoNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where prevRegnNo does not contain DEFAULT_PREV_REGN_NO
        defaultVehicleShouldNotBeFound("prevRegnNo.doesNotContain=" + DEFAULT_PREV_REGN_NO);

        // Get all the vehicleList where prevRegnNo does not contain UPDATED_PREV_REGN_NO
        defaultVehicleShouldBeFound("prevRegnNo.doesNotContain=" + UPDATED_PREV_REGN_NO);
    }


    @Test
    @Transactional
    public void getAllVehiclesByMakersNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersName equals to DEFAULT_MAKERS_NAME
        defaultVehicleShouldBeFound("makersName.equals=" + DEFAULT_MAKERS_NAME);

        // Get all the vehicleList where makersName equals to UPDATED_MAKERS_NAME
        defaultVehicleShouldNotBeFound("makersName.equals=" + UPDATED_MAKERS_NAME);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMakersNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersName not equals to DEFAULT_MAKERS_NAME
        defaultVehicleShouldNotBeFound("makersName.notEquals=" + DEFAULT_MAKERS_NAME);

        // Get all the vehicleList where makersName not equals to UPDATED_MAKERS_NAME
        defaultVehicleShouldBeFound("makersName.notEquals=" + UPDATED_MAKERS_NAME);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMakersNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersName in DEFAULT_MAKERS_NAME or UPDATED_MAKERS_NAME
        defaultVehicleShouldBeFound("makersName.in=" + DEFAULT_MAKERS_NAME + "," + UPDATED_MAKERS_NAME);

        // Get all the vehicleList where makersName equals to UPDATED_MAKERS_NAME
        defaultVehicleShouldNotBeFound("makersName.in=" + UPDATED_MAKERS_NAME);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMakersNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersName is not null
        defaultVehicleShouldBeFound("makersName.specified=true");

        // Get all the vehicleList where makersName is null
        defaultVehicleShouldNotBeFound("makersName.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByMakersNameContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersName contains DEFAULT_MAKERS_NAME
        defaultVehicleShouldBeFound("makersName.contains=" + DEFAULT_MAKERS_NAME);

        // Get all the vehicleList where makersName contains UPDATED_MAKERS_NAME
        defaultVehicleShouldNotBeFound("makersName.contains=" + UPDATED_MAKERS_NAME);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMakersNameNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersName does not contain DEFAULT_MAKERS_NAME
        defaultVehicleShouldNotBeFound("makersName.doesNotContain=" + DEFAULT_MAKERS_NAME);

        // Get all the vehicleList where makersName does not contain UPDATED_MAKERS_NAME
        defaultVehicleShouldBeFound("makersName.doesNotContain=" + UPDATED_MAKERS_NAME);
    }


    @Test
    @Transactional
    public void getAllVehiclesByMakersCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersCountry equals to DEFAULT_MAKERS_COUNTRY
        defaultVehicleShouldBeFound("makersCountry.equals=" + DEFAULT_MAKERS_COUNTRY);

        // Get all the vehicleList where makersCountry equals to UPDATED_MAKERS_COUNTRY
        defaultVehicleShouldNotBeFound("makersCountry.equals=" + UPDATED_MAKERS_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMakersCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersCountry not equals to DEFAULT_MAKERS_COUNTRY
        defaultVehicleShouldNotBeFound("makersCountry.notEquals=" + DEFAULT_MAKERS_COUNTRY);

        // Get all the vehicleList where makersCountry not equals to UPDATED_MAKERS_COUNTRY
        defaultVehicleShouldBeFound("makersCountry.notEquals=" + UPDATED_MAKERS_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMakersCountryIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersCountry in DEFAULT_MAKERS_COUNTRY or UPDATED_MAKERS_COUNTRY
        defaultVehicleShouldBeFound("makersCountry.in=" + DEFAULT_MAKERS_COUNTRY + "," + UPDATED_MAKERS_COUNTRY);

        // Get all the vehicleList where makersCountry equals to UPDATED_MAKERS_COUNTRY
        defaultVehicleShouldNotBeFound("makersCountry.in=" + UPDATED_MAKERS_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMakersCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersCountry is not null
        defaultVehicleShouldBeFound("makersCountry.specified=true");

        // Get all the vehicleList where makersCountry is null
        defaultVehicleShouldNotBeFound("makersCountry.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByMakersCountryContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersCountry contains DEFAULT_MAKERS_COUNTRY
        defaultVehicleShouldBeFound("makersCountry.contains=" + DEFAULT_MAKERS_COUNTRY);

        // Get all the vehicleList where makersCountry contains UPDATED_MAKERS_COUNTRY
        defaultVehicleShouldNotBeFound("makersCountry.contains=" + UPDATED_MAKERS_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMakersCountryNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where makersCountry does not contain DEFAULT_MAKERS_COUNTRY
        defaultVehicleShouldNotBeFound("makersCountry.doesNotContain=" + DEFAULT_MAKERS_COUNTRY);

        // Get all the vehicleList where makersCountry does not contain UPDATED_MAKERS_COUNTRY
        defaultVehicleShouldBeFound("makersCountry.doesNotContain=" + UPDATED_MAKERS_COUNTRY);
    }


    @Test
    @Transactional
    public void getAllVehiclesByYearsOfManufactureIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where yearsOfManufacture equals to DEFAULT_YEARS_OF_MANUFACTURE
        defaultVehicleShouldBeFound("yearsOfManufacture.equals=" + DEFAULT_YEARS_OF_MANUFACTURE);

        // Get all the vehicleList where yearsOfManufacture equals to UPDATED_YEARS_OF_MANUFACTURE
        defaultVehicleShouldNotBeFound("yearsOfManufacture.equals=" + UPDATED_YEARS_OF_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByYearsOfManufactureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where yearsOfManufacture not equals to DEFAULT_YEARS_OF_MANUFACTURE
        defaultVehicleShouldNotBeFound("yearsOfManufacture.notEquals=" + DEFAULT_YEARS_OF_MANUFACTURE);

        // Get all the vehicleList where yearsOfManufacture not equals to UPDATED_YEARS_OF_MANUFACTURE
        defaultVehicleShouldBeFound("yearsOfManufacture.notEquals=" + UPDATED_YEARS_OF_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByYearsOfManufactureIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where yearsOfManufacture in DEFAULT_YEARS_OF_MANUFACTURE or UPDATED_YEARS_OF_MANUFACTURE
        defaultVehicleShouldBeFound("yearsOfManufacture.in=" + DEFAULT_YEARS_OF_MANUFACTURE + "," + UPDATED_YEARS_OF_MANUFACTURE);

        // Get all the vehicleList where yearsOfManufacture equals to UPDATED_YEARS_OF_MANUFACTURE
        defaultVehicleShouldNotBeFound("yearsOfManufacture.in=" + UPDATED_YEARS_OF_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByYearsOfManufactureIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where yearsOfManufacture is not null
        defaultVehicleShouldBeFound("yearsOfManufacture.specified=true");

        // Get all the vehicleList where yearsOfManufacture is null
        defaultVehicleShouldNotBeFound("yearsOfManufacture.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByYearsOfManufactureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where yearsOfManufacture is greater than or equal to DEFAULT_YEARS_OF_MANUFACTURE
        defaultVehicleShouldBeFound("yearsOfManufacture.greaterThanOrEqual=" + DEFAULT_YEARS_OF_MANUFACTURE);

        // Get all the vehicleList where yearsOfManufacture is greater than or equal to UPDATED_YEARS_OF_MANUFACTURE
        defaultVehicleShouldNotBeFound("yearsOfManufacture.greaterThanOrEqual=" + UPDATED_YEARS_OF_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByYearsOfManufactureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where yearsOfManufacture is less than or equal to DEFAULT_YEARS_OF_MANUFACTURE
        defaultVehicleShouldBeFound("yearsOfManufacture.lessThanOrEqual=" + DEFAULT_YEARS_OF_MANUFACTURE);

        // Get all the vehicleList where yearsOfManufacture is less than or equal to SMALLER_YEARS_OF_MANUFACTURE
        defaultVehicleShouldNotBeFound("yearsOfManufacture.lessThanOrEqual=" + SMALLER_YEARS_OF_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByYearsOfManufactureIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where yearsOfManufacture is less than DEFAULT_YEARS_OF_MANUFACTURE
        defaultVehicleShouldNotBeFound("yearsOfManufacture.lessThan=" + DEFAULT_YEARS_OF_MANUFACTURE);

        // Get all the vehicleList where yearsOfManufacture is less than UPDATED_YEARS_OF_MANUFACTURE
        defaultVehicleShouldBeFound("yearsOfManufacture.lessThan=" + UPDATED_YEARS_OF_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByYearsOfManufactureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where yearsOfManufacture is greater than DEFAULT_YEARS_OF_MANUFACTURE
        defaultVehicleShouldNotBeFound("yearsOfManufacture.greaterThan=" + DEFAULT_YEARS_OF_MANUFACTURE);

        // Get all the vehicleList where yearsOfManufacture is greater than SMALLER_YEARS_OF_MANUFACTURE
        defaultVehicleShouldBeFound("yearsOfManufacture.greaterThan=" + SMALLER_YEARS_OF_MANUFACTURE);
    }


    @Test
    @Transactional
    public void getAllVehiclesByChassisNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisNumber equals to DEFAULT_CHASSIS_NUMBER
        defaultVehicleShouldBeFound("chassisNumber.equals=" + DEFAULT_CHASSIS_NUMBER);

        // Get all the vehicleList where chassisNumber equals to UPDATED_CHASSIS_NUMBER
        defaultVehicleShouldNotBeFound("chassisNumber.equals=" + UPDATED_CHASSIS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByChassisNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisNumber not equals to DEFAULT_CHASSIS_NUMBER
        defaultVehicleShouldNotBeFound("chassisNumber.notEquals=" + DEFAULT_CHASSIS_NUMBER);

        // Get all the vehicleList where chassisNumber not equals to UPDATED_CHASSIS_NUMBER
        defaultVehicleShouldBeFound("chassisNumber.notEquals=" + UPDATED_CHASSIS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByChassisNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisNumber in DEFAULT_CHASSIS_NUMBER or UPDATED_CHASSIS_NUMBER
        defaultVehicleShouldBeFound("chassisNumber.in=" + DEFAULT_CHASSIS_NUMBER + "," + UPDATED_CHASSIS_NUMBER);

        // Get all the vehicleList where chassisNumber equals to UPDATED_CHASSIS_NUMBER
        defaultVehicleShouldNotBeFound("chassisNumber.in=" + UPDATED_CHASSIS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByChassisNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisNumber is not null
        defaultVehicleShouldBeFound("chassisNumber.specified=true");

        // Get all the vehicleList where chassisNumber is null
        defaultVehicleShouldNotBeFound("chassisNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehiclesByChassisNumberContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisNumber contains DEFAULT_CHASSIS_NUMBER
        defaultVehicleShouldBeFound("chassisNumber.contains=" + DEFAULT_CHASSIS_NUMBER);

        // Get all the vehicleList where chassisNumber contains UPDATED_CHASSIS_NUMBER
        defaultVehicleShouldNotBeFound("chassisNumber.contains=" + UPDATED_CHASSIS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByChassisNumberNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where chassisNumber does not contain DEFAULT_CHASSIS_NUMBER
        defaultVehicleShouldNotBeFound("chassisNumber.doesNotContain=" + DEFAULT_CHASSIS_NUMBER);

        // Get all the vehicleList where chassisNumber does not contain UPDATED_CHASSIS_NUMBER
        defaultVehicleShouldBeFound("chassisNumber.doesNotContain=" + UPDATED_CHASSIS_NUMBER);
    }


    @Test
    @Transactional
    public void getAllVehiclesByFuelUsedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where fuelUsed equals to DEFAULT_FUEL_USED
        defaultVehicleShouldBeFound("fuelUsed.equals=" + DEFAULT_FUEL_USED);

        // Get all the vehicleList where fuelUsed equals to UPDATED_FUEL_USED
        defaultVehicleShouldNotBeFound("fuelUsed.equals=" + UPDATED_FUEL_USED);
    }

    @Test
    @Transactional
    public void getAllVehiclesByFuelUsedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where fuelUsed not equals to DEFAULT_FUEL_USED
        defaultVehicleShouldNotBeFound("fuelUsed.notEquals=" + DEFAULT_FUEL_USED);

        // Get all the vehicleList where fuelUsed not equals to UPDATED_FUEL_USED
        defaultVehicleShouldBeFound("fuelUsed.notEquals=" + UPDATED_FUEL_USED);
    }

    @Test
    @Transactional
    public void getAllVehiclesByFuelUsedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where fuelUsed in DEFAULT_FUEL_USED or UPDATED_FUEL_USED
        defaultVehicleShouldBeFound("fuelUsed.in=" + DEFAULT_FUEL_USED + "," + UPDATED_FUEL_USED);

        // Get all the vehicleList where fuelUsed equals to UPDATED_FUEL_USED
        defaultVehicleShouldNotBeFound("fuelUsed.in=" + UPDATED_FUEL_USED);
    }

    @Test
    @Transactional
    public void getAllVehiclesByFuelUsedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where fuelUsed is not null
        defaultVehicleShouldBeFound("fuelUsed.specified=true");

        // Get all the vehicleList where fuelUsed is null
        defaultVehicleShouldNotBeFound("fuelUsed.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByRpmIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rpm equals to DEFAULT_RPM
        defaultVehicleShouldBeFound("rpm.equals=" + DEFAULT_RPM);

        // Get all the vehicleList where rpm equals to UPDATED_RPM
        defaultVehicleShouldNotBeFound("rpm.equals=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    public void getAllVehiclesByRpmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rpm not equals to DEFAULT_RPM
        defaultVehicleShouldNotBeFound("rpm.notEquals=" + DEFAULT_RPM);

        // Get all the vehicleList where rpm not equals to UPDATED_RPM
        defaultVehicleShouldBeFound("rpm.notEquals=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    public void getAllVehiclesByRpmIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rpm in DEFAULT_RPM or UPDATED_RPM
        defaultVehicleShouldBeFound("rpm.in=" + DEFAULT_RPM + "," + UPDATED_RPM);

        // Get all the vehicleList where rpm equals to UPDATED_RPM
        defaultVehicleShouldNotBeFound("rpm.in=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    public void getAllVehiclesByRpmIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rpm is not null
        defaultVehicleShouldBeFound("rpm.specified=true");

        // Get all the vehicleList where rpm is null
        defaultVehicleShouldNotBeFound("rpm.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByRpmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rpm is greater than or equal to DEFAULT_RPM
        defaultVehicleShouldBeFound("rpm.greaterThanOrEqual=" + DEFAULT_RPM);

        // Get all the vehicleList where rpm is greater than or equal to UPDATED_RPM
        defaultVehicleShouldNotBeFound("rpm.greaterThanOrEqual=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    public void getAllVehiclesByRpmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rpm is less than or equal to DEFAULT_RPM
        defaultVehicleShouldBeFound("rpm.lessThanOrEqual=" + DEFAULT_RPM);

        // Get all the vehicleList where rpm is less than or equal to SMALLER_RPM
        defaultVehicleShouldNotBeFound("rpm.lessThanOrEqual=" + SMALLER_RPM);
    }

    @Test
    @Transactional
    public void getAllVehiclesByRpmIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rpm is less than DEFAULT_RPM
        defaultVehicleShouldNotBeFound("rpm.lessThan=" + DEFAULT_RPM);

        // Get all the vehicleList where rpm is less than UPDATED_RPM
        defaultVehicleShouldBeFound("rpm.lessThan=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    public void getAllVehiclesByRpmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where rpm is greater than DEFAULT_RPM
        defaultVehicleShouldNotBeFound("rpm.greaterThan=" + DEFAULT_RPM);

        // Get all the vehicleList where rpm is greater than SMALLER_RPM
        defaultVehicleShouldBeFound("rpm.greaterThan=" + SMALLER_RPM);
    }


    @Test
    @Transactional
    public void getAllVehiclesBySeatsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where seats equals to DEFAULT_SEATS
        defaultVehicleShouldBeFound("seats.equals=" + DEFAULT_SEATS);

        // Get all the vehicleList where seats equals to UPDATED_SEATS
        defaultVehicleShouldNotBeFound("seats.equals=" + UPDATED_SEATS);
    }

    @Test
    @Transactional
    public void getAllVehiclesBySeatsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where seats not equals to DEFAULT_SEATS
        defaultVehicleShouldNotBeFound("seats.notEquals=" + DEFAULT_SEATS);

        // Get all the vehicleList where seats not equals to UPDATED_SEATS
        defaultVehicleShouldBeFound("seats.notEquals=" + UPDATED_SEATS);
    }

    @Test
    @Transactional
    public void getAllVehiclesBySeatsIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where seats in DEFAULT_SEATS or UPDATED_SEATS
        defaultVehicleShouldBeFound("seats.in=" + DEFAULT_SEATS + "," + UPDATED_SEATS);

        // Get all the vehicleList where seats equals to UPDATED_SEATS
        defaultVehicleShouldNotBeFound("seats.in=" + UPDATED_SEATS);
    }

    @Test
    @Transactional
    public void getAllVehiclesBySeatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where seats is not null
        defaultVehicleShouldBeFound("seats.specified=true");

        // Get all the vehicleList where seats is null
        defaultVehicleShouldNotBeFound("seats.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesBySeatsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where seats is greater than or equal to DEFAULT_SEATS
        defaultVehicleShouldBeFound("seats.greaterThanOrEqual=" + DEFAULT_SEATS);

        // Get all the vehicleList where seats is greater than or equal to UPDATED_SEATS
        defaultVehicleShouldNotBeFound("seats.greaterThanOrEqual=" + UPDATED_SEATS);
    }

    @Test
    @Transactional
    public void getAllVehiclesBySeatsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where seats is less than or equal to DEFAULT_SEATS
        defaultVehicleShouldBeFound("seats.lessThanOrEqual=" + DEFAULT_SEATS);

        // Get all the vehicleList where seats is less than or equal to SMALLER_SEATS
        defaultVehicleShouldNotBeFound("seats.lessThanOrEqual=" + SMALLER_SEATS);
    }

    @Test
    @Transactional
    public void getAllVehiclesBySeatsIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where seats is less than DEFAULT_SEATS
        defaultVehicleShouldNotBeFound("seats.lessThan=" + DEFAULT_SEATS);

        // Get all the vehicleList where seats is less than UPDATED_SEATS
        defaultVehicleShouldBeFound("seats.lessThan=" + UPDATED_SEATS);
    }

    @Test
    @Transactional
    public void getAllVehiclesBySeatsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where seats is greater than DEFAULT_SEATS
        defaultVehicleShouldNotBeFound("seats.greaterThan=" + DEFAULT_SEATS);

        // Get all the vehicleList where seats is greater than SMALLER_SEATS
        defaultVehicleShouldBeFound("seats.greaterThan=" + SMALLER_SEATS);
    }


    @Test
    @Transactional
    public void getAllVehiclesByWheelBaseIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wheelBase equals to DEFAULT_WHEEL_BASE
        defaultVehicleShouldBeFound("wheelBase.equals=" + DEFAULT_WHEEL_BASE);

        // Get all the vehicleList where wheelBase equals to UPDATED_WHEEL_BASE
        defaultVehicleShouldNotBeFound("wheelBase.equals=" + UPDATED_WHEEL_BASE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByWheelBaseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wheelBase not equals to DEFAULT_WHEEL_BASE
        defaultVehicleShouldNotBeFound("wheelBase.notEquals=" + DEFAULT_WHEEL_BASE);

        // Get all the vehicleList where wheelBase not equals to UPDATED_WHEEL_BASE
        defaultVehicleShouldBeFound("wheelBase.notEquals=" + UPDATED_WHEEL_BASE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByWheelBaseIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wheelBase in DEFAULT_WHEEL_BASE or UPDATED_WHEEL_BASE
        defaultVehicleShouldBeFound("wheelBase.in=" + DEFAULT_WHEEL_BASE + "," + UPDATED_WHEEL_BASE);

        // Get all the vehicleList where wheelBase equals to UPDATED_WHEEL_BASE
        defaultVehicleShouldNotBeFound("wheelBase.in=" + UPDATED_WHEEL_BASE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByWheelBaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wheelBase is not null
        defaultVehicleShouldBeFound("wheelBase.specified=true");

        // Get all the vehicleList where wheelBase is null
        defaultVehicleShouldNotBeFound("wheelBase.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByWheelBaseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wheelBase is greater than or equal to DEFAULT_WHEEL_BASE
        defaultVehicleShouldBeFound("wheelBase.greaterThanOrEqual=" + DEFAULT_WHEEL_BASE);

        // Get all the vehicleList where wheelBase is greater than or equal to UPDATED_WHEEL_BASE
        defaultVehicleShouldNotBeFound("wheelBase.greaterThanOrEqual=" + UPDATED_WHEEL_BASE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByWheelBaseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wheelBase is less than or equal to DEFAULT_WHEEL_BASE
        defaultVehicleShouldBeFound("wheelBase.lessThanOrEqual=" + DEFAULT_WHEEL_BASE);

        // Get all the vehicleList where wheelBase is less than or equal to SMALLER_WHEEL_BASE
        defaultVehicleShouldNotBeFound("wheelBase.lessThanOrEqual=" + SMALLER_WHEEL_BASE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByWheelBaseIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wheelBase is less than DEFAULT_WHEEL_BASE
        defaultVehicleShouldNotBeFound("wheelBase.lessThan=" + DEFAULT_WHEEL_BASE);

        // Get all the vehicleList where wheelBase is less than UPDATED_WHEEL_BASE
        defaultVehicleShouldBeFound("wheelBase.lessThan=" + UPDATED_WHEEL_BASE);
    }

    @Test
    @Transactional
    public void getAllVehiclesByWheelBaseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where wheelBase is greater than DEFAULT_WHEEL_BASE
        defaultVehicleShouldNotBeFound("wheelBase.greaterThan=" + DEFAULT_WHEEL_BASE);

        // Get all the vehicleList where wheelBase is greater than SMALLER_WHEEL_BASE
        defaultVehicleShouldBeFound("wheelBase.greaterThan=" + SMALLER_WHEEL_BASE);
    }


    @Test
    @Transactional
    public void getAllVehiclesByMaxLadenIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where maxLaden equals to DEFAULT_MAX_LADEN
        defaultVehicleShouldBeFound("maxLaden.equals=" + DEFAULT_MAX_LADEN);

        // Get all the vehicleList where maxLaden equals to UPDATED_MAX_LADEN
        defaultVehicleShouldNotBeFound("maxLaden.equals=" + UPDATED_MAX_LADEN);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMaxLadenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where maxLaden not equals to DEFAULT_MAX_LADEN
        defaultVehicleShouldNotBeFound("maxLaden.notEquals=" + DEFAULT_MAX_LADEN);

        // Get all the vehicleList where maxLaden not equals to UPDATED_MAX_LADEN
        defaultVehicleShouldBeFound("maxLaden.notEquals=" + UPDATED_MAX_LADEN);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMaxLadenIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where maxLaden in DEFAULT_MAX_LADEN or UPDATED_MAX_LADEN
        defaultVehicleShouldBeFound("maxLaden.in=" + DEFAULT_MAX_LADEN + "," + UPDATED_MAX_LADEN);

        // Get all the vehicleList where maxLaden equals to UPDATED_MAX_LADEN
        defaultVehicleShouldNotBeFound("maxLaden.in=" + UPDATED_MAX_LADEN);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMaxLadenIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where maxLaden is not null
        defaultVehicleShouldBeFound("maxLaden.specified=true");

        // Get all the vehicleList where maxLaden is null
        defaultVehicleShouldNotBeFound("maxLaden.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByMaxLadenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where maxLaden is greater than or equal to DEFAULT_MAX_LADEN
        defaultVehicleShouldBeFound("maxLaden.greaterThanOrEqual=" + DEFAULT_MAX_LADEN);

        // Get all the vehicleList where maxLaden is greater than or equal to UPDATED_MAX_LADEN
        defaultVehicleShouldNotBeFound("maxLaden.greaterThanOrEqual=" + UPDATED_MAX_LADEN);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMaxLadenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where maxLaden is less than or equal to DEFAULT_MAX_LADEN
        defaultVehicleShouldBeFound("maxLaden.lessThanOrEqual=" + DEFAULT_MAX_LADEN);

        // Get all the vehicleList where maxLaden is less than or equal to SMALLER_MAX_LADEN
        defaultVehicleShouldNotBeFound("maxLaden.lessThanOrEqual=" + SMALLER_MAX_LADEN);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMaxLadenIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where maxLaden is less than DEFAULT_MAX_LADEN
        defaultVehicleShouldNotBeFound("maxLaden.lessThan=" + DEFAULT_MAX_LADEN);

        // Get all the vehicleList where maxLaden is less than UPDATED_MAX_LADEN
        defaultVehicleShouldBeFound("maxLaden.lessThan=" + UPDATED_MAX_LADEN);
    }

    @Test
    @Transactional
    public void getAllVehiclesByMaxLadenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where maxLaden is greater than DEFAULT_MAX_LADEN
        defaultVehicleShouldNotBeFound("maxLaden.greaterThan=" + DEFAULT_MAX_LADEN);

        // Get all the vehicleList where maxLaden is greater than SMALLER_MAX_LADEN
        defaultVehicleShouldBeFound("maxLaden.greaterThan=" + SMALLER_MAX_LADEN);
    }


    @Test
    @Transactional
    public void getAllVehiclesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        vehicle.setCustomer(customer);
        vehicleRepository.saveAndFlush(vehicle);
        Long customerId = customer.getId();

        // Get all the vehicleList where customer equals to customerId
        defaultVehicleShouldBeFound("customerId.equals=" + customerId);

        // Get all the vehicleList where customer equals to customerId + 1
        defaultVehicleShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleShouldBeFound(String filter) throws Exception {
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vehicleId").value(hasItem(DEFAULT_VEHICLE_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].classOfVehicle").value(hasItem(DEFAULT_CLASS_OF_VEHICLE)))
            .andExpect(jsonPath("$.[*].typeOfBody").value(hasItem(DEFAULT_TYPE_OF_BODY)))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR.toString())))
            .andExpect(jsonPath("$.[*].numberOfCylinders").value(hasItem(DEFAULT_NUMBER_OF_CYLINDERS.toString())))
            .andExpect(jsonPath("$.[*].engineNumber").value(hasItem(DEFAULT_ENGINE_NUMBER)))
            .andExpect(jsonPath("$.[*].horsePower").value(hasItem(DEFAULT_HORSE_POWER)))
            .andExpect(jsonPath("$.[*].cubicCapacity").value(hasItem(DEFAULT_CUBIC_CAPACITY)))
            .andExpect(jsonPath("$.[*].noOfStandee").value(hasItem(DEFAULT_NO_OF_STANDEE)))
            .andExpect(jsonPath("$.[*].unladenWeight").value(hasItem(DEFAULT_UNLADEN_WEIGHT)))
            .andExpect(jsonPath("$.[*].prevRegnNo").value(hasItem(DEFAULT_PREV_REGN_NO)))
            .andExpect(jsonPath("$.[*].makersName").value(hasItem(DEFAULT_MAKERS_NAME)))
            .andExpect(jsonPath("$.[*].makersCountry").value(hasItem(DEFAULT_MAKERS_COUNTRY)))
            .andExpect(jsonPath("$.[*].yearsOfManufacture").value(hasItem(DEFAULT_YEARS_OF_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].chassisNumber").value(hasItem(DEFAULT_CHASSIS_NUMBER)))
            .andExpect(jsonPath("$.[*].fuelUsed").value(hasItem(DEFAULT_FUEL_USED.toString())))
            .andExpect(jsonPath("$.[*].rpm").value(hasItem(DEFAULT_RPM)))
            .andExpect(jsonPath("$.[*].seats").value(hasItem(DEFAULT_SEATS)))
            .andExpect(jsonPath("$.[*].wheelBase").value(hasItem(DEFAULT_WHEEL_BASE)))
            .andExpect(jsonPath("$.[*].maxLaden").value(hasItem(DEFAULT_MAX_LADEN)));

        // Check, that the count call also returns 1
        restVehicleMockMvc.perform(get("/api/vehicles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleShouldNotBeFound(String filter) throws Exception {
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleMockMvc.perform(get("/api/vehicles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicle() throws Exception {
        // Initialize the database
        vehicleService.save(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).get();
        // Disconnect from session so that the updates on updatedVehicle are not directly saved in db
        em.detach(updatedVehicle);
        updatedVehicle
            .name(UPDATED_NAME)
            .vehicleId(UPDATED_VEHICLE_ID)
            .type(UPDATED_TYPE)
            .classOfVehicle(UPDATED_CLASS_OF_VEHICLE)
            .typeOfBody(UPDATED_TYPE_OF_BODY)
            .colour(UPDATED_COLOUR)
            .numberOfCylinders(UPDATED_NUMBER_OF_CYLINDERS)
            .engineNumber(UPDATED_ENGINE_NUMBER)
            .horsePower(UPDATED_HORSE_POWER)
            .cubicCapacity(UPDATED_CUBIC_CAPACITY)
            .noOfStandee(UPDATED_NO_OF_STANDEE)
            .unladenWeight(UPDATED_UNLADEN_WEIGHT)
            .prevRegnNo(UPDATED_PREV_REGN_NO)
            .makersName(UPDATED_MAKERS_NAME)
            .makersCountry(UPDATED_MAKERS_COUNTRY)
            .yearsOfManufacture(UPDATED_YEARS_OF_MANUFACTURE)
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .fuelUsed(UPDATED_FUEL_USED)
            .rpm(UPDATED_RPM)
            .seats(UPDATED_SEATS)
            .wheelBase(UPDATED_WHEEL_BASE)
            .maxLaden(UPDATED_MAX_LADEN);

        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicle)))
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicle.getVehicleId()).isEqualTo(UPDATED_VEHICLE_ID);
        assertThat(testVehicle.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVehicle.getClassOfVehicle()).isEqualTo(UPDATED_CLASS_OF_VEHICLE);
        assertThat(testVehicle.getTypeOfBody()).isEqualTo(UPDATED_TYPE_OF_BODY);
        assertThat(testVehicle.getColour()).isEqualTo(UPDATED_COLOUR);
        assertThat(testVehicle.getNumberOfCylinders()).isEqualTo(UPDATED_NUMBER_OF_CYLINDERS);
        assertThat(testVehicle.getEngineNumber()).isEqualTo(UPDATED_ENGINE_NUMBER);
        assertThat(testVehicle.getHorsePower()).isEqualTo(UPDATED_HORSE_POWER);
        assertThat(testVehicle.getCubicCapacity()).isEqualTo(UPDATED_CUBIC_CAPACITY);
        assertThat(testVehicle.getNoOfStandee()).isEqualTo(UPDATED_NO_OF_STANDEE);
        assertThat(testVehicle.getUnladenWeight()).isEqualTo(UPDATED_UNLADEN_WEIGHT);
        assertThat(testVehicle.getPrevRegnNo()).isEqualTo(UPDATED_PREV_REGN_NO);
        assertThat(testVehicle.getMakersName()).isEqualTo(UPDATED_MAKERS_NAME);
        assertThat(testVehicle.getMakersCountry()).isEqualTo(UPDATED_MAKERS_COUNTRY);
        assertThat(testVehicle.getYearsOfManufacture()).isEqualTo(UPDATED_YEARS_OF_MANUFACTURE);
        assertThat(testVehicle.getChassisNumber()).isEqualTo(UPDATED_CHASSIS_NUMBER);
        assertThat(testVehicle.getFuelUsed()).isEqualTo(UPDATED_FUEL_USED);
        assertThat(testVehicle.getRpm()).isEqualTo(UPDATED_RPM);
        assertThat(testVehicle.getSeats()).isEqualTo(UPDATED_SEATS);
        assertThat(testVehicle.getWheelBase()).isEqualTo(UPDATED_WHEEL_BASE);
        assertThat(testVehicle.getMaxLaden()).isEqualTo(UPDATED_MAX_LADEN);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleService.save(vehicle);

        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Delete the vehicle
        restVehicleMockMvc.perform(delete("/api/vehicles/{id}", vehicle.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
