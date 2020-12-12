import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IJobHistory {
  id?: number;
  serial?: number;
  organization?: string;
  designation?: string;
  from?: Moment;
  to?: Moment;
  payScale?: number;
  totalExperience?: number;
  employee?: IEmployee;
}

export class JobHistory implements IJobHistory {
  constructor(
    public id?: number,
    public serial?: number,
    public organization?: string,
    public designation?: string,
    public from?: Moment,
    public to?: Moment,
    public payScale?: number,
    public totalExperience?: number,
    public employee?: IEmployee
  ) {}
}
