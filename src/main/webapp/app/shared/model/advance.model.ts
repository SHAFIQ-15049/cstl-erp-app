import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IAdvance {
  id?: number;
  paidOn?: Moment;
  reason?: any;
  amount?: number;
  paymentPercentage?: number;
  paymentStatus?: PaymentStatus;
  employee?: IEmployee;
}

export class Advance implements IAdvance {
  constructor(
    public id?: number,
    public paidOn?: Moment,
    public reason?: any,
    public amount?: number,
    public paymentPercentage?: number,
    public paymentStatus?: PaymentStatus,
    public employee?: IEmployee
  ) {}
}
