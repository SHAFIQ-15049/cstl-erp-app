import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IdCardManagementComponentsPage, IdCardManagementDeleteDialog, IdCardManagementUpdatePage } from './id-card-management.page-object';

const expect = chai.expect;

describe('IdCardManagement e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let idCardManagementComponentsPage: IdCardManagementComponentsPage;
  let idCardManagementUpdatePage: IdCardManagementUpdatePage;
  let idCardManagementDeleteDialog: IdCardManagementDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IdCardManagements', async () => {
    await navBarPage.goToEntity('id-card-management');
    idCardManagementComponentsPage = new IdCardManagementComponentsPage();
    await browser.wait(ec.visibilityOf(idCardManagementComponentsPage.title), 5000);
    expect(await idCardManagementComponentsPage.getTitle()).to.eq('Id Card Managements');
    await browser.wait(
      ec.or(ec.visibilityOf(idCardManagementComponentsPage.entities), ec.visibilityOf(idCardManagementComponentsPage.noResult)),
      1000
    );
  });

  it('should load create IdCardManagement page', async () => {
    await idCardManagementComponentsPage.clickOnCreateButton();
    idCardManagementUpdatePage = new IdCardManagementUpdatePage();
    expect(await idCardManagementUpdatePage.getPageTitle()).to.eq('Create or edit a Id Card Management');
    await idCardManagementUpdatePage.cancel();
  });

  it('should create and save IdCardManagements', async () => {
    const nbButtonsBeforeCreate = await idCardManagementComponentsPage.countDeleteButtons();

    await idCardManagementComponentsPage.clickOnCreateButton();

    await promise.all([
      idCardManagementUpdatePage.setCardNoInput('cardNo'),
      idCardManagementUpdatePage.setIssueDateInput('2000-12-31'),
      idCardManagementUpdatePage.setTicketNoInput('ticketNo'),
      idCardManagementUpdatePage.setValidTillInput('2000-12-31'),
      idCardManagementUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await idCardManagementUpdatePage.getCardNoInput()).to.eq('cardNo', 'Expected CardNo value to be equals to cardNo');
    expect(await idCardManagementUpdatePage.getIssueDateInput()).to.eq('2000-12-31', 'Expected issueDate value to be equals to 2000-12-31');
    expect(await idCardManagementUpdatePage.getTicketNoInput()).to.eq('ticketNo', 'Expected TicketNo value to be equals to ticketNo');
    expect(await idCardManagementUpdatePage.getValidTillInput()).to.eq('2000-12-31', 'Expected validTill value to be equals to 2000-12-31');

    await idCardManagementUpdatePage.save();
    expect(await idCardManagementUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await idCardManagementComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last IdCardManagement', async () => {
    const nbButtonsBeforeDelete = await idCardManagementComponentsPage.countDeleteButtons();
    await idCardManagementComponentsPage.clickOnLastDeleteButton();

    idCardManagementDeleteDialog = new IdCardManagementDeleteDialog();
    expect(await idCardManagementDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Id Card Management?');
    await idCardManagementDeleteDialog.clickOnConfirmButton();

    expect(await idCardManagementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
