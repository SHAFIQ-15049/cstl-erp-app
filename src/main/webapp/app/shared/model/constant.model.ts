export interface IConstant {
  id?: number;
  name?: string;
}

export class Constant implements IConstant {
  constructor(public id?: number, public name?: string) {}
}
