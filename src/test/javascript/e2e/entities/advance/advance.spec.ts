import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AdvanceComponentsPage, AdvanceDeleteDialog, AdvanceUpdatePage } from './advance.page-object';

const expect = chai.expect;

describe('Advance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let advanceComponentsPage: AdvanceComponentsPage;
  let advanceUpdatePage: AdvanceUpdatePage;
  let advanceDeleteDialog: AdvanceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Advances', async () => {
    await navBarPage.goToEntity('advance');
    advanceComponentsPage = new AdvanceComponentsPage();
    await browser.wait(ec.visibilityOf(advanceComponentsPage.title), 5000);
    expect(await advanceComponentsPage.getTitle()).to.eq('Advances');
    await browser.wait(ec.or(ec.visibilityOf(advanceComponentsPage.entities), ec.visibilityOf(advanceComponentsPage.noResult)), 1000);
  });

  it('should load create Advance page', async () => {
    await advanceComponentsPage.clickOnCreateButton();
    advanceUpdatePage = new AdvanceUpdatePage();
    expect(await advanceUpdatePage.getPageTitle()).to.eq('Create or edit a Advance');
    await advanceUpdatePage.cancel();
  });

  it('should create and save Advances', async () => {
    const nbButtonsBeforeCreate = await advanceComponentsPage.countDeleteButtons();

    await advanceComponentsPage.clickOnCreateButton();

    await promise.all([
      advanceUpdatePage.setProvidedOnInput('2000-12-31'),
      advanceUpdatePage.setReasonInput('reason'),
      advanceUpdatePage.setAmountInput('5'),
      advanceUpdatePage.setPaymentPercentageInput('5'),
      advanceUpdatePage.setMonthlyPaymentAmountInput('5'),
      advanceUpdatePage.paymentStatusSelectLastOption(),
      advanceUpdatePage.setAmountPaidInput('5'),
      advanceUpdatePage.setAmountLeftInput('5'),
      advanceUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await advanceUpdatePage.getProvidedOnInput()).to.eq('2000-12-31', 'Expected providedOn value to be equals to 2000-12-31');
    expect(await advanceUpdatePage.getReasonInput()).to.eq('reason', 'Expected Reason value to be equals to reason');
    expect(await advanceUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await advanceUpdatePage.getPaymentPercentageInput()).to.eq('5', 'Expected paymentPercentage value to be equals to 5');
    expect(await advanceUpdatePage.getMonthlyPaymentAmountInput()).to.eq('5', 'Expected monthlyPaymentAmount value to be equals to 5');
    expect(await advanceUpdatePage.getAmountPaidInput()).to.eq('5', 'Expected amountPaid value to be equals to 5');
    expect(await advanceUpdatePage.getAmountLeftInput()).to.eq('5', 'Expected amountLeft value to be equals to 5');

    await advanceUpdatePage.save();
    expect(await advanceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await advanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Advance', async () => {
    const nbButtonsBeforeDelete = await advanceComponentsPage.countDeleteButtons();
    await advanceComponentsPage.clickOnLastDeleteButton();

    advanceDeleteDialog = new AdvanceDeleteDialog();
    expect(await advanceDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Advance?');
    await advanceDeleteDialog.clickOnConfirmButton();

    expect(await advanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
