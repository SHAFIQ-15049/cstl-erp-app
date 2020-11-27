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

  streetInput = element(by.id('field_street'));
  areaInput = element(by.id('field_area'));
  postCodeInput = element(by.id('field_postCode'));
  addressTypeSelect = element(by.id('field_addressType'));

  divisionSelect = element(by.id('field_division'));
  districtSelect = element(by.id('field_district'));
  thanaSelect = element(by.id('field_thana'));
  employeeSelect = element(by.id('field_employee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setStreetInput(street: string): Promise<void> {
    await this.streetInput.sendKeys(street);
  }

  async getStreetInput(): Promise<string> {
    return await this.streetInput.getAttribute('value');
  }

  async setAreaInput(area: string): Promise<void> {
    await this.areaInput.sendKeys(area);
  }

  async getAreaInput(): Promise<string> {
    return await this.areaInput.getAttribute('value');
  }

  async setPostCodeInput(postCode: string): Promise<void> {
    await this.postCodeInput.sendKeys(postCode);
  }

  async getPostCodeInput(): Promise<string> {
    return await this.postCodeInput.getAttribute('value');
  }

  async setAddressTypeSelect(addressType: string): Promise<void> {
    await this.addressTypeSelect.sendKeys(addressType);
  }

  async getAddressTypeSelect(): Promise<string> {
    return await this.addressTypeSelect.element(by.css('option:checked')).getText();
  }

  async addressTypeSelectLastOption(): Promise<void> {
    await this.addressTypeSelect.all(by.tagName('option')).last().click();
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

  async thanaSelectLastOption(): Promise<void> {
    await this.thanaSelect.all(by.tagName('option')).last().click();
  }

  async thanaSelectOption(option: string): Promise<void> {
    await this.thanaSelect.sendKeys(option);
  }

  getThanaSelect(): ElementFinder {
    return this.thanaSelect;
  }

  async getThanaSelectedOption(): Promise<string> {
    return await this.thanaSelect.element(by.css('option:checked')).getText();
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
