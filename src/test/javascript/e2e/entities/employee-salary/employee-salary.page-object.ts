import { element, by, ElementFinder } from 'protractor';

export class EmployeeSalaryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employee-salary div table .btn-danger'));
  title = element.all(by.css('jhi-employee-salary div h2#page-heading span')).first();
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

export class EmployeeSalaryUpdatePage {
  pageTitle = element(by.id('jhi-employee-salary-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  grossInput = element(by.id('field_gross'));
  incrementAmountInput = element(by.id('field_incrementAmount'));
  incrementPercentageInput = element(by.id('field_incrementPercentage'));
  salaryStartDateInput = element(by.id('field_salaryStartDate'));
  salaryEndDateInput = element(by.id('field_salaryEndDate'));
  nextIncrementDateInput = element(by.id('field_nextIncrementDate'));
  basicInput = element(by.id('field_basic'));
  basicPercentInput = element(by.id('field_basicPercent'));
  houseRentInput = element(by.id('field_houseRent'));
  houseRentPercentInput = element(by.id('field_houseRentPercent'));
  totalAllowanceInput = element(by.id('field_totalAllowance'));
  medicalAllowanceInput = element(by.id('field_medicalAllowance'));
  medicalAllowancePercentInput = element(by.id('field_medicalAllowancePercent'));
  convinceAllowanceInput = element(by.id('field_convinceAllowance'));
  convinceAllowancePercentInput = element(by.id('field_convinceAllowancePercent'));
  foodAllowanceInput = element(by.id('field_foodAllowance'));
  foodAllowancePercentInput = element(by.id('field_foodAllowancePercent'));
  specialAllowanceActiveStatusSelect = element(by.id('field_specialAllowanceActiveStatus'));
  specialAllowanceInput = element(by.id('field_specialAllowance'));
  specialAllowancePercentInput = element(by.id('field_specialAllowancePercent'));
  specialAllowanceDescriptionInput = element(by.id('field_specialAllowanceDescription'));
  insuranceActiveStatusSelect = element(by.id('field_insuranceActiveStatus'));
  insuranceAmountInput = element(by.id('field_insuranceAmount'));
  insurancePercentInput = element(by.id('field_insurancePercent'));
  insuranceDescriptionInput = element(by.id('field_insuranceDescription'));
  insuranceProcessTypeSelect = element(by.id('field_insuranceProcessType'));
  statusSelect = element(by.id('field_status'));

  employeeSelect = element(by.id('field_employee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setGrossInput(gross: string): Promise<void> {
    await this.grossInput.sendKeys(gross);
  }

  async getGrossInput(): Promise<string> {
    return await this.grossInput.getAttribute('value');
  }

  async setIncrementAmountInput(incrementAmount: string): Promise<void> {
    await this.incrementAmountInput.sendKeys(incrementAmount);
  }

  async getIncrementAmountInput(): Promise<string> {
    return await this.incrementAmountInput.getAttribute('value');
  }

  async setIncrementPercentageInput(incrementPercentage: string): Promise<void> {
    await this.incrementPercentageInput.sendKeys(incrementPercentage);
  }

  async getIncrementPercentageInput(): Promise<string> {
    return await this.incrementPercentageInput.getAttribute('value');
  }

  async setSalaryStartDateInput(salaryStartDate: string): Promise<void> {
    await this.salaryStartDateInput.sendKeys(salaryStartDate);
  }

  async getSalaryStartDateInput(): Promise<string> {
    return await this.salaryStartDateInput.getAttribute('value');
  }

  async setSalaryEndDateInput(salaryEndDate: string): Promise<void> {
    await this.salaryEndDateInput.sendKeys(salaryEndDate);
  }

  async getSalaryEndDateInput(): Promise<string> {
    return await this.salaryEndDateInput.getAttribute('value');
  }

  async setNextIncrementDateInput(nextIncrementDate: string): Promise<void> {
    await this.nextIncrementDateInput.sendKeys(nextIncrementDate);
  }

  async getNextIncrementDateInput(): Promise<string> {
    return await this.nextIncrementDateInput.getAttribute('value');
  }

  async setBasicInput(basic: string): Promise<void> {
    await this.basicInput.sendKeys(basic);
  }

  async getBasicInput(): Promise<string> {
    return await this.basicInput.getAttribute('value');
  }

  async setBasicPercentInput(basicPercent: string): Promise<void> {
    await this.basicPercentInput.sendKeys(basicPercent);
  }

  async getBasicPercentInput(): Promise<string> {
    return await this.basicPercentInput.getAttribute('value');
  }

  async setHouseRentInput(houseRent: string): Promise<void> {
    await this.houseRentInput.sendKeys(houseRent);
  }

  async getHouseRentInput(): Promise<string> {
    return await this.houseRentInput.getAttribute('value');
  }

  async setHouseRentPercentInput(houseRentPercent: string): Promise<void> {
    await this.houseRentPercentInput.sendKeys(houseRentPercent);
  }

  async getHouseRentPercentInput(): Promise<string> {
    return await this.houseRentPercentInput.getAttribute('value');
  }

  async setTotalAllowanceInput(totalAllowance: string): Promise<void> {
    await this.totalAllowanceInput.sendKeys(totalAllowance);
  }

  async getTotalAllowanceInput(): Promise<string> {
    return await this.totalAllowanceInput.getAttribute('value');
  }

  async setMedicalAllowanceInput(medicalAllowance: string): Promise<void> {
    await this.medicalAllowanceInput.sendKeys(medicalAllowance);
  }

  async getMedicalAllowanceInput(): Promise<string> {
    return await this.medicalAllowanceInput.getAttribute('value');
  }

  async setMedicalAllowancePercentInput(medicalAllowancePercent: string): Promise<void> {
    await this.medicalAllowancePercentInput.sendKeys(medicalAllowancePercent);
  }

  async getMedicalAllowancePercentInput(): Promise<string> {
    return await this.medicalAllowancePercentInput.getAttribute('value');
  }

  async setConvinceAllowanceInput(convinceAllowance: string): Promise<void> {
    await this.convinceAllowanceInput.sendKeys(convinceAllowance);
  }

  async getConvinceAllowanceInput(): Promise<string> {
    return await this.convinceAllowanceInput.getAttribute('value');
  }

  async setConvinceAllowancePercentInput(convinceAllowancePercent: string): Promise<void> {
    await this.convinceAllowancePercentInput.sendKeys(convinceAllowancePercent);
  }

  async getConvinceAllowancePercentInput(): Promise<string> {
    return await this.convinceAllowancePercentInput.getAttribute('value');
  }

  async setFoodAllowanceInput(foodAllowance: string): Promise<void> {
    await this.foodAllowanceInput.sendKeys(foodAllowance);
  }

  async getFoodAllowanceInput(): Promise<string> {
    return await this.foodAllowanceInput.getAttribute('value');
  }

  async setFoodAllowancePercentInput(foodAllowancePercent: string): Promise<void> {
    await this.foodAllowancePercentInput.sendKeys(foodAllowancePercent);
  }

  async getFoodAllowancePercentInput(): Promise<string> {
    return await this.foodAllowancePercentInput.getAttribute('value');
  }

  async setSpecialAllowanceActiveStatusSelect(specialAllowanceActiveStatus: string): Promise<void> {
    await this.specialAllowanceActiveStatusSelect.sendKeys(specialAllowanceActiveStatus);
  }

  async getSpecialAllowanceActiveStatusSelect(): Promise<string> {
    return await this.specialAllowanceActiveStatusSelect.element(by.css('option:checked')).getText();
  }

  async specialAllowanceActiveStatusSelectLastOption(): Promise<void> {
    await this.specialAllowanceActiveStatusSelect.all(by.tagName('option')).last().click();
  }

  async setSpecialAllowanceInput(specialAllowance: string): Promise<void> {
    await this.specialAllowanceInput.sendKeys(specialAllowance);
  }

  async getSpecialAllowanceInput(): Promise<string> {
    return await this.specialAllowanceInput.getAttribute('value');
  }

  async setSpecialAllowancePercentInput(specialAllowancePercent: string): Promise<void> {
    await this.specialAllowancePercentInput.sendKeys(specialAllowancePercent);
  }

  async getSpecialAllowancePercentInput(): Promise<string> {
    return await this.specialAllowancePercentInput.getAttribute('value');
  }

  async setSpecialAllowanceDescriptionInput(specialAllowanceDescription: string): Promise<void> {
    await this.specialAllowanceDescriptionInput.sendKeys(specialAllowanceDescription);
  }

  async getSpecialAllowanceDescriptionInput(): Promise<string> {
    return await this.specialAllowanceDescriptionInput.getAttribute('value');
  }

  async setInsuranceActiveStatusSelect(insuranceActiveStatus: string): Promise<void> {
    await this.insuranceActiveStatusSelect.sendKeys(insuranceActiveStatus);
  }

  async getInsuranceActiveStatusSelect(): Promise<string> {
    return await this.insuranceActiveStatusSelect.element(by.css('option:checked')).getText();
  }

  async insuranceActiveStatusSelectLastOption(): Promise<void> {
    await this.insuranceActiveStatusSelect.all(by.tagName('option')).last().click();
  }

  async setInsuranceAmountInput(insuranceAmount: string): Promise<void> {
    await this.insuranceAmountInput.sendKeys(insuranceAmount);
  }

  async getInsuranceAmountInput(): Promise<string> {
    return await this.insuranceAmountInput.getAttribute('value');
  }

  async setInsurancePercentInput(insurancePercent: string): Promise<void> {
    await this.insurancePercentInput.sendKeys(insurancePercent);
  }

  async getInsurancePercentInput(): Promise<string> {
    return await this.insurancePercentInput.getAttribute('value');
  }

  async setInsuranceDescriptionInput(insuranceDescription: string): Promise<void> {
    await this.insuranceDescriptionInput.sendKeys(insuranceDescription);
  }

  async getInsuranceDescriptionInput(): Promise<string> {
    return await this.insuranceDescriptionInput.getAttribute('value');
  }

  async setInsuranceProcessTypeSelect(insuranceProcessType: string): Promise<void> {
    await this.insuranceProcessTypeSelect.sendKeys(insuranceProcessType);
  }

  async getInsuranceProcessTypeSelect(): Promise<string> {
    return await this.insuranceProcessTypeSelect.element(by.css('option:checked')).getText();
  }

  async insuranceProcessTypeSelectLastOption(): Promise<void> {
    await this.insuranceProcessTypeSelect.all(by.tagName('option')).last().click();
  }

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async employeeSelectLastOption(): Promise<void> {
    await this.employeeSelect.all(by.tagName('option')).last().click();
  }

  async employeeSelectOption(option: string): Promise<void> {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect(): ElementFinder {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption(): Promise<string> {
    return await this.employeeSelect.element(by.css('option:checked')).getText();
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

export class EmployeeSalaryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employeeSalary-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employeeSalary'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
