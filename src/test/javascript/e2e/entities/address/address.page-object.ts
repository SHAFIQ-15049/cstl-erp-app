import { element, by, ElementFinder } from 'protractor';

export class AddressComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-address div table .btn-danger'));
  title = element.all(by.css('jhi-address div h2#page-heading span')).first();
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

export class AddressUpdatePage {
  pageTitle = element(by.id('jhi-address-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  presentThanaTxtInput = element(by.id('field_presentThanaTxt'));
  presentStreetInput = element(by.id('field_presentStreet'));
  presentStreetBanglaInput = element(by.id('field_presentStreetBangla'));
  presentAreaInput = element(by.id('field_presentArea'));
  presentAreaBanglaInput = element(by.id('field_presentAreaBangla'));
  presentPostCodeInput = element(by.id('field_presentPostCode'));
  presentPostCodeBanglaInput = element(by.id('field_presentPostCodeBangla'));
  permanentThanaTxtInput = element(by.id('field_permanentThanaTxt'));
  permanentStreetInput = element(by.id('field_permanentStreet'));
  permanentStreetBanglaInput = element(by.id('field_permanentStreetBangla'));
  permanentAreaInput = element(by.id('field_permanentArea'));
  permanentAreaBanglaInput = element(by.id('field_permanentAreaBangla'));
  permanentPostCodeInput = element(by.id('field_permanentPostCode'));
  permenentPostCodeBanglaInput = element(by.id('field_permenentPostCodeBangla'));
  isSameInput = element(by.id('field_isSame'));

  employeeSelect = element(by.id('field_employee'));
  presentDivisionSelect = element(by.id('field_presentDivision'));
  presentDistrictSelect = element(by.id('field_presentDistrict'));
  presentThanaSelect = element(by.id('field_presentThana'));
  permanentDivisionSelect = element(by.id('field_permanentDivision'));
  permanentDistrictSelect = element(by.id('field_permanentDistrict'));
  permanentThanaSelect = element(by.id('field_permanentThana'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setPresentThanaTxtInput(presentThanaTxt: string): Promise<void> {
    await this.presentThanaTxtInput.sendKeys(presentThanaTxt);
  }

  async getPresentThanaTxtInput(): Promise<string> {
    return await this.presentThanaTxtInput.getAttribute('value');
  }

  async setPresentStreetInput(presentStreet: string): Promise<void> {
    await this.presentStreetInput.sendKeys(presentStreet);
  }

  async getPresentStreetInput(): Promise<string> {
    return await this.presentStreetInput.getAttribute('value');
  }

  async setPresentStreetBanglaInput(presentStreetBangla: string): Promise<void> {
    await this.presentStreetBanglaInput.sendKeys(presentStreetBangla);
  }

  async getPresentStreetBanglaInput(): Promise<string> {
    return await this.presentStreetBanglaInput.getAttribute('value');
  }

  async setPresentAreaInput(presentArea: string): Promise<void> {
    await this.presentAreaInput.sendKeys(presentArea);
  }

  async getPresentAreaInput(): Promise<string> {
    return await this.presentAreaInput.getAttribute('value');
  }

  async setPresentAreaBanglaInput(presentAreaBangla: string): Promise<void> {
    await this.presentAreaBanglaInput.sendKeys(presentAreaBangla);
  }

  async getPresentAreaBanglaInput(): Promise<string> {
    return await this.presentAreaBanglaInput.getAttribute('value');
  }

  async setPresentPostCodeInput(presentPostCode: string): Promise<void> {
    await this.presentPostCodeInput.sendKeys(presentPostCode);
  }

  async getPresentPostCodeInput(): Promise<string> {
    return await this.presentPostCodeInput.getAttribute('value');
  }

  async setPresentPostCodeBanglaInput(presentPostCodeBangla: string): Promise<void> {
    await this.presentPostCodeBanglaInput.sendKeys(presentPostCodeBangla);
  }

  async getPresentPostCodeBanglaInput(): Promise<string> {
    return await this.presentPostCodeBanglaInput.getAttribute('value');
  }

  async setPermanentThanaTxtInput(permanentThanaTxt: string): Promise<void> {
    await this.permanentThanaTxtInput.sendKeys(permanentThanaTxt);
  }

  async getPermanentThanaTxtInput(): Promise<string> {
    return await this.permanentThanaTxtInput.getAttribute('value');
  }

  async setPermanentStreetInput(permanentStreet: string): Promise<void> {
    await this.permanentStreetInput.sendKeys(permanentStreet);
  }

  async getPermanentStreetInput(): Promise<string> {
    return await this.permanentStreetInput.getAttribute('value');
  }

  async setPermanentStreetBanglaInput(permanentStreetBangla: string): Promise<void> {
    await this.permanentStreetBanglaInput.sendKeys(permanentStreetBangla);
  }

  async getPermanentStreetBanglaInput(): Promise<string> {
    return await this.permanentStreetBanglaInput.getAttribute('value');
  }

  async setPermanentAreaInput(permanentArea: string): Promise<void> {
    await this.permanentAreaInput.sendKeys(permanentArea);
  }

  async getPermanentAreaInput(): Promise<string> {
    return await this.permanentAreaInput.getAttribute('value');
  }

  async setPermanentAreaBanglaInput(permanentAreaBangla: string): Promise<void> {
    await this.permanentAreaBanglaInput.sendKeys(permanentAreaBangla);
  }

  async getPermanentAreaBanglaInput(): Promise<string> {
    return await this.permanentAreaBanglaInput.getAttribute('value');
  }

  async setPermanentPostCodeInput(permanentPostCode: string): Promise<void> {
    await this.permanentPostCodeInput.sendKeys(permanentPostCode);
  }

  async getPermanentPostCodeInput(): Promise<string> {
    return await this.permanentPostCodeInput.getAttribute('value');
  }

  async setPermenentPostCodeBanglaInput(permenentPostCodeBangla: string): Promise<void> {
    await this.permenentPostCodeBanglaInput.sendKeys(permenentPostCodeBangla);
  }

  async getPermenentPostCodeBanglaInput(): Promise<string> {
    return await this.permenentPostCodeBanglaInput.getAttribute('value');
  }

  getIsSameInput(): ElementFinder {
    return this.isSameInput;
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

  async presentDivisionSelectLastOption(): Promise<void> {
    await this.presentDivisionSelect.all(by.tagName('option')).last().click();
  }

  async presentDivisionSelectOption(option: string): Promise<void> {
    await this.presentDivisionSelect.sendKeys(option);
  }

  getPresentDivisionSelect(): ElementFinder {
    return this.presentDivisionSelect;
  }

  async getPresentDivisionSelectedOption(): Promise<string> {
    return await this.presentDivisionSelect.element(by.css('option:checked')).getText();
  }

  async presentDistrictSelectLastOption(): Promise<void> {
    await this.presentDistrictSelect.all(by.tagName('option')).last().click();
  }

  async presentDistrictSelectOption(option: string): Promise<void> {
    await this.presentDistrictSelect.sendKeys(option);
  }

  getPresentDistrictSelect(): ElementFinder {
    return this.presentDistrictSelect;
  }

  async getPresentDistrictSelectedOption(): Promise<string> {
    return await this.presentDistrictSelect.element(by.css('option:checked')).getText();
  }

  async presentThanaSelectLastOption(): Promise<void> {
    await this.presentThanaSelect.all(by.tagName('option')).last().click();
  }

  async presentThanaSelectOption(option: string): Promise<void> {
    await this.presentThanaSelect.sendKeys(option);
  }

  getPresentThanaSelect(): ElementFinder {
    return this.presentThanaSelect;
  }

  async getPresentThanaSelectedOption(): Promise<string> {
    return await this.presentThanaSelect.element(by.css('option:checked')).getText();
  }

  async permanentDivisionSelectLastOption(): Promise<void> {
    await this.permanentDivisionSelect.all(by.tagName('option')).last().click();
  }

  async permanentDivisionSelectOption(option: string): Promise<void> {
    await this.permanentDivisionSelect.sendKeys(option);
  }

  getPermanentDivisionSelect(): ElementFinder {
    return this.permanentDivisionSelect;
  }

  async getPermanentDivisionSelectedOption(): Promise<string> {
    return await this.permanentDivisionSelect.element(by.css('option:checked')).getText();
  }

  async permanentDistrictSelectLastOption(): Promise<void> {
    await this.permanentDistrictSelect.all(by.tagName('option')).last().click();
  }

  async permanentDistrictSelectOption(option: string): Promise<void> {
    await this.permanentDistrictSelect.sendKeys(option);
  }

  getPermanentDistrictSelect(): ElementFinder {
    return this.permanentDistrictSelect;
  }

  async getPermanentDistrictSelectedOption(): Promise<string> {
    return await this.permanentDistrictSelect.element(by.css('option:checked')).getText();
  }

  async permanentThanaSelectLastOption(): Promise<void> {
    await this.permanentThanaSelect.all(by.tagName('option')).last().click();
  }

  async permanentThanaSelectOption(option: string): Promise<void> {
    await this.permanentThanaSelect.sendKeys(option);
  }

  getPermanentThanaSelect(): ElementFinder {
    return this.permanentThanaSelect;
  }

  async getPermanentThanaSelectedOption(): Promise<string> {
    return await this.permanentThanaSelect.element(by.css('option:checked')).getText();
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

export class AddressDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-address-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-address'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
