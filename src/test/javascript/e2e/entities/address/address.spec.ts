import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AddressComponentsPage, AddressDeleteDialog, AddressUpdatePage } from './address.page-object';

const expect = chai.expect;

describe('Address e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let addressComponentsPage: AddressComponentsPage;
  let addressUpdatePage: AddressUpdatePage;
  let addressDeleteDialog: AddressDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Addresses', async () => {
    await navBarPage.goToEntity('address');
    addressComponentsPage = new AddressComponentsPage();
    await browser.wait(ec.visibilityOf(addressComponentsPage.title), 5000);
    expect(await addressComponentsPage.getTitle()).to.eq('Addresses');
    await browser.wait(ec.or(ec.visibilityOf(addressComponentsPage.entities), ec.visibilityOf(addressComponentsPage.noResult)), 1000);
  });

  it('should load create Address page', async () => {
    await addressComponentsPage.clickOnCreateButton();
    addressUpdatePage = new AddressUpdatePage();
    expect(await addressUpdatePage.getPageTitle()).to.eq('Create or edit a Address');
    await addressUpdatePage.cancel();
  });

  it('should create and save Addresses', async () => {
    const nbButtonsBeforeCreate = await addressComponentsPage.countDeleteButtons();

    await addressComponentsPage.clickOnCreateButton();

    await promise.all([
      addressUpdatePage.setPresentThanaTxtInput('presentThanaTxt'),
      addressUpdatePage.setPresentStreetInput('presentStreet'),
      addressUpdatePage.setPresentStreetBanglaInput('presentStreetBangla'),
      addressUpdatePage.setPresentAreaInput('presentArea'),
      addressUpdatePage.setPresentAreaBanglaInput('presentAreaBangla'),
      addressUpdatePage.setPresentPostCodeInput('5'),
      addressUpdatePage.setPresentPostCodeBanglaInput('presentPostCodeBangla'),
      addressUpdatePage.setPermanentThanaTxtInput('permanentThanaTxt'),
      addressUpdatePage.setPermanentStreetInput('permanentStreet'),
      addressUpdatePage.setPermanentStreetBanglaInput('permanentStreetBangla'),
      addressUpdatePage.setPermanentAreaInput('permanentArea'),
      addressUpdatePage.setPermanentAreaBanglaInput('permanentAreaBangla'),
      addressUpdatePage.setPermanentPostCodeInput('5'),
      addressUpdatePage.setPermenentPostCodeBanglaInput('permenentPostCodeBangla'),
      addressUpdatePage.employeeSelectLastOption(),
      addressUpdatePage.presentDivisionSelectLastOption(),
      addressUpdatePage.presentDistrictSelectLastOption(),
      addressUpdatePage.presentThanaSelectLastOption(),
      addressUpdatePage.permanentDivisionSelectLastOption(),
      addressUpdatePage.permanentDistrictSelectLastOption(),
      addressUpdatePage.permanentThanaSelectLastOption(),
    ]);

    expect(await addressUpdatePage.getPresentThanaTxtInput()).to.eq(
      'presentThanaTxt',
      'Expected PresentThanaTxt value to be equals to presentThanaTxt'
    );
    expect(await addressUpdatePage.getPresentStreetInput()).to.eq(
      'presentStreet',
      'Expected PresentStreet value to be equals to presentStreet'
    );
    expect(await addressUpdatePage.getPresentStreetBanglaInput()).to.eq(
      'presentStreetBangla',
      'Expected PresentStreetBangla value to be equals to presentStreetBangla'
    );
    expect(await addressUpdatePage.getPresentAreaInput()).to.eq('presentArea', 'Expected PresentArea value to be equals to presentArea');
    expect(await addressUpdatePage.getPresentAreaBanglaInput()).to.eq(
      'presentAreaBangla',
      'Expected PresentAreaBangla value to be equals to presentAreaBangla'
    );
    expect(await addressUpdatePage.getPresentPostCodeInput()).to.eq('5', 'Expected presentPostCode value to be equals to 5');
    expect(await addressUpdatePage.getPresentPostCodeBanglaInput()).to.eq(
      'presentPostCodeBangla',
      'Expected PresentPostCodeBangla value to be equals to presentPostCodeBangla'
    );
    expect(await addressUpdatePage.getPermanentThanaTxtInput()).to.eq(
      'permanentThanaTxt',
      'Expected PermanentThanaTxt value to be equals to permanentThanaTxt'
    );
    expect(await addressUpdatePage.getPermanentStreetInput()).to.eq(
      'permanentStreet',
      'Expected PermanentStreet value to be equals to permanentStreet'
    );
    expect(await addressUpdatePage.getPermanentStreetBanglaInput()).to.eq(
      'permanentStreetBangla',
      'Expected PermanentStreetBangla value to be equals to permanentStreetBangla'
    );
    expect(await addressUpdatePage.getPermanentAreaInput()).to.eq(
      'permanentArea',
      'Expected PermanentArea value to be equals to permanentArea'
    );
    expect(await addressUpdatePage.getPermanentAreaBanglaInput()).to.eq(
      'permanentAreaBangla',
      'Expected PermanentAreaBangla value to be equals to permanentAreaBangla'
    );
    expect(await addressUpdatePage.getPermanentPostCodeInput()).to.eq('5', 'Expected permanentPostCode value to be equals to 5');
    expect(await addressUpdatePage.getPermenentPostCodeBanglaInput()).to.eq(
      'permenentPostCodeBangla',
      'Expected PermenentPostCodeBangla value to be equals to permenentPostCodeBangla'
    );
    const selectedIsSame = addressUpdatePage.getIsSameInput();
    if (await selectedIsSame.isSelected()) {
      await addressUpdatePage.getIsSameInput().click();
      expect(await addressUpdatePage.getIsSameInput().isSelected(), 'Expected isSame not to be selected').to.be.false;
    } else {
      await addressUpdatePage.getIsSameInput().click();
      expect(await addressUpdatePage.getIsSameInput().isSelected(), 'Expected isSame to be selected').to.be.true;
    }

    await addressUpdatePage.save();
    expect(await addressUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await addressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Address', async () => {
    const nbButtonsBeforeDelete = await addressComponentsPage.countDeleteButtons();
    await addressComponentsPage.clickOnLastDeleteButton();

    addressDeleteDialog = new AddressDeleteDialog();
    expect(await addressDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Address?');
    await addressDeleteDialog.clickOnConfirmButton();

    expect(await addressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
