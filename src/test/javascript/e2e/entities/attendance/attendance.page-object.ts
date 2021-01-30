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
  machineNoInput = element(by.id('field_machineNo'));
  markedAsSelect = element(by.id('field_markedAs'));
  leaveAppliedSelect = element(by.id('field_leaveApplied'));
  employeeMachineIdInput = element(by.id('field_employeeMachineId'));
  employeeCategorySelect = element(by.id('field_employeeCategory'));
  employeeTypeSelect = element(by.id('field_employeeType'));

  employeeSelect = element(by.id('field_employee'));
  employeeSalarySelect = element(by.id('field_employeeSalary'));
  departmentSelect = element(by.id('field_department'));
  designationSelect = element(by.id('field_designation'));
  lineSelect = element(by.id('field_line'));
  gradeSelect = element(by.id('field_grade'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setAttendanceTimeInput(attendanceTime: string): Promise<void> {
    await this.attendanceTimeInput.sendKeys(attendanceTime);
  }

  async getAttendanceTimeInput(): Promise<string> {
    return await this.attendanceTimeInput.getAttribute('value');
  }

  async setMachineNoInput(machineNo: string): Promise<void> {
    await this.machineNoInput.sendKeys(machineNo);
  }

  async getMachineNoInput(): Promise<string> {
    return await this.machineNoInput.getAttribute('value');
  }

  async setMarkedAsSelect(markedAs: string): Promise<void> {
    await this.markedAsSelect.sendKeys(markedAs);
  }

  async getMarkedAsSelect(): Promise<string> {
    return await this.markedAsSelect.element(by.css('option:checked')).getText();
  }

  async markedAsSelectLastOption(): Promise<void> {
    await this.markedAsSelect.all(by.tagName('option')).last().click();
  }

  async setLeaveAppliedSelect(leaveApplied: string): Promise<void> {
    await this.leaveAppliedSelect.sendKeys(leaveApplied);
  }

  async getLeaveAppliedSelect(): Promise<string> {
    return await this.leaveAppliedSelect.element(by.css('option:checked')).getText();
  }

  async leaveAppliedSelectLastOption(): Promise<void> {
    await this.leaveAppliedSelect.all(by.tagName('option')).last().click();
  }

  async setEmployeeMachineIdInput(employeeMachineId: string): Promise<void> {
    await this.employeeMachineIdInput.sendKeys(employeeMachineId);
  }

  async getEmployeeMachineIdInput(): Promise<string> {
    return await this.employeeMachineIdInput.getAttribute('value');
  }

  async setEmployeeCategorySelect(employeeCategory: string): Promise<void> {
    await this.employeeCategorySelect.sendKeys(employeeCategory);
  }

  async getEmployeeCategorySelect(): Promise<string> {
    return await this.employeeCategorySelect.element(by.css('option:checked')).getText();
  }

  async employeeCategorySelectLastOption(): Promise<void> {
    await this.employeeCategorySelect.all(by.tagName('option')).last().click();
  }

  async setEmployeeTypeSelect(employeeType: string): Promise<void> {
    await this.employeeTypeSelect.sendKeys(employeeType);
  }

  async getEmployeeTypeSelect(): Promise<string> {
    return await this.employeeTypeSelect.element(by.css('option:checked')).getText();
  }

  async employeeTypeSelectLastOption(): Promise<void> {
    await this.employeeTypeSelect.all(by.tagName('option')).last().click();
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

  async lineSelectLastOption(): Promise<void> {
    await this.lineSelect.all(by.tagName('option')).last().click();
  }

  async lineSelectOption(option: string): Promise<void> {
    await this.lineSelect.sendKeys(option);
  }

  getLineSelect(): ElementFinder {
    return this.lineSelect;
  }

  async getLineSelectedOption(): Promise<string> {
    return await this.lineSelect.element(by.css('option:checked')).getText();
  }

  async gradeSelectLastOption(): Promise<void> {
    await this.gradeSelect.all(by.tagName('option')).last().click();
  }

  async gradeSelectOption(option: string): Promise<void> {
    await this.gradeSelect.sendKeys(option);
  }

  getGradeSelect(): ElementFinder {
    return this.gradeSelect;
  }

  async getGradeSelectedOption(): Promise<string> {
    return await this.gradeSelect.element(by.css('option:checked')).getText();
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
