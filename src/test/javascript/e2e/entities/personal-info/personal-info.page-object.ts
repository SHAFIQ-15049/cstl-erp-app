import { element, by, ElementFinder } from 'protractor';

export class PersonalInfoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-personal-info div table .btn-danger'));
  title = element.all(by.css('jhi-personal-info div h2#page-heading span')).first();
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

export class PersonalInfoUpdatePage {
  pageTitle = element(by.id('jhi-personal-info-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  banglaNameInput = element(by.id('field_banglaName'));
  photoInput = element(by.id('file_photo'));
  fatherNameInput = element(by.id('field_fatherName'));
  motherNameInput = element(by.id('field_motherName'));
  maritalStatusSelect = element(by.id('field_maritalStatus'));
  spouseNameInput = element(by.id('field_spouseName'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  nationalIdInput = element(by.id('field_nationalId'));
  birthRegistrationInput = element(by.id('field_birthRegistration'));
  heightInput = element(by.id('field_height'));
  genderSelect = element(by.id('field_gender'));
  bloodGroupSelect = element(by.id('field_bloodGroup'));
  emergencyContactInput = element(by.id('field_emergencyContact'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setBanglaNameInput(banglaName: string): Promise<void> {
    await this.banglaNameInput.sendKeys(banglaName);
  }

  async getBanglaNameInput(): Promise<string> {
    return await this.banglaNameInput.getAttribute('value');
  }

  async setPhotoInput(photo: string): Promise<void> {
    await this.photoInput.sendKeys(photo);
  }

  async getPhotoInput(): Promise<string> {
    return await this.photoInput.getAttribute('value');
  }

  async setFatherNameInput(fatherName: string): Promise<void> {
    await this.fatherNameInput.sendKeys(fatherName);
  }

  async getFatherNameInput(): Promise<string> {
    return await this.fatherNameInput.getAttribute('value');
  }

  async setMotherNameInput(motherName: string): Promise<void> {
    await this.motherNameInput.sendKeys(motherName);
  }

  async getMotherNameInput(): Promise<string> {
    return await this.motherNameInput.getAttribute('value');
  }

  async setMaritalStatusSelect(maritalStatus: string): Promise<void> {
    await this.maritalStatusSelect.sendKeys(maritalStatus);
  }

  async getMaritalStatusSelect(): Promise<string> {
    return await this.maritalStatusSelect.element(by.css('option:checked')).getText();
  }

  async maritalStatusSelectLastOption(): Promise<void> {
    await this.maritalStatusSelect.all(by.tagName('option')).last().click();
  }

  async setSpouseNameInput(spouseName: string): Promise<void> {
    await this.spouseNameInput.sendKeys(spouseName);
  }

  async getSpouseNameInput(): Promise<string> {
    return await this.spouseNameInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth: string): Promise<void> {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput(): Promise<string> {
    return await this.dateOfBirthInput.getAttribute('value');
  }

  async setNationalIdInput(nationalId: string): Promise<void> {
    await this.nationalIdInput.sendKeys(nationalId);
  }

  async getNationalIdInput(): Promise<string> {
    return await this.nationalIdInput.getAttribute('value');
  }

  async setBirthRegistrationInput(birthRegistration: string): Promise<void> {
    await this.birthRegistrationInput.sendKeys(birthRegistration);
  }

  async getBirthRegistrationInput(): Promise<string> {
    return await this.birthRegistrationInput.getAttribute('value');
  }

  async setHeightInput(height: string): Promise<void> {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput(): Promise<string> {
    return await this.heightInput.getAttribute('value');
  }

  async setGenderSelect(gender: string): Promise<void> {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect(): Promise<string> {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(): Promise<void> {
    await this.genderSelect.all(by.tagName('option')).last().click();
  }

  async setBloodGroupSelect(bloodGroup: string): Promise<void> {
    await this.bloodGroupSelect.sendKeys(bloodGroup);
  }

  async getBloodGroupSelect(): Promise<string> {
    return await this.bloodGroupSelect.element(by.css('option:checked')).getText();
  }

  async bloodGroupSelectLastOption(): Promise<void> {
    await this.bloodGroupSelect.all(by.tagName('option')).last().click();
  }

  async setEmergencyContactInput(emergencyContact: string): Promise<void> {
    await this.emergencyContactInput.sendKeys(emergencyContact);
  }

  async getEmergencyContactInput(): Promise<string> {
    return await this.emergencyContactInput.getAttribute('value');
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

export class PersonalInfoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-personalInfo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-personalInfo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
