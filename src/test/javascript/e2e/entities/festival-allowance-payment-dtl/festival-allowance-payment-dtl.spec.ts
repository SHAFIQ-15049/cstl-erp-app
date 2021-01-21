import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FestivalAllowancePaymentDtlComponentsPage,
  FestivalAllowancePaymentDtlDeleteDialog,
  FestivalAllowancePaymentDtlUpdatePage,
} from './festival-allowance-payment-dtl.page-object';

const expect = chai.expect;

describe('FestivalAllowancePaymentDtl e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let festivalAllowancePaymentDtlComponentsPage: FestivalAllowancePaymentDtlComponentsPage;
  let festivalAllowancePaymentDtlUpdatePage: FestivalAllowancePaymentDtlUpdatePage;
  let festivalAllowancePaymentDtlDeleteDialog: FestivalAllowancePaymentDtlDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FestivalAllowancePaymentDtls', async () => {
    await navBarPage.goToEntity('festival-allowance-payment-dtl');
    festivalAllowancePaymentDtlComponentsPage = new FestivalAllowancePaymentDtlComponentsPage();
    await browser.wait(ec.visibilityOf(festivalAllowancePaymentDtlComponentsPage.title), 5000);
    expect(await festivalAllowancePaymentDtlComponentsPage.getTitle()).to.eq('Festival Allowance Payment Dtls');
    await browser.wait(
      ec.or(
        ec.visibilityOf(festivalAllowancePaymentDtlComponentsPage.entities),
        ec.visibilityOf(festivalAllowancePaymentDtlComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create FestivalAllowancePaymentDtl page', async () => {
    await festivalAllowancePaymentDtlComponentsPage.clickOnCreateButton();
    festivalAllowancePaymentDtlUpdatePage = new FestivalAllowancePaymentDtlUpdatePage();
    expect(await festivalAllowancePaymentDtlUpdatePage.getPageTitle()).to.eq('Create or edit a Festival Allowance Payment Dtl');
    await festivalAllowancePaymentDtlUpdatePage.cancel();
  });

  it('should create and save FestivalAllowancePaymentDtls', async () => {
    const nbButtonsBeforeCreate = await festivalAllowancePaymentDtlComponentsPage.countDeleteButtons();

    await festivalAllowancePaymentDtlComponentsPage.clickOnCreateButton();

    await promise.all([
      festivalAllowancePaymentDtlUpdatePage.setAmountInput('5'),
      festivalAllowancePaymentDtlUpdatePage.statusSelectLastOption(),
      festivalAllowancePaymentDtlUpdatePage.setExecutedOnInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      festivalAllowancePaymentDtlUpdatePage.setExecutedByInput('executedBy'),
      festivalAllowancePaymentDtlUpdatePage.setNoteInput('note'),
      festivalAllowancePaymentDtlUpdatePage.employeeSelectLastOption(),
      festivalAllowancePaymentDtlUpdatePage.festivalAllowancePaymentSelectLastOption(),
    ]);

    expect(await festivalAllowancePaymentDtlUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await festivalAllowancePaymentDtlUpdatePage.getExecutedOnInput()).to.contain(
      '2001-01-01T02:30',
      'Expected executedOn value to be equals to 2000-12-31'
    );
    expect(await festivalAllowancePaymentDtlUpdatePage.getExecutedByInput()).to.eq(
      'executedBy',
      'Expected ExecutedBy value to be equals to executedBy'
    );
    expect(await festivalAllowancePaymentDtlUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');

    await festivalAllowancePaymentDtlUpdatePage.save();
    expect(await festivalAllowancePaymentDtlUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await festivalAllowancePaymentDtlComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FestivalAllowancePaymentDtl', async () => {
    const nbButtonsBeforeDelete = await festivalAllowancePaymentDtlComponentsPage.countDeleteButtons();
    await festivalAllowancePaymentDtlComponentsPage.clickOnLastDeleteButton();

    festivalAllowancePaymentDtlDeleteDialog = new FestivalAllowancePaymentDtlDeleteDialog();
    expect(await festivalAllowancePaymentDtlDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Festival Allowance Payment Dtl?'
    );
    await festivalAllowancePaymentDtlDeleteDialog.clickOnConfirmButton();

    expect(await festivalAllowancePaymentDtlComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
