import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WeekendComponentsPage, WeekendDeleteDialog, WeekendUpdatePage } from './weekend.page-object';

const expect = chai.expect;

describe('Weekend e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let weekendComponentsPage: WeekendComponentsPage;
  let weekendUpdatePage: WeekendUpdatePage;
  let weekendDeleteDialog: WeekendDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Weekends', async () => {
    await navBarPage.goToEntity('weekend');
    weekendComponentsPage = new WeekendComponentsPage();
    await browser.wait(ec.visibilityOf(weekendComponentsPage.title), 5000);
    expect(await weekendComponentsPage.getTitle()).to.eq('Weekends');
    await browser.wait(ec.or(ec.visibilityOf(weekendComponentsPage.entities), ec.visibilityOf(weekendComponentsPage.noResult)), 1000);
  });

  it('should load create Weekend page', async () => {
    await weekendComponentsPage.clickOnCreateButton();
    weekendUpdatePage = new WeekendUpdatePage();
    expect(await weekendUpdatePage.getPageTitle()).to.eq('Create or edit a Weekend');
    await weekendUpdatePage.cancel();
  });

  it('should create and save Weekends', async () => {
    const nbButtonsBeforeCreate = await weekendComponentsPage.countDeleteButtons();

    await weekendComponentsPage.clickOnCreateButton();

    await promise.all([weekendUpdatePage.daySelectLastOption(), weekendUpdatePage.statusSelectLastOption()]);

    await weekendUpdatePage.save();
    expect(await weekendUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await weekendComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Weekend', async () => {
    const nbButtonsBeforeDelete = await weekendComponentsPage.countDeleteButtons();
    await weekendComponentsPage.clickOnLastDeleteButton();

    weekendDeleteDialog = new WeekendDeleteDialog();
    expect(await weekendDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Weekend?');
    await weekendDeleteDialog.clickOnConfirmButton();

    expect(await weekendComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
