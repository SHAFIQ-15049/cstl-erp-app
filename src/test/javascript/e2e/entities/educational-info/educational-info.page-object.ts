import { element, by, ElementFinder } from 'protractor';

export class EducationalInfoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-educational-info div table .btn-danger'));
  title = element.all(by.css('jhi-educational-info div h2#page-heading span')).first();
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

export class EducationalInfoUpdatePage {
  pageTitle = element(by.id('jhi-educational-info-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  serialInput = element(by.id('field_serial'));
  degreeInput = element(by.id('field_degree'));
  institutionInput = element(by.id('field_institution'));
  passingYearInput = element(by.id('field_passingYear'));
  courseDurationInput = element(by.id('field_courseDuration'));
  attachmentInput = element(by.id('file_attachment'));

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

  async setDegreeInput(degree: string): Promise<void> {
    await this.degreeInput.sendKeys(degree);
  }

  async getDegreeInput(): Promise<string> {
    return await this.degreeInput.getAttribute('value');
  }

  async setInstitutionInput(institution: string): Promise<void> {
    await this.institutionInput.sendKeys(institution);
  }

  async getInstitutionInput(): Promise<string> {
    return await this.institutionInput.getAttribute('value');
  }

  async setPassingYearInput(passingYear: string): Promise<void> {
    await this.passingYearInput.sendKeys(passingYear);
  }

  async getPassingYearInput(): Promise<string> {
    return await this.passingYearInput.getAttribute('value');
  }

  async setCourseDurationInput(courseDuration: string): Promise<void> {
    await this.courseDurationInput.sendKeys(courseDuration);
  }

  async getCourseDurationInput(): Promise<string> {
    return await this.courseDurationInput.getAttribute('value');
  }

  async setAttachmentInput(attachment: string): Promise<void> {
    await this.attachmentInput.sendKeys(attachment);
  }

  async getAttachmentInput(): Promise<string> {
    return await this.attachmentInput.getAttribute('value');
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

export class EducationalInfoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-educationalInfo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-educationalInfo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
