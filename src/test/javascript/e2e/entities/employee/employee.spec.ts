import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmployeeComponentsPage, EmployeeDeleteDialog, EmployeeUpdatePage } from './employee.page-object';

const expect = chai.expect;

describe('Employee e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeComponentsPage: EmployeeComponentsPage;
  let employeeUpdatePage: EmployeeUpdatePage;
  let employeeDeleteDialog: EmployeeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Employees', async () => {
    await navBarPage.goToEntity('employee');
    employeeComponentsPage = new EmployeeComponentsPage();
    await browser.wait(ec.visibilityOf(employeeComponentsPage.title), 5000);
    expect(await employeeComponentsPage.getTitle()).to.eq('Employees');
    await browser.wait(ec.or(ec.visibilityOf(employeeComponentsPage.entities), ec.visibilityOf(employeeComponentsPage.noResult)), 1000);
  });

  it('should load create Employee page', async () => {
    await employeeComponentsPage.clickOnCreateButton();
    employeeUpdatePage = new EmployeeUpdatePage();
    expect(await employeeUpdatePage.getPageTitle()).to.eq('Create or edit a Employee');
    await employeeUpdatePage.cancel();
  });

  it('should create and save Employees', async () => {
    const nbButtonsBeforeCreate = await employeeComponentsPage.countDeleteButtons();

    await employeeComponentsPage.clickOnCreateButton();

    await promise.all([
      employeeUpdatePage.setNameInput('name'),
      employeeUpdatePage.setEmpIdInput('empId'),
      employeeUpdatePage.setGlobalIdInput('globalId'),
      employeeUpdatePage.setAttendanceMachineIdInput('attendanceMachineId'),
      employeeUpdatePage.setLocalIdInput('localId'),
      employeeUpdatePage.categorySelectLastOption(),
      employeeUpdatePage.typeSelectLastOption(),
      employeeUpdatePage.setJoiningDateInput('2000-12-31'),
      employeeUpdatePage.statusSelectLastOption(),
      employeeUpdatePage.setTerminationDateInput('2000-12-31'),
      employeeUpdatePage.setTerminationReasonInput('terminationReason'),
      employeeUpdatePage.companySelectLastOption(),
      employeeUpdatePage.departmentSelectLastOption(),
      employeeUpdatePage.gradeSelectLastOption(),
      employeeUpdatePage.designationSelectLastOption(),
      employeeUpdatePage.lineSelectLastOption(),
    ]);

    expect(await employeeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await employeeUpdatePage.getEmpIdInput()).to.eq('empId', 'Expected EmpId value to be equals to empId');
    expect(await employeeUpdatePage.getGlobalIdInput()).to.eq('globalId', 'Expected GlobalId value to be equals to globalId');
    expect(await employeeUpdatePage.getAttendanceMachineIdInput()).to.eq(
      'attendanceMachineId',
      'Expected AttendanceMachineId value to be equals to attendanceMachineId'
    );
    expect(await employeeUpdatePage.getLocalIdInput()).to.eq('localId', 'Expected LocalId value to be equals to localId');
    expect(await employeeUpdatePage.getJoiningDateInput()).to.eq('2000-12-31', 'Expected joiningDate value to be equals to 2000-12-31');
    expect(await employeeUpdatePage.getTerminationDateInput()).to.eq(
      '2000-12-31',
      'Expected terminationDate value to be equals to 2000-12-31'
    );
    expect(await employeeUpdatePage.getTerminationReasonInput()).to.eq(
      'terminationReason',
      'Expected TerminationReason value to be equals to terminationReason'
    );

    await employeeUpdatePage.save();
    expect(await employeeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Employee', async () => {
    const nbButtonsBeforeDelete = await employeeComponentsPage.countDeleteButtons();
    await employeeComponentsPage.clickOnLastDeleteButton();

    employeeDeleteDialog = new EmployeeDeleteDialog();
    expect(await employeeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Employee?');
    await employeeDeleteDialog.clickOnConfirmButton();

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
