import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GradeComponentsPage, GradeDeleteDialog, GradeUpdatePage } from './grade.page-object';

const expect = chai.expect;

describe('Grade e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let gradeComponentsPage: GradeComponentsPage;
  let gradeUpdatePage: GradeUpdatePage;
  let gradeDeleteDialog: GradeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Grades', async () => {
    await navBarPage.goToEntity('grade');
    gradeComponentsPage = new GradeComponentsPage();
    await browser.wait(ec.visibilityOf(gradeComponentsPage.title), 5000);
    expect(await gradeComponentsPage.getTitle()).to.eq('Grades');
    await browser.wait(ec.or(ec.visibilityOf(gradeComponentsPage.entities), ec.visibilityOf(gradeComponentsPage.noResult)), 1000);
  });

  it('should load create Grade page', async () => {
    await gradeComponentsPage.clickOnCreateButton();
    gradeUpdatePage = new GradeUpdatePage();
    expect(await gradeUpdatePage.getPageTitle()).to.eq('Create or edit a Grade');
    await gradeUpdatePage.cancel();
  });

  it('should create and save Grades', async () => {
    const nbButtonsBeforeCreate = await gradeComponentsPage.countDeleteButtons();

    await gradeComponentsPage.clickOnCreateButton();

    await promise.all([
      gradeUpdatePage.categorySelectLastOption(),
      gradeUpdatePage.setNameInput('name'),
      gradeUpdatePage.setDescriptionInput('description'),
      gradeUpdatePage.setInitialSalaryInput('5'),
    ]);

    expect(await gradeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await gradeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await gradeUpdatePage.getInitialSalaryInput()).to.eq('5', 'Expected initialSalary value to be equals to 5');

    await gradeUpdatePage.save();
    expect(await gradeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await gradeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Grade', async () => {
    const nbButtonsBeforeDelete = await gradeComponentsPage.countDeleteButtons();
    await gradeComponentsPage.clickOnLastDeleteButton();

    gradeDeleteDialog = new GradeDeleteDialog();
    expect(await gradeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Grade?');
    await gradeDeleteDialog.clickOnConfirmButton();

    expect(await gradeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
