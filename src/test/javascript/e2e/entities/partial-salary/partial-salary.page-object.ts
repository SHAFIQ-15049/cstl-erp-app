import { element, by, ElementFinder } from 'protractor';

export class PartialSalaryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-partial-salary div table .btn-danger'));
  title = element.all(by.css('jhi-partial-salary div h2#page-heading span')).first();
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

export class PartialSalaryUpdatePage {
  pageTitle = element(by.id('jhi-partial-salary-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  yearInput = element(by.id('field_year'));
  monthSelect = element(by.id('field_month'));
  totalMonthDaysInput = element(by.id('field_totalMonthDays'));
  fromDateInput = element(by.id('field_fromDate'));
  toDateInput = element(by.id('field_toDate'));
  grossInput = element(by.id('field_gross'));
  basicInput = element(by.id('field_basic'));
  basicPercentInput = element(by.id('field_basicPercent'));
  houseRentInput = element(by.id('field_houseRent'));
  houseRentPercentInput = element(by.id('field_houseRentPercent'));
  medicalAllowanceInput = element(by.id('field_medicalAllowance'));
  medicalAllowancePercentInput = element(by.id('field_medicalAllowancePercent'));
  convinceAllowanceInput = element(by.id('field_convinceAllowance'));
  convinceAllowancePercentInput = element(by.id('field_convinceAllowancePercent'));
  foodAllowanceInput = element(by.id('field_foodAllowance'));
  foodAllowancePercentInput = element(by.id('field_foodAllowancePercent'));
  fineInput = element(by.id('field_fine'));
  advanceInput = element(by.id('field_advance'));
  statusSelect = element(by.id('field_status'));
  executedOnInput = element(by.id('field_executedOn'));
  executedByInput = element(by.id('field_executedBy'));
  noteInput = element(by.id('field_note'));

  employeeSelect = element(by.id('field_employee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setYearInput(year: string): Promise<void> {
    await this.yearInput.sendKeys(year);
  }

  async getYearInput(): Promise<string> {
    return await this.yearInput.getAttribute('value');
  }

  async setMonthSelect(month: string): Promise<void> {
    await this.monthSelect.sendKeys(month);
  }

  async getMonthSelect(): Promise<string> {
    return await this.monthSelect.element(by.css('option:checked')).getText();
  }

  async monthSelectLastOption(): Promise<void> {
    await this.monthSelect.all(by.tagName('option')).last().click();
  }

  async setTotalMonthDaysInput(totalMonthDays: string): Promise<void> {
    await this.totalMonthDaysInput.sendKeys(totalMonthDays);
  }

  async getTotalMonthDaysInput(): Promise<string> {
    return await this.totalMonthDaysInput.getAttribute('value');
  }

  async setFromDateInput(fromDate: string): Promise<void> {
    await this.fromDateInput.sendKeys(fromDate);
  }

  async getFromDateInput(): Promise<string> {
    return await this.fromDateInput.getAttribute('value');
  }

  async setToDateInput(toDate: string): Promise<void> {
    await this.toDateInput.sendKeys(toDate);
  }

  async getToDateInput(): Promise<string> {
    return await this.toDateInput.getAttribute('value');
  }

  async setGrossInput(gross: string): Promise<void> {
    await this.grossInput.sendKeys(gross);
  }

  async getGrossInput(): Promise<string> {
    return await this.grossInput.getAttribute('value');
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

  async setFineInput(fine: string): Promise<void> {
    await this.fineInput.sendKeys(fine);
  }

  async getFineInput(): Promise<string> {
    return await this.fineInput.getAttribute('value');
  }

  async setAdvanceInput(advance: string): Promise<void> {
    await this.advanceInput.sendKeys(advance);
  }

  async getAdvanceInput(): Promise<string> {
    return await this.advanceInput.getAttribute('value');
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

  async setExecutedOnInput(executedOn: string): Promise<void> {
    await this.executedOnInput.sendKeys(executedOn);
  }

  async getExecutedOnInput(): Promise<string> {
    return await this.executedOnInput.getAttribute('value');
  }

  async setExecutedByInput(executedBy: string): Promise<void> {
    await this.executedByInput.sendKeys(executedBy);
  }

  async getExecutedByInput(): Promise<string> {
    return await this.executedByInput.getAttribute('value');
  }

  async setNoteInput(note: string): Promise<void> {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput(): Promise<string> {
    return await this.noteInput.getAttribute('value');
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

export class PartialSalaryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-partialSalary-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-partialSalary'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
