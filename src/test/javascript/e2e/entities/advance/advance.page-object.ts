import { element, by, ElementFinder } from 'protractor';

export class AdvanceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-advance div table .btn-danger'));
  title = element.all(by.css('jhi-advance div h2#page-heading span')).first();
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

export class AdvanceUpdatePage {
  pageTitle = element(by.id('jhi-advance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  providedOnInput = element(by.id('field_providedOn'));
  reasonInput = element(by.id('field_reason'));
  amountInput = element(by.id('field_amount'));
  paymentPercentageInput = element(by.id('field_paymentPercentage'));
  monthlyPaymentAmountInput = element(by.id('field_monthlyPaymentAmount'));
  paymentStatusSelect = element(by.id('field_paymentStatus'));
  amountPaidInput = element(by.id('field_amountPaid'));
  amountLeftInput = element(by.id('field_amountLeft'));

  employeeSelect = element(by.id('field_employee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setProvidedOnInput(providedOn: string): Promise<void> {
    await this.providedOnInput.sendKeys(providedOn);
  }

  async getProvidedOnInput(): Promise<string> {
    return await this.providedOnInput.getAttribute('value');
  }

  async setReasonInput(reason: string): Promise<void> {
    await this.reasonInput.sendKeys(reason);
  }

  async getReasonInput(): Promise<string> {
    return await this.reasonInput.getAttribute('value');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async setPaymentPercentageInput(paymentPercentage: string): Promise<void> {
    await this.paymentPercentageInput.sendKeys(paymentPercentage);
  }

  async getPaymentPercentageInput(): Promise<string> {
    return await this.paymentPercentageInput.getAttribute('value');
  }

  async setMonthlyPaymentAmountInput(monthlyPaymentAmount: string): Promise<void> {
    await this.monthlyPaymentAmountInput.sendKeys(monthlyPaymentAmount);
  }

  async getMonthlyPaymentAmountInput(): Promise<string> {
    return await this.monthlyPaymentAmountInput.getAttribute('value');
  }

  async setPaymentStatusSelect(paymentStatus: string): Promise<void> {
    await this.paymentStatusSelect.sendKeys(paymentStatus);
  }

  async getPaymentStatusSelect(): Promise<string> {
    return await this.paymentStatusSelect.element(by.css('option:checked')).getText();
  }

  async paymentStatusSelectLastOption(): Promise<void> {
    await this.paymentStatusSelect.all(by.tagName('option')).last().click();
  }

  async setAmountPaidInput(amountPaid: string): Promise<void> {
    await this.amountPaidInput.sendKeys(amountPaid);
  }

  async getAmountPaidInput(): Promise<string> {
    return await this.amountPaidInput.getAttribute('value');
  }

  async setAmountLeftInput(amountLeft: string): Promise<void> {
    await this.amountLeftInput.sendKeys(amountLeft);
  }

  async getAmountLeftInput(): Promise<string> {
    return await this.amountLeftInput.getAttribute('value');
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

export class AdvanceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-advance-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-advance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
