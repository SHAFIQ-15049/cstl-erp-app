import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmployeeSalaryComponentsPage, EmployeeSalaryDeleteDialog, EmployeeSalaryUpdatePage } from './employee-salary.page-object';

const expect = chai.expect;

describe('EmployeeSalary e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeSalaryComponentsPage: EmployeeSalaryComponentsPage;
  let employeeSalaryUpdatePage: EmployeeSalaryUpdatePage;
  let employeeSalaryDeleteDialog: EmployeeSalaryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmployeeSalaries', async () => {
    await navBarPage.goToEntity('employee-salary');
    employeeSalaryComponentsPage = new EmployeeSalaryComponentsPage();
    await browser.wait(ec.visibilityOf(employeeSalaryComponentsPage.title), 5000);
    expect(await employeeSalaryComponentsPage.getTitle()).to.eq('Employee Salaries');
    await browser.wait(
      ec.or(ec.visibilityOf(employeeSalaryComponentsPage.entities), ec.visibilityOf(employeeSalaryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmployeeSalary page', async () => {
    await employeeSalaryComponentsPage.clickOnCreateButton();
    employeeSalaryUpdatePage = new EmployeeSalaryUpdatePage();
    expect(await employeeSalaryUpdatePage.getPageTitle()).to.eq('Create or edit a Employee Salary');
    await employeeSalaryUpdatePage.cancel();
  });

  it('should create and save EmployeeSalaries', async () => {
    const nbButtonsBeforeCreate = await employeeSalaryComponentsPage.countDeleteButtons();

    await employeeSalaryComponentsPage.clickOnCreateButton();

    await promise.all([
      employeeSalaryUpdatePage.setGrossInput('5'),
      employeeSalaryUpdatePage.setIncrementAmountInput('5'),
      employeeSalaryUpdatePage.setIncrementPercentageInput('5'),
      employeeSalaryUpdatePage.setSalaryStartDateInput('2000-12-31'),
      employeeSalaryUpdatePage.setSalaryEndDateInput('2000-12-31'),
      employeeSalaryUpdatePage.setNextIncrementDateInput('2000-12-31'),
      employeeSalaryUpdatePage.setBasicInput('5'),
      employeeSalaryUpdatePage.setBasicPercentInput('5'),
      employeeSalaryUpdatePage.setHouseRentInput('5'),
      employeeSalaryUpdatePage.setHouseRentPercentInput('5'),
      employeeSalaryUpdatePage.setTotalAllowanceInput('5'),
      employeeSalaryUpdatePage.setMedicalAllowanceInput('5'),
      employeeSalaryUpdatePage.setMedicalAllowancePercentInput('5'),
      employeeSalaryUpdatePage.setConvinceAllowanceInput('5'),
      employeeSalaryUpdatePage.setConvinceAllowancePercentInput('5'),
      employeeSalaryUpdatePage.setFoodAllowanceInput('5'),
      employeeSalaryUpdatePage.setFoodAllowancePercentInput('5'),
      employeeSalaryUpdatePage.statusSelectLastOption(),
      employeeSalaryUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await employeeSalaryUpdatePage.getGrossInput()).to.eq('5', 'Expected gross value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getIncrementAmountInput()).to.eq('5', 'Expected incrementAmount value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getIncrementPercentageInput()).to.eq('5', 'Expected incrementPercentage value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getSalaryStartDateInput()).to.eq(
      '2000-12-31',
      'Expected salaryStartDate value to be equals to 2000-12-31'
    );
    expect(await employeeSalaryUpdatePage.getSalaryEndDateInput()).to.eq(
      '2000-12-31',
      'Expected salaryEndDate value to be equals to 2000-12-31'
    );
    expect(await employeeSalaryUpdatePage.getNextIncrementDateInput()).to.eq(
      '2000-12-31',
      'Expected nextIncrementDate value to be equals to 2000-12-31'
    );
    expect(await employeeSalaryUpdatePage.getBasicInput()).to.eq('5', 'Expected basic value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getBasicPercentInput()).to.eq('5', 'Expected basicPercent value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getHouseRentInput()).to.eq('5', 'Expected houseRent value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getHouseRentPercentInput()).to.eq('5', 'Expected houseRentPercent value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getTotalAllowanceInput()).to.eq('5', 'Expected totalAllowance value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getMedicalAllowanceInput()).to.eq('5', 'Expected medicalAllowance value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getMedicalAllowancePercentInput()).to.eq(
      '5',
      'Expected medicalAllowancePercent value to be equals to 5'
    );
    expect(await employeeSalaryUpdatePage.getConvinceAllowanceInput()).to.eq('5', 'Expected convinceAllowance value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getConvinceAllowancePercentInput()).to.eq(
      '5',
      'Expected convinceAllowancePercent value to be equals to 5'
    );
    expect(await employeeSalaryUpdatePage.getFoodAllowanceInput()).to.eq('5', 'Expected foodAllowance value to be equals to 5');
    expect(await employeeSalaryUpdatePage.getFoodAllowancePercentInput()).to.eq(
      '5',
      'Expected foodAllowancePercent value to be equals to 5'
    );

    await employeeSalaryUpdatePage.save();
    expect(await employeeSalaryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeSalaryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmployeeSalary', async () => {
    const nbButtonsBeforeDelete = await employeeSalaryComponentsPage.countDeleteButtons();
    await employeeSalaryComponentsPage.clickOnLastDeleteButton();

    employeeSalaryDeleteDialog = new EmployeeSalaryDeleteDialog();
    expect(await employeeSalaryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Employee Salary?');
    await employeeSalaryDeleteDialog.clickOnConfirmButton();

    expect(await employeeSalaryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
