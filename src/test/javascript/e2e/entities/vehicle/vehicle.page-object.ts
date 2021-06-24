import { element, by, ElementFinder } from 'protractor';

export class VehicleComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-vehicle div table .btn-danger'));
  title = element.all(by.css('jhi-vehicle div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class VehicleUpdatePage {
  pageTitle = element(by.id('jhi-vehicle-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  vehicleIdInput = element(by.id('field_vehicleId'));
  typeSelect = element(by.id('field_type'));
  classOfVehicleInput = element(by.id('field_classOfVehicle'));
  typeOfBodyInput = element(by.id('field_typeOfBody'));
  colourSelect = element(by.id('field_colour'));
  numberOfCylindersSelect = element(by.id('field_numberOfCylinders'));
  engineNumberInput = element(by.id('field_engineNumber'));
  horsePowerInput = element(by.id('field_horsePower'));
  cubicCapacityInput = element(by.id('field_cubicCapacity'));
  noOfStandeeInput = element(by.id('field_noOfStandee'));
  unladenWeightInput = element(by.id('field_unladenWeight'));
  prevRegnNoInput = element(by.id('field_prevRegnNo'));
  makersNameInput = element(by.id('field_makersName'));
  makersCountryInput = element(by.id('field_makersCountry'));
  yearsOfManufactureInput = element(by.id('field_yearsOfManufacture'));
  chassisNumberInput = element(by.id('field_chassisNumber'));
  fuelUsedSelect = element(by.id('field_fuelUsed'));
  rpmInput = element(by.id('field_rpm'));
  seatsInput = element(by.id('field_seats'));
  wheelBaseInput = element(by.id('field_wheelBase'));
  maxLadenInput = element(by.id('field_maxLaden'));

  customerSelect = element(by.id('field_customer'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setVehicleIdInput(vehicleId: string): Promise<void> {
    await this.vehicleIdInput.sendKeys(vehicleId);
  }

  async getVehicleIdInput(): Promise<string> {
    return await this.vehicleIdInput.getAttribute('value');
  }

  async setTypeSelect(type: string): Promise<void> {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }

  async setClassOfVehicleInput(classOfVehicle: string): Promise<void> {
    await this.classOfVehicleInput.sendKeys(classOfVehicle);
  }

  async getClassOfVehicleInput(): Promise<string> {
    return await this.classOfVehicleInput.getAttribute('value');
  }

  async setTypeOfBodyInput(typeOfBody: string): Promise<void> {
    await this.typeOfBodyInput.sendKeys(typeOfBody);
  }

  async getTypeOfBodyInput(): Promise<string> {
    return await this.typeOfBodyInput.getAttribute('value');
  }

  async setColourSelect(colour: string): Promise<void> {
    await this.colourSelect.sendKeys(colour);
  }

  async getColourSelect(): Promise<string> {
    return await this.colourSelect.element(by.css('option:checked')).getText();
  }

  async colourSelectLastOption(): Promise<void> {
    await this.colourSelect.all(by.tagName('option')).last().click();
  }

  async setNumberOfCylindersSelect(numberOfCylinders: string): Promise<void> {
    await this.numberOfCylindersSelect.sendKeys(numberOfCylinders);
  }

  async getNumberOfCylindersSelect(): Promise<string> {
    return await this.numberOfCylindersSelect.element(by.css('option:checked')).getText();
  }

  async numberOfCylindersSelectLastOption(): Promise<void> {
    await this.numberOfCylindersSelect.all(by.tagName('option')).last().click();
  }

  async setEngineNumberInput(engineNumber: string): Promise<void> {
    await this.engineNumberInput.sendKeys(engineNumber);
  }

  async getEngineNumberInput(): Promise<string> {
    return await this.engineNumberInput.getAttribute('value');
  }

  async setHorsePowerInput(horsePower: string): Promise<void> {
    await this.horsePowerInput.sendKeys(horsePower);
  }

  async getHorsePowerInput(): Promise<string> {
    return await this.horsePowerInput.getAttribute('value');
  }

  async setCubicCapacityInput(cubicCapacity: string): Promise<void> {
    await this.cubicCapacityInput.sendKeys(cubicCapacity);
  }

  async getCubicCapacityInput(): Promise<string> {
    return await this.cubicCapacityInput.getAttribute('value');
  }

  async setNoOfStandeeInput(noOfStandee: string): Promise<void> {
    await this.noOfStandeeInput.sendKeys(noOfStandee);
  }

  async getNoOfStandeeInput(): Promise<string> {
    return await this.noOfStandeeInput.getAttribute('value');
  }

  async setUnladenWeightInput(unladenWeight: string): Promise<void> {
    await this.unladenWeightInput.sendKeys(unladenWeight);
  }

  async getUnladenWeightInput(): Promise<string> {
    return await this.unladenWeightInput.getAttribute('value');
  }

  async setPrevRegnNoInput(prevRegnNo: string): Promise<void> {
    await this.prevRegnNoInput.sendKeys(prevRegnNo);
  }

  async getPrevRegnNoInput(): Promise<string> {
    return await this.prevRegnNoInput.getAttribute('value');
  }

  async setMakersNameInput(makersName: string): Promise<void> {
    await this.makersNameInput.sendKeys(makersName);
  }

  async getMakersNameInput(): Promise<string> {
    return await this.makersNameInput.getAttribute('value');
  }

  async setMakersCountryInput(makersCountry: string): Promise<void> {
    await this.makersCountryInput.sendKeys(makersCountry);
  }

  async getMakersCountryInput(): Promise<string> {
    return await this.makersCountryInput.getAttribute('value');
  }

  async setYearsOfManufactureInput(yearsOfManufacture: string): Promise<void> {
    await this.yearsOfManufactureInput.sendKeys(yearsOfManufacture);
  }

  async getYearsOfManufactureInput(): Promise<string> {
    return await this.yearsOfManufactureInput.getAttribute('value');
  }

  async setChassisNumberInput(chassisNumber: string): Promise<void> {
    await this.chassisNumberInput.sendKeys(chassisNumber);
  }

  async getChassisNumberInput(): Promise<string> {
    return await this.chassisNumberInput.getAttribute('value');
  }

  async setFuelUsedSelect(fuelUsed: string): Promise<void> {
    await this.fuelUsedSelect.sendKeys(fuelUsed);
  }

  async getFuelUsedSelect(): Promise<string> {
    return await this.fuelUsedSelect.element(by.css('option:checked')).getText();
  }

  async fuelUsedSelectLastOption(): Promise<void> {
    await this.fuelUsedSelect.all(by.tagName('option')).last().click();
  }

  async setRpmInput(rpm: string): Promise<void> {
    await this.rpmInput.sendKeys(rpm);
  }

  async getRpmInput(): Promise<string> {
    return await this.rpmInput.getAttribute('value');
  }

  async setSeatsInput(seats: string): Promise<void> {
    await this.seatsInput.sendKeys(seats);
  }

  async getSeatsInput(): Promise<string> {
    return await this.seatsInput.getAttribute('value');
  }

  async setWheelBaseInput(wheelBase: string): Promise<void> {
    await this.wheelBaseInput.sendKeys(wheelBase);
  }

  async getWheelBaseInput(): Promise<string> {
    return await this.wheelBaseInput.getAttribute('value');
  }

  async setMaxLadenInput(maxLaden: string): Promise<void> {
    await this.maxLadenInput.sendKeys(maxLaden);
  }

  async getMaxLadenInput(): Promise<string> {
    return await this.maxLadenInput.getAttribute('value');
  }

  async customerSelectLastOption(): Promise<void> {
    await this.customerSelect.all(by.tagName('option')).last().click();
  }

  async customerSelectOption(option: string): Promise<void> {
    await this.customerSelect.sendKeys(option);
  }

  getCustomerSelect(): ElementFinder {
    return this.customerSelect;
  }

  async getCustomerSelectedOption(): Promise<string> {
    return await this.customerSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class VehicleDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-vehicle-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-vehicle'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
