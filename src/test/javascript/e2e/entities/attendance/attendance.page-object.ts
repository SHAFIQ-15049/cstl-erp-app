import { by, element, ElementFinder } from 'protractor';

export class AttendanceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-attendance div table .btn-danger'));
  title = element.all(by.css('jhi-attendance div h2#page-heading span')).first();
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

export class AttendanceUpdatePage {
  pageTitle = element(by.id('jhi-attendance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  attendanceTimeInput = element(by.id('field_attendanceTime'));
  considerAsSelect = element(by.id('field_considerAs'));
  machineNoInput = element(by.id('field_machineNo'));

  employeeSelect = element(by.id('field_employee'));
  attendanceDataUploadSelect = element(by.id('field_attendanceDataUpload'));
  employeeSalarySelect = element(by.id('field_employeeSalary'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setAttendanceTimeInput(attendanceTime: string): Promise<void> {
    await this.attendanceTimeInput.sendKeys(attendanceTime);
  }

  async getAttendanceTimeInput(): Promise<string> {
    return await this.attendanceTimeInput.getAttribute('value');
  }

  async setConsiderAsSelect(considerAs: string): Promise<void> {
    await this.considerAsSelect.sendKeys(considerAs);
  }

  async getConsiderAsSelect(): Promise<string> {
    return await this.considerAsSelect.element(by.css('option:checked')).getText();
  }

  async considerAsSelectLastOption(): Promise<void> {
    await this.considerAsSelect.all(by.tagName('option')).last().click();
  }

  async setMachineNoInput(machineNo: string): Promise<void> {
    await this.machineNoInput.sendKeys(machineNo);
  }

  async getMachineNoInput(): Promise<string> {
    return await this.machineNoInput.getAttribute('value');
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

  async attendanceDataUploadSelectLastOption(): Promise<void> {
    await this.attendanceDataUploadSelect.all(by.tagName('option')).last().click();
  }

  async attendanceDataUploadSelectOption(option: string): Promise<void> {
    await this.attendanceDataUploadSelect.sendKeys(option);
  }

  getAttendanceDataUploadSelect(): ElementFinder {
    return this.attendanceDataUploadSelect;
  }

  async getAttendanceDataUploadSelectedOption(): Promise<string> {
    return await this.attendanceDataUploadSelect.element(by.css('option:checked')).getText();
  }

  async employeeSalarySelectLastOption(): Promise<void> {
    await this.employeeSalarySelect.all(by.tagName('option')).last().click();
  }

  async employeeSalarySelectOption(option: string): Promise<void> {
    await this.employeeSalarySelect.sendKeys(option);
  }

  getEmployeeSalarySelect(): ElementFinder {
    return this.employeeSalarySelect;
  }

  async getEmployeeSalarySelectedOption(): Promise<string> {
    return await this.employeeSalarySelect.element(by.css('option:checked')).getText();
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

export class AttendanceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-attendance-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-attendance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
