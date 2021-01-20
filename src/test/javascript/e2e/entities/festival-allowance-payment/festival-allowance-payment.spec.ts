import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FestivalAllowancePaymentComponentsPage,
  FestivalAllowancePaymentDeleteDialog,
  FestivalAllowancePaymentUpdatePage,
} from './festival-allowance-payment.page-object';

const expect = chai.expect;

describe('FestivalAllowancePayment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let festivalAllowancePaymentComponentsPage: FestivalAllowancePaymentComponentsPage;
  let festivalAllowancePaymentUpdatePage: FestivalAllowancePaymentUpdatePage;
  let festivalAllowancePaymentDeleteDialog: FestivalAllowancePaymentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FestivalAllowancePayments', async () => {
    await navBarPage.goToEntity('festival-allowance-payment');
    festivalAllowancePaymentComponentsPage = new FestivalAllowancePaymentComponentsPage();
    await browser.wait(ec.visibilityOf(festivalAllowancePaymentComponentsPage.title), 5000);
    expect(await festivalAllowancePaymentComponentsPage.getTitle()).to.eq('Festival Allowance Payments');
    await browser.wait(
      ec.or(
        ec.visibilityOf(festivalAllowancePaymentComponentsPage.entities),
        ec.visibilityOf(festivalAllowancePaymentComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create FestivalAllowancePayment page', async () => {
    await festivalAllowancePaymentComponentsPage.clickOnCreateButton();
    festivalAllowancePaymentUpdatePage = new FestivalAllowancePaymentUpdatePage();
    expect(await festivalAllowancePaymentUpdatePage.getPageTitle()).to.eq('Create or edit a Festival Allowance Payment');
    await festivalAllowancePaymentUpdatePage.cancel();
  });

  it('should create and save FestivalAllowancePayments', async () => {
    const nbButtonsBeforeCreate = await festivalAllowancePaymentComponentsPage.countDeleteButtons();

    await festivalAllowancePaymentComponentsPage.clickOnCreateButton();

    await promise.all([
      festivalAllowancePaymentUpdatePage.setYearInput('5'),
      festivalAllowancePaymentUpdatePage.monthSelectLastOption(),
      festivalAllowancePaymentUpdatePage.statusSelectLastOption(),
      festivalAllowancePaymentUpdatePage.setExecutedOnInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      festivalAllowancePaymentUpdatePage.setExecutedByInput('executedBy'),
      festivalAllowancePaymentUpdatePage.designationSelectLastOption(),
    ]);

    expect(await festivalAllowancePaymentUpdatePage.getYearInput()).to.eq('5', 'Expected year value to be equals to 5');
    expect(await festivalAllowancePaymentUpdatePage.getExecutedOnInput()).to.contain(
      '2001-01-01T02:30',
      'Expected executedOn value to be equals to 2000-12-31'
    );
    expect(await festivalAllowancePaymentUpdatePage.getExecutedByInput()).to.eq(
      'executedBy',
      'Expected ExecutedBy value to be equals to executedBy'
    );

    await festivalAllowancePaymentUpdatePage.save();
    expect(await festivalAllowancePaymentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await festivalAllowancePaymentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FestivalAllowancePayment', async () => {
    const nbButtonsBeforeDelete = await festivalAllowancePaymentComponentsPage.countDeleteButtons();
    await festivalAllowancePaymentComponentsPage.clickOnLastDeleteButton();

    festivalAllowancePaymentDeleteDialog = new FestivalAllowancePaymentDeleteDialog();
    expect(await festivalAllowancePaymentDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Festival Allowance Payment?'
    );
    await festivalAllowancePaymentDeleteDialog.clickOnConfirmButton();

    expect(await festivalAllowancePaymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
