import { ActiveStatus } from 'app/shared/model/enumerations/active-status.model';

export interface IDefaultAllowance {
  id?: number;
  basic?: number;
  basicPercent?: number;
  totalAllowance?: number;
  medicalAllowance?: number;
  medicalAllowancePercent?: number;
  convinceAllowance?: number;
  convinceAllowancePercent?: number;
  foodAllowance?: number;
  foodAllowancePercent?: number;
  festivalAllowance?: number;
  festivalAllowancePercent?: number;
  status?: ActiveStatus;
}

export class DefaultAllowance implements IDefaultAllowance {
  constructor(
    public id?: number,
    public basic?: number,
    public basicPercent?: number,
    public totalAllowance?: number,
    public medicalAllowance?: number,
    public medicalAllowancePercent?: number,
    public convinceAllowance?: number,
    public convinceAllowancePercent?: number,
    public foodAllowance?: number,
    public foodAllowancePercent?: number,
    public festivalAllowance?: number,
    public festivalAllowancePercent?: number,
    public status?: ActiveStatus
  ) {}
}
