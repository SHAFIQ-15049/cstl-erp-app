import { element, by, ElementFinder } from 'protractor';

export class MonthlySalaryDtlComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-monthly-salary-dtl div table .btn-danger'));
  title = element.all(by.css('jhi-monthly-salary-dtl div h2#page-heading span')).first();
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

export class MonthlySalaryDtlUpdatePage {
  pageTitle = element(by.id('jhi-monthly-salary-dtl-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

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
  totalWorkingDaysInput = element(by.id('field_totalWorkingDays'));
  regularLeaveInput = element(by.id('field_regularLeave'));
  sickLeaveInput = element(by.id('field_sickLeave'));
  compensationLeaveInput = element(by.id('field_compensationLeave'));
  festivalLeaveInput = element(by.id('field_festivalLeave'));
  weeklyLeaveInput = element(by.id('field_weeklyLeave'));
  presentInput = element(by.id('field_present'));
  absentInput = element(by.id('field_absent'));
  totalMonthDaysInput = element(by.id('field_totalMonthDays'));
  overTimeHourInput = element(by.id('field_overTimeHour'));
  overTimeSalaryHourlyInput = element(by.id('field_overTimeSalaryHourly'));
  overTimeSalaryInput = element(by.id('field_overTimeSalary'));
  presentBonusInput = element(by.id('field_presentBonus'));
  absentFineInput = element(by.id('field_absentFine'));
  stampPriceInput = element(by.id('field_stampPrice'));
  taxInput = element(by.id('field_tax'));
  othersInput = element(by.id('field_others'));
  totalPayableInput = element(by.id('field_totalPayable'));
  statusSelect = element(by.id('field_status'));
  typeSelect = element(by.id('field_type'));
  executedOnInput = element(by.id('field_executedOn'));
  executedByInput = element(by.id('field_executedBy'));
  noteInput = element(by.id('field_note'));

  employeeSelect = element(by.id('field_employee'));
  monthlySalarySelect = element(by.id('field_monthlySalary'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
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

  async setTotalWorkingDaysInput(totalWorkingDays: string): Promise<void> {
    await this.totalWorkingDaysInput.sendKeys(totalWorkingDays);
  }

  async getTotalWorkingDaysInput(): Promise<string> {
    return await this.totalWorkingDaysInput.getAttribute('value');
  }

  async setRegularLeaveInput(regularLeave: string): Promise<void> {
    await this.regularLeaveInput.sendKeys(regularLeave);
  }

  async getRegularLeaveInput(): Promise<string> {
    return await this.regularLeaveInput.getAttribute('value');
  }

  async setSickLeaveInput(sickLeave: string): Promise<void> {
    await this.sickLeaveInput.sendKeys(sickLeave);
  }

  async getSickLeaveInput(): Promise<string> {
    return await this.sickLeaveInput.getAttribute('value');
  }

  async setCompensationLeaveInput(compensationLeave: string): Promise<void> {
    await this.compensationLeaveInput.sendKeys(compensationLeave);
  }

  async getCompensationLeaveInput(): Promise<string> {
    return await this.compensationLeaveInput.getAttribute('value');
  }

  async setFestivalLeaveInput(festivalLeave: string): Promise<void> {
    await this.festivalLeaveInput.sendKeys(festivalLeave);
  }

  async getFestivalLeaveInput(): Promise<string> {
    return await this.festivalLeaveInput.getAttribute('value');
  }

  async setWeeklyLeaveInput(weeklyLeave: string): Promise<void> {
    await this.weeklyLeaveInput.sendKeys(weeklyLeave);
  }

  async getWeeklyLeaveInput(): Promise<string> {
    return await this.weeklyLeaveInput.getAttribute('value');
  }

  async setPresentInput(present: string): Promise<void> {
    await this.presentInput.sendKeys(present);
  }

  async getPresentInput(): Promise<string> {
    return await this.presentInput.getAttribute('value');
  }

  async setAbsentInput(absent: string): Promise<void> {
    await this.absentInput.sendKeys(absent);
  }

  async getAbsentInput(): Promise<string> {
    return await this.absentInput.getAttribute('value');
  }

  async setTotalMonthDaysInput(totalMonthDays: string): Promise<void> {
    await this.totalMonthDaysInput.sendKeys(totalMonthDays);
  }

  async getTotalMonthDaysInput(): Promise<string> {
    return await this.totalMonthDaysInput.getAttribute('value');
  }

  async setOverTimeHourInput(overTimeHour: string): Promise<void> {
    await this.overTimeHourInput.sendKeys(overTimeHour);
  }

  async getOverTimeHourInput(): Promise<string> {
    return await this.overTimeHourInput.getAttribute('value');
  }

  async setOverTimeSalaryHourlyInput(overTimeSalaryHourly: string): Promise<void> {
    await this.overTimeSalaryHourlyInput.sendKeys(overTimeSalaryHourly);
  }

  async getOverTimeSalaryHourlyInput(): Promise<string> {
    return await this.overTimeSalaryHourlyInput.getAttribute('value');
  }

  async setOverTimeSalaryInput(overTimeSalary: string): Promise<void> {
    await this.overTimeSalaryInput.sendKeys(overTimeSalary);
  }

  async getOverTimeSalaryInput(): Promise<string> {
    return await this.overTimeSalaryInput.getAttribute('value');
  }

  async setPresentBonusInput(presentBonus: string): Promise<void> {
    await this.presentBonusInput.sendKeys(presentBonus);
  }

  async getPresentBonusInput(): Promise<string> {
    return await this.presentBonusInput.getAttribute('value');
  }

  async setAbsentFineInput(absentFine: string): Promise<void> {
    await this.absentFineInput.sendKeys(absentFine);
  }

  async getAbsentFineInput(): Promise<string> {
    return await this.absentFineInput.getAttribute('value');
  }

  async setStampPriceInput(stampPrice: string): Promise<void> {
    await this.stampPriceInput.sendKeys(stampPrice);
  }

  async getStampPriceInput(): Promise<string> {
    return await this.stampPriceInput.getAttribute('value');
  }

  async setTaxInput(tax: string): Promise<void> {
    await this.taxInput.sendKeys(tax);
  }

  async getTaxInput(): Promise<string> {
    return await this.taxInput.getAttribute('value');
  }

  async setOthersInput(others: string): Promise<void> {
    await this.othersInput.sendKeys(others);
  }

  async getOthersInput(): Promise<string> {
    return await this.othersInput.getAttribute('value');
  }

  async setTotalPayableInput(totalPayable: string): Promise<void> {
    await this.totalPayableInput.sendKeys(totalPayable);
  }

  async getTotalPayableInput(): Promise<string> {
    return await this.totalPayableInput.getAttribute('value');
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

  async setTypeSelect(type: string): Promise<void> {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect.all(by.tagName('option')).last().click();
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

  async monthlySalarySelectLastOption(): Promise<void> {
    await this.monthlySalarySelect.all(by.tagName('option')).last().click();
  }

  async monthlySalarySelectOption(option: string): Promise<void> {
    await this.monthlySalarySelect.sendKeys(option);
  }

  getMonthlySalarySelect(): ElementFinder {
    return this.monthlySalarySelect;
  }

  async getMonthlySalarySelectedOption(): Promise<string> {
    return await this.monthlySalarySelect.element(by.css('option:checked')).getText();
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

export class MonthlySalaryDtlDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-monthlySalaryDtl-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-monthlySalaryDtl'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
