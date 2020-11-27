import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EducationalInfoComponentsPage, EducationalInfoDeleteDialog, EducationalInfoUpdatePage } from './educational-info.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('EducationalInfo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let educationalInfoComponentsPage: EducationalInfoComponentsPage;
  let educationalInfoUpdatePage: EducationalInfoUpdatePage;
  let educationalInfoDeleteDialog: EducationalInfoDeleteDialog;
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

  it('should load EducationalInfos', async () => {
    await navBarPage.goToEntity('educational-info');
    educationalInfoComponentsPage = new EducationalInfoComponentsPage();
    await browser.wait(ec.visibilityOf(educationalInfoComponentsPage.title), 5000);
    expect(await educationalInfoComponentsPage.getTitle()).to.eq('Educational Infos');
    await browser.wait(
      ec.or(ec.visibilityOf(educationalInfoComponentsPage.entities), ec.visibilityOf(educationalInfoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EducationalInfo page', async () => {
    await educationalInfoComponentsPage.clickOnCreateButton();
    educationalInfoUpdatePage = new EducationalInfoUpdatePage();
    expect(await educationalInfoUpdatePage.getPageTitle()).to.eq('Create or edit a Educational Info');
    await educationalInfoUpdatePage.cancel();
  });

  it('should create and save EducationalInfos', async () => {
    const nbButtonsBeforeCreate = await educationalInfoComponentsPage.countDeleteButtons();

    await educationalInfoComponentsPage.clickOnCreateButton();

    await promise.all([
      educationalInfoUpdatePage.setSerialInput('5'),
      educationalInfoUpdatePage.setDegreeInput('degree'),
      educationalInfoUpdatePage.setInstitutionInput('institution'),
      educationalInfoUpdatePage.setPassingYearInput('5'),
      educationalInfoUpdatePage.setCourseDurationInput('5'),
      educationalInfoUpdatePage.setAttachmentInput(absolutePath),
      educationalInfoUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await educationalInfoUpdatePage.getSerialInput()).to.eq('5', 'Expected serial value to be equals to 5');
    expect(await educationalInfoUpdatePage.getDegreeInput()).to.eq('degree', 'Expected Degree value to be equals to degree');
    expect(await educationalInfoUpdatePage.getInstitutionInput()).to.eq(
      'institution',
      'Expected Institution value to be equals to institution'
    );
    expect(await educationalInfoUpdatePage.getPassingYearInput()).to.eq('5', 'Expected passingYear value to be equals to 5');
    expect(await educationalInfoUpdatePage.getCourseDurationInput()).to.eq('5', 'Expected courseDuration value to be equals to 5');
    expect(await educationalInfoUpdatePage.getAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected Attachment value to be end with ' + fileNameToUpload
    );

    await educationalInfoUpdatePage.save();
    expect(await educationalInfoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await educationalInfoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EducationalInfo', async () => {
    const nbButtonsBeforeDelete = await educationalInfoComponentsPage.countDeleteButtons();
    await educationalInfoComponentsPage.clickOnLastDeleteButton();

    educationalInfoDeleteDialog = new EducationalInfoDeleteDialog();
    expect(await educationalInfoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Educational Info?');
    await educationalInfoDeleteDialog.clickOnConfirmButton();

    expect(await educationalInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
