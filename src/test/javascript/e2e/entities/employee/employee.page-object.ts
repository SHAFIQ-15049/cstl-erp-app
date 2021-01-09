import { element, by, ElementFinder } from 'protractor';

export class EmployeeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employee div table .btn-danger'));
  title = element.all(by.css('jhi-employee div h2#page-heading span')).first();
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

export class EmployeeUpdatePage {
  pageTitle = element(by.id('jhi-employee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  empIdInput = element(by.id('field_empId'));
  globalIdInput = element(by.id('field_globalId'));
  attendanceMachineIdInput = element(by.id('field_attendanceMachineId'));
  localIdInput = element(by.id('field_localId'));
  categorySelect = element(by.id('field_category'));
  typeSelect = element(by.id('field_type'));
  joiningDateInput = element(by.id('field_joiningDate'));
  statusSelect = element(by.id('field_status'));
  terminationDateInput = element(by.id('field_terminationDate'));
  terminationReasonInput = element(by.id('field_terminationReason'));

  companySelect = element(by.id('field_company'));
  departmentSelect = element(by.id('field_department'));
  gradeSelect = element(by.id('field_grade'));
  designationSelect = element(by.id('field_designation'));
  lineSelect = element(by.id('field_line'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setEmpIdInput(empId: string): Promise<void> {
    await this.empIdInput.sendKeys(empId);
  }

  async getEmpIdInput(): Promise<string> {
    return await this.empIdInput.getAttribute('value');
  }

  async setGlobalIdInput(globalId: string): Promise<void> {
    await this.globalIdInput.sendKeys(globalId);
  }

  async getGlobalIdInput(): Promise<string> {
    return await this.globalIdInput.getAttribute('value');
  }

  async setAttendanceMachineIdInput(attendanceMachineId: string): Promise<void> {
    await this.attendanceMachineIdInput.sendKeys(attendanceMachineId);
  }

  async getAttendanceMachineIdInput(): Promise<string> {
    return await this.attendanceMachineIdInput.getAttribute('value');
  }

  async setLocalIdInput(localId: string): Promise<void> {
    await this.localIdInput.sendKeys(localId);
  }

  async getLocalIdInput(): Promise<string> {
    return await this.localIdInput.getAttribute('value');
  }

  async setCategorySelect(category: string): Promise<void> {
    await this.categorySelect.sendKeys(category);
  }

  async getCategorySelect(): Promise<string> {
    return await this.categorySelect.element(by.css('option:checked')).getText();
  }

  async categorySelectLastOption(): Promise<void> {
    await this.categorySelect.all(by.tagName('option')).last().click();
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

  async setJoiningDateInput(joiningDate: string): Promise<void> {
    await this.joiningDateInput.sendKeys(joiningDate);
  }

  async getJoiningDateInput(): Promise<string> {
    return await this.joiningDateInput.getAttribute('value');
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

  async setTerminationDateInput(terminationDate: string): Promise<void> {
    await this.terminationDateInput.sendKeys(terminationDate);
  }

  async getTerminationDateInput(): Promise<string> {
    return await this.terminationDateInput.getAttribute('value');
  }

  async setTerminationReasonInput(terminationReason: string): Promise<void> {
    await this.terminationReasonInput.sendKeys(terminationReason);
  }

  async getTerminationReasonInput(): Promise<string> {
    return await this.terminationReasonInput.getAttribute('value');
  }

  async companySelectLastOption(): Promise<void> {
    await this.companySelect.all(by.tagName('option')).last().click();
  }

  async companySelectOption(option: string): Promise<void> {
    await this.companySelect.sendKeys(option);
  }

  getCompanySelect(): ElementFinder {
    return this.companySelect;
  }

  async getCompanySelectedOption(): Promise<string> {
    return await this.companySelect.element(by.css('option:checked')).getText();
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

export class EmployeeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-employee-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-employee'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
