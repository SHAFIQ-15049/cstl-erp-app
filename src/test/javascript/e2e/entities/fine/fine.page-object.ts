import { element, by, ElementFinder } from 'protractor';

export class FineComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-fine div table .btn-danger'));
  title = element.all(by.css('jhi-fine div h2#page-heading span')).first();
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

export class FineUpdatePage {
  pageTitle = element(by.id('jhi-fine-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  finedOnInput = element(by.id('field_finedOn'));
  reasonInput = element(by.id('field_reason'));
  amountInput = element(by.id('field_amount'));
  finePercentageInput = element(by.id('field_finePercentage'));
  monthlyFineAmountInput = element(by.id('field_monthlyFineAmount'));
  paymentStatusSelect = element(by.id('field_paymentStatus'));
  amountPaidInput = element(by.id('field_amountPaid'));
  amountLeftInput = element(by.id('field_amountLeft'));

  employeeSelect = element(by.id('field_employee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setFinedOnInput(finedOn: string): Promise<void> {
    await this.finedOnInput.sendKeys(finedOn);
  }

  async getFinedOnInput(): Promise<string> {
    return await this.finedOnInput.getAttribute('value');
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

  async setFinePercentageInput(finePercentage: string): Promise<void> {
    await this.finePercentageInput.sendKeys(finePercentage);
  }

  async getFinePercentageInput(): Promise<string> {
    return await this.finePercentageInput.getAttribute('value');
  }

  async setMonthlyFineAmountInput(monthlyFineAmount: string): Promise<void> {
    await this.monthlyFineAmountInput.sendKeys(monthlyFineAmount);
  }

  async getMonthlyFineAmountInput(): Promise<string> {
    return await this.monthlyFineAmountInput.getAttribute('value');
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

export class FineDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-fine-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-fine'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
