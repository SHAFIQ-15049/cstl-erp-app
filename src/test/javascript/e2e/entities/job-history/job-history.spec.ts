import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { JobHistoryComponentsPage, JobHistoryDeleteDialog, JobHistoryUpdatePage } from './job-history.page-object';

const expect = chai.expect;

describe('JobHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let jobHistoryComponentsPage: JobHistoryComponentsPage;
  let jobHistoryUpdatePage: JobHistoryUpdatePage;
  let jobHistoryDeleteDialog: JobHistoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load JobHistories', async () => {
    await navBarPage.goToEntity('job-history');
    jobHistoryComponentsPage = new JobHistoryComponentsPage();
    await browser.wait(ec.visibilityOf(jobHistoryComponentsPage.title), 5000);
    expect(await jobHistoryComponentsPage.getTitle()).to.eq('Job Histories');
    await browser.wait(ec.or(ec.visibilityOf(jobHistoryComponentsPage.entities), ec.visibilityOf(jobHistoryComponentsPage.noResult)), 1000);
  });

  it('should load create JobHistory page', async () => {
    await jobHistoryComponentsPage.clickOnCreateButton();
    jobHistoryUpdatePage = new JobHistoryUpdatePage();
    expect(await jobHistoryUpdatePage.getPageTitle()).to.eq('Create or edit a Job History');
    await jobHistoryUpdatePage.cancel();
  });

  it('should create and save JobHistories', async () => {
    const nbButtonsBeforeCreate = await jobHistoryComponentsPage.countDeleteButtons();

    await jobHistoryComponentsPage.clickOnCreateButton();

    await promise.all([
      jobHistoryUpdatePage.setSerialInput('5'),
      jobHistoryUpdatePage.setOrganizationInput('organization'),
      jobHistoryUpdatePage.setDesignationInput('designation'),
      jobHistoryUpdatePage.setFromInput('2000-12-31'),
      jobHistoryUpdatePage.setToInput('2000-12-31'),
      jobHistoryUpdatePage.setPayScaleInput('5'),
      jobHistoryUpdatePage.setTotalExperienceInput('5'),
      jobHistoryUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await jobHistoryUpdatePage.getSerialInput()).to.eq('5', 'Expected serial value to be equals to 5');
    expect(await jobHistoryUpdatePage.getOrganizationInput()).to.eq(
      'organization',
      'Expected Organization value to be equals to organization'
    );
    expect(await jobHistoryUpdatePage.getDesignationInput()).to.eq('designation', 'Expected Designation value to be equals to designation');
    expect(await jobHistoryUpdatePage.getFromInput()).to.eq('2000-12-31', 'Expected from value to be equals to 2000-12-31');
    expect(await jobHistoryUpdatePage.getToInput()).to.eq('2000-12-31', 'Expected to value to be equals to 2000-12-31');
    expect(await jobHistoryUpdatePage.getPayScaleInput()).to.eq('5', 'Expected payScale value to be equals to 5');
    expect(await jobHistoryUpdatePage.getTotalExperienceInput()).to.eq('5', 'Expected totalExperience value to be equals to 5');

    await jobHistoryUpdatePage.save();
    expect(await jobHistoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await jobHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last JobHistory', async () => {
    const nbButtonsBeforeDelete = await jobHistoryComponentsPage.countDeleteButtons();
    await jobHistoryComponentsPage.clickOnLastDeleteButton();

    jobHistoryDeleteDialog = new JobHistoryDeleteDialog();
    expect(await jobHistoryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Job History?');
    await jobHistoryDeleteDialog.clickOnConfirmButton();

    expect(await jobHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
