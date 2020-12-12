import { element, by, ElementFinder } from 'protractor';

export class TrainingComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-training div table .btn-danger'));
  title = element.all(by.css('jhi-training div h2#page-heading span')).first();
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

export class TrainingUpdatePage {
  pageTitle = element(by.id('jhi-training-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  serialInput = element(by.id('field_serial'));
  nameInput = element(by.id('field_name'));
  trainingInstituteInput = element(by.id('field_trainingInstitute'));
  receivedOnInput = element(by.id('field_receivedOn'));
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

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setTrainingInstituteInput(trainingInstitute: string): Promise<void> {
    await this.trainingInstituteInput.sendKeys(trainingInstitute);
  }

  async getTrainingInstituteInput(): Promise<string> {
    return await this.trainingInstituteInput.getAttribute('value');
  }

  async setReceivedOnInput(receivedOn: string): Promise<void> {
    await this.receivedOnInput.sendKeys(receivedOn);
  }

  async getReceivedOnInput(): Promise<string> {
    return await this.receivedOnInput.getAttribute('value');
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

export class TrainingDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-training-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-training'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
