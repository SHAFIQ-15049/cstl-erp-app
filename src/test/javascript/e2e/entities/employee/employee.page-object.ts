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

  employeeIdInput = element(by.id('field_employeeId'));
  globalIdInput = element(by.id('field_globalId'));
  localIdInput = element(by.id('field_localId'));
  typeSelect = element(by.id('field_type'));
  joiningDateInput = element(by.id('field_joiningDate'));
  terminationDateInput = element(by.id('field_terminationDate'));
  terminationReasonInput = element(by.id('field_terminationReason'));

  personalInfoSelect = element(by.id('field_personalInfo'));
  companySelect = element(by.id('field_company'));
  departmentSelect = element(by.id('field_department'));
  gradeSelect = element(by.id('field_grade'));
  designationSelect = element(by.id('field_designation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setEmployeeIdInput(employeeId: string): Promise<void> {
    await this.employeeIdInput.sendKeys(employeeId);
  }

  async getEmployeeIdInput(): Promise<string> {
    return await this.employeeIdInput.getAttribute('value');
  }

  async setGlobalIdInput(globalId: string): Promise<void> {
    await this.globalIdInput.sendKeys(globalId);
  }

  async getGlobalIdInput(): Promise<string> {
    return await this.globalIdInput.getAttribute('value');
  }

  async setLocalIdInput(localId: string): Promise<void> {
    await this.localIdInput.sendKeys(localId);
  }

  async getLocalIdInput(): Promise<string> {
    return await this.localIdInput.getAttribute('value');
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

  async personalInfoSelectLastOption(): Promise<void> {
    await this.personalInfoSelect.all(by.tagName('option')).last().click();
  }

  async personalInfoSelectOption(option: string): Promise<void> {
    await this.personalInfoSelect.sendKeys(option);
  }

  getPersonalInfoSelect(): ElementFinder {
    return this.personalInfoSelect;
  }

  async getPersonalInfoSelectedOption(): Promise<string> {
    return await this.personalInfoSelect.element(by.css('option:checked')).getText();
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
