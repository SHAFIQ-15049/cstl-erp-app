import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DesignationComponentsPage, DesignationDeleteDialog, DesignationUpdatePage } from './designation.page-object';

const expect = chai.expect;

describe('Designation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let designationComponentsPage: DesignationComponentsPage;
  let designationUpdatePage: DesignationUpdatePage;
  let designationDeleteDialog: DesignationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Designations', async () => {
    await navBarPage.goToEntity('designation');
    designationComponentsPage = new DesignationComponentsPage();
    await browser.wait(ec.visibilityOf(designationComponentsPage.title), 5000);
    expect(await designationComponentsPage.getTitle()).to.eq('Designations');
    await browser.wait(
      ec.or(ec.visibilityOf(designationComponentsPage.entities), ec.visibilityOf(designationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Designation page', async () => {
    await designationComponentsPage.clickOnCreateButton();
    designationUpdatePage = new DesignationUpdatePage();
    expect(await designationUpdatePage.getPageTitle()).to.eq('Create or edit a Designation');
    await designationUpdatePage.cancel();
  });

  it('should create and save Designations', async () => {
    const nbButtonsBeforeCreate = await designationComponentsPage.countDeleteButtons();

    await designationComponentsPage.clickOnCreateButton();

    await promise.all([
      designationUpdatePage.categorySelectLastOption(),
      designationUpdatePage.setNameInput('name'),
      designationUpdatePage.setShortNameInput('shortName'),
      designationUpdatePage.setNameInBanglaInput('nameInBangla'),
      designationUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await designationUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await designationUpdatePage.getShortNameInput()).to.eq('shortName', 'Expected ShortName value to be equals to shortName');
    expect(await designationUpdatePage.getNameInBanglaInput()).to.eq(
      'nameInBangla',
      'Expected NameInBangla value to be equals to nameInBangla'
    );
    expect(await designationUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await designationUpdatePage.save();
    expect(await designationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await designationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Designation', async () => {
    const nbButtonsBeforeDelete = await designationComponentsPage.countDeleteButtons();
    await designationComponentsPage.clickOnLastDeleteButton();

    designationDeleteDialog = new DesignationDeleteDialog();
    expect(await designationDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Designation?');
    await designationDeleteDialog.clickOnConfirmButton();

    expect(await designationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
