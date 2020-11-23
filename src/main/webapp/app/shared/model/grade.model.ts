export interface IGrade {
  id?: number;
  name?: string;
  description?: any;
}

export class Grade implements IGrade {
  constructor(public id?: number, public name?: string, public description?: any) {}
}
