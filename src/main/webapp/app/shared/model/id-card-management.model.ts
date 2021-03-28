import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IIdCardManagement {
  id?: number;
  cardNo?: string;
  issueDate?: Moment;
  ticketNo?: string;
  validTill?: Moment;
  employee?: IEmployee;
}

export class IdCardManagement implements IIdCardManagement {
  constructor(
    public id?: number,
    public cardNo?: string,
    public issueDate?: Moment,
    public ticketNo?: string,
    public validTill?: Moment,
    public employee?: IEmployee
  ) {}
}
