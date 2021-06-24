import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VehicleComponentsPage, VehicleDeleteDialog, VehicleUpdatePage } from './vehicle.page-object';

const expect = chai.expect;

describe('Vehicle e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vehicleComponentsPage: VehicleComponentsPage;
  let vehicleUpdatePage: VehicleUpdatePage;
  let vehicleDeleteDialog: VehicleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Vehicles', async () => {
    await navBarPage.goToEntity('vehicle');
    vehicleComponentsPage = new VehicleComponentsPage();
    await browser.wait(ec.visibilityOf(vehicleComponentsPage.title), 5000);
    expect(await vehicleComponentsPage.getTitle()).to.eq('Vehicles');
    await browser.wait(ec.or(ec.visibilityOf(vehicleComponentsPage.entities), ec.visibilityOf(vehicleComponentsPage.noResult)), 1000);
  });

  it('should load create Vehicle page', async () => {
    await vehicleComponentsPage.clickOnCreateButton();
    vehicleUpdatePage = new VehicleUpdatePage();
    expect(await vehicleUpdatePage.getPageTitle()).to.eq('Create or edit a Vehicle');
    await vehicleUpdatePage.cancel();
  });

  it('should create and save Vehicles', async () => {
    const nbButtonsBeforeCreate = await vehicleComponentsPage.countDeleteButtons();

    await vehicleComponentsPage.clickOnCreateButton();

    await promise.all([
      vehicleUpdatePage.setNameInput('name'),
      vehicleUpdatePage.setVehicleIdInput('vehicleId'),
      vehicleUpdatePage.typeSelectLastOption(),
      vehicleUpdatePage.setClassOfVehicleInput('classOfVehicle'),
      vehicleUpdatePage.setTypeOfBodyInput('typeOfBody'),
      vehicleUpdatePage.colourSelectLastOption(),
      vehicleUpdatePage.numberOfCylindersSelectLastOption(),
      vehicleUpdatePage.setEngineNumberInput('engineNumber'),
      vehicleUpdatePage.setHorsePowerInput('5'),
      vehicleUpdatePage.setCubicCapacityInput('5'),
      vehicleUpdatePage.setNoOfStandeeInput('noOfStandee'),
      vehicleUpdatePage.setUnladenWeightInput('5'),
      vehicleUpdatePage.setPrevRegnNoInput('prevRegnNo'),
      vehicleUpdatePage.setMakersNameInput('makersName'),
      vehicleUpdatePage.setMakersCountryInput('makersCountry'),
      vehicleUpdatePage.setYearsOfManufactureInput('5'),
      vehicleUpdatePage.setChassisNumberInput('chassisNumber'),
      vehicleUpdatePage.fuelUsedSelectLastOption(),
      vehicleUpdatePage.setRpmInput('5'),
      vehicleUpdatePage.setSeatsInput('5'),
      vehicleUpdatePage.setWheelBaseInput('5'),
      vehicleUpdatePage.setMaxLadenInput('5'),
      vehicleUpdatePage.customerSelectLastOption(),
    ]);

    expect(await vehicleUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await vehicleUpdatePage.getVehicleIdInput()).to.eq('vehicleId', 'Expected VehicleId value to be equals to vehicleId');
    expect(await vehicleUpdatePage.getClassOfVehicleInput()).to.eq(
      'classOfVehicle',
      'Expected ClassOfVehicle value to be equals to classOfVehicle'
    );
    expect(await vehicleUpdatePage.getTypeOfBodyInput()).to.eq('typeOfBody', 'Expected TypeOfBody value to be equals to typeOfBody');
    expect(await vehicleUpdatePage.getEngineNumberInput()).to.eq(
      'engineNumber',
      'Expected EngineNumber value to be equals to engineNumber'
    );
    expect(await vehicleUpdatePage.getHorsePowerInput()).to.eq('5', 'Expected horsePower value to be equals to 5');
    expect(await vehicleUpdatePage.getCubicCapacityInput()).to.eq('5', 'Expected cubicCapacity value to be equals to 5');
    expect(await vehicleUpdatePage.getNoOfStandeeInput()).to.eq('noOfStandee', 'Expected NoOfStandee value to be equals to noOfStandee');
    expect(await vehicleUpdatePage.getUnladenWeightInput()).to.eq('5', 'Expected unladenWeight value to be equals to 5');
    expect(await vehicleUpdatePage.getPrevRegnNoInput()).to.eq('prevRegnNo', 'Expected PrevRegnNo value to be equals to prevRegnNo');
    expect(await vehicleUpdatePage.getMakersNameInput()).to.eq('makersName', 'Expected MakersName value to be equals to makersName');
    expect(await vehicleUpdatePage.getMakersCountryInput()).to.eq(
      'makersCountry',
      'Expected MakersCountry value to be equals to makersCountry'
    );
    expect(await vehicleUpdatePage.getYearsOfManufactureInput()).to.eq('5', 'Expected yearsOfManufacture value to be equals to 5');
    expect(await vehicleUpdatePage.getChassisNumberInput()).to.eq(
      'chassisNumber',
      'Expected ChassisNumber value to be equals to chassisNumber'
    );
    expect(await vehicleUpdatePage.getRpmInput()).to.eq('5', 'Expected rpm value to be equals to 5');
    expect(await vehicleUpdatePage.getSeatsInput()).to.eq('5', 'Expected seats value to be equals to 5');
    expect(await vehicleUpdatePage.getWheelBaseInput()).to.eq('5', 'Expected wheelBase value to be equals to 5');
    expect(await vehicleUpdatePage.getMaxLadenInput()).to.eq('5', 'Expected maxLaden value to be equals to 5');

    await vehicleUpdatePage.save();
    expect(await vehicleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await vehicleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Vehicle', async () => {
    const nbButtonsBeforeDelete = await vehicleComponentsPage.countDeleteButtons();
    await vehicleComponentsPage.clickOnLastDeleteButton();

    vehicleDeleteDialog = new VehicleDeleteDialog();
    expect(await vehicleDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Vehicle?');
    await vehicleDeleteDialog.clickOnConfirmButton();

    expect(await vehicleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
