import { Moment } from 'moment';
import { IFestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

export interface IFestivalAllowancePaymentDtl {
  id?: number;
  amount?: number;
  status?: SalaryExecutionStatus;
  executedOn?: Moment;
  executedBy?: Moment;
  note?: any;
  festivalAllowancePayment?: IFestivalAllowancePayment;
  employee?: IEmployee;
}

export class FestivalAllowancePaymentDtl implements IFestivalAllowancePaymentDtl {
  constructor(
    public id?: number,
    public amount?: number,
    public status?: SalaryExecutionStatus,
    public executedOn?: Moment,
    public executedBy?: Moment,
    public note?: any,
    public festivalAllowancePayment?: IFestivalAllowancePayment,
    public employee?: IEmployee
  ) {}
}
