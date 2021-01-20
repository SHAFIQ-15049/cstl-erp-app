import { by, element, ElementFinder } from 'protractor';

export class LeaveApplicationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-leave-application div table .btn-danger'));
  title = element.all(by.css('jhi-leave-application div h2#page-heading span')).first();
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

export class LeaveApplicationUpdatePage {
  pageTitle = element(by.id('jhi-leave-application-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromInput = element(by.id('field_from'));
  toInput = element(by.id('field_to'));
  totalDaysInput = element(by.id('field_totalDays'));
  statusSelect = element(by.id('field_status'));
  reasonInput = element(by.id('field_reason'));

  appliedBySelect = element(by.id('field_appliedBy'));
  actionTakenBySelect = element(by.id('field_actionTakenBy'));
  leaveTypeSelect = element(by.id('field_leaveType'));
  applicantSelect = element(by.id('field_applicant'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setFromInput(from: string): Promise<void> {
    await this.fromInput.sendKeys(from);
  }

  async getFromInput(): Promise<string> {
    return await this.fromInput.getAttribute('value');
  }

  async setToInput(to: string): Promise<void> {
    await this.toInput.sendKeys(to);
  }

  async getToInput(): Promise<string> {
    return await this.toInput.getAttribute('value');
  }

  async setTotalDaysInput(totalDays: string): Promise<void> {
    await this.totalDaysInput.sendKeys(totalDays);
  }

  async getTotalDaysInput(): Promise<string> {
    return await this.totalDaysInput.getAttribute('value');
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

  async setReasonInput(reason: string): Promise<void> {
    await this.reasonInput.sendKeys(reason);
  }

  async getReasonInput(): Promise<string> {
    return await this.reasonInput.getAttribute('value');
  }

  async appliedBySelectLastOption(): Promise<void> {
    await this.appliedBySelect.all(by.tagName('option')).last().click();
  }

  async appliedBySelectOption(option: string): Promise<void> {
    await this.appliedBySelect.sendKeys(option);
  }

  getAppliedBySelect(): ElementFinder {
    return this.appliedBySelect;
  }

  async getAppliedBySelectedOption(): Promise<string> {
    return await this.appliedBySelect.element(by.css('option:checked')).getText();
  }

  async actionTakenBySelectLastOption(): Promise<void> {
    await this.actionTakenBySelect.all(by.tagName('option')).last().click();
  }

  async actionTakenBySelectOption(option: string): Promise<void> {
    await this.actionTakenBySelect.sendKeys(option);
  }

  getActionTakenBySelect(): ElementFinder {
    return this.actionTakenBySelect;
  }

  async getActionTakenBySelectedOption(): Promise<string> {
    return await this.actionTakenBySelect.element(by.css('option:checked')).getText();
  }

  async leaveTypeSelectLastOption(): Promise<void> {
    await this.leaveTypeSelect.all(by.tagName('option')).last().click();
  }

  async leaveTypeSelectOption(option: string): Promise<void> {
    await this.leaveTypeSelect.sendKeys(option);
  }

  getLeaveTypeSelect(): ElementFinder {
    return this.leaveTypeSelect;
  }

  async getLeaveTypeSelectedOption(): Promise<string> {
    return await this.leaveTypeSelect.element(by.css('option:checked')).getText();
  }

  async applicantSelectLastOption(): Promise<void> {
    await this.applicantSelect.all(by.tagName('option')).last().click();
  }

  async applicantSelectOption(option: string): Promise<void> {
    await this.applicantSelect.sendKeys(option);
  }

  getApplicantSelect(): ElementFinder {
    return this.applicantSelect;
  }

  async getApplicantSelectedOption(): Promise<string> {
    return await this.applicantSelect.element(by.css('option:checked')).getText();
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

export class LeaveApplicationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-leaveApplication-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-leaveApplication'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
