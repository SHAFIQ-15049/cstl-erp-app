import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LeaveApplicationComponentsPage,
  /* LeaveApplicationDeleteDialog, */
  LeaveApplicationUpdatePage,
} from './leave-application.page-object';

const expect = chai.expect;

describe('LeaveApplication e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let leaveApplicationComponentsPage: LeaveApplicationComponentsPage;
  let leaveApplicationUpdatePage: LeaveApplicationUpdatePage;
  /* let leaveApplicationDeleteDialog: LeaveApplicationDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LeaveApplications', async () => {
    await navBarPage.goToEntity('leave-application');
    leaveApplicationComponentsPage = new LeaveApplicationComponentsPage();
    await browser.wait(ec.visibilityOf(leaveApplicationComponentsPage.title), 5000);
    expect(await leaveApplicationComponentsPage.getTitle()).to.eq('Leave Applications');
    await browser.wait(
      ec.or(ec.visibilityOf(leaveApplicationComponentsPage.entities), ec.visibilityOf(leaveApplicationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LeaveApplication page', async () => {
    await leaveApplicationComponentsPage.clickOnCreateButton();
    leaveApplicationUpdatePage = new LeaveApplicationUpdatePage();
    expect(await leaveApplicationUpdatePage.getPageTitle()).to.eq('Create or edit a Leave Application');
    await leaveApplicationUpdatePage.cancel();
  });

  /* it('should create and save LeaveApplications', async () => {
        const nbButtonsBeforeCreate = await leaveApplicationComponentsPage.countDeleteButtons();

        await leaveApplicationComponentsPage.clickOnCreateButton();

        await promise.all([
            leaveApplicationUpdatePage.setFromInput('2000-12-31'),
            leaveApplicationUpdatePage.setToInput('2000-12-31'),
            leaveApplicationUpdatePage.setTotalDaysInput('5'),
            leaveApplicationUpdatePage.statusSelectLastOption(),
            leaveApplicationUpdatePage.setReasonInput('reason'),
            leaveApplicationUpdatePage.appliedBySelectLastOption(),
            leaveApplicationUpdatePage.actionTakenBySelectLastOption(),
            leaveApplicationUpdatePage.leaveTypeSelectLastOption(),
        ]);

        expect(await leaveApplicationUpdatePage.getFromInput()).to.eq('2000-12-31', 'Expected from value to be equals to 2000-12-31');
        expect(await leaveApplicationUpdatePage.getToInput()).to.eq('2000-12-31', 'Expected to value to be equals to 2000-12-31');
        expect(await leaveApplicationUpdatePage.getTotalDaysInput()).to.eq('5', 'Expected totalDays value to be equals to 5');
        expect(await leaveApplicationUpdatePage.getReasonInput()).to.eq('reason', 'Expected Reason value to be equals to reason');

        await leaveApplicationUpdatePage.save();
        expect(await leaveApplicationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await leaveApplicationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last LeaveApplication', async () => {
        const nbButtonsBeforeDelete = await leaveApplicationComponentsPage.countDeleteButtons();
        await leaveApplicationComponentsPage.clickOnLastDeleteButton();

        leaveApplicationDeleteDialog = new LeaveApplicationDeleteDialog();
        expect(await leaveApplicationDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Leave Application?');
        await leaveApplicationDeleteDialog.clickOnConfirmButton();

        expect(await leaveApplicationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
