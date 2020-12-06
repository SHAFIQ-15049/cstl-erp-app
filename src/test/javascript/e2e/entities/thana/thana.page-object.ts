import { element, by, ElementFinder } from 'protractor';

export class ThanaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-thana div table .btn-danger'));
  title = element.all(by.css('jhi-thana div h2#page-heading span')).first();
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

export class ThanaUpdatePage {
  pageTitle = element(by.id('jhi-thana-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  banglaInput = element(by.id('field_bangla'));
  webInput = element(by.id('field_web'));

  districtSelect = element(by.id('field_district'));

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

  async districtSelectLastOption(): Promise<void> {
    await this.districtSelect.all(by.tagName('option')).last().click();
  }

  async districtSelectOption(option: string): Promise<void> {
    await this.districtSelect.sendKeys(option);
  }

  getDistrictSelect(): ElementFinder {
    return this.districtSelect;
  }

  async getDistrictSelectedOption(): Promise<string> {
    return await this.districtSelect.element(by.css('option:checked')).getText();
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

export class ThanaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-thana-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-thana'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
