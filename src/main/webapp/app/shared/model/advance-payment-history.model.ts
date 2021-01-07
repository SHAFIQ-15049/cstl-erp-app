import { IAdvance } from 'app/shared/model/advance.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';

export interface IAdvancePaymentHistory {
  id?: number;
  year?: number;
  monthType?: MonthType;
  amount?: number;
  before?: number;
  after?: number;
  advance?: IAdvance;
}

export class AdvancePaymentHistory implements IAdvancePaymentHistory {
  constructor(
    public id?: number,
    public year?: number,
    public monthType?: MonthType,
    public amount?: number,
    public before?: number,
    public after?: number,
    public advance?: IAdvance
  ) {}
}
