import { element, by, ElementFinder } from 'protractor';

export class HolidayComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-holiday div table .btn-danger'));
  title = element.all(by.css('jhi-holiday div h2#page-heading span')).first();
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

export class HolidayUpdatePage {
  pageTitle = element(by.id('jhi-holiday-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromInput = element(by.id('field_from'));
  toInput = element(by.id('field_to'));
  totalDaysInput = element(by.id('field_totalDays'));

  holidayTypeSelect = element(by.id('field_holidayType'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
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

  async setTotalDaysInput(totalDays: string): Promise<void> {
    await this.totalDaysInput.sendKeys(totalDays);
  }

  async getTotalDaysInput(): Promise<string> {
    return await this.totalDaysInput.getAttribute('value');
  }

  async holidayTypeSelectLastOption(): Promise<void> {
    await this.holidayTypeSelect.all(by.tagName('option')).last().click();
  }

  async holidayTypeSelectOption(option: string): Promise<void> {
    await this.holidayTypeSelect.sendKeys(option);
  }

  getHolidayTypeSelect(): ElementFinder {
    return this.holidayTypeSelect;
  }

  async getHolidayTypeSelectedOption(): Promise<string> {
    return await this.holidayTypeSelect.element(by.css('option:checked')).getText();
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

export class HolidayDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-holiday-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-holiday'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
