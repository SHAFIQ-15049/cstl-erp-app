import { element, by, ElementFinder } from 'protractor';

export class WeekendComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-weekend div table .btn-danger'));
  title = element.all(by.css('jhi-weekend div h2#page-heading span')).first();
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

export class WeekendUpdatePage {
  pageTitle = element(by.id('jhi-weekend-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  daySelect = element(by.id('field_day'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setDaySelect(day: string): Promise<void> {
    await this.daySelect.sendKeys(day);
  }

  async getDaySelect(): Promise<string> {
    return await this.daySelect.element(by.css('option:checked')).getText();
  }

  async daySelectLastOption(): Promise<void> {
    await this.daySelect.all(by.tagName('option')).last().click();
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

export class WeekendDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-weekend-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-weekend'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
