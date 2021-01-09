import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AttendanceComponentsPage, AttendanceUpdatePage } from './attendance.page-object';

const expect = chai.expect;

describe('Attendance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let attendanceComponentsPage: AttendanceComponentsPage;
  let attendanceUpdatePage: AttendanceUpdatePage;
  /* let attendanceDeleteDialog: AttendanceDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Attendances', async () => {
    await navBarPage.goToEntity('attendance');
    attendanceComponentsPage = new AttendanceComponentsPage();
    await browser.wait(ec.visibilityOf(attendanceComponentsPage.title), 5000);
    expect(await attendanceComponentsPage.getTitle()).to.eq('Attendances');
    await browser.wait(ec.or(ec.visibilityOf(attendanceComponentsPage.entities), ec.visibilityOf(attendanceComponentsPage.noResult)), 1000);
  });

  it('should load create Attendance page', async () => {
    await attendanceComponentsPage.clickOnCreateButton();
    attendanceUpdatePage = new AttendanceUpdatePage();
    expect(await attendanceUpdatePage.getPageTitle()).to.eq('Create or edit a Attendance');
    await attendanceUpdatePage.cancel();
  });

  /* it('should create and save Attendances', async () => {
        const nbButtonsBeforeCreate = await attendanceComponentsPage.countDeleteButtons();

        await attendanceComponentsPage.clickOnCreateButton();

        await promise.all([
            attendanceUpdatePage.setAttendanceDateInput('2000-12-31'),
            attendanceUpdatePage.setAttendanceTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            attendanceUpdatePage.considerAsSelectLastOption(),
            attendanceUpdatePage.setMachineNoInput('machineNo'),
            attendanceUpdatePage.employeeSelectLastOption(),
            attendanceUpdatePage.attendanceDataUploadSelectLastOption(),
            attendanceUpdatePage.employeeSalarySelectLastOption(),
        ]);

        expect(await attendanceUpdatePage.getAttendanceDateInput()).to.eq('2000-12-31', 'Expected attendanceDate value to be equals to 2000-12-31');
        expect(await attendanceUpdatePage.getAttendanceTimeInput()).to.contain('2001-01-01T02:30', 'Expected attendanceTime value to be equals to 2000-12-31');
        expect(await attendanceUpdatePage.getMachineNoInput()).to.eq('machineNo', 'Expected MachineNo value to be equals to machineNo');

        await attendanceUpdatePage.save();
        expect(await attendanceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await attendanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Attendance', async () => {
        const nbButtonsBeforeDelete = await attendanceComponentsPage.countDeleteButtons();
        await attendanceComponentsPage.clickOnLastDeleteButton();

        attendanceDeleteDialog = new AttendanceDeleteDialog();
        expect(await attendanceDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Attendance?');
        await attendanceDeleteDialog.clickOnConfirmButton();

        expect(await attendanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
