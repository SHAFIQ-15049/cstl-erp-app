import { by, element, ElementFinder } from 'protractor';

export class AttendanceDataUploadComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-attendance-data-upload div table .btn-danger'));
  title = element.all(by.css('jhi-attendance-data-upload div h2#page-heading span')).first();
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

export class AttendanceDataUploadUpdatePage {
  pageTitle = element(by.id('jhi-attendance-data-upload-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fileUploadInput = element(by.id('file_fileUpload'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setFileUploadInput(fileUpload: string): Promise<void> {
    await this.fileUploadInput.sendKeys(fileUpload);
  }

  async getFileUploadInput(): Promise<string> {
    return await this.fileUploadInput.getAttribute('value');
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

export class AttendanceDataUploadDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-attendanceDataUpload-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-attendanceDataUpload'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
