import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IFestivalAllowancePayment } from 'app/shared/model/festival-allowance-payment.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

export interface IFestivalAllowancePaymentDtl {
  id?: number;
  amount?: number;
  status?: SalaryExecutionStatus;
  executedOn?: Moment;
  executedBy?: string;
  note?: any;
  employee?: IEmployee;
  festivalAllowancePayment?: IFestivalAllowancePayment;
}

export class FestivalAllowancePaymentDtl implements IFestivalAllowancePaymentDtl {
  constructor(
    public id?: number,
    public amount?: number,
    public status?: SalaryExecutionStatus,
    public executedOn?: Moment,
    public executedBy?: string,
    public note?: any,
    public employee?: IEmployee,
    public festivalAllowancePayment?: IFestivalAllowancePayment
  ) {}
}
