import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AttendanceDataUploadComponentsPage,
  AttendanceDataUploadDeleteDialog,
  AttendanceDataUploadUpdatePage,
} from './attendance-data-upload.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AttendanceDataUpload e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let attendanceDataUploadComponentsPage: AttendanceDataUploadComponentsPage;
  let attendanceDataUploadUpdatePage: AttendanceDataUploadUpdatePage;
  let attendanceDataUploadDeleteDialog: AttendanceDataUploadDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AttendanceDataUploads', async () => {
    await navBarPage.goToEntity('attendance-data-upload');
    attendanceDataUploadComponentsPage = new AttendanceDataUploadComponentsPage();
    await browser.wait(ec.visibilityOf(attendanceDataUploadComponentsPage.title), 5000);
    expect(await attendanceDataUploadComponentsPage.getTitle()).to.eq('Attendance Data Uploads');
    await browser.wait(
      ec.or(ec.visibilityOf(attendanceDataUploadComponentsPage.entities), ec.visibilityOf(attendanceDataUploadComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AttendanceDataUpload page', async () => {
    await attendanceDataUploadComponentsPage.clickOnCreateButton();
    attendanceDataUploadUpdatePage = new AttendanceDataUploadUpdatePage();
    expect(await attendanceDataUploadUpdatePage.getPageTitle()).to.eq('Create or edit a Attendance Data Upload');
    await attendanceDataUploadUpdatePage.cancel();
  });

  it('should create and save AttendanceDataUploads', async () => {
    const nbButtonsBeforeCreate = await attendanceDataUploadComponentsPage.countDeleteButtons();

    await attendanceDataUploadComponentsPage.clickOnCreateButton();

    await promise.all([attendanceDataUploadUpdatePage.setFileUploadInput(absolutePath)]);

    expect(await attendanceDataUploadUpdatePage.getFileUploadInput()).to.endsWith(
      fileNameToUpload,
      'Expected FileUpload value to be end with ' + fileNameToUpload
    );

    await attendanceDataUploadUpdatePage.save();
    expect(await attendanceDataUploadUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await attendanceDataUploadComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AttendanceDataUpload', async () => {
    const nbButtonsBeforeDelete = await attendanceDataUploadComponentsPage.countDeleteButtons();
    await attendanceDataUploadComponentsPage.clickOnLastDeleteButton();

    attendanceDataUploadDeleteDialog = new AttendanceDataUploadDeleteDialog();
    expect(await attendanceDataUploadDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Attendance Data Upload?');
    await attendanceDataUploadDeleteDialog.clickOnConfirmButton();

    expect(await attendanceDataUploadComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
