import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ThanaComponentsPage, ThanaDeleteDialog, ThanaUpdatePage } from './thana.page-object';

const expect = chai.expect;

describe('Thana e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let thanaComponentsPage: ThanaComponentsPage;
  let thanaUpdatePage: ThanaUpdatePage;
  let thanaDeleteDialog: ThanaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Thanas', async () => {
    await navBarPage.goToEntity('thana');
    thanaComponentsPage = new ThanaComponentsPage();
    await browser.wait(ec.visibilityOf(thanaComponentsPage.title), 5000);
    expect(await thanaComponentsPage.getTitle()).to.eq('Thanas');
    await browser.wait(ec.or(ec.visibilityOf(thanaComponentsPage.entities), ec.visibilityOf(thanaComponentsPage.noResult)), 1000);
  });

  it('should load create Thana page', async () => {
    await thanaComponentsPage.clickOnCreateButton();
    thanaUpdatePage = new ThanaUpdatePage();
    expect(await thanaUpdatePage.getPageTitle()).to.eq('Create or edit a Thana');
    await thanaUpdatePage.cancel();
  });

  it('should create and save Thanas', async () => {
    const nbButtonsBeforeCreate = await thanaComponentsPage.countDeleteButtons();

    await thanaComponentsPage.clickOnCreateButton();

    await promise.all([
      thanaUpdatePage.setNameInput('name'),
      thanaUpdatePage.setBanglaInput('bangla'),
      thanaUpdatePage.setWebInput('web'),
      thanaUpdatePage.districtSelectLastOption(),
    ]);

    expect(await thanaUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await thanaUpdatePage.getBanglaInput()).to.eq('bangla', 'Expected Bangla value to be equals to bangla');
    expect(await thanaUpdatePage.getWebInput()).to.eq('web', 'Expected Web value to be equals to web');

    await thanaUpdatePage.save();
    expect(await thanaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await thanaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Thana', async () => {
    const nbButtonsBeforeDelete = await thanaComponentsPage.countDeleteButtons();
    await thanaComponentsPage.clickOnLastDeleteButton();

    thanaDeleteDialog = new ThanaDeleteDialog();
    expect(await thanaDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Thana?');
    await thanaDeleteDialog.clickOnConfirmButton();

    expect(await thanaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
