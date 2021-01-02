import { MonthType } from 'app/shared/model/enumerations/month-type.model';

export interface IFestivalAllowanceTimeLine {
  id?: number;
  year?: number;
  month?: MonthType;
}

export class FestivalAllowanceTimeLine implements IFestivalAllowanceTimeLine {
  constructor(public id?: number, public year?: number, public month?: MonthType) {}
}
