import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FestivalAllowanceTimeLineComponentsPage,
  FestivalAllowanceTimeLineDeleteDialog,
  FestivalAllowanceTimeLineUpdatePage,
} from './festival-allowance-time-line.page-object';

const expect = chai.expect;

describe('FestivalAllowanceTimeLine e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let festivalAllowanceTimeLineComponentsPage: FestivalAllowanceTimeLineComponentsPage;
  let festivalAllowanceTimeLineUpdatePage: FestivalAllowanceTimeLineUpdatePage;
  let festivalAllowanceTimeLineDeleteDialog: FestivalAllowanceTimeLineDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FestivalAllowanceTimeLines', async () => {
    await navBarPage.goToEntity('festival-allowance-time-line');
    festivalAllowanceTimeLineComponentsPage = new FestivalAllowanceTimeLineComponentsPage();
    await browser.wait(ec.visibilityOf(festivalAllowanceTimeLineComponentsPage.title), 5000);
    expect(await festivalAllowanceTimeLineComponentsPage.getTitle()).to.eq('Festival Allowance Time Lines');
    await browser.wait(
      ec.or(
        ec.visibilityOf(festivalAllowanceTimeLineComponentsPage.entities),
        ec.visibilityOf(festivalAllowanceTimeLineComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create FestivalAllowanceTimeLine page', async () => {
    await festivalAllowanceTimeLineComponentsPage.clickOnCreateButton();
    festivalAllowanceTimeLineUpdatePage = new FestivalAllowanceTimeLineUpdatePage();
    expect(await festivalAllowanceTimeLineUpdatePage.getPageTitle()).to.eq('Create or edit a Festival Allowance Time Line');
    await festivalAllowanceTimeLineUpdatePage.cancel();
  });

  it('should create and save FestivalAllowanceTimeLines', async () => {
    const nbButtonsBeforeCreate = await festivalAllowanceTimeLineComponentsPage.countDeleteButtons();

    await festivalAllowanceTimeLineComponentsPage.clickOnCreateButton();

    await promise.all([festivalAllowanceTimeLineUpdatePage.setYearInput('5'), festivalAllowanceTimeLineUpdatePage.monthSelectLastOption()]);

    expect(await festivalAllowanceTimeLineUpdatePage.getYearInput()).to.eq('5', 'Expected year value to be equals to 5');

    await festivalAllowanceTimeLineUpdatePage.save();
    expect(await festivalAllowanceTimeLineUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await festivalAllowanceTimeLineComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FestivalAllowanceTimeLine', async () => {
    const nbButtonsBeforeDelete = await festivalAllowanceTimeLineComponentsPage.countDeleteButtons();
    await festivalAllowanceTimeLineComponentsPage.clickOnLastDeleteButton();

    festivalAllowanceTimeLineDeleteDialog = new FestivalAllowanceTimeLineDeleteDialog();
    expect(await festivalAllowanceTimeLineDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Festival Allowance Time Line?'
    );
    await festivalAllowanceTimeLineDeleteDialog.clickOnConfirmButton();

    expect(await festivalAllowanceTimeLineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
