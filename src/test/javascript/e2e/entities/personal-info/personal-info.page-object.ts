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
  photoIdInput = element(by.id('field_photoId'));
  fatherNameInput = element(by.id('field_fatherName'));
  fatherNameBanglaInput = element(by.id('field_fatherNameBangla'));
  motherNameInput = element(by.id('field_motherName'));
  motherNameBanglaInput = element(by.id('field_motherNameBangla'));
  maritalStatusSelect = element(by.id('field_maritalStatus'));
  spouseNameInput = element(by.id('field_spouseName'));
  spouseNameBanglaInput = element(by.id('field_spouseNameBangla'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  nationalIdInput = element(by.id('field_nationalId'));
  nationalIdAttachmentInput = element(by.id('file_nationalIdAttachment'));
  nationalIdAttachmentIdInput = element(by.id('field_nationalIdAttachmentId'));
  birthRegistrationInput = element(by.id('field_birthRegistration'));
  birthRegistrationAttachmentInput = element(by.id('file_birthRegistrationAttachment'));
  birthRegistrationAttachmentIdInput = element(by.id('field_birthRegistrationAttachmentId'));
  heightInput = element(by.id('field_height'));
  genderSelect = element(by.id('field_gender'));
  religionSelect = element(by.id('field_religion'));
  bloodGroupSelect = element(by.id('field_bloodGroup'));
  emergencyContactInput = element(by.id('field_emergencyContact'));

  employeeSelect = element(by.id('field_employee'));

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

  async setPhotoIdInput(photoId: string): Promise<void> {
    await this.photoIdInput.sendKeys(photoId);
  }

  async getPhotoIdInput(): Promise<string> {
    return await this.photoIdInput.getAttribute('value');
  }

  async setFatherNameInput(fatherName: string): Promise<void> {
    await this.fatherNameInput.sendKeys(fatherName);
  }

  async getFatherNameInput(): Promise<string> {
    return await this.fatherNameInput.getAttribute('value');
  }

  async setFatherNameBanglaInput(fatherNameBangla: string): Promise<void> {
    await this.fatherNameBanglaInput.sendKeys(fatherNameBangla);
  }

  async getFatherNameBanglaInput(): Promise<string> {
    return await this.fatherNameBanglaInput.getAttribute('value');
  }

  async setMotherNameInput(motherName: string): Promise<void> {
    await this.motherNameInput.sendKeys(motherName);
  }

  async getMotherNameInput(): Promise<string> {
    return await this.motherNameInput.getAttribute('value');
  }

  async setMotherNameBanglaInput(motherNameBangla: string): Promise<void> {
    await this.motherNameBanglaInput.sendKeys(motherNameBangla);
  }

  async getMotherNameBanglaInput(): Promise<string> {
    return await this.motherNameBanglaInput.getAttribute('value');
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

  async setSpouseNameBanglaInput(spouseNameBangla: string): Promise<void> {
    await this.spouseNameBanglaInput.sendKeys(spouseNameBangla);
  }

  async getSpouseNameBanglaInput(): Promise<string> {
    return await this.spouseNameBanglaInput.getAttribute('value');
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

  async setNationalIdAttachmentInput(nationalIdAttachment: string): Promise<void> {
    await this.nationalIdAttachmentInput.sendKeys(nationalIdAttachment);
  }

  async getNationalIdAttachmentInput(): Promise<string> {
    return await this.nationalIdAttachmentInput.getAttribute('value');
  }

  async setNationalIdAttachmentIdInput(nationalIdAttachmentId: string): Promise<void> {
    await this.nationalIdAttachmentIdInput.sendKeys(nationalIdAttachmentId);
  }

  async getNationalIdAttachmentIdInput(): Promise<string> {
    return await this.nationalIdAttachmentIdInput.getAttribute('value');
  }

  async setBirthRegistrationInput(birthRegistration: string): Promise<void> {
    await this.birthRegistrationInput.sendKeys(birthRegistration);
  }

  async getBirthRegistrationInput(): Promise<string> {
    return await this.birthRegistrationInput.getAttribute('value');
  }

  async setBirthRegistrationAttachmentInput(birthRegistrationAttachment: string): Promise<void> {
    await this.birthRegistrationAttachmentInput.sendKeys(birthRegistrationAttachment);
  }

  async getBirthRegistrationAttachmentInput(): Promise<string> {
    return await this.birthRegistrationAttachmentInput.getAttribute('value');
  }

  async setBirthRegistrationAttachmentIdInput(birthRegistrationAttachmentId: string): Promise<void> {
    await this.birthRegistrationAttachmentIdInput.sendKeys(birthRegistrationAttachmentId);
  }

  async getBirthRegistrationAttachmentIdInput(): Promise<string> {
    return await this.birthRegistrationAttachmentIdInput.getAttribute('value');
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

  async setReligionSelect(religion: string): Promise<void> {
    await this.religionSelect.sendKeys(religion);
  }

  async getReligionSelect(): Promise<string> {
    return await this.religionSelect.element(by.css('option:checked')).getText();
  }

  async religionSelectLastOption(): Promise<void> {
    await this.religionSelect.all(by.tagName('option')).last().click();
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
