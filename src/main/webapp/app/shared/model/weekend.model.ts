import { WeekDay } from 'app/shared/model/enumerations/week-day.model';
import { WeekendStatus } from 'app/shared/model/enumerations/weekend-status.model';

export interface IWeekend {
  id?: number;
  day?: WeekDay;
  status?: WeekendStatus;
}

export class Weekend implements IWeekend {
  constructor(public id?: number, public day?: WeekDay, public status?: WeekendStatus) {}
}
