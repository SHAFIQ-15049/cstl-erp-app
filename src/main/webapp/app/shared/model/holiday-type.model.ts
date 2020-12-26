export interface IHolidayType {
  id?: number;
  name?: string;
}

export class HolidayType implements IHolidayType {
  constructor(public id?: number, public name?: string) {}
}
