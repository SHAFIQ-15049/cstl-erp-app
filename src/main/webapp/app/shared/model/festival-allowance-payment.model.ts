import { Moment } from 'moment';
import { IFestivalAllowancePaymentDtl } from 'app/shared/model/festival-allowance-payment-dtl.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';
import { SalaryExecutionStatus } from 'app/shared/model/enumerations/salary-execution-status.model';

export interface IFestivalAllowancePayment {
  id?: number;
  year?: number;
  month?: MonthType;
  status?: SalaryExecutionStatus;
  executedOn?: Moment;
  executedBy?: string;
  festivalAllowancePaymentDtls?: IFestivalAllowancePaymentDtl[];
  designation?: IDesignation;
}

export class FestivalAllowancePayment implements IFestivalAllowancePayment {
  constructor(
    public id?: number,
    public year?: number,
    public month?: MonthType,
    public status?: SalaryExecutionStatus,
    public executedOn?: Moment,
    public executedBy?: string,
    public festivalAllowancePaymentDtls?: IFestivalAllowancePaymentDtl[],
    public designation?: IDesignation
  ) {}
}
