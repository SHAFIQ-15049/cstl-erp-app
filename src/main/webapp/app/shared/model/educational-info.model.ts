import { IEmployee } from 'app/shared/model/employee.model';

export interface IEducationalInfo {
  id?: number;
  serial?: number;
  degree?: string;
  institution?: string;
  passingYear?: number;
  courseDuration?: number;
  attachmentContentType?: string;
  attachment?: any;
  employee?: IEmployee;
}

export class EducationalInfo implements IEducationalInfo {
  constructor(
    public id?: number,
    public serial?: number,
    public degree?: string,
    public institution?: string,
    public passingYear?: number,
    public courseDuration?: number,
    public attachmentContentType?: string,
    public attachment?: any,
    public employee?: IEmployee
  ) {}
}
