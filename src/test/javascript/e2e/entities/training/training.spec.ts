import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TrainingComponentsPage, TrainingDeleteDialog, TrainingUpdatePage } from './training.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Training e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let trainingComponentsPage: TrainingComponentsPage;
  let trainingUpdatePage: TrainingUpdatePage;
  let trainingDeleteDialog: TrainingDeleteDialog;
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

  it('should load Trainings', async () => {
    await navBarPage.goToEntity('training');
    trainingComponentsPage = new TrainingComponentsPage();
    await browser.wait(ec.visibilityOf(trainingComponentsPage.title), 5000);
    expect(await trainingComponentsPage.getTitle()).to.eq('Trainings');
    await browser.wait(ec.or(ec.visibilityOf(trainingComponentsPage.entities), ec.visibilityOf(trainingComponentsPage.noResult)), 1000);
  });

  it('should load create Training page', async () => {
    await trainingComponentsPage.clickOnCreateButton();
    trainingUpdatePage = new TrainingUpdatePage();
    expect(await trainingUpdatePage.getPageTitle()).to.eq('Create or edit a Training');
    await trainingUpdatePage.cancel();
  });

  it('should create and save Trainings', async () => {
    const nbButtonsBeforeCreate = await trainingComponentsPage.countDeleteButtons();

    await trainingComponentsPage.clickOnCreateButton();

    await promise.all([
      trainingUpdatePage.setSerialInput('5'),
      trainingUpdatePage.setNameInput('name'),
      trainingUpdatePage.setTrainingInstituteInput('trainingInstitute'),
      trainingUpdatePage.setReceivedOnInput('2000-12-31'),
      trainingUpdatePage.setAttachmentInput(absolutePath),
      trainingUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await trainingUpdatePage.getSerialInput()).to.eq('5', 'Expected serial value to be equals to 5');
    expect(await trainingUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await trainingUpdatePage.getTrainingInstituteInput()).to.eq(
      'trainingInstitute',
      'Expected TrainingInstitute value to be equals to trainingInstitute'
    );
    expect(await trainingUpdatePage.getReceivedOnInput()).to.eq('2000-12-31', 'Expected receivedOn value to be equals to 2000-12-31');
    expect(await trainingUpdatePage.getAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected Attachment value to be end with ' + fileNameToUpload
    );

    await trainingUpdatePage.save();
    expect(await trainingUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await trainingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Training', async () => {
    const nbButtonsBeforeDelete = await trainingComponentsPage.countDeleteButtons();
    await trainingComponentsPage.clickOnLastDeleteButton();

    trainingDeleteDialog = new TrainingDeleteDialog();
    expect(await trainingDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Training?');
    await trainingDeleteDialog.clickOnConfirmButton();

    expect(await trainingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
