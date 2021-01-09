import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LeaveTypeComponentsPage, LeaveTypeDeleteDialog, LeaveTypeUpdatePage } from './leave-type.page-object';

const expect = chai.expect;

describe('LeaveType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let leaveTypeComponentsPage: LeaveTypeComponentsPage;
  let leaveTypeUpdatePage: LeaveTypeUpdatePage;
  let leaveTypeDeleteDialog: LeaveTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LeaveTypes', async () => {
    await navBarPage.goToEntity('leave-type');
    leaveTypeComponentsPage = new LeaveTypeComponentsPage();
    await browser.wait(ec.visibilityOf(leaveTypeComponentsPage.title), 5000);
    expect(await leaveTypeComponentsPage.getTitle()).to.eq('Leave Types');
    await browser.wait(ec.or(ec.visibilityOf(leaveTypeComponentsPage.entities), ec.visibilityOf(leaveTypeComponentsPage.noResult)), 1000);
  });

  it('should load create LeaveType page', async () => {
    await leaveTypeComponentsPage.clickOnCreateButton();
    leaveTypeUpdatePage = new LeaveTypeUpdatePage();
    expect(await leaveTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Leave Type');
    await leaveTypeUpdatePage.cancel();
  });

  it('should create and save LeaveTypes', async () => {
    const nbButtonsBeforeCreate = await leaveTypeComponentsPage.countDeleteButtons();

    await leaveTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      leaveTypeUpdatePage.setNameInput('name'),
      leaveTypeUpdatePage.setTotalDaysInput('5'),
      leaveTypeUpdatePage.setMaxValidityInput('5'),
    ]);

    expect(await leaveTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await leaveTypeUpdatePage.getTotalDaysInput()).to.eq('5', 'Expected totalDays value to be equals to 5');
    expect(await leaveTypeUpdatePage.getMaxValidityInput()).to.eq('5', 'Expected maxValidity value to be equals to 5');

    await leaveTypeUpdatePage.save();
    expect(await leaveTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await leaveTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LeaveType', async () => {
    const nbButtonsBeforeDelete = await leaveTypeComponentsPage.countDeleteButtons();
    await leaveTypeComponentsPage.clickOnLastDeleteButton();

    leaveTypeDeleteDialog = new LeaveTypeDeleteDialog();
    expect(await leaveTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Leave Type?');
    await leaveTypeDeleteDialog.clickOnConfirmButton();

    expect(await leaveTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
