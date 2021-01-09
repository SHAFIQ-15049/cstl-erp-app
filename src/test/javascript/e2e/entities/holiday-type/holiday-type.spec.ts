import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { HolidayTypeComponentsPage, HolidayTypeDeleteDialog, HolidayTypeUpdatePage } from './holiday-type.page-object';

const expect = chai.expect;

describe('HolidayType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let holidayTypeComponentsPage: HolidayTypeComponentsPage;
  let holidayTypeUpdatePage: HolidayTypeUpdatePage;
  let holidayTypeDeleteDialog: HolidayTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load HolidayTypes', async () => {
    await navBarPage.goToEntity('holiday-type');
    holidayTypeComponentsPage = new HolidayTypeComponentsPage();
    await browser.wait(ec.visibilityOf(holidayTypeComponentsPage.title), 5000);
    expect(await holidayTypeComponentsPage.getTitle()).to.eq('Holiday Types');
    await browser.wait(
      ec.or(ec.visibilityOf(holidayTypeComponentsPage.entities), ec.visibilityOf(holidayTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create HolidayType page', async () => {
    await holidayTypeComponentsPage.clickOnCreateButton();
    holidayTypeUpdatePage = new HolidayTypeUpdatePage();
    expect(await holidayTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Holiday Type');
    await holidayTypeUpdatePage.cancel();
  });

  it('should create and save HolidayTypes', async () => {
    const nbButtonsBeforeCreate = await holidayTypeComponentsPage.countDeleteButtons();

    await holidayTypeComponentsPage.clickOnCreateButton();

    await promise.all([holidayTypeUpdatePage.setNameInput('name')]);

    expect(await holidayTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await holidayTypeUpdatePage.save();
    expect(await holidayTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await holidayTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last HolidayType', async () => {
    const nbButtonsBeforeDelete = await holidayTypeComponentsPage.countDeleteButtons();
    await holidayTypeComponentsPage.clickOnLastDeleteButton();

    holidayTypeDeleteDialog = new HolidayTypeDeleteDialog();
    expect(await holidayTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Holiday Type?');
    await holidayTypeDeleteDialog.clickOnConfirmButton();

    expect(await holidayTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
