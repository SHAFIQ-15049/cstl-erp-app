import { element, by, ElementFinder } from 'protractor';

export class DistrictComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-district div table .btn-danger'));
  title = element.all(by.css('jhi-district div h2#page-heading span')).first();
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

export class DistrictUpdatePage {
  pageTitle = element(by.id('jhi-district-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  banglaInput = element(by.id('field_bangla'));
  webInput = element(by.id('field_web'));

  divisionSelect = element(by.id('field_division'));

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

  async divisionSelectLastOption(): Promise<void> {
    await this.divisionSelect.all(by.tagName('option')).last().click();
  }

  async divisionSelectOption(option: string): Promise<void> {
    await this.divisionSelect.sendKeys(option);
  }

  getDivisionSelect(): ElementFinder {
    return this.divisionSelect;
  }

  async getDivisionSelectedOption(): Promise<string> {
    return await this.divisionSelect.element(by.css('option:checked')).getText();
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

export class DistrictDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-district-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-district'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
