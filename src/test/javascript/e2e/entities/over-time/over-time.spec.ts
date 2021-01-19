import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OverTimeComponentsPage, OverTimeDeleteDialog, OverTimeUpdatePage } from './over-time.page-object';

const expect = chai.expect;

describe('OverTime e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let overTimeComponentsPage: OverTimeComponentsPage;
  let overTimeUpdatePage: OverTimeUpdatePage;
  let overTimeDeleteDialog: OverTimeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OverTimes', async () => {
    await navBarPage.goToEntity('over-time');
    overTimeComponentsPage = new OverTimeComponentsPage();
    await browser.wait(ec.visibilityOf(overTimeComponentsPage.title), 5000);
    expect(await overTimeComponentsPage.getTitle()).to.eq('Over Times');
    await browser.wait(ec.or(ec.visibilityOf(overTimeComponentsPage.entities), ec.visibilityOf(overTimeComponentsPage.noResult)), 1000);
  });

  it('should load create OverTime page', async () => {
    await overTimeComponentsPage.clickOnCreateButton();
    overTimeUpdatePage = new OverTimeUpdatePage();
    expect(await overTimeUpdatePage.getPageTitle()).to.eq('Create or edit a Over Time');
    await overTimeUpdatePage.cancel();
  });

  it('should create and save OverTimes', async () => {
    const nbButtonsBeforeCreate = await overTimeComponentsPage.countDeleteButtons();

    await overTimeComponentsPage.clickOnCreateButton();

    await promise.all([
      overTimeUpdatePage.setYearInput('5'),
      overTimeUpdatePage.monthSelectLastOption(),
      overTimeUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      overTimeUpdatePage.setToDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      overTimeUpdatePage.setTotalOverTimeInput('5'),
      overTimeUpdatePage.setOfficialOverTimeInput('5'),
      overTimeUpdatePage.setExtraOverTimeInput('5'),
      overTimeUpdatePage.setTotalAmountInput('5'),
      overTimeUpdatePage.setOfficialAmountInput('5'),
      overTimeUpdatePage.setExtraAmountInput('5'),
      overTimeUpdatePage.setNoteInput('note'),
      overTimeUpdatePage.setExecutedOnInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      overTimeUpdatePage.setExecutedByInput('executedBy'),
      overTimeUpdatePage.designationSelectLastOption(),
      overTimeUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await overTimeUpdatePage.getYearInput()).to.eq('5', 'Expected year value to be equals to 5');
    expect(await overTimeUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await overTimeUpdatePage.getToDateInput()).to.contain('2001-01-01T02:30', 'Expected toDate value to be equals to 2000-12-31');
    expect(await overTimeUpdatePage.getTotalOverTimeInput()).to.eq('5', 'Expected totalOverTime value to be equals to 5');
    expect(await overTimeUpdatePage.getOfficialOverTimeInput()).to.eq('5', 'Expected officialOverTime value to be equals to 5');
    expect(await overTimeUpdatePage.getExtraOverTimeInput()).to.eq('5', 'Expected extraOverTime value to be equals to 5');
    expect(await overTimeUpdatePage.getTotalAmountInput()).to.eq('5', 'Expected totalAmount value to be equals to 5');
    expect(await overTimeUpdatePage.getOfficialAmountInput()).to.eq('5', 'Expected officialAmount value to be equals to 5');
    expect(await overTimeUpdatePage.getExtraAmountInput()).to.eq('5', 'Expected extraAmount value to be equals to 5');
    expect(await overTimeUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');
    expect(await overTimeUpdatePage.getExecutedOnInput()).to.contain(
      '2001-01-01T02:30',
      'Expected executedOn value to be equals to 2000-12-31'
    );
    expect(await overTimeUpdatePage.getExecutedByInput()).to.eq('executedBy', 'Expected ExecutedBy value to be equals to executedBy');

    await overTimeUpdatePage.save();
    expect(await overTimeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await overTimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OverTime', async () => {
    const nbButtonsBeforeDelete = await overTimeComponentsPage.countDeleteButtons();
    await overTimeComponentsPage.clickOnLastDeleteButton();

    overTimeDeleteDialog = new OverTimeDeleteDialog();
    expect(await overTimeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Over Time?');
    await overTimeDeleteDialog.clickOnConfirmButton();

    expect(await overTimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
