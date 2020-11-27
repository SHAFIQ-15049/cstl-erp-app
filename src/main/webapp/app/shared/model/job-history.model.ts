import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IJobHistory {
  id?: number;
  serial?: number;
  organization?: string;
  from?: Moment;
  to?: Moment;
  total?: number;
  employee?: IEmployee;
}

export class JobHistory implements IJobHistory {
  constructor(
    public id?: number,
    public serial?: number,
    public organization?: string,
    public from?: Moment,
    public to?: Moment,
    public total?: number,
    public employee?: IEmployee
  ) {}
}
