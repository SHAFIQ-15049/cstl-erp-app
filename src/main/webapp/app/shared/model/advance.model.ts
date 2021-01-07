import { Moment } from 'moment';
import { IAdvancePaymentHistory } from 'app/shared/model/advance-payment-history.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IAdvance {
  id?: number;
  providedOn?: Moment;
  reason?: any;
  amount?: number;
  paymentPercentage?: number;
  monthlyPaymentAmount?: number;
  paymentStatus?: PaymentStatus;
  advancePaymentHistories?: IAdvancePaymentHistory[];
  employee?: IEmployee;
}

export class Advance implements IAdvance {
  constructor(
    public id?: number,
    public providedOn?: Moment,
    public reason?: any,
    public amount?: number,
    public paymentPercentage?: number,
    public monthlyPaymentAmount?: number,
    public paymentStatus?: PaymentStatus,
    public advancePaymentHistories?: IAdvancePaymentHistory[],
    public employee?: IEmployee
  ) {}
}
