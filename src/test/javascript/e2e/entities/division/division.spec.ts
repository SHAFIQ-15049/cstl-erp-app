import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DivisionComponentsPage, DivisionDeleteDialog, DivisionUpdatePage } from './division.page-object';

const expect = chai.expect;

describe('Division e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let divisionComponentsPage: DivisionComponentsPage;
  let divisionUpdatePage: DivisionUpdatePage;
  let divisionDeleteDialog: DivisionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Divisions', async () => {
    await navBarPage.goToEntity('division');
    divisionComponentsPage = new DivisionComponentsPage();
    await browser.wait(ec.visibilityOf(divisionComponentsPage.title), 5000);
    expect(await divisionComponentsPage.getTitle()).to.eq('Divisions');
    await browser.wait(ec.or(ec.visibilityOf(divisionComponentsPage.entities), ec.visibilityOf(divisionComponentsPage.noResult)), 1000);
  });

  it('should load create Division page', async () => {
    await divisionComponentsPage.clickOnCreateButton();
    divisionUpdatePage = new DivisionUpdatePage();
    expect(await divisionUpdatePage.getPageTitle()).to.eq('Create or edit a Division');
    await divisionUpdatePage.cancel();
  });

  it('should create and save Divisions', async () => {
    const nbButtonsBeforeCreate = await divisionComponentsPage.countDeleteButtons();

    await divisionComponentsPage.clickOnCreateButton();

    await promise.all([
      divisionUpdatePage.setNameInput('name'),
      divisionUpdatePage.setBanglaInput('bangla'),
      divisionUpdatePage.setWebInput('web'),
    ]);

    expect(await divisionUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await divisionUpdatePage.getBanglaInput()).to.eq('bangla', 'Expected Bangla value to be equals to bangla');
    expect(await divisionUpdatePage.getWebInput()).to.eq('web', 'Expected Web value to be equals to web');

    await divisionUpdatePage.save();
    expect(await divisionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await divisionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Division', async () => {
    const nbButtonsBeforeDelete = await divisionComponentsPage.countDeleteButtons();
    await divisionComponentsPage.clickOnLastDeleteButton();

    divisionDeleteDialog = new DivisionDeleteDialog();
    expect(await divisionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Division?');
    await divisionDeleteDialog.clickOnConfirmButton();

    expect(await divisionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
