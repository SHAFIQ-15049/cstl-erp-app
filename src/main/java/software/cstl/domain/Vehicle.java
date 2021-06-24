package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import software.cstl.domain.enumeration.VehicleType;

import software.cstl.domain.enumeration.ColourType;

import software.cstl.domain.enumeration.CylinderNumber;

import software.cstl.domain.enumeration.FuelType;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vehicle_id")
    private String vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private VehicleType type;

    @Column(name = "class_of_vehicle")
    private String classOfVehicle;

    @Column(name = "type_of_body")
    private String typeOfBody;

    @Enumerated(EnumType.STRING)
    @Column(name = "colour")
    private ColourType colour;

    @Enumerated(EnumType.STRING)
    @Column(name = "number_of_cylinders")
    private CylinderNumber numberOfCylinders;

    @Column(name = "engine_number")
    private String engineNumber;

    @Column(name = "horse_power")
    private Integer horsePower;

    @Column(name = "cubic_capacity")
    private Integer cubicCapacity;

    @Column(name = "no_of_standee")
    private String noOfStandee;

    @Column(name = "unladen_weight")
    private Integer unladenWeight;

    @Column(name = "prev_regn_no")
    private String prevRegnNo;

    @Column(name = "makers_name")
    private String makersName;

    @Column(name = "makers_country")
    private String makersCountry;

    @Column(name = "years_of_manufacture")
    private Integer yearsOfManufacture;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_used")
    private FuelType fuelUsed;

    @Column(name = "rpm")
    private Integer rpm;

    @Column(name = "seats")
    private Integer seats;

    @Column(name = "wheel_base")
    private Integer wheelBase;

    @Column(name = "max_laden")
    private Integer maxLaden;

    @OneToOne
    @JoinColumn(unique = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Vehicle name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public Vehicle vehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleType getType() {
        return type;
    }

    public Vehicle type(VehicleType type) {
        this.type = type;
        return this;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getClassOfVehicle() {
        return classOfVehicle;
    }

    public Vehicle classOfVehicle(String classOfVehicle) {
        this.classOfVehicle = classOfVehicle;
        return this;
    }

    public void setClassOfVehicle(String classOfVehicle) {
        this.classOfVehicle = classOfVehicle;
    }

    public String getTypeOfBody() {
        return typeOfBody;
    }

    public Vehicle typeOfBody(String typeOfBody) {
        this.typeOfBody = typeOfBody;
        return this;
    }

    public void setTypeOfBody(String typeOfBody) {
        this.typeOfBody = typeOfBody;
    }

    public ColourType getColour() {
        return colour;
    }

    public Vehicle colour(ColourType colour) {
        this.colour = colour;
        return this;
    }

    public void setColour(ColourType colour) {
        this.colour = colour;
    }

    public CylinderNumber getNumberOfCylinders() {
        return numberOfCylinders;
    }

    public Vehicle numberOfCylinders(CylinderNumber numberOfCylinders) {
        this.numberOfCylinders = numberOfCylinders;
        return this;
    }

    public void setNumberOfCylinders(CylinderNumber numberOfCylinders) {
        this.numberOfCylinders = numberOfCylinders;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public Vehicle engineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
        return this;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public Vehicle horsePower(Integer horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
    }

    public Integer getCubicCapacity() {
        return cubicCapacity;
    }

    public Vehicle cubicCapacity(Integer cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
        return this;
    }

    public void setCubicCapacity(Integer cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public String getNoOfStandee() {
        return noOfStandee;
    }

    public Vehicle noOfStandee(String noOfStandee) {
        this.noOfStandee = noOfStandee;
        return this;
    }

    public void setNoOfStandee(String noOfStandee) {
        this.noOfStandee = noOfStandee;
    }

    public Integer getUnladenWeight() {
        return unladenWeight;
    }

    public Vehicle unladenWeight(Integer unladenWeight) {
        this.unladenWeight = unladenWeight;
        return this;
    }

    public void setUnladenWeight(Integer unladenWeight) {
        this.unladenWeight = unladenWeight;
    }

    public String getPrevRegnNo() {
        return prevRegnNo;
    }

    public Vehicle prevRegnNo(String prevRegnNo) {
        this.prevRegnNo = prevRegnNo;
        return this;
    }

    public void setPrevRegnNo(String prevRegnNo) {
        this.prevRegnNo = prevRegnNo;
    }

    public String getMakersName() {
        return makersName;
    }

    public Vehicle makersName(String makersName) {
        this.makersName = makersName;
        return this;
    }

    public void setMakersName(String makersName) {
        this.makersName = makersName;
    }

    public String getMakersCountry() {
        return makersCountry;
    }

    public Vehicle makersCountry(String makersCountry) {
        this.makersCountry = makersCountry;
        return this;
    }

    public void setMakersCountry(String makersCountry) {
        this.makersCountry = makersCountry;
    }

    public Integer getYearsOfManufacture() {
        return yearsOfManufacture;
    }

    public Vehicle yearsOfManufacture(Integer yearsOfManufacture) {
        this.yearsOfManufacture = yearsOfManufacture;
        return this;
    }

    public void setYearsOfManufacture(Integer yearsOfManufacture) {
        this.yearsOfManufacture = yearsOfManufacture;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public Vehicle chassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
        return this;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public FuelType getFuelUsed() {
        return fuelUsed;
    }

    public Vehicle fuelUsed(FuelType fuelUsed) {
        this.fuelUsed = fuelUsed;
        return this;
    }

    public void setFuelUsed(FuelType fuelUsed) {
        this.fuelUsed = fuelUsed;
    }

    public Integer getRpm() {
        return rpm;
    }

    public Vehicle rpm(Integer rpm) {
        this.rpm = rpm;
        return this;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    public Integer getSeats() {
        return seats;
    }

    public Vehicle seats(Integer seats) {
        this.seats = seats;
        return this;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Integer getWheelBase() {
        return wheelBase;
    }

    public Vehicle wheelBase(Integer wheelBase) {
        this.wheelBase = wheelBase;
        return this;
    }

    public void setWheelBase(Integer wheelBase) {
        this.wheelBase = wheelBase;
    }

    public Integer getMaxLaden() {
        return maxLaden;
    }

    public Vehicle maxLaden(Integer maxLaden) {
        this.maxLaden = maxLaden;
        return this;
    }

    public void setMaxLaden(Integer maxLaden) {
        this.maxLaden = maxLaden;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vehicleId='" + getVehicleId() + "'" +
            ", type='" + getType() + "'" +
            ", classOfVehicle='" + getClassOfVehicle() + "'" +
            ", typeOfBody='" + getTypeOfBody() + "'" +
            ", colour='" + getColour() + "'" +
            ", numberOfCylinders='" + getNumberOfCylinders() + "'" +
            ", engineNumber='" + getEngineNumber() + "'" +
            ", horsePower=" + getHorsePower() +
            ", cubicCapacity=" + getCubicCapacity() +
            ", noOfStandee='" + getNoOfStandee() + "'" +
            ", unladenWeight=" + getUnladenWeight() +
            ", prevRegnNo='" + getPrevRegnNo() + "'" +
            ", makersName='" + getMakersName() + "'" +
            ", makersCountry='" + getMakersCountry() + "'" +
            ", yearsOfManufacture=" + getYearsOfManufacture() +
            ", chassisNumber='" + getChassisNumber() + "'" +
            ", fuelUsed='" + getFuelUsed() + "'" +
            ", rpm=" + getRpm() +
            ", seats=" + getSeats() +
            ", wheelBase=" + getWheelBase() +
            ", maxLaden=" + getMaxLaden() +
            "}";
    }
}
