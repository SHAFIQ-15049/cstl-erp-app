import { Moment } from 'moment';
import { IFinePaymentHistory } from 'app/shared/model/fine-payment-history.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IFine {
  id?: number;
  finedOn?: Moment;
  reason?: any;
  amount?: number;
  finePercentage?: number;
  monthlyFineAmount?: number;
  paymentStatus?: PaymentStatus;
  amountPaid?: number;
  amountLeft?: number;
  finePaymentHistories?: IFinePaymentHistory[];
  employee?: IEmployee;
}

export class Fine implements IFine {
  constructor(
    public id?: number,
    public finedOn?: Moment,
    public reason?: any,
    public amount?: number,
    public finePercentage?: number,
    public monthlyFineAmount?: number,
    public paymentStatus?: PaymentStatus,
    public amountPaid?: number,
    public amountLeft?: number,
    public finePaymentHistories?: IFinePaymentHistory[],
    public employee?: IEmployee
  ) {}
}
