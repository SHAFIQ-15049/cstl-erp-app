import { element, by, ElementFinder } from 'protractor';

export class OverTimeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-over-time div table .btn-danger'));
  title = element.all(by.css('jhi-over-time div h2#page-heading span')).first();
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

export class OverTimeUpdatePage {
  pageTitle = element(by.id('jhi-over-time-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  yearInput = element(by.id('field_year'));
  monthSelect = element(by.id('field_month'));
  fromDateInput = element(by.id('field_fromDate'));
  toDateInput = element(by.id('field_toDate'));
  totalOverTimeInput = element(by.id('field_totalOverTime'));
  officialOverTimeInput = element(by.id('field_officialOverTime'));
  extraOverTimeInput = element(by.id('field_extraOverTime'));
  totalAmountInput = element(by.id('field_totalAmount'));
  officialAmountInput = element(by.id('field_officialAmount'));
  extraAmountInput = element(by.id('field_extraAmount'));
  noteInput = element(by.id('field_note'));
  executedOnInput = element(by.id('field_executedOn'));
  executedByInput = element(by.id('field_executedBy'));

  designationSelect = element(by.id('field_designation'));
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

  async setTotalOverTimeInput(totalOverTime: string): Promise<void> {
    await this.totalOverTimeInput.sendKeys(totalOverTime);
  }

  async getTotalOverTimeInput(): Promise<string> {
    return await this.totalOverTimeInput.getAttribute('value');
  }

  async setOfficialOverTimeInput(officialOverTime: string): Promise<void> {
    await this.officialOverTimeInput.sendKeys(officialOverTime);
  }

  async getOfficialOverTimeInput(): Promise<string> {
    return await this.officialOverTimeInput.getAttribute('value');
  }

  async setExtraOverTimeInput(extraOverTime: string): Promise<void> {
    await this.extraOverTimeInput.sendKeys(extraOverTime);
  }

  async getExtraOverTimeInput(): Promise<string> {
    return await this.extraOverTimeInput.getAttribute('value');
  }

  async setTotalAmountInput(totalAmount: string): Promise<void> {
    await this.totalAmountInput.sendKeys(totalAmount);
  }

  async getTotalAmountInput(): Promise<string> {
    return await this.totalAmountInput.getAttribute('value');
  }

  async setOfficialAmountInput(officialAmount: string): Promise<void> {
    await this.officialAmountInput.sendKeys(officialAmount);
  }

  async getOfficialAmountInput(): Promise<string> {
    return await this.officialAmountInput.getAttribute('value');
  }

  async setExtraAmountInput(extraAmount: string): Promise<void> {
    await this.extraAmountInput.sendKeys(extraAmount);
  }

  async getExtraAmountInput(): Promise<string> {
    return await this.extraAmountInput.getAttribute('value');
  }

  async setNoteInput(note: string): Promise<void> {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput(): Promise<string> {
    return await this.noteInput.getAttribute('value');
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

  async designationSelectLastOption(): Promise<void> {
    await this.designationSelect.all(by.tagName('option')).last().click();
  }

  async designationSelectOption(option: string): Promise<void> {
    await this.designationSelect.sendKeys(option);
  }

  getDesignationSelect(): ElementFinder {
    return this.designationSelect;
  }

  async getDesignationSelectedOption(): Promise<string> {
    return await this.designationSelect.element(by.css('option:checked')).getText();
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

export class OverTimeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-overTime-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-overTime'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
