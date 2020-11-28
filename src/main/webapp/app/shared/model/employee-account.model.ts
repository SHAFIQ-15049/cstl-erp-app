import { IEmployee } from 'app/shared/model/employee.model';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

export interface IEmployeeAccount {
  id?: number;
  accountType?: AccountType;
  accountNo?: string;
  isSalaryAccount?: boolean;
  employee?: IEmployee;
}

export class EmployeeAccount implements IEmployeeAccount {
  constructor(
    public id?: number,
    public accountType?: AccountType,
    public accountNo?: string,
    public isSalaryAccount?: boolean,
    public employee?: IEmployee
  ) {
    this.isSalaryAccount = this.isSalaryAccount || false;
  }
}
