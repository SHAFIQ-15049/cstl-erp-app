import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ServiceHistoryComponentsPage, ServiceHistoryDeleteDialog, ServiceHistoryUpdatePage } from './service-history.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ServiceHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceHistoryComponentsPage: ServiceHistoryComponentsPage;
  let serviceHistoryUpdatePage: ServiceHistoryUpdatePage;
  let serviceHistoryDeleteDialog: ServiceHistoryDeleteDialog;
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

  it('should load ServiceHistories', async () => {
    await navBarPage.goToEntity('service-history');
    serviceHistoryComponentsPage = new ServiceHistoryComponentsPage();
    await browser.wait(ec.visibilityOf(serviceHistoryComponentsPage.title), 5000);
    expect(await serviceHistoryComponentsPage.getTitle()).to.eq('Service Histories');
    await browser.wait(
      ec.or(ec.visibilityOf(serviceHistoryComponentsPage.entities), ec.visibilityOf(serviceHistoryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ServiceHistory page', async () => {
    await serviceHistoryComponentsPage.clickOnCreateButton();
    serviceHistoryUpdatePage = new ServiceHistoryUpdatePage();
    expect(await serviceHistoryUpdatePage.getPageTitle()).to.eq('Create or edit a Service History');
    await serviceHistoryUpdatePage.cancel();
  });

  it('should create and save ServiceHistories', async () => {
    const nbButtonsBeforeCreate = await serviceHistoryComponentsPage.countDeleteButtons();

    await serviceHistoryComponentsPage.clickOnCreateButton();

    await promise.all([
      serviceHistoryUpdatePage.employeeTypeSelectLastOption(),
      serviceHistoryUpdatePage.categorySelectLastOption(),
      serviceHistoryUpdatePage.setFromInput('2000-12-31'),
      serviceHistoryUpdatePage.setToInput('2000-12-31'),
      serviceHistoryUpdatePage.setAttachmentInput(absolutePath),
      serviceHistoryUpdatePage.statusSelectLastOption(),
      serviceHistoryUpdatePage.departmentSelectLastOption(),
      serviceHistoryUpdatePage.designationSelectLastOption(),
      serviceHistoryUpdatePage.gradeSelectLastOption(),
      serviceHistoryUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await serviceHistoryUpdatePage.getFromInput()).to.eq('2000-12-31', 'Expected from value to be equals to 2000-12-31');
    expect(await serviceHistoryUpdatePage.getToInput()).to.eq('2000-12-31', 'Expected to value to be equals to 2000-12-31');
    expect(await serviceHistoryUpdatePage.getAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected Attachment value to be end with ' + fileNameToUpload
    );

    await serviceHistoryUpdatePage.save();
    expect(await serviceHistoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await serviceHistoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ServiceHistory', async () => {
    const nbButtonsBeforeDelete = await serviceHistoryComponentsPage.countDeleteButtons();
    await serviceHistoryComponentsPage.clickOnLastDeleteButton();

    serviceHistoryDeleteDialog = new ServiceHistoryDeleteDialog();
    expect(await serviceHistoryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Service History?');
    await serviceHistoryDeleteDialog.clickOnConfirmButton();

    expect(await serviceHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
