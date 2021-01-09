import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PersonalInfoComponentsPage, PersonalInfoDeleteDialog, PersonalInfoUpdatePage } from './personal-info.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('PersonalInfo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let personalInfoComponentsPage: PersonalInfoComponentsPage;
  let personalInfoUpdatePage: PersonalInfoUpdatePage;
  let personalInfoDeleteDialog: PersonalInfoDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PersonalInfos', async () => {
    await navBarPage.goToEntity('personal-info');
    personalInfoComponentsPage = new PersonalInfoComponentsPage();
    await browser.wait(ec.visibilityOf(personalInfoComponentsPage.title), 5000);
    expect(await personalInfoComponentsPage.getTitle()).to.eq('Personal Infos');
    await browser.wait(
      ec.or(ec.visibilityOf(personalInfoComponentsPage.entities), ec.visibilityOf(personalInfoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PersonalInfo page', async () => {
    await personalInfoComponentsPage.clickOnCreateButton();
    personalInfoUpdatePage = new PersonalInfoUpdatePage();
    expect(await personalInfoUpdatePage.getPageTitle()).to.eq('Create or edit a Personal Info');
    await personalInfoUpdatePage.cancel();
  });

  it('should create and save PersonalInfos', async () => {
    const nbButtonsBeforeCreate = await personalInfoComponentsPage.countDeleteButtons();

    await personalInfoComponentsPage.clickOnCreateButton();

    await promise.all([
      personalInfoUpdatePage.setNameInput('name'),
      personalInfoUpdatePage.setBanglaNameInput('banglaName'),
      personalInfoUpdatePage.setPhotoInput(absolutePath),
      personalInfoUpdatePage.setPhotoIdInput('photoId'),
      personalInfoUpdatePage.setFatherNameInput('fatherName'),
      personalInfoUpdatePage.setFatherNameBanglaInput('fatherNameBangla'),
      personalInfoUpdatePage.setMotherNameInput('motherName'),
      personalInfoUpdatePage.setMotherNameBanglaInput('motherNameBangla'),
      personalInfoUpdatePage.maritalStatusSelectLastOption(),
      personalInfoUpdatePage.setSpouseNameInput('spouseName'),
      personalInfoUpdatePage.setSpouseNameBanglaInput('spouseNameBangla'),
      personalInfoUpdatePage.setDateOfBirthInput('2000-12-31'),
      personalInfoUpdatePage.setNationalIdInput('nationalId'),
      personalInfoUpdatePage.setNationalIdAttachmentInput(absolutePath),
      personalInfoUpdatePage.setNationalIdAttachmentIdInput('nationalIdAttachmentId'),
      personalInfoUpdatePage.setBirthRegistrationInput('birthRegistration'),
      personalInfoUpdatePage.setBirthRegistrationAttachmentInput(absolutePath),
      personalInfoUpdatePage.setBirthRegistrationAttachmentIdInput('birthRegistrationAttachmentId'),
      personalInfoUpdatePage.setHeightInput('5'),
      personalInfoUpdatePage.genderSelectLastOption(),
      personalInfoUpdatePage.religionSelectLastOption(),
      personalInfoUpdatePage.bloodGroupSelectLastOption(),
      personalInfoUpdatePage.setEmergencyContactInput('emergencyContact'),
      personalInfoUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await personalInfoUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await personalInfoUpdatePage.getBanglaNameInput()).to.eq('banglaName', 'Expected BanglaName value to be equals to banglaName');
    expect(await personalInfoUpdatePage.getPhotoInput()).to.endsWith(
      fileNameToUpload,
      'Expected Photo value to be end with ' + fileNameToUpload
    );
    expect(await personalInfoUpdatePage.getPhotoIdInput()).to.eq('photoId', 'Expected PhotoId value to be equals to photoId');
    expect(await personalInfoUpdatePage.getFatherNameInput()).to.eq('fatherName', 'Expected FatherName value to be equals to fatherName');
    expect(await personalInfoUpdatePage.getFatherNameBanglaInput()).to.eq(
      'fatherNameBangla',
      'Expected FatherNameBangla value to be equals to fatherNameBangla'
    );
    expect(await personalInfoUpdatePage.getMotherNameInput()).to.eq('motherName', 'Expected MotherName value to be equals to motherName');
    expect(await personalInfoUpdatePage.getMotherNameBanglaInput()).to.eq(
      'motherNameBangla',
      'Expected MotherNameBangla value to be equals to motherNameBangla'
    );
    expect(await personalInfoUpdatePage.getSpouseNameInput()).to.eq('spouseName', 'Expected SpouseName value to be equals to spouseName');
    expect(await personalInfoUpdatePage.getSpouseNameBanglaInput()).to.eq(
      'spouseNameBangla',
      'Expected SpouseNameBangla value to be equals to spouseNameBangla'
    );
    expect(await personalInfoUpdatePage.getDateOfBirthInput()).to.eq('2000-12-31', 'Expected dateOfBirth value to be equals to 2000-12-31');
    expect(await personalInfoUpdatePage.getNationalIdInput()).to.eq('nationalId', 'Expected NationalId value to be equals to nationalId');
    expect(await personalInfoUpdatePage.getNationalIdAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected NationalIdAttachment value to be end with ' + fileNameToUpload
    );
    expect(await personalInfoUpdatePage.getNationalIdAttachmentIdInput()).to.eq(
      'nationalIdAttachmentId',
      'Expected NationalIdAttachmentId value to be equals to nationalIdAttachmentId'
    );
    expect(await personalInfoUpdatePage.getBirthRegistrationInput()).to.eq(
      'birthRegistration',
      'Expected BirthRegistration value to be equals to birthRegistration'
    );
    expect(await personalInfoUpdatePage.getBirthRegistrationAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected BirthRegistrationAttachment value to be end with ' + fileNameToUpload
    );
    expect(await personalInfoUpdatePage.getBirthRegistrationAttachmentIdInput()).to.eq(
      'birthRegistrationAttachmentId',
      'Expected BirthRegistrationAttachmentId value to be equals to birthRegistrationAttachmentId'
    );
    expect(await personalInfoUpdatePage.getHeightInput()).to.eq('5', 'Expected height value to be equals to 5');
    expect(await personalInfoUpdatePage.getEmergencyContactInput()).to.eq(
      'emergencyContact',
      'Expected EmergencyContact value to be equals to emergencyContact'
    );

    await personalInfoUpdatePage.save();
    expect(await personalInfoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await personalInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PersonalInfo', async () => {
    const nbButtonsBeforeDelete = await personalInfoComponentsPage.countDeleteButtons();
    await personalInfoComponentsPage.clickOnLastDeleteButton();

    personalInfoDeleteDialog = new PersonalInfoDeleteDialog();
    expect(await personalInfoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Personal Info?');
    await personalInfoDeleteDialog.clickOnConfirmButton();

    expect(await personalInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
