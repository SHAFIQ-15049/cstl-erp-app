import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CompanyComponentsPage, CompanyDeleteDialog, CompanyUpdatePage } from './company.page-object';

const expect = chai.expect;

describe('Company e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let companyComponentsPage: CompanyComponentsPage;
  let companyUpdatePage: CompanyUpdatePage;
  let companyDeleteDialog: CompanyDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Companies', async () => {
    await navBarPage.goToEntity('company');
    companyComponentsPage = new CompanyComponentsPage();
    await browser.wait(ec.visibilityOf(companyComponentsPage.title), 5000);
    expect(await companyComponentsPage.getTitle()).to.eq('Companies');
    await browser.wait(ec.or(ec.visibilityOf(companyComponentsPage.entities), ec.visibilityOf(companyComponentsPage.noResult)), 1000);
  });

  it('should load create Company page', async () => {
    await companyComponentsPage.clickOnCreateButton();
    companyUpdatePage = new CompanyUpdatePage();
    expect(await companyUpdatePage.getPageTitle()).to.eq('Create or edit a Company');
    await companyUpdatePage.cancel();
  });

  it('should create and save Companies', async () => {
    const nbButtonsBeforeCreate = await companyComponentsPage.countDeleteButtons();

    await companyComponentsPage.clickOnCreateButton();

    await promise.all([
      companyUpdatePage.setNameInput('name'),
      companyUpdatePage.setShortNameInput('shortName'),
      companyUpdatePage.setNameInBanglaInput('nameInBangla'),
      companyUpdatePage.setDescriptionInput('description'),
      companyUpdatePage.setAddressInput('address'),
      companyUpdatePage.setPhoneInput('phone'),
    ]);

    expect(await companyUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await companyUpdatePage.getShortNameInput()).to.eq('shortName', 'Expected ShortName value to be equals to shortName');
    expect(await companyUpdatePage.getNameInBanglaInput()).to.eq(
      'nameInBangla',
      'Expected NameInBangla value to be equals to nameInBangla'
    );
    expect(await companyUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await companyUpdatePage.getAddressInput()).to.eq('address', 'Expected Address value to be equals to address');
    expect(await companyUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');

    await companyUpdatePage.save();
    expect(await companyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await companyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Company', async () => {
    const nbButtonsBeforeDelete = await companyComponentsPage.countDeleteButtons();
    await companyComponentsPage.clickOnLastDeleteButton();

    companyDeleteDialog = new CompanyDeleteDialog();
    expect(await companyDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Company?');
    await companyDeleteDialog.clickOnConfirmButton();

    expect(await companyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
