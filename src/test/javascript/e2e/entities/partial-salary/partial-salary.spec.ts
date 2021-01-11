import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartialSalaryComponentsPage, PartialSalaryDeleteDialog, PartialSalaryUpdatePage } from './partial-salary.page-object';

const expect = chai.expect;

describe('PartialSalary e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partialSalaryComponentsPage: PartialSalaryComponentsPage;
  let partialSalaryUpdatePage: PartialSalaryUpdatePage;
  let partialSalaryDeleteDialog: PartialSalaryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PartialSalaries', async () => {
    await navBarPage.goToEntity('partial-salary');
    partialSalaryComponentsPage = new PartialSalaryComponentsPage();
    await browser.wait(ec.visibilityOf(partialSalaryComponentsPage.title), 5000);
    expect(await partialSalaryComponentsPage.getTitle()).to.eq('Partial Salaries');
    await browser.wait(
      ec.or(ec.visibilityOf(partialSalaryComponentsPage.entities), ec.visibilityOf(partialSalaryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PartialSalary page', async () => {
    await partialSalaryComponentsPage.clickOnCreateButton();
    partialSalaryUpdatePage = new PartialSalaryUpdatePage();
    expect(await partialSalaryUpdatePage.getPageTitle()).to.eq('Create or edit a Partial Salary');
    await partialSalaryUpdatePage.cancel();
  });

  it('should create and save PartialSalaries', async () => {
    const nbButtonsBeforeCreate = await partialSalaryComponentsPage.countDeleteButtons();

    await partialSalaryComponentsPage.clickOnCreateButton();

    await promise.all([
      partialSalaryUpdatePage.setYearInput('5'),
      partialSalaryUpdatePage.monthSelectLastOption(),
      partialSalaryUpdatePage.setFromDateInput('2000-12-31'),
      partialSalaryUpdatePage.setToDateInput('2000-12-31'),
      partialSalaryUpdatePage.setGrossInput('5'),
      partialSalaryUpdatePage.setBasicInput('5'),
      partialSalaryUpdatePage.setBasicPercentInput('5'),
      partialSalaryUpdatePage.setHouseRentInput('5'),
      partialSalaryUpdatePage.setHouseRentPercentInput('5'),
      partialSalaryUpdatePage.setMedicalAllowanceInput('5'),
      partialSalaryUpdatePage.setMedicalAllowancePercentInput('5'),
      partialSalaryUpdatePage.setConvinceAllowanceInput('5'),
      partialSalaryUpdatePage.setConvinceAllowancePercentInput('5'),
      partialSalaryUpdatePage.setFoodAllowanceInput('5'),
      partialSalaryUpdatePage.setFoodAllowancePercentInput('5'),
      partialSalaryUpdatePage.setFineInput('5'),
      partialSalaryUpdatePage.setAdvanceInput('5'),
      partialSalaryUpdatePage.statusSelectLastOption(),
      partialSalaryUpdatePage.setExecutedOnInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partialSalaryUpdatePage.setExecutedByInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partialSalaryUpdatePage.setNoteInput('note'),
      partialSalaryUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await partialSalaryUpdatePage.getYearInput()).to.eq('5', 'Expected year value to be equals to 5');
    expect(await partialSalaryUpdatePage.getFromDateInput()).to.eq('2000-12-31', 'Expected fromDate value to be equals to 2000-12-31');
    expect(await partialSalaryUpdatePage.getToDateInput()).to.eq('2000-12-31', 'Expected toDate value to be equals to 2000-12-31');
    expect(await partialSalaryUpdatePage.getGrossInput()).to.eq('5', 'Expected gross value to be equals to 5');
    expect(await partialSalaryUpdatePage.getBasicInput()).to.eq('5', 'Expected basic value to be equals to 5');
    expect(await partialSalaryUpdatePage.getBasicPercentInput()).to.eq('5', 'Expected basicPercent value to be equals to 5');
    expect(await partialSalaryUpdatePage.getHouseRentInput()).to.eq('5', 'Expected houseRent value to be equals to 5');
    expect(await partialSalaryUpdatePage.getHouseRentPercentInput()).to.eq('5', 'Expected houseRentPercent value to be equals to 5');
    expect(await partialSalaryUpdatePage.getMedicalAllowanceInput()).to.eq('5', 'Expected medicalAllowance value to be equals to 5');
    expect(await partialSalaryUpdatePage.getMedicalAllowancePercentInput()).to.eq(
      '5',
      'Expected medicalAllowancePercent value to be equals to 5'
    );
    expect(await partialSalaryUpdatePage.getConvinceAllowanceInput()).to.eq('5', 'Expected convinceAllowance value to be equals to 5');
    expect(await partialSalaryUpdatePage.getConvinceAllowancePercentInput()).to.eq(
      '5',
      'Expected convinceAllowancePercent value to be equals to 5'
    );
    expect(await partialSalaryUpdatePage.getFoodAllowanceInput()).to.eq('5', 'Expected foodAllowance value to be equals to 5');
    expect(await partialSalaryUpdatePage.getFoodAllowancePercentInput()).to.eq(
      '5',
      'Expected foodAllowancePercent value to be equals to 5'
    );
    expect(await partialSalaryUpdatePage.getFineInput()).to.eq('5', 'Expected fine value to be equals to 5');
    expect(await partialSalaryUpdatePage.getAdvanceInput()).to.eq('5', 'Expected advance value to be equals to 5');
    expect(await partialSalaryUpdatePage.getExecutedOnInput()).to.contain(
      '2001-01-01T02:30',
      'Expected executedOn value to be equals to 2000-12-31'
    );
    expect(await partialSalaryUpdatePage.getExecutedByInput()).to.contain(
      '2001-01-01T02:30',
      'Expected executedBy value to be equals to 2000-12-31'
    );
    expect(await partialSalaryUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');

    await partialSalaryUpdatePage.save();
    expect(await partialSalaryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partialSalaryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PartialSalary', async () => {
    const nbButtonsBeforeDelete = await partialSalaryComponentsPage.countDeleteButtons();
    await partialSalaryComponentsPage.clickOnLastDeleteButton();

    partialSalaryDeleteDialog = new PartialSalaryDeleteDialog();
    expect(await partialSalaryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Partial Salary?');
    await partialSalaryDeleteDialog.clickOnConfirmButton();

    expect(await partialSalaryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
