import { element, by, ElementFinder } from 'protractor';

export class DivisionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-division div table .btn-danger'));
  title = element.all(by.css('jhi-division div h2#page-heading span')).first();
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

export class DivisionUpdatePage {
  pageTitle = element(by.id('jhi-division-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  banglaInput = element(by.id('field_bangla'));
  webInput = element(by.id('field_web'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setBanglaInput(bangla: string): Promise<void> {
    await this.banglaInput.sendKeys(bangla);
  }

  async getBanglaInput(): Promise<string> {
    return await this.banglaInput.getAttribute('value');
  }

  async setWebInput(web: string): Promise<void> {
    await this.webInput.sendKeys(web);
  }

  async getWebInput(): Promise<string> {
    return await this.webInput.getAttribute('value');
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

export class DivisionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-division-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-division'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
