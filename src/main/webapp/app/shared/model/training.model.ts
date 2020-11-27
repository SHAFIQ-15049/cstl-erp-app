import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';

export interface ITraining {
  id?: number;
  serial?: number;
  name?: string;
  trainingInstitute?: string;
  receivedOn?: Moment;
  employee?: IEmployee;
}

export class Training implements ITraining {
  constructor(
    public id?: number,
    public serial?: number,
    public name?: string,
    public trainingInstitute?: string,
    public receivedOn?: Moment,
    public employee?: IEmployee
  ) {}
}
