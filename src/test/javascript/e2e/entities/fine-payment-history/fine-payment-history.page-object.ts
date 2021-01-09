import { element, by, ElementFinder } from 'protractor';

export class FinePaymentHistoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-fine-payment-history div table .btn-danger'));
  title = element.all(by.css('jhi-fine-payment-history div h2#page-heading span')).first();
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

export class FinePaymentHistoryUpdatePage {
  pageTitle = element(by.id('jhi-fine-payment-history-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  yearInput = element(by.id('field_year'));
  monthTypeSelect = element(by.id('field_monthType'));
  amountInput = element(by.id('field_amount'));
  beforeFineInput = element(by.id('field_beforeFine'));
  afterFineInput = element(by.id('field_afterFine'));

  fineSelect = element(by.id('field_fine'));

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

  async setBeforeFineInput(beforeFine: string): Promise<void> {
    await this.beforeFineInput.sendKeys(beforeFine);
  }

  async getBeforeFineInput(): Promise<string> {
    return await this.beforeFineInput.getAttribute('value');
  }

  async setAfterFineInput(afterFine: string): Promise<void> {
    await this.afterFineInput.sendKeys(afterFine);
  }

  async getAfterFineInput(): Promise<string> {
    return await this.afterFineInput.getAttribute('value');
  }

  async fineSelectLastOption(): Promise<void> {
    await this.fineSelect.all(by.tagName('option')).last().click();
  }

  async fineSelectOption(option: string): Promise<void> {
    await this.fineSelect.sendKeys(option);
  }

  getFineSelect(): ElementFinder {
    return this.fineSelect;
  }

  async getFineSelectedOption(): Promise<string> {
    return await this.fineSelect.element(by.css('option:checked')).getText();
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

export class FinePaymentHistoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-finePaymentHistory-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-finePaymentHistory'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
