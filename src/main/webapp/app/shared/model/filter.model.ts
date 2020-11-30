export interface IFilter {
  key?: any;
  value?: any;
}

export class Filter implements IFilter {
  constructor(public key?: any, public value?: any) {}
}
