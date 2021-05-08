import { element, by, ElementFinder } from 'protractor';

export class MonthlySalaryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-monthly-salary div table .btn-danger'));
  title = element.all(by.css('jhi-monthly-salary div h2#page-heading span')).first();
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

export class MonthlySalaryUpdatePage {
  pageTitle = element(by.id('jhi-monthly-salary-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  yearInput = element(by.id('field_year'));
  monthSelect = element(by.id('field_month'));
  fromDateInput = element(by.id('field_fromDate'));
  toDateInput = element(by.id('field_toDate'));
  statusSelect = element(by.id('field_status'));
  executedOnInput = element(by.id('field_executedOn'));
  executedByInput = element(by.id('field_executedBy'));

  departmentSelect = element(by.id('field_department'));

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

  async departmentSelectLastOption(): Promise<void> {
    await this.departmentSelect.all(by.tagName('option')).last().click();
  }

  async departmentSelectOption(option: string): Promise<void> {
    await this.departmentSelect.sendKeys(option);
  }

  getDepartmentSelect(): ElementFinder {
    return this.departmentSelect;
  }

  async getDepartmentSelectedOption(): Promise<string> {
    return await this.departmentSelect.element(by.css('option:checked')).getText();
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

export class MonthlySalaryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-monthlySalary-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-monthlySalary'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
