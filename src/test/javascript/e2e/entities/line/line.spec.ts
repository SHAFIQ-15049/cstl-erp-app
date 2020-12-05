import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LineComponentsPage, LineDeleteDialog, LineUpdatePage } from './line.page-object';

const expect = chai.expect;

describe('Line e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let lineComponentsPage: LineComponentsPage;
  let lineUpdatePage: LineUpdatePage;
  let lineDeleteDialog: LineDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Lines', async () => {
    await navBarPage.goToEntity('line');
    lineComponentsPage = new LineComponentsPage();
    await browser.wait(ec.visibilityOf(lineComponentsPage.title), 5000);
    expect(await lineComponentsPage.getTitle()).to.eq('Lines');
    await browser.wait(ec.or(ec.visibilityOf(lineComponentsPage.entities), ec.visibilityOf(lineComponentsPage.noResult)), 1000);
  });

  it('should load create Line page', async () => {
    await lineComponentsPage.clickOnCreateButton();
    lineUpdatePage = new LineUpdatePage();
    expect(await lineUpdatePage.getPageTitle()).to.eq('Create or edit a Line');
    await lineUpdatePage.cancel();
  });

  it('should create and save Lines', async () => {
    const nbButtonsBeforeCreate = await lineComponentsPage.countDeleteButtons();

    await lineComponentsPage.clickOnCreateButton();

    await promise.all([
      lineUpdatePage.setNameInput('name'),
      lineUpdatePage.setDescriptionInput('description'),
      lineUpdatePage.departmentSelectLastOption(),
    ]);

    expect(await lineUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await lineUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await lineUpdatePage.save();
    expect(await lineUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await lineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Line', async () => {
    const nbButtonsBeforeDelete = await lineComponentsPage.countDeleteButtons();
    await lineComponentsPage.clickOnLastDeleteButton();

    lineDeleteDialog = new LineDeleteDialog();
    expect(await lineDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Line?');
    await lineDeleteDialog.clickOnConfirmButton();

    expect(await lineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
