export interface IDivision {
  id?: number;
  name?: string;
  bangla?: string;
}

export class Division implements IDivision {
  constructor(public id?: number, public name?: string, public bangla?: string) {}
}
