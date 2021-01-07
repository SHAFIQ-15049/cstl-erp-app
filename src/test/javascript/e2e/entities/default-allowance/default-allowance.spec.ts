import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DefaultAllowanceComponentsPage, DefaultAllowanceDeleteDialog, DefaultAllowanceUpdatePage } from './default-allowance.page-object';

const expect = chai.expect;

describe('DefaultAllowance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let defaultAllowanceComponentsPage: DefaultAllowanceComponentsPage;
  let defaultAllowanceUpdatePage: DefaultAllowanceUpdatePage;
  let defaultAllowanceDeleteDialog: DefaultAllowanceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DefaultAllowances', async () => {
    await navBarPage.goToEntity('default-allowance');
    defaultAllowanceComponentsPage = new DefaultAllowanceComponentsPage();
    await browser.wait(ec.visibilityOf(defaultAllowanceComponentsPage.title), 5000);
    expect(await defaultAllowanceComponentsPage.getTitle()).to.eq('Default Allowances');
    await browser.wait(
      ec.or(ec.visibilityOf(defaultAllowanceComponentsPage.entities), ec.visibilityOf(defaultAllowanceComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DefaultAllowance page', async () => {
    await defaultAllowanceComponentsPage.clickOnCreateButton();
    defaultAllowanceUpdatePage = new DefaultAllowanceUpdatePage();
    expect(await defaultAllowanceUpdatePage.getPageTitle()).to.eq('Create or edit a Default Allowance');
    await defaultAllowanceUpdatePage.cancel();
  });

  it('should create and save DefaultAllowances', async () => {
    const nbButtonsBeforeCreate = await defaultAllowanceComponentsPage.countDeleteButtons();

    await defaultAllowanceComponentsPage.clickOnCreateButton();

    await promise.all([
      defaultAllowanceUpdatePage.setBasicInput('5'),
      defaultAllowanceUpdatePage.setBasicPercentInput('5'),
      defaultAllowanceUpdatePage.setTotalAllowanceInput('5'),
      defaultAllowanceUpdatePage.setMedicalAllowanceInput('5'),
      defaultAllowanceUpdatePage.setMedicalAllowancePercentInput('5'),
      defaultAllowanceUpdatePage.setConvinceAllowanceInput('5'),
      defaultAllowanceUpdatePage.setConvinceAllowancePercentInput('5'),
      defaultAllowanceUpdatePage.setFoodAllowanceInput('5'),
      defaultAllowanceUpdatePage.setFoodAllowancePercentInput('5'),
      defaultAllowanceUpdatePage.setFestivalAllowanceInput('5'),
      defaultAllowanceUpdatePage.setFestivalAllowancePercentInput('5'),
      defaultAllowanceUpdatePage.statusSelectLastOption(),
    ]);

    expect(await defaultAllowanceUpdatePage.getBasicInput()).to.eq('5', 'Expected basic value to be equals to 5');
    expect(await defaultAllowanceUpdatePage.getBasicPercentInput()).to.eq('5', 'Expected basicPercent value to be equals to 5');
    expect(await defaultAllowanceUpdatePage.getTotalAllowanceInput()).to.eq('5', 'Expected totalAllowance value to be equals to 5');
    expect(await defaultAllowanceUpdatePage.getMedicalAllowanceInput()).to.eq('5', 'Expected medicalAllowance value to be equals to 5');
    expect(await defaultAllowanceUpdatePage.getMedicalAllowancePercentInput()).to.eq(
      '5',
      'Expected medicalAllowancePercent value to be equals to 5'
    );
    expect(await defaultAllowanceUpdatePage.getConvinceAllowanceInput()).to.eq('5', 'Expected convinceAllowance value to be equals to 5');
    expect(await defaultAllowanceUpdatePage.getConvinceAllowancePercentInput()).to.eq(
      '5',
      'Expected convinceAllowancePercent value to be equals to 5'
    );
    expect(await defaultAllowanceUpdatePage.getFoodAllowanceInput()).to.eq('5', 'Expected foodAllowance value to be equals to 5');
    expect(await defaultAllowanceUpdatePage.getFoodAllowancePercentInput()).to.eq(
      '5',
      'Expected foodAllowancePercent value to be equals to 5'
    );
    expect(await defaultAllowanceUpdatePage.getFestivalAllowanceInput()).to.eq('5', 'Expected festivalAllowance value to be equals to 5');
    expect(await defaultAllowanceUpdatePage.getFestivalAllowancePercentInput()).to.eq(
      '5',
      'Expected festivalAllowancePercent value to be equals to 5'
    );

    await defaultAllowanceUpdatePage.save();
    expect(await defaultAllowanceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await defaultAllowanceComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DefaultAllowance', async () => {
    const nbButtonsBeforeDelete = await defaultAllowanceComponentsPage.countDeleteButtons();
    await defaultAllowanceComponentsPage.clickOnLastDeleteButton();

    defaultAllowanceDeleteDialog = new DefaultAllowanceDeleteDialog();
    expect(await defaultAllowanceDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Default Allowance?');
    await defaultAllowanceDeleteDialog.clickOnConfirmButton();

    expect(await defaultAllowanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
