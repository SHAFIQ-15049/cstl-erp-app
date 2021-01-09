import { IFine } from 'app/shared/model/fine.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';

export interface IFinePaymentHistory {
  id?: number;
  year?: number;
  monthType?: MonthType;
  amount?: number;
  beforeFine?: number;
  afterFine?: number;
  fine?: IFine;
}

export class FinePaymentHistory implements IFinePaymentHistory {
  constructor(
    public id?: number,
    public year?: number,
    public monthType?: MonthType,
    public amount?: number,
    public beforeFine?: number,
    public afterFine?: number,
    public fine?: IFine
  ) {}
}
