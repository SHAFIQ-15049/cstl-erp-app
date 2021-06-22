import { element, by, ElementFinder } from 'protractor';

export class CustomerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-customer div table .btn-danger'));
  title = element.all(by.css('jhi-customer div h2#page-heading span')).first();
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

export class CustomerUpdatePage {
  pageTitle = element(by.id('jhi-customer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  fatherOrHusbandInput = element(by.id('field_fatherOrHusband'));
  addressInput = element(by.id('field_address'));
  sexSelect = element(by.id('field_sex'));
  phoneNoInput = element(by.id('field_phoneNo'));
  nationalityInput = element(by.id('field_nationality'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  guardiansNameInput = element(by.id('field_guardiansName'));
  chassisNoInput = element(by.id('field_chassisNo'));
  engineNoInput = element(by.id('field_engineNo'));
  yearsOfMfgInput = element(by.id('field_yearsOfMfg'));
  preRegnNoInput = element(by.id('field_preRegnNo'));
  poOrBankInput = element(by.id('field_poOrBank'));
  voterIdNoInput = element(by.id('field_voterIdNo'));
  voterIdAttachmentInput = element(by.id('file_voterIdAttachment'));
  passportAttachmentInput = element(by.id('file_passportAttachment'));
  birthCertificateAttachmentInput = element(by.id('file_birthCertificateAttachment'));
  gassOrWaterOrElectricityBillInput = element(by.id('file_gassOrWaterOrElectricityBill'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setFatherOrHusbandInput(fatherOrHusband: string): Promise<void> {
    await this.fatherOrHusbandInput.sendKeys(fatherOrHusband);
  }

  async getFatherOrHusbandInput(): Promise<string> {
    return await this.fatherOrHusbandInput.getAttribute('value');
  }

  async setAddressInput(address: string): Promise<void> {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput(): Promise<string> {
    return await this.addressInput.getAttribute('value');
  }

  async setSexSelect(sex: string): Promise<void> {
    await this.sexSelect.sendKeys(sex);
  }

  async getSexSelect(): Promise<string> {
    return await this.sexSelect.element(by.css('option:checked')).getText();
  }

  async sexSelectLastOption(): Promise<void> {
    await this.sexSelect.all(by.tagName('option')).last().click();
  }

  async setPhoneNoInput(phoneNo: string): Promise<void> {
    await this.phoneNoInput.sendKeys(phoneNo);
  }

  async getPhoneNoInput(): Promise<string> {
    return await this.phoneNoInput.getAttribute('value');
  }

  async setNationalityInput(nationality: string): Promise<void> {
    await this.nationalityInput.sendKeys(nationality);
  }

  async getNationalityInput(): Promise<string> {
    return await this.nationalityInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth: string): Promise<void> {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput(): Promise<string> {
    return await this.dateOfBirthInput.getAttribute('value');
  }

  async setGuardiansNameInput(guardiansName: string): Promise<void> {
    await this.guardiansNameInput.sendKeys(guardiansName);
  }

  async getGuardiansNameInput(): Promise<string> {
    return await this.guardiansNameInput.getAttribute('value');
  }

  async setChassisNoInput(chassisNo: string): Promise<void> {
    await this.chassisNoInput.sendKeys(chassisNo);
  }

  async getChassisNoInput(): Promise<string> {
    return await this.chassisNoInput.getAttribute('value');
  }

  async setEngineNoInput(engineNo: string): Promise<void> {
    await this.engineNoInput.sendKeys(engineNo);
  }

  async getEngineNoInput(): Promise<string> {
    return await this.engineNoInput.getAttribute('value');
  }

  async setYearsOfMfgInput(yearsOfMfg: string): Promise<void> {
    await this.yearsOfMfgInput.sendKeys(yearsOfMfg);
  }

  async getYearsOfMfgInput(): Promise<string> {
    return await this.yearsOfMfgInput.getAttribute('value');
  }

  async setPreRegnNoInput(preRegnNo: string): Promise<void> {
    await this.preRegnNoInput.sendKeys(preRegnNo);
  }

  async getPreRegnNoInput(): Promise<string> {
    return await this.preRegnNoInput.getAttribute('value');
  }

  async setPoOrBankInput(poOrBank: string): Promise<void> {
    await this.poOrBankInput.sendKeys(poOrBank);
  }

  async getPoOrBankInput(): Promise<string> {
    return await this.poOrBankInput.getAttribute('value');
  }

  async setVoterIdNoInput(voterIdNo: string): Promise<void> {
    await this.voterIdNoInput.sendKeys(voterIdNo);
  }

  async getVoterIdNoInput(): Promise<string> {
    return await this.voterIdNoInput.getAttribute('value');
  }

  async setVoterIdAttachmentInput(voterIdAttachment: string): Promise<void> {
    await this.voterIdAttachmentInput.sendKeys(voterIdAttachment);
  }

  async getVoterIdAttachmentInput(): Promise<string> {
    return await this.voterIdAttachmentInput.getAttribute('value');
  }

  async setPassportAttachmentInput(passportAttachment: string): Promise<void> {
    await this.passportAttachmentInput.sendKeys(passportAttachment);
  }

  async getPassportAttachmentInput(): Promise<string> {
    return await this.passportAttachmentInput.getAttribute('value');
  }

  async setBirthCertificateAttachmentInput(birthCertificateAttachment: string): Promise<void> {
    await this.birthCertificateAttachmentInput.sendKeys(birthCertificateAttachment);
  }

  async getBirthCertificateAttachmentInput(): Promise<string> {
    return await this.birthCertificateAttachmentInput.getAttribute('value');
  }

  async setGassOrWaterOrElectricityBillInput(gassOrWaterOrElectricityBill: string): Promise<void> {
    await this.gassOrWaterOrElectricityBillInput.sendKeys(gassOrWaterOrElectricityBill);
  }

  async getGassOrWaterOrElectricityBillInput(): Promise<string> {
    return await this.gassOrWaterOrElectricityBillInput.getAttribute('value');
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

export class CustomerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-customer-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-customer'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
