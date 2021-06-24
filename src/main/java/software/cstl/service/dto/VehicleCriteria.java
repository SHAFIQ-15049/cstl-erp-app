package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.VehicleType;
import software.cstl.domain.enumeration.ColourType;
import software.cstl.domain.enumeration.CylinderNumber;
import software.cstl.domain.enumeration.FuelType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link software.cstl.domain.Vehicle} entity. This class is used
 * in {@link software.cstl.web.rest.VehicleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleCriteria implements Serializable, Criteria {
    /**
     * Class for filtering VehicleType
     */
    public static class VehicleTypeFilter extends Filter<VehicleType> {

        public VehicleTypeFilter() {
        }

        public VehicleTypeFilter(VehicleTypeFilter filter) {
            super(filter);
        }

        @Override
        public VehicleTypeFilter copy() {
            return new VehicleTypeFilter(this);
        }

    }
    /**
     * Class for filtering ColourType
     */
    public static class ColourTypeFilter extends Filter<ColourType> {

        public ColourTypeFilter() {
        }

        public ColourTypeFilter(ColourTypeFilter filter) {
            super(filter);
        }

        @Override
        public ColourTypeFilter copy() {
            return new ColourTypeFilter(this);
        }

    }
    /**
     * Class for filtering CylinderNumber
     */
    public static class CylinderNumberFilter extends Filter<CylinderNumber> {

        public CylinderNumberFilter() {
        }

        public CylinderNumberFilter(CylinderNumberFilter filter) {
            super(filter);
        }

        @Override
        public CylinderNumberFilter copy() {
            return new CylinderNumberFilter(this);
        }

    }
    /**
     * Class for filtering FuelType
     */
    public static class FuelTypeFilter extends Filter<FuelType> {

        public FuelTypeFilter() {
        }

        public FuelTypeFilter(FuelTypeFilter filter) {
            super(filter);
        }

        @Override
        public FuelTypeFilter copy() {
            return new FuelTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter vehicleId;

    private VehicleTypeFilter type;

    private StringFilter classOfVehicle;

    private StringFilter typeOfBody;

    private ColourTypeFilter colour;

    private CylinderNumberFilter numberOfCylinders;

    private StringFilter engineNumber;

    private IntegerFilter horsePower;

    private IntegerFilter cubicCapacity;

    private StringFilter noOfStandee;

    private IntegerFilter unladenWeight;

    private StringFilter prevRegnNo;

    private StringFilter makersName;

    private StringFilter makersCountry;

    private IntegerFilter yearsOfManufacture;

    private StringFilter chassisNumber;

    private FuelTypeFilter fuelUsed;

    private IntegerFilter rpm;

    private IntegerFilter seats;

    private IntegerFilter wheelBase;

    private IntegerFilter maxLaden;



    public VehicleCriteria() {
    }

    public VehicleCriteria(VehicleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.vehicleId = other.vehicleId == null ? null : other.vehicleId.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.classOfVehicle = other.classOfVehicle == null ? null : other.classOfVehicle.copy();
        this.typeOfBody = other.typeOfBody == null ? null : other.typeOfBody.copy();
        this.colour = other.colour == null ? null : other.colour.copy();
        this.numberOfCylinders = other.numberOfCylinders == null ? null : other.numberOfCylinders.copy();
        this.engineNumber = other.engineNumber == null ? null : other.engineNumber.copy();
        this.horsePower = other.horsePower == null ? null : other.horsePower.copy();
        this.cubicCapacity = other.cubicCapacity == null ? null : other.cubicCapacity.copy();
        this.noOfStandee = other.noOfStandee == null ? null : other.noOfStandee.copy();
        this.unladenWeight = other.unladenWeight == null ? null : other.unladenWeight.copy();
        this.prevRegnNo = other.prevRegnNo == null ? null : other.prevRegnNo.copy();
        this.makersName = other.makersName == null ? null : other.makersName.copy();
        this.makersCountry = other.makersCountry == null ? null : other.makersCountry.copy();
        this.yearsOfManufacture = other.yearsOfManufacture == null ? null : other.yearsOfManufacture.copy();
        this.chassisNumber = other.chassisNumber == null ? null : other.chassisNumber.copy();
        this.fuelUsed = other.fuelUsed == null ? null : other.fuelUsed.copy();
        this.rpm = other.rpm == null ? null : other.rpm.copy();
        this.seats = other.seats == null ? null : other.seats.copy();
        this.wheelBase = other.wheelBase == null ? null : other.wheelBase.copy();
        this.maxLaden = other.maxLaden == null ? null : other.maxLaden.copy();

    }

    @Override
    public VehicleCriteria copy() {
        return new VehicleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(StringFilter vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleTypeFilter getType() {
        return type;
    }

    public void setType(VehicleTypeFilter type) {
        this.type = type;
    }

    public StringFilter getClassOfVehicle() {
        return classOfVehicle;
    }

    public void setClassOfVehicle(StringFilter classOfVehicle) {
        this.classOfVehicle = classOfVehicle;
    }

    public StringFilter getTypeOfBody() {
        return typeOfBody;
    }

    public void setTypeOfBody(StringFilter typeOfBody) {
        this.typeOfBody = typeOfBody;
    }

    public ColourTypeFilter getColour() {
        return colour;
    }

    public void setColour(ColourTypeFilter colour) {
        this.colour = colour;
    }

    public CylinderNumberFilter getNumberOfCylinders() {
        return numberOfCylinders;
    }

    public void setNumberOfCylinders(CylinderNumberFilter numberOfCylinders) {
        this.numberOfCylinders = numberOfCylinders;
    }

    public StringFilter getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(StringFilter engineNumber) {
        this.engineNumber = engineNumber;
    }

    public IntegerFilter getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(IntegerFilter horsePower) {
        this.horsePower = horsePower;
    }

    public IntegerFilter getCubicCapacity() {
        return cubicCapacity;
    }

    public void setCubicCapacity(IntegerFilter cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public StringFilter getNoOfStandee() {
        return noOfStandee;
    }

    public void setNoOfStandee(StringFilter noOfStandee) {
        this.noOfStandee = noOfStandee;
    }

    public IntegerFilter getUnladenWeight() {
        return unladenWeight;
    }

    public void setUnladenWeight(IntegerFilter unladenWeight) {
        this.unladenWeight = unladenWeight;
    }

    public StringFilter getPrevRegnNo() {
        return prevRegnNo;
    }

    public void setPrevRegnNo(StringFilter prevRegnNo) {
        this.prevRegnNo = prevRegnNo;
    }

    public StringFilter getMakersName() {
        return makersName;
    }

    public void setMakersName(StringFilter makersName) {
        this.makersName = makersName;
    }

    public StringFilter getMakersCountry() {
        return makersCountry;
    }

    public void setMakersCountry(StringFilter makersCountry) {
        this.makersCountry = makersCountry;
    }

    public IntegerFilter getYearsOfManufacture() {
        return yearsOfManufacture;
    }

    public void setYearsOfManufacture(IntegerFilter yearsOfManufacture) {
        this.yearsOfManufacture = yearsOfManufacture;
    }

    public StringFilter getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(StringFilter chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public FuelTypeFilter getFuelUsed() {
        return fuelUsed;
    }

    public void setFuelUsed(FuelTypeFilter fuelUsed) {
        this.fuelUsed = fuelUsed;
    }

    public IntegerFilter getRpm() {
        return rpm;
    }

    public void setRpm(IntegerFilter rpm) {
        this.rpm = rpm;
    }

    public IntegerFilter getSeats() {
        return seats;
    }

    public void setSeats(IntegerFilter seats) {
        this.seats = seats;
    }

    public IntegerFilter getWheelBase() {
        return wheelBase;
    }

    public void setWheelBase(IntegerFilter wheelBase) {
        this.wheelBase = wheelBase;
    }

    public IntegerFilter getMaxLaden() {
        return maxLaden;
    }

    public void setMaxLaden(IntegerFilter maxLaden) {
        this.maxLaden = maxLaden;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleCriteria that = (VehicleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(vehicleId, that.vehicleId) &&
            Objects.equals(type, that.type) &&
            Objects.equals(classOfVehicle, that.classOfVehicle) &&
            Objects.equals(typeOfBody, that.typeOfBody) &&
            Objects.equals(colour, that.colour) &&
            Objects.equals(numberOfCylinders, that.numberOfCylinders) &&
            Objects.equals(engineNumber, that.engineNumber) &&
            Objects.equals(horsePower, that.horsePower) &&
            Objects.equals(cubicCapacity, that.cubicCapacity) &&
            Objects.equals(noOfStandee, that.noOfStandee) &&
            Objects.equals(unladenWeight, that.unladenWeight) &&
            Objects.equals(prevRegnNo, that.prevRegnNo) &&
            Objects.equals(makersName, that.makersName) &&
            Objects.equals(makersCountry, that.makersCountry) &&
            Objects.equals(yearsOfManufacture, that.yearsOfManufacture) &&
            Objects.equals(chassisNumber, that.chassisNumber) &&
            Objects.equals(fuelUsed, that.fuelUsed) &&
            Objects.equals(rpm, that.rpm) &&
            Objects.equals(seats, that.seats) &&
            Objects.equals(wheelBase, that.wheelBase) &&
            Objects.equals(maxLaden, that.maxLaden) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        vehicleId,
        type,
        classOfVehicle,
        typeOfBody,
        colour,
        numberOfCylinders,
        engineNumber,
        horsePower,
        cubicCapacity,
        noOfStandee,
        unladenWeight,
        prevRegnNo,
        makersName,
        makersCountry,
        yearsOfManufacture,
        chassisNumber,
        fuelUsed,
        rpm,
        seats,
        wheelBase,
        maxLaden
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (vehicleId != null ? "vehicleId=" + vehicleId + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (classOfVehicle != null ? "classOfVehicle=" + classOfVehicle + ", " : "") +
                (typeOfBody != null ? "typeOfBody=" + typeOfBody + ", " : "") +
                (colour != null ? "colour=" + colour + ", " : "") +
                (numberOfCylinders != null ? "numberOfCylinders=" + numberOfCylinders + ", " : "") +
                (engineNumber != null ? "engineNumber=" + engineNumber + ", " : "") +
                (horsePower != null ? "horsePower=" + horsePower + ", " : "") +
                (cubicCapacity != null ? "cubicCapacity=" + cubicCapacity + ", " : "") +
                (noOfStandee != null ? "noOfStandee=" + noOfStandee + ", " : "") +
                (unladenWeight != null ? "unladenWeight=" + unladenWeight + ", " : "") +
                (prevRegnNo != null ? "prevRegnNo=" + prevRegnNo + ", " : "") +
                (makersName != null ? "makersName=" + makersName + ", " : "") +
                (makersCountry != null ? "makersCountry=" + makersCountry + ", " : "") +
                (yearsOfManufacture != null ? "yearsOfManufacture=" + yearsOfManufacture + ", " : "") +
                (chassisNumber != null ? "chassisNumber=" + chassisNumber + ", " : "") +
                (fuelUsed != null ? "fuelUsed=" + fuelUsed + ", " : "") +
                (rpm != null ? "rpm=" + rpm + ", " : "") +
                (seats != null ? "seats=" + seats + ", " : "") +
                (wheelBase != null ? "wheelBase=" + wheelBase + ", " : "") +
                (maxLaden != null ? "maxLaden=" + maxLaden + ", " : "") +

            "}";
    }

}
