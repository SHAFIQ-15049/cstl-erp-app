export interface IDivision {
  id?: number;
  name?: string;
}

export class Division implements IDivision {
  constructor(public id?: number, public name?: string) {}
}
