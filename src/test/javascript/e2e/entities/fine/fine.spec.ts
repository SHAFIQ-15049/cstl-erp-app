import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FineComponentsPage, FineDeleteDialog, FineUpdatePage } from './fine.page-object';

const expect = chai.expect;

describe('Fine e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fineComponentsPage: FineComponentsPage;
  let fineUpdatePage: FineUpdatePage;
  let fineDeleteDialog: FineDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Fines', async () => {
    await navBarPage.goToEntity('fine');
    fineComponentsPage = new FineComponentsPage();
    await browser.wait(ec.visibilityOf(fineComponentsPage.title), 5000);
    expect(await fineComponentsPage.getTitle()).to.eq('Fines');
    await browser.wait(ec.or(ec.visibilityOf(fineComponentsPage.entities), ec.visibilityOf(fineComponentsPage.noResult)), 1000);
  });

  it('should load create Fine page', async () => {
    await fineComponentsPage.clickOnCreateButton();
    fineUpdatePage = new FineUpdatePage();
    expect(await fineUpdatePage.getPageTitle()).to.eq('Create or edit a Fine');
    await fineUpdatePage.cancel();
  });

  it('should create and save Fines', async () => {
    const nbButtonsBeforeCreate = await fineComponentsPage.countDeleteButtons();

    await fineComponentsPage.clickOnCreateButton();

    await promise.all([
      fineUpdatePage.setFinedOnInput('2000-12-31'),
      fineUpdatePage.setReasonInput('reason'),
      fineUpdatePage.setAmountInput('5'),
      fineUpdatePage.setFinePercentageInput('5'),
      fineUpdatePage.setMonthlyFineAmountInput('5'),
      fineUpdatePage.paymentStatusSelectLastOption(),
      fineUpdatePage.setAmountPaidInput('5'),
      fineUpdatePage.setAmountLeftInput('5'),
      fineUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await fineUpdatePage.getFinedOnInput()).to.eq('2000-12-31', 'Expected finedOn value to be equals to 2000-12-31');
    expect(await fineUpdatePage.getReasonInput()).to.eq('reason', 'Expected Reason value to be equals to reason');
    expect(await fineUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await fineUpdatePage.getFinePercentageInput()).to.eq('5', 'Expected finePercentage value to be equals to 5');
    expect(await fineUpdatePage.getMonthlyFineAmountInput()).to.eq('5', 'Expected monthlyFineAmount value to be equals to 5');
    expect(await fineUpdatePage.getAmountPaidInput()).to.eq('5', 'Expected amountPaid value to be equals to 5');
    expect(await fineUpdatePage.getAmountLeftInput()).to.eq('5', 'Expected amountLeft value to be equals to 5');

    await fineUpdatePage.save();
    expect(await fineUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Fine', async () => {
    const nbButtonsBeforeDelete = await fineComponentsPage.countDeleteButtons();
    await fineComponentsPage.clickOnLastDeleteButton();

    fineDeleteDialog = new FineDeleteDialog();
    expect(await fineDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Fine?');
    await fineDeleteDialog.clickOnConfirmButton();

    expect(await fineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
