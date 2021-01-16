import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AttendanceComponentsPage } from './attendance.page-object';

const expect = chai.expect;

describe('Attendance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let attendanceComponentsPage: AttendanceComponentsPage;

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

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
