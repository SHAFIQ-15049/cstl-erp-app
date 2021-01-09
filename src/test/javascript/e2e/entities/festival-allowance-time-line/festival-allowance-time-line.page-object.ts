import { element, by, ElementFinder } from 'protractor';

export class FestivalAllowanceTimeLineComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-festival-allowance-time-line div table .btn-danger'));
  title = element.all(by.css('jhi-festival-allowance-time-line div h2#page-heading span')).first();
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

export class FestivalAllowanceTimeLineUpdatePage {
  pageTitle = element(by.id('jhi-festival-allowance-time-line-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  yearInput = element(by.id('field_year'));
  monthSelect = element(by.id('field_month'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setYearInput(year: string): Promise<void> {
    await this.yearInput.sendKeys(year);
  }

  async getYearInput(): Promise<string> {
    return await this.yearInput.getAttribute('value');
  }

  async setMonthSelect(month: string): Promise<void> {
    await this.monthSelect.sendKeys(month);
  }

  async getMonthSelect(): Promise<string> {
    return await this.monthSelect.element(by.css('option:checked')).getText();
  }

  async monthSelectLastOption(): Promise<void> {
    await this.monthSelect.all(by.tagName('option')).last().click();
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

export class FestivalAllowanceTimeLineDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-festivalAllowanceTimeLine-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-festivalAllowanceTimeLine'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
