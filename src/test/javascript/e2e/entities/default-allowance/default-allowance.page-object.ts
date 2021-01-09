import { element, by, ElementFinder } from 'protractor';

export class DefaultAllowanceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-default-allowance div table .btn-danger'));
  title = element.all(by.css('jhi-default-allowance div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class DefaultAllowanceUpdatePage {
  pageTitle = element(by.id('jhi-default-allowance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  basicInput = element(by.id('field_basic'));
  basicPercentInput = element(by.id('field_basicPercent'));
  totalAllowanceInput = element(by.id('field_totalAllowance'));
  medicalAllowanceInput = element(by.id('field_medicalAllowance'));
  medicalAllowancePercentInput = element(by.id('field_medicalAllowancePercent'));
  convinceAllowanceInput = element(by.id('field_convinceAllowance'));
  convinceAllowancePercentInput = element(by.id('field_convinceAllowancePercent'));
  foodAllowanceInput = element(by.id('field_foodAllowance'));
  foodAllowancePercentInput = element(by.id('field_foodAllowancePercent'));
  festivalAllowanceInput = element(by.id('field_festivalAllowance'));
  festivalAllowancePercentInput = element(by.id('field_festivalAllowancePercent'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setBasicInput(basic: string): Promise<void> {
    await this.basicInput.sendKeys(basic);
  }

  async getBasicInput(): Promise<string> {
    return await this.basicInput.getAttribute('value');
  }

  async setBasicPercentInput(basicPercent: string): Promise<void> {
    await this.basicPercentInput.sendKeys(basicPercent);
  }

  async getBasicPercentInput(): Promise<string> {
    return await this.basicPercentInput.getAttribute('value');
  }

  async setTotalAllowanceInput(totalAllowance: string): Promise<void> {
    await this.totalAllowanceInput.sendKeys(totalAllowance);
  }

  async getTotalAllowanceInput(): Promise<string> {
    return await this.totalAllowanceInput.getAttribute('value');
  }

  async setMedicalAllowanceInput(medicalAllowance: string): Promise<void> {
    await this.medicalAllowanceInput.sendKeys(medicalAllowance);
  }

  async getMedicalAllowanceInput(): Promise<string> {
    return await this.medicalAllowanceInput.getAttribute('value');
  }

  async setMedicalAllowancePercentInput(medicalAllowancePercent: string): Promise<void> {
    await this.medicalAllowancePercentInput.sendKeys(medicalAllowancePercent);
  }

  async getMedicalAllowancePercentInput(): Promise<string> {
    return await this.medicalAllowancePercentInput.getAttribute('value');
  }

  async setConvinceAllowanceInput(convinceAllowance: string): Promise<void> {
    await this.convinceAllowanceInput.sendKeys(convinceAllowance);
  }

  async getConvinceAllowanceInput(): Promise<string> {
    return await this.convinceAllowanceInput.getAttribute('value');
  }

  async setConvinceAllowancePercentInput(convinceAllowancePercent: string): Promise<void> {
    await this.convinceAllowancePercentInput.sendKeys(convinceAllowancePercent);
  }

  async getConvinceAllowancePercentInput(): Promise<string> {
    return await this.convinceAllowancePercentInput.getAttribute('value');
  }

  async setFoodAllowanceInput(foodAllowance: string): Promise<void> {
    await this.foodAllowanceInput.sendKeys(foodAllowance);
  }

  async getFoodAllowanceInput(): Promise<string> {
    return await this.foodAllowanceInput.getAttribute('value');
  }

  async setFoodAllowancePercentInput(foodAllowancePercent: string): Promise<void> {
    await this.foodAllowancePercentInput.sendKeys(foodAllowancePercent);
  }

  async getFoodAllowancePercentInput(): Promise<string> {
    return await this.foodAllowancePercentInput.getAttribute('value');
  }

  async setFestivalAllowanceInput(festivalAllowance: string): Promise<void> {
    await this.festivalAllowanceInput.sendKeys(festivalAllowance);
  }

  async getFestivalAllowanceInput(): Promise<string> {
    return await this.festivalAllowanceInput.getAttribute('value');
  }

  async setFestivalAllowancePercentInput(festivalAllowancePercent: string): Promise<void> {
    await this.festivalAllowancePercentInput.sendKeys(festivalAllowancePercent);
  }

  async getFestivalAllowancePercentInput(): Promise<string> {
    return await this.festivalAllowancePercentInput.getAttribute('value');
  }

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DefaultAllowanceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-defaultAllowance-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-defaultAllowance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
