import { Moment } from 'moment';
import { IHolidayType } from 'app/shared/model/holiday-type.model';

export interface IHoliday {
  id?: number;
  from?: Moment;
  to?: Moment;
  totalDays?: number;
  holidayType?: IHolidayType;
}

export class Holiday implements IHoliday {
  constructor(public id?: number, public from?: Moment, public to?: Moment, public totalDays?: number, public holidayType?: IHolidayType) {}
}
