import { element, by, ElementFinder } from 'protractor';

export class FestivalAllowancePaymentDtlComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-festival-allowance-payment-dtl div table .btn-danger'));
  title = element.all(by.css('jhi-festival-allowance-payment-dtl div h2#page-heading span')).first();
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

export class FestivalAllowancePaymentDtlUpdatePage {
  pageTitle = element(by.id('jhi-festival-allowance-payment-dtl-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  amountInput = element(by.id('field_amount'));
  statusSelect = element(by.id('field_status'));
  executedOnInput = element(by.id('field_executedOn'));
  executedByInput = element(by.id('field_executedBy'));
  noteInput = element(by.id('field_note'));

  employeeSelect = element(by.id('field_employee'));
  festivalAllowancePaymentSelect = element(by.id('field_festivalAllowancePayment'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
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

  async setNoteInput(note: string): Promise<void> {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput(): Promise<string> {
    return await this.noteInput.getAttribute('value');
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

  async festivalAllowancePaymentSelectLastOption(): Promise<void> {
    await this.festivalAllowancePaymentSelect.all(by.tagName('option')).last().click();
  }

  async festivalAllowancePaymentSelectOption(option: string): Promise<void> {
    await this.festivalAllowancePaymentSelect.sendKeys(option);
  }

  getFestivalAllowancePaymentSelect(): ElementFinder {
    return this.festivalAllowancePaymentSelect;
  }

  async getFestivalAllowancePaymentSelectedOption(): Promise<string> {
    return await this.festivalAllowancePaymentSelect.element(by.css('option:checked')).getText();
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

export class FestivalAllowancePaymentDtlDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-festivalAllowancePaymentDtl-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-festivalAllowancePaymentDtl'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
