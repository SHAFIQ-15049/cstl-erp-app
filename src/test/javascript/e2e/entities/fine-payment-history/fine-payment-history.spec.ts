import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FinePaymentHistoryComponentsPage,
  FinePaymentHistoryDeleteDialog,
  FinePaymentHistoryUpdatePage,
} from './fine-payment-history.page-object';

const expect = chai.expect;

describe('FinePaymentHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let finePaymentHistoryComponentsPage: FinePaymentHistoryComponentsPage;
  let finePaymentHistoryUpdatePage: FinePaymentHistoryUpdatePage;
  let finePaymentHistoryDeleteDialog: FinePaymentHistoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FinePaymentHistories', async () => {
    await navBarPage.goToEntity('fine-payment-history');
    finePaymentHistoryComponentsPage = new FinePaymentHistoryComponentsPage();
    await browser.wait(ec.visibilityOf(finePaymentHistoryComponentsPage.title), 5000);
    expect(await finePaymentHistoryComponentsPage.getTitle()).to.eq('Fine Payment Histories');
    await browser.wait(
      ec.or(ec.visibilityOf(finePaymentHistoryComponentsPage.entities), ec.visibilityOf(finePaymentHistoryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FinePaymentHistory page', async () => {
    await finePaymentHistoryComponentsPage.clickOnCreateButton();
    finePaymentHistoryUpdatePage = new FinePaymentHistoryUpdatePage();
    expect(await finePaymentHistoryUpdatePage.getPageTitle()).to.eq('Create or edit a Fine Payment History');
    await finePaymentHistoryUpdatePage.cancel();
  });

  it('should create and save FinePaymentHistories', async () => {
    const nbButtonsBeforeCreate = await finePaymentHistoryComponentsPage.countDeleteButtons();

    await finePaymentHistoryComponentsPage.clickOnCreateButton();

    await promise.all([
      finePaymentHistoryUpdatePage.setYearInput('5'),
      finePaymentHistoryUpdatePage.monthTypeSelectLastOption(),
      finePaymentHistoryUpdatePage.setAmountInput('5'),
      finePaymentHistoryUpdatePage.setBeforeFineInput('5'),
      finePaymentHistoryUpdatePage.setAfterFineInput('5'),
      finePaymentHistoryUpdatePage.fineSelectLastOption(),
    ]);

    expect(await finePaymentHistoryUpdatePage.getYearInput()).to.eq('5', 'Expected year value to be equals to 5');
    expect(await finePaymentHistoryUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await finePaymentHistoryUpdatePage.getBeforeFineInput()).to.eq('5', 'Expected beforeFine value to be equals to 5');
    expect(await finePaymentHistoryUpdatePage.getAfterFineInput()).to.eq('5', 'Expected afterFine value to be equals to 5');

    await finePaymentHistoryUpdatePage.save();
    expect(await finePaymentHistoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await finePaymentHistoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FinePaymentHistory', async () => {
    const nbButtonsBeforeDelete = await finePaymentHistoryComponentsPage.countDeleteButtons();
    await finePaymentHistoryComponentsPage.clickOnLastDeleteButton();

    finePaymentHistoryDeleteDialog = new FinePaymentHistoryDeleteDialog();
    expect(await finePaymentHistoryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Fine Payment History?');
    await finePaymentHistoryDeleteDialog.clickOnConfirmButton();

    expect(await finePaymentHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
