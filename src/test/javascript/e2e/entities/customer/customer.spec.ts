import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CustomerComponentsPage, CustomerDeleteDialog, CustomerUpdatePage } from './customer.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Customer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let customerComponentsPage: CustomerComponentsPage;
  let customerUpdatePage: CustomerUpdatePage;
  let customerDeleteDialog: CustomerDeleteDialog;
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

  it('should load Customers', async () => {
    await navBarPage.goToEntity('customer');
    customerComponentsPage = new CustomerComponentsPage();
    await browser.wait(ec.visibilityOf(customerComponentsPage.title), 5000);
    expect(await customerComponentsPage.getTitle()).to.eq('Customers');
    await browser.wait(ec.or(ec.visibilityOf(customerComponentsPage.entities), ec.visibilityOf(customerComponentsPage.noResult)), 1000);
  });

  it('should load create Customer page', async () => {
    await customerComponentsPage.clickOnCreateButton();
    customerUpdatePage = new CustomerUpdatePage();
    expect(await customerUpdatePage.getPageTitle()).to.eq('Create or edit a Customer');
    await customerUpdatePage.cancel();
  });

  it('should create and save Customers', async () => {
    const nbButtonsBeforeCreate = await customerComponentsPage.countDeleteButtons();

    await customerComponentsPage.clickOnCreateButton();

    await promise.all([
      customerUpdatePage.setNameInput('name'),
      customerUpdatePage.setFatherOrHusbandInput('fatherOrHusband'),
      customerUpdatePage.setAddressInput('address'),
      customerUpdatePage.sexSelectLastOption(),
      customerUpdatePage.setPhoneNoInput('phoneNo'),
      customerUpdatePage.setNationalityInput('nationality'),
      customerUpdatePage.setDateOfBirthInput('2000-12-31'),
      customerUpdatePage.setGuardiansNameInput('guardiansName'),
      customerUpdatePage.setChassisNoInput('chassisNo'),
      customerUpdatePage.setEngineNoInput('engineNo'),
      customerUpdatePage.setYearsOfMfgInput('5'),
      customerUpdatePage.setPreRegnNoInput('preRegnNo'),
      customerUpdatePage.setPoOrBankInput('poOrBank'),
      customerUpdatePage.setVoterIdNoInput('voterIdNo'),
      customerUpdatePage.setVoterIdAttachmentInput(absolutePath),
      customerUpdatePage.setPassportAttachmentInput(absolutePath),
      customerUpdatePage.setBirthCertificateAttachmentInput(absolutePath),
      customerUpdatePage.setGassOrWaterOrElectricityBillInput(absolutePath),
    ]);

    expect(await customerUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await customerUpdatePage.getFatherOrHusbandInput()).to.eq(
      'fatherOrHusband',
      'Expected FatherOrHusband value to be equals to fatherOrHusband'
    );
    expect(await customerUpdatePage.getAddressInput()).to.eq('address', 'Expected Address value to be equals to address');
    expect(await customerUpdatePage.getPhoneNoInput()).to.eq('phoneNo', 'Expected PhoneNo value to be equals to phoneNo');
    expect(await customerUpdatePage.getNationalityInput()).to.eq('nationality', 'Expected Nationality value to be equals to nationality');
    expect(await customerUpdatePage.getDateOfBirthInput()).to.eq('2000-12-31', 'Expected dateOfBirth value to be equals to 2000-12-31');
    expect(await customerUpdatePage.getGuardiansNameInput()).to.eq(
      'guardiansName',
      'Expected GuardiansName value to be equals to guardiansName'
    );
    expect(await customerUpdatePage.getChassisNoInput()).to.eq('chassisNo', 'Expected ChassisNo value to be equals to chassisNo');
    expect(await customerUpdatePage.getEngineNoInput()).to.eq('engineNo', 'Expected EngineNo value to be equals to engineNo');
    expect(await customerUpdatePage.getYearsOfMfgInput()).to.eq('5', 'Expected yearsOfMfg value to be equals to 5');
    expect(await customerUpdatePage.getPreRegnNoInput()).to.eq('preRegnNo', 'Expected PreRegnNo value to be equals to preRegnNo');
    expect(await customerUpdatePage.getPoOrBankInput()).to.eq('poOrBank', 'Expected PoOrBank value to be equals to poOrBank');
    expect(await customerUpdatePage.getVoterIdNoInput()).to.eq('voterIdNo', 'Expected VoterIdNo value to be equals to voterIdNo');
    expect(await customerUpdatePage.getVoterIdAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected VoterIdAttachment value to be end with ' + fileNameToUpload
    );
    expect(await customerUpdatePage.getPassportAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected PassportAttachment value to be end with ' + fileNameToUpload
    );
    expect(await customerUpdatePage.getBirthCertificateAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected BirthCertificateAttachment value to be end with ' + fileNameToUpload
    );
    expect(await customerUpdatePage.getGassOrWaterOrElectricityBillInput()).to.endsWith(
      fileNameToUpload,
      'Expected GassOrWaterOrElectricityBill value to be end with ' + fileNameToUpload
    );

    await customerUpdatePage.save();
    expect(await customerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Customer', async () => {
    const nbButtonsBeforeDelete = await customerComponentsPage.countDeleteButtons();
    await customerComponentsPage.clickOnLastDeleteButton();

    customerDeleteDialog = new CustomerDeleteDialog();
    expect(await customerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Customer?');
    await customerDeleteDialog.clickOnConfirmButton();

    expect(await customerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
