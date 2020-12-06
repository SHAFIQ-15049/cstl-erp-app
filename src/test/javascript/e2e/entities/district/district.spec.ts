import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DistrictComponentsPage, DistrictDeleteDialog, DistrictUpdatePage } from './district.page-object';

const expect = chai.expect;

describe('District e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let districtComponentsPage: DistrictComponentsPage;
  let districtUpdatePage: DistrictUpdatePage;
  let districtDeleteDialog: DistrictDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Districts', async () => {
    await navBarPage.goToEntity('district');
    districtComponentsPage = new DistrictComponentsPage();
    await browser.wait(ec.visibilityOf(districtComponentsPage.title), 5000);
    expect(await districtComponentsPage.getTitle()).to.eq('Districts');
    await browser.wait(ec.or(ec.visibilityOf(districtComponentsPage.entities), ec.visibilityOf(districtComponentsPage.noResult)), 1000);
  });

  it('should load create District page', async () => {
    await districtComponentsPage.clickOnCreateButton();
    districtUpdatePage = new DistrictUpdatePage();
    expect(await districtUpdatePage.getPageTitle()).to.eq('Create or edit a District');
    await districtUpdatePage.cancel();
  });

  it('should create and save Districts', async () => {
    const nbButtonsBeforeCreate = await districtComponentsPage.countDeleteButtons();

    await districtComponentsPage.clickOnCreateButton();

    await promise.all([
      districtUpdatePage.setNameInput('name'),
      districtUpdatePage.setBanglaInput('bangla'),
      districtUpdatePage.setWebInput('web'),
      districtUpdatePage.divisionSelectLastOption(),
    ]);

    expect(await districtUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await districtUpdatePage.getBanglaInput()).to.eq('bangla', 'Expected Bangla value to be equals to bangla');
    expect(await districtUpdatePage.getWebInput()).to.eq('web', 'Expected Web value to be equals to web');

    await districtUpdatePage.save();
    expect(await districtUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await districtComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last District', async () => {
    const nbButtonsBeforeDelete = await districtComponentsPage.countDeleteButtons();
    await districtComponentsPage.clickOnLastDeleteButton();

    districtDeleteDialog = new DistrictDeleteDialog();
    expect(await districtDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this District?');
    await districtDeleteDialog.clickOnConfirmButton();

    expect(await districtComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
