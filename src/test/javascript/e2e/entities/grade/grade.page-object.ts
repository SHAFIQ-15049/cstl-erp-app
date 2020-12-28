import { element, by, ElementFinder } from 'protractor';

export class GradeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-grade div table .btn-danger'));
  title = element.all(by.css('jhi-grade div h2#page-heading span')).first();
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

export class GradeUpdatePage {
  pageTitle = element(by.id('jhi-grade-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  categorySelect = element(by.id('field_category'));
  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  initialSalaryInput = element(by.id('field_initialSalary'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setCategorySelect(category: string): Promise<void> {
    await this.categorySelect.sendKeys(category);
  }

  async getCategorySelect(): Promise<string> {
    return await this.categorySelect.element(by.css('option:checked')).getText();
  }

  async categorySelectLastOption(): Promise<void> {
    await this.categorySelect.all(by.tagName('option')).last().click();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setInitialSalaryInput(initialSalary: string): Promise<void> {
    await this.initialSalaryInput.sendKeys(initialSalary);
  }

  async getInitialSalaryInput(): Promise<string> {
    return await this.initialSalaryInput.getAttribute('value');
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

export class GradeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-grade-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-grade'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
