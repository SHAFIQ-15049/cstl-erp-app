import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MonthlySalaryComponentsPage, MonthlySalaryDeleteDialog, MonthlySalaryUpdatePage } from './monthly-salary.page-object';

const expect = chai.expect;

describe('MonthlySalary e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let monthlySalaryComponentsPage: MonthlySalaryComponentsPage;
  let monthlySalaryUpdatePage: MonthlySalaryUpdatePage;
  let monthlySalaryDeleteDialog: MonthlySalaryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MonthlySalaries', async () => {
    await navBarPage.goToEntity('monthly-salary');
    monthlySalaryComponentsPage = new MonthlySalaryComponentsPage();
    await browser.wait(ec.visibilityOf(monthlySalaryComponentsPage.title), 5000);
    expect(await monthlySalaryComponentsPage.getTitle()).to.eq('Monthly Salaries');
    await browser.wait(
      ec.or(ec.visibilityOf(monthlySalaryComponentsPage.entities), ec.visibilityOf(monthlySalaryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create MonthlySalary page', async () => {
    await monthlySalaryComponentsPage.clickOnCreateButton();
    monthlySalaryUpdatePage = new MonthlySalaryUpdatePage();
    expect(await monthlySalaryUpdatePage.getPageTitle()).to.eq('Create or edit a Monthly Salary');
    await monthlySalaryUpdatePage.cancel();
  });

  it('should create and save MonthlySalaries', async () => {
    const nbButtonsBeforeCreate = await monthlySalaryComponentsPage.countDeleteButtons();

    await monthlySalaryComponentsPage.clickOnCreateButton();

    await promise.all([
      monthlySalaryUpdatePage.setYearInput('5'),
      monthlySalaryUpdatePage.monthSelectLastOption(),
      monthlySalaryUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      monthlySalaryUpdatePage.setToDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      monthlySalaryUpdatePage.statusSelectLastOption(),
      monthlySalaryUpdatePage.setExecutedOnInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      monthlySalaryUpdatePage.setExecutedByInput('executedBy'),
      monthlySalaryUpdatePage.departmentSelectLastOption(),
    ]);

    expect(await monthlySalaryUpdatePage.getYearInput()).to.eq('5', 'Expected year value to be equals to 5');
    expect(await monthlySalaryUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await monthlySalaryUpdatePage.getToDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected toDate value to be equals to 2000-12-31'
    );
    expect(await monthlySalaryUpdatePage.getExecutedOnInput()).to.contain(
      '2001-01-01T02:30',
      'Expected executedOn value to be equals to 2000-12-31'
    );
    expect(await monthlySalaryUpdatePage.getExecutedByInput()).to.eq('executedBy', 'Expected ExecutedBy value to be equals to executedBy');

    await monthlySalaryUpdatePage.save();
    expect(await monthlySalaryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await monthlySalaryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last MonthlySalary', async () => {
    const nbButtonsBeforeDelete = await monthlySalaryComponentsPage.countDeleteButtons();
    await monthlySalaryComponentsPage.clickOnLastDeleteButton();

    monthlySalaryDeleteDialog = new MonthlySalaryDeleteDialog();
    expect(await monthlySalaryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Monthly Salary?');
    await monthlySalaryDeleteDialog.clickOnConfirmButton();

    expect(await monthlySalaryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
