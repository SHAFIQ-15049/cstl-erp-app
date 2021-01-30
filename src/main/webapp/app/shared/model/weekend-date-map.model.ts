import { Moment } from 'moment';
import { WeekDay } from 'app/shared/model/enumerations/week-day.model';

export interface IWeekendDateMap {
  serialNo?: number;
  weekendDate?: Moment;
  weekendId?: number;
  weekendDay?: WeekDay;
}

export class WeekendDateMap implements IWeekendDateMap {
  constructor(public serialNo?: number, public weekendDate?: Moment, public weekendId?: number, public weekendDay?: WeekDay) {}
}
