import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  HolidayComponentsPage,
  /* HolidayDeleteDialog, */
  HolidayUpdatePage,
} from './holiday.page-object';

const expect = chai.expect;

describe('Holiday e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let holidayComponentsPage: HolidayComponentsPage;
  let holidayUpdatePage: HolidayUpdatePage;
  /* let holidayDeleteDialog: HolidayDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Holidays', async () => {
    await navBarPage.goToEntity('holiday');
    holidayComponentsPage = new HolidayComponentsPage();
    await browser.wait(ec.visibilityOf(holidayComponentsPage.title), 5000);
    expect(await holidayComponentsPage.getTitle()).to.eq('Holidays');
    await browser.wait(ec.or(ec.visibilityOf(holidayComponentsPage.entities), ec.visibilityOf(holidayComponentsPage.noResult)), 1000);
  });

  it('should load create Holiday page', async () => {
    await holidayComponentsPage.clickOnCreateButton();
    holidayUpdatePage = new HolidayUpdatePage();
    expect(await holidayUpdatePage.getPageTitle()).to.eq('Create or edit a Holiday');
    await holidayUpdatePage.cancel();
  });

  /* it('should create and save Holidays', async () => {
        const nbButtonsBeforeCreate = await holidayComponentsPage.countDeleteButtons();

        await holidayComponentsPage.clickOnCreateButton();

        await promise.all([
            holidayUpdatePage.setFromInput('2000-12-31'),
            holidayUpdatePage.setToInput('2000-12-31'),
            holidayUpdatePage.setTotalDaysInput('5'),
            holidayUpdatePage.holidayTypeSelectLastOption(),
        ]);

        expect(await holidayUpdatePage.getFromInput()).to.eq('2000-12-31', 'Expected from value to be equals to 2000-12-31');
        expect(await holidayUpdatePage.getToInput()).to.eq('2000-12-31', 'Expected to value to be equals to 2000-12-31');
        expect(await holidayUpdatePage.getTotalDaysInput()).to.eq('5', 'Expected totalDays value to be equals to 5');

        await holidayUpdatePage.save();
        expect(await holidayUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await holidayComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Holiday', async () => {
        const nbButtonsBeforeDelete = await holidayComponentsPage.countDeleteButtons();
        await holidayComponentsPage.clickOnLastDeleteButton();

        holidayDeleteDialog = new HolidayDeleteDialog();
        expect(await holidayDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Holiday?');
        await holidayDeleteDialog.clickOnConfirmButton();

        expect(await holidayComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
