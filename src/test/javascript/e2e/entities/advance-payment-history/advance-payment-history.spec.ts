import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AdvancePaymentHistoryComponentsPage,
  AdvancePaymentHistoryDeleteDialog,
  AdvancePaymentHistoryUpdatePage,
} from './advance-payment-history.page-object';

const expect = chai.expect;

describe('AdvancePaymentHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let advancePaymentHistoryComponentsPage: AdvancePaymentHistoryComponentsPage;
  let advancePaymentHistoryUpdatePage: AdvancePaymentHistoryUpdatePage;
  let advancePaymentHistoryDeleteDialog: AdvancePaymentHistoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AdvancePaymentHistories', async () => {
    await navBarPage.goToEntity('advance-payment-history');
    advancePaymentHistoryComponentsPage = new AdvancePaymentHistoryComponentsPage();
    await browser.wait(ec.visibilityOf(advancePaymentHistoryComponentsPage.title), 5000);
    expect(await advancePaymentHistoryComponentsPage.getTitle()).to.eq('Advance Payment Histories');
    await browser.wait(
      ec.or(ec.visibilityOf(advancePaymentHistoryComponentsPage.entities), ec.visibilityOf(advancePaymentHistoryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AdvancePaymentHistory page', async () => {
    await advancePaymentHistoryComponentsPage.clickOnCreateButton();
    advancePaymentHistoryUpdatePage = new AdvancePaymentHistoryUpdatePage();
    expect(await advancePaymentHistoryUpdatePage.getPageTitle()).to.eq('Create or edit a Advance Payment History');
    await advancePaymentHistoryUpdatePage.cancel();
  });

  it('should create and save AdvancePaymentHistories', async () => {
    const nbButtonsBeforeCreate = await advancePaymentHistoryComponentsPage.countDeleteButtons();

    await advancePaymentHistoryComponentsPage.clickOnCreateButton();

    await promise.all([
      advancePaymentHistoryUpdatePage.setYearInput('5'),
      advancePaymentHistoryUpdatePage.monthTypeSelectLastOption(),
      advancePaymentHistoryUpdatePage.setAmountInput('5'),
      advancePaymentHistoryUpdatePage.setBeforeInput('5'),
      advancePaymentHistoryUpdatePage.setAfterInput('5'),
      advancePaymentHistoryUpdatePage.advanceSelectLastOption(),
    ]);

    expect(await advancePaymentHistoryUpdatePage.getYearInput()).to.eq('5', 'Expected year value to be equals to 5');
    expect(await advancePaymentHistoryUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await advancePaymentHistoryUpdatePage.getBeforeInput()).to.eq('5', 'Expected before value to be equals to 5');
    expect(await advancePaymentHistoryUpdatePage.getAfterInput()).to.eq('5', 'Expected after value to be equals to 5');

    await advancePaymentHistoryUpdatePage.save();
    expect(await advancePaymentHistoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await advancePaymentHistoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AdvancePaymentHistory', async () => {
    const nbButtonsBeforeDelete = await advancePaymentHistoryComponentsPage.countDeleteButtons();
    await advancePaymentHistoryComponentsPage.clickOnLastDeleteButton();

    advancePaymentHistoryDeleteDialog = new AdvancePaymentHistoryDeleteDialog();
    expect(await advancePaymentHistoryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Advance Payment History?');
    await advancePaymentHistoryDeleteDialog.clickOnConfirmButton();

    expect(await advancePaymentHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
