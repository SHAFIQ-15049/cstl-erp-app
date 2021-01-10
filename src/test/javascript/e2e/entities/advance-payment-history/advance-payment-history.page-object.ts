import { element, by, ElementFinder } from 'protractor';

export class AdvancePaymentHistoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-advance-payment-history div table .btn-danger'));
  title = element.all(by.css('jhi-advance-payment-history div h2#page-heading span')).first();
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

export class AdvancePaymentHistoryUpdatePage {
  pageTitle = element(by.id('jhi-advance-payment-history-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  yearInput = element(by.id('field_year'));
  monthTypeSelect = element(by.id('field_monthType'));
  amountInput = element(by.id('field_amount'));
  beforeInput = element(by.id('field_before'));
  afterInput = element(by.id('field_after'));

  advanceSelect = element(by.id('field_advance'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setYearInput(year: string): Promise<void> {
    await this.yearInput.sendKeys(year);
  }

  async getYearInput(): Promise<string> {
    return await this.yearInput.getAttribute('value');
  }

  async setMonthTypeSelect(monthType: string): Promise<void> {
    await this.monthTypeSelect.sendKeys(monthType);
  }

  async getMonthTypeSelect(): Promise<string> {
    return await this.monthTypeSelect.element(by.css('option:checked')).getText();
  }

  async monthTypeSelectLastOption(): Promise<void> {
    await this.monthTypeSelect.all(by.tagName('option')).last().click();
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async setBeforeInput(before: string): Promise<void> {
    await this.beforeInput.sendKeys(before);
  }

  async getBeforeInput(): Promise<string> {
    return await this.beforeInput.getAttribute('value');
  }

  async setAfterInput(after: string): Promise<void> {
    await this.afterInput.sendKeys(after);
  }

  async getAfterInput(): Promise<string> {
    return await this.afterInput.getAttribute('value');
  }

  async advanceSelectLastOption(): Promise<void> {
    await this.advanceSelect.all(by.tagName('option')).last().click();
  }

  async advanceSelectOption(option: string): Promise<void> {
    await this.advanceSelect.sendKeys(option);
  }

  getAdvanceSelect(): ElementFinder {
    return this.advanceSelect;
  }

  async getAdvanceSelectedOption(): Promise<string> {
    return await this.advanceSelect.element(by.css('option:checked')).getText();
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

export class AdvancePaymentHistoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-advancePaymentHistory-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-advancePaymentHistory'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
