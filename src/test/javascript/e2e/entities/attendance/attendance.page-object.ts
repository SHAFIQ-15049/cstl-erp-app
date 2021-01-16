import { by, element } from 'protractor';

export class AttendanceComponentsPage {
  title = element.all(by.css('jhi-attendance div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}
