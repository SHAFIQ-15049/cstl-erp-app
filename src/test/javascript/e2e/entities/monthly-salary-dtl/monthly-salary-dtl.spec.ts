import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MonthlySalaryDtlComponentsPage, MonthlySalaryDtlDeleteDialog, MonthlySalaryDtlUpdatePage } from './monthly-salary-dtl.page-object';

const expect = chai.expect;

describe('MonthlySalaryDtl e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let monthlySalaryDtlComponentsPage: MonthlySalaryDtlComponentsPage;
  let monthlySalaryDtlUpdatePage: MonthlySalaryDtlUpdatePage;
  let monthlySalaryDtlDeleteDialog: MonthlySalaryDtlDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MonthlySalaryDtls', async () => {
    await navBarPage.goToEntity('monthly-salary-dtl');
    monthlySalaryDtlComponentsPage = new MonthlySalaryDtlComponentsPage();
    await browser.wait(ec.visibilityOf(monthlySalaryDtlComponentsPage.title), 5000);
    expect(await monthlySalaryDtlComponentsPage.getTitle()).to.eq('Monthly Salary Dtls');
    await browser.wait(
      ec.or(ec.visibilityOf(monthlySalaryDtlComponentsPage.entities), ec.visibilityOf(monthlySalaryDtlComponentsPage.noResult)),
      1000
    );
  });

  it('should load create MonthlySalaryDtl page', async () => {
    await monthlySalaryDtlComponentsPage.clickOnCreateButton();
    monthlySalaryDtlUpdatePage = new MonthlySalaryDtlUpdatePage();
    expect(await monthlySalaryDtlUpdatePage.getPageTitle()).to.eq('Create or edit a Monthly Salary Dtl');
    await monthlySalaryDtlUpdatePage.cancel();
  });

  it('should create and save MonthlySalaryDtls', async () => {
    const nbButtonsBeforeCreate = await monthlySalaryDtlComponentsPage.countDeleteButtons();

    await monthlySalaryDtlComponentsPage.clickOnCreateButton();

    await promise.all([
      monthlySalaryDtlUpdatePage.setGrossInput('5'),
      monthlySalaryDtlUpdatePage.setBasicInput('5'),
      monthlySalaryDtlUpdatePage.setBasicPercentInput('5'),
      monthlySalaryDtlUpdatePage.setHouseRentInput('5'),
      monthlySalaryDtlUpdatePage.setHouseRentPercentInput('5'),
      monthlySalaryDtlUpdatePage.setMedicalAllowanceInput('5'),
      monthlySalaryDtlUpdatePage.setMedicalAllowancePercentInput('5'),
      monthlySalaryDtlUpdatePage.setConvinceAllowanceInput('5'),
      monthlySalaryDtlUpdatePage.setConvinceAllowancePercentInput('5'),
      monthlySalaryDtlUpdatePage.setFoodAllowanceInput('5'),
      monthlySalaryDtlUpdatePage.setFoodAllowancePercentInput('5'),
      monthlySalaryDtlUpdatePage.setFineInput('5'),
      monthlySalaryDtlUpdatePage.setAdvanceInput('5'),
      monthlySalaryDtlUpdatePage.setTotalWorkingDaysInput('5'),
      monthlySalaryDtlUpdatePage.setRegularLeaveInput('5'),
      monthlySalaryDtlUpdatePage.setSickLeaveInput('5'),
      monthlySalaryDtlUpdatePage.setCompensationLeaveInput('5'),
      monthlySalaryDtlUpdatePage.setFestivalLeaveInput('5'),
      monthlySalaryDtlUpdatePage.setWeeklyLeaveInput('5'),
      monthlySalaryDtlUpdatePage.setPresentInput('5'),
      monthlySalaryDtlUpdatePage.setAbsentInput('5'),
      monthlySalaryDtlUpdatePage.setTotalMonthDaysInput('5'),
      monthlySalaryDtlUpdatePage.setOverTimeHourInput('5'),
      monthlySalaryDtlUpdatePage.setOverTimeSalaryHourlyInput('5'),
      monthlySalaryDtlUpdatePage.setOverTimeSalaryInput('5'),
      monthlySalaryDtlUpdatePage.setPresentBonusInput('5'),
      monthlySalaryDtlUpdatePage.setAbsentFineInput('5'),
      monthlySalaryDtlUpdatePage.setStampPriceInput('5'),
      monthlySalaryDtlUpdatePage.setTaxInput('5'),
      monthlySalaryDtlUpdatePage.setOthersInput('5'),
      monthlySalaryDtlUpdatePage.setTotalPayableInput('5'),
      monthlySalaryDtlUpdatePage.statusSelectLastOption(),
      monthlySalaryDtlUpdatePage.typeSelectLastOption(),
      monthlySalaryDtlUpdatePage.setExecutedOnInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      monthlySalaryDtlUpdatePage.setExecutedByInput('executedBy'),
      monthlySalaryDtlUpdatePage.setNoteInput('note'),
      monthlySalaryDtlUpdatePage.employeeSelectLastOption(),
      monthlySalaryDtlUpdatePage.monthlySalarySelectLastOption(),
    ]);

    expect(await monthlySalaryDtlUpdatePage.getGrossInput()).to.eq('5', 'Expected gross value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getBasicInput()).to.eq('5', 'Expected basic value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getBasicPercentInput()).to.eq('5', 'Expected basicPercent value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getHouseRentInput()).to.eq('5', 'Expected houseRent value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getHouseRentPercentInput()).to.eq('5', 'Expected houseRentPercent value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getMedicalAllowanceInput()).to.eq('5', 'Expected medicalAllowance value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getMedicalAllowancePercentInput()).to.eq(
      '5',
      'Expected medicalAllowancePercent value to be equals to 5'
    );
    expect(await monthlySalaryDtlUpdatePage.getConvinceAllowanceInput()).to.eq('5', 'Expected convinceAllowance value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getConvinceAllowancePercentInput()).to.eq(
      '5',
      'Expected convinceAllowancePercent value to be equals to 5'
    );
    expect(await monthlySalaryDtlUpdatePage.getFoodAllowanceInput()).to.eq('5', 'Expected foodAllowance value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getFoodAllowancePercentInput()).to.eq(
      '5',
      'Expected foodAllowancePercent value to be equals to 5'
    );
    expect(await monthlySalaryDtlUpdatePage.getFineInput()).to.eq('5', 'Expected fine value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getAdvanceInput()).to.eq('5', 'Expected advance value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getTotalWorkingDaysInput()).to.eq('5', 'Expected totalWorkingDays value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getRegularLeaveInput()).to.eq('5', 'Expected regularLeave value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getSickLeaveInput()).to.eq('5', 'Expected sickLeave value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getCompensationLeaveInput()).to.eq('5', 'Expected compensationLeave value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getFestivalLeaveInput()).to.eq('5', 'Expected festivalLeave value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getWeeklyLeaveInput()).to.eq('5', 'Expected weeklyLeave value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getPresentInput()).to.eq('5', 'Expected present value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getAbsentInput()).to.eq('5', 'Expected absent value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getTotalMonthDaysInput()).to.eq('5', 'Expected totalMonthDays value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getOverTimeHourInput()).to.eq('5', 'Expected overTimeHour value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getOverTimeSalaryHourlyInput()).to.eq(
      '5',
      'Expected overTimeSalaryHourly value to be equals to 5'
    );
    expect(await monthlySalaryDtlUpdatePage.getOverTimeSalaryInput()).to.eq('5', 'Expected overTimeSalary value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getPresentBonusInput()).to.eq('5', 'Expected presentBonus value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getAbsentFineInput()).to.eq('5', 'Expected absentFine value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getStampPriceInput()).to.eq('5', 'Expected stampPrice value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getTaxInput()).to.eq('5', 'Expected tax value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getOthersInput()).to.eq('5', 'Expected others value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getTotalPayableInput()).to.eq('5', 'Expected totalPayable value to be equals to 5');
    expect(await monthlySalaryDtlUpdatePage.getExecutedOnInput()).to.contain(
      '2001-01-01T02:30',
      'Expected executedOn value to be equals to 2000-12-31'
    );
    expect(await monthlySalaryDtlUpdatePage.getExecutedByInput()).to.eq(
      'executedBy',
      'Expected ExecutedBy value to be equals to executedBy'
    );
    expect(await monthlySalaryDtlUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');

    await monthlySalaryDtlUpdatePage.save();
    expect(await monthlySalaryDtlUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await monthlySalaryDtlComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last MonthlySalaryDtl', async () => {
    const nbButtonsBeforeDelete = await monthlySalaryDtlComponentsPage.countDeleteButtons();
    await monthlySalaryDtlComponentsPage.clickOnLastDeleteButton();

    monthlySalaryDtlDeleteDialog = new MonthlySalaryDtlDeleteDialog();
    expect(await monthlySalaryDtlDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Monthly Salary Dtl?');
    await monthlySalaryDtlDeleteDialog.clickOnConfirmButton();

    expect(await monthlySalaryDtlComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
