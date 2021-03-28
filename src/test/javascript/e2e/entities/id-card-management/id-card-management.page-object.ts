import { element, by, ElementFinder } from 'protractor';

export class IdCardManagementComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-id-card-management div table .btn-danger'));
  title = element.all(by.css('jhi-id-card-management div h2#page-heading span')).first();
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

export class IdCardManagementUpdatePage {
  pageTitle = element(by.id('jhi-id-card-management-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  cardNoInput = element(by.id('field_cardNo'));
  issueDateInput = element(by.id('field_issueDate'));
  ticketNoInput = element(by.id('field_ticketNo'));
  validTillInput = element(by.id('field_validTill'));

  employeeSelect = element(by.id('field_employee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setCardNoInput(cardNo: string): Promise<void> {
    await this.cardNoInput.sendKeys(cardNo);
  }

  async getCardNoInput(): Promise<string> {
    return await this.cardNoInput.getAttribute('value');
  }

  async setIssueDateInput(issueDate: string): Promise<void> {
    await this.issueDateInput.sendKeys(issueDate);
  }

  async getIssueDateInput(): Promise<string> {
    return await this.issueDateInput.getAttribute('value');
  }

  async setTicketNoInput(ticketNo: string): Promise<void> {
    await this.ticketNoInput.sendKeys(ticketNo);
  }

  async getTicketNoInput(): Promise<string> {
    return await this.ticketNoInput.getAttribute('value');
  }

  async setValidTillInput(validTill: string): Promise<void> {
    await this.validTillInput.sendKeys(validTill);
  }

  async getValidTillInput(): Promise<string> {
    return await this.validTillInput.getAttribute('value');
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

export class IdCardManagementDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-idCardManagement-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-idCardManagement'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
