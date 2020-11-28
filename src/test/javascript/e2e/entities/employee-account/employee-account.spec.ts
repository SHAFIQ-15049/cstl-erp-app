import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmployeeAccountComponentsPage, EmployeeAccountDeleteDialog, EmployeeAccountUpdatePage } from './employee-account.page-object';

const expect = chai.expect;

describe('EmployeeAccount e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeAccountComponentsPage: EmployeeAccountComponentsPage;
  let employeeAccountUpdatePage: EmployeeAccountUpdatePage;
  let employeeAccountDeleteDialog: EmployeeAccountDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmployeeAccounts', async () => {
    await navBarPage.goToEntity('employee-account');
    employeeAccountComponentsPage = new EmployeeAccountComponentsPage();
    await browser.wait(ec.visibilityOf(employeeAccountComponentsPage.title), 5000);
    expect(await employeeAccountComponentsPage.getTitle()).to.eq('Employee Accounts');
    await browser.wait(
      ec.or(ec.visibilityOf(employeeAccountComponentsPage.entities), ec.visibilityOf(employeeAccountComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmployeeAccount page', async () => {
    await employeeAccountComponentsPage.clickOnCreateButton();
    employeeAccountUpdatePage = new EmployeeAccountUpdatePage();
    expect(await employeeAccountUpdatePage.getPageTitle()).to.eq('Create or edit a Employee Account');
    await employeeAccountUpdatePage.cancel();
  });

  it('should create and save EmployeeAccounts', async () => {
    const nbButtonsBeforeCreate = await employeeAccountComponentsPage.countDeleteButtons();

    await employeeAccountComponentsPage.clickOnCreateButton();

    await promise.all([
      employeeAccountUpdatePage.accountTypeSelectLastOption(),
      employeeAccountUpdatePage.setAccountNoInput('accountNo'),
      employeeAccountUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await employeeAccountUpdatePage.getAccountNoInput()).to.eq('accountNo', 'Expected AccountNo value to be equals to accountNo');
    const selectedIsSalaryAccount = employeeAccountUpdatePage.getIsSalaryAccountInput();
    if (await selectedIsSalaryAccount.isSelected()) {
      await employeeAccountUpdatePage.getIsSalaryAccountInput().click();
      expect(await employeeAccountUpdatePage.getIsSalaryAccountInput().isSelected(), 'Expected isSalaryAccount not to be selected').to.be
        .false;
    } else {
      await employeeAccountUpdatePage.getIsSalaryAccountInput().click();
      expect(await employeeAccountUpdatePage.getIsSalaryAccountInput().isSelected(), 'Expected isSalaryAccount to be selected').to.be.true;
    }

    await employeeAccountUpdatePage.save();
    expect(await employeeAccountUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeAccountComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmployeeAccount', async () => {
    const nbButtonsBeforeDelete = await employeeAccountComponentsPage.countDeleteButtons();
    await employeeAccountComponentsPage.clickOnLastDeleteButton();

    employeeAccountDeleteDialog = new EmployeeAccountDeleteDialog();
    expect(await employeeAccountDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Employee Account?');
    await employeeAccountDeleteDialog.clickOnConfirmButton();

    expect(await employeeAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
