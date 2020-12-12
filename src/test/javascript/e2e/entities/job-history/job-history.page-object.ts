import { element, by, ElementFinder } from 'protractor';

export class JobHistoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-job-history div table .btn-danger'));
  title = element.all(by.css('jhi-job-history div h2#page-heading span')).first();
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

export class JobHistoryUpdatePage {
  pageTitle = element(by.id('jhi-job-history-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  serialInput = element(by.id('field_serial'));
  organizationInput = element(by.id('field_organization'));
  designationInput = element(by.id('field_designation'));
  fromInput = element(by.id('field_from'));
  toInput = element(by.id('field_to'));
  payScaleInput = element(by.id('field_payScale'));
  totalExperienceInput = element(by.id('field_totalExperience'));

  employeeSelect = element(by.id('field_employee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setSerialInput(serial: string): Promise<void> {
    await this.serialInput.sendKeys(serial);
  }

  async getSerialInput(): Promise<string> {
    return await this.serialInput.getAttribute('value');
  }

  async setOrganizationInput(organization: string): Promise<void> {
    await this.organizationInput.sendKeys(organization);
  }

  async getOrganizationInput(): Promise<string> {
    return await this.organizationInput.getAttribute('value');
  }

  async setDesignationInput(designation: string): Promise<void> {
    await this.designationInput.sendKeys(designation);
  }

  async getDesignationInput(): Promise<string> {
    return await this.designationInput.getAttribute('value');
  }

  async setFromInput(from: string): Promise<void> {
    await this.fromInput.sendKeys(from);
  }

  async getFromInput(): Promise<string> {
    return await this.fromInput.getAttribute('value');
  }

  async setToInput(to: string): Promise<void> {
    await this.toInput.sendKeys(to);
  }

  async getToInput(): Promise<string> {
    return await this.toInput.getAttribute('value');
  }

  async setPayScaleInput(payScale: string): Promise<void> {
    await this.payScaleInput.sendKeys(payScale);
  }

  async getPayScaleInput(): Promise<string> {
    return await this.payScaleInput.getAttribute('value');
  }

  async setTotalExperienceInput(totalExperience: string): Promise<void> {
    await this.totalExperienceInput.sendKeys(totalExperience);
  }

  async getTotalExperienceInput(): Promise<string> {
    return await this.totalExperienceInput.getAttribute('value');
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

export class JobHistoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-jobHistory-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-jobHistory'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
